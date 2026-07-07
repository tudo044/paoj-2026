package com.pao.proiect.magazin.repository;

import com.pao.proiect.magazin.exception.StocInsuficientException;
import com.pao.proiect.magazin.model.*;
import com.pao.proiect.magazin.util.DatabaseConnection;

import java.sql.*;
import java.util.*;

public class ComandaRepository implements Repository<Comanda, Integer> {
    private final Connection conn = DatabaseConnection.getInstance().getConnection();
    private final DistribuitorRepository distribuitorRepository = new DistribuitorRepository();

    @Override
    public void save(Comanda c) {
        String sql = "INSERT INTO comenzi (id, distribuitor_id, data_comanda, status) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, c.getId());
            ps.setInt(2, c.getDistribuitor().getId());
            ps.setString(3, c.getDataComanda().toString());
            ps.setString(4, c.getStatus().name());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Eroare la salvarea comenzii: " + e.getMessage(), e);
        }
    }

    @Override
    public Optional<Comanda> findById(Integer id) {
        String sql = "SELECT * FROM comenzi WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return Optional.of(mapRowHeader(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }

    @Override
    public List<Comanda> findAll() {
        List<Comanda> rezultat = new ArrayList<>();
        String sql = "SELECT * FROM comenzi";
        try (Statement st = conn.createStatement(); ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) rezultat.add(mapRowHeader(rs));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return rezultat;
    }

    @Override
    public void update(Comanda c) {
        String sql = "UPDATE comenzi SET status = ? WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, c.getStatus().name());
            ps.setInt(2, c.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Eroare la actualizarea comenzii: " + e.getMessage(), e);
        }
    }

    @Override
    public void delete(Integer id) {
        try (PreparedStatement psDetalii = conn.prepareStatement("DELETE FROM detalii_comanda WHERE comanda_id = ?");
             PreparedStatement psComanda = conn.prepareStatement("DELETE FROM comenzi WHERE id = ?")) {
            psDetalii.setInt(1, id);
            psDetalii.executeUpdate();
            psComanda.setInt(1, id);
            psComanda.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Eroare la stergerea comenzii: " + e.getMessage(), e);
        }
    }


    private Comanda mapRowHeader(ResultSet rs) throws SQLException {
        int comandaId = rs.getInt("id");
        int distribuitorId = rs.getInt("distribuitor_id");
        String status = rs.getString("status");

        Distribuitor distribuitor = distribuitorRepository.findById(distribuitorId)
                .orElseThrow(() -> new RuntimeException("Distribuitor inexistent pentru comanda #" + comandaId));

        Comanda c = new Comanda(comandaId, distribuitor);
        c.setStatus(Comanda.Status.valueOf(status));
        return c;
    }


    public void plaseazaComandaTranzactional(Comanda comanda) throws StocInsuficientException {
        try {
            conn.setAutoCommit(false);

            for (DetaliiComanda d : comanda.getDetalii()) {
                int disponibil = getStocCurent(d.getProdus().getCodProdus().getCod());
                if (disponibil < d.getCantitateComandată()) {
                    throw new StocInsuficientException(d.getProdus().getNume(), disponibil, d.getCantitateComandată());
                }
            }

            try (PreparedStatement ps = conn.prepareStatement(
                    "INSERT INTO comenzi (id, distribuitor_id, data_comanda, status) VALUES (?, ?, ?, ?)")) {
                ps.setInt(1, comanda.getId());
                ps.setInt(2, comanda.getDistribuitor().getId());
                ps.setString(3, comanda.getDataComanda().toString());
                ps.setString(4, comanda.getStatus().name());
                ps.executeUpdate();
            }

            try (PreparedStatement psDetaliu = conn.prepareStatement(
                    "INSERT INTO detalii_comanda (comanda_id, produs_cod, cantitate, pret_unitar) VALUES (?, ?, ?, ?)");
                 PreparedStatement psStoc = conn.prepareStatement(
                    "UPDATE produse SET cantitate = cantitate - ? WHERE cod = ?")) {
                for (DetaliiComanda d : comanda.getDetalii()) {
                    psDetaliu.setInt(1, comanda.getId());
                    psDetaliu.setString(2, d.getProdus().getCodProdus().getCod());
                    psDetaliu.setInt(3, d.getCantitateComandată());
                    psDetaliu.setDouble(4, d.getPretUnitar());
                    psDetaliu.executeUpdate();

                    psStoc.setInt(1, d.getCantitateComandată());
                    psStoc.setString(2, d.getProdus().getCodProdus().getCod());
                    psStoc.executeUpdate();
                }
            }

            conn.commit();
        } catch (StocInsuficientException e) {
            rollbackSilent();
            throw e;
        } catch (SQLException e) {
            rollbackSilent();
            throw new RuntimeException("Eroare JDBC la plasarea comenzii, s-a facut rollback: " + e.getMessage(), e);
        } finally {
            try { conn.setAutoCommit(true); } catch (SQLException ignored) {}
        }
    }

    private int getStocCurent(String cod) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement("SELECT cantitate FROM produse WHERE cod = ?")) {
            ps.setString(1, cod);
            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) throw new SQLException("Produsul nu exista in baza de date: " + cod);
                return rs.getInt("cantitate");
            }
        }
    }

    private void rollbackSilent() {
        try { conn.rollback(); } catch (SQLException ignored) {}
    }


    public List<String> getComenziCuDistribuitor() {
        List<String> rezultat = new ArrayList<>();
        String sql = "SELECT co.id, d.nume AS distribuitor, co.data_comanda, co.status " +
                     "FROM comenzi co JOIN distribuitori d ON co.distribuitor_id = d.id " +
                     "ORDER BY co.data_comanda DESC";
        try (Statement st = conn.createStatement(); ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                rezultat.add(String.format("Comanda #%d | Distribuitor: %-20s | Data: %s | Status: %s",
                        rs.getInt("id"), rs.getString("distribuitor"), rs.getString("data_comanda"), rs.getString("status")));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return rezultat;
    }
}