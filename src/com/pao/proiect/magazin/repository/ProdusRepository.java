package com.pao.proiect.magazin.repository;

import com.pao.proiect.magazin.model.*;
import com.pao.proiect.magazin.util.DatabaseConnection;

import java.sql.*;
import java.util.*;

public class ProdusRepository implements Repository<Produs, String> {
    private final Connection conn = DatabaseConnection.getInstance().getConnection();
    private final CategorieRepository categorieRepository = new CategorieRepository();
    private final DistribuitorRepository distribuitorRepository = new DistribuitorRepository();

    @Override
    public void save(Produs p) {
        String sql = "INSERT INTO produse (cod, nume, descriere, pret, cantitate, stoc_minim, categorie_id, distribuitor_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, p.getCodProdus().getCod());
            ps.setString(2, p.getNume());
            ps.setString(3, p.getDescriere());
            ps.setDouble(4, p.getPret());
            ps.setInt(5, p.getCantitate());
            ps.setInt(6, p.getStocMinim());
            ps.setInt(7, p.getCategorie().getId());
            if (p.getDistribuitor() != null) ps.setInt(8, p.getDistribuitor().getId());
            else ps.setNull(8, Types.INTEGER);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Eroare la salvarea produsului: " + e.getMessage(), e);
        }
    }

    @Override
    public Optional<Produs> findById(String cod) {
        String sql = "SELECT * FROM produse WHERE cod = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, cod);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return Optional.of(mapRow(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }

    @Override
    public List<Produs> findAll() {
        List<Produs> rezultat = new ArrayList<>();
        String sql = "SELECT * FROM produse";
        try (Statement st = conn.createStatement(); ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) rezultat.add(mapRow(rs));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return rezultat;
    }

    @Override
    public void update(Produs p) {
        String sql = "UPDATE produse SET nume=?, descriere=?, pret=?, cantitate=?, stoc_minim=?, categorie_id=?, distribuitor_id=? WHERE cod=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, p.getNume());
            ps.setString(2, p.getDescriere());
            ps.setDouble(3, p.getPret());
            ps.setInt(4, p.getCantitate());
            ps.setInt(5, p.getStocMinim());
            ps.setInt(6, p.getCategorie().getId());
            if (p.getDistribuitor() != null) ps.setInt(7, p.getDistribuitor().getId());
            else ps.setNull(7, Types.INTEGER);
            ps.setString(8, p.getCodProdus().getCod());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Eroare la actualizarea produsului: " + e.getMessage(), e);
        }
    }

    @Override
    public void delete(String cod) {
        String sql = "DELETE FROM produse WHERE cod = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, cod);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Eroare la stergerea produsului: " + e.getMessage(), e);
        }
    }

    private Produs mapRow(ResultSet rs) throws SQLException {
        CodProdus cod = parseCod(rs.getString("cod"));
        Categorie categorie = categorieRepository.findById(rs.getInt("categorie_id"))
                .orElseThrow(() -> new RuntimeException("Categorie inexistenta pentru produsul " + cod));
        Produs p = new Produs(cod, rs.getString("nume"), rs.getString("descriere"),
                rs.getDouble("pret"), rs.getInt("cantitate"), rs.getInt("stoc_minim"), categorie);
        int distribuitorId = rs.getInt("distribuitor_id");
        if (!rs.wasNull()) {
            distribuitorRepository.findById(distribuitorId).ifPresent(p::setDistribuitor);
        }
        return p;
    }

    private CodProdus parseCod(String codStr) {
        String[] parts = codStr.split("-");
        return new CodProdus(parts[0], Integer.parseInt(parts[1]), Integer.parseInt(parts[2]));
    }

    // ---- Interogari avansate cu JOIN ----

    public List<String> getProduseCuNumeDistribuitor() {
        List<String> rezultat = new ArrayList<>();
        String sql = "SELECT p.nume AS produs, p.cantitate, d.nume AS distribuitor " +
                     "FROM produse p JOIN distribuitori d ON p.distribuitor_id = d.id " +
                     "ORDER BY d.nume";
        try (Statement st = conn.createStatement(); ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                rezultat.add(String.format("%-20s stoc: %-5d distribuitor: %s",
                        rs.getString("produs"), rs.getInt("cantitate"), rs.getString("distribuitor")));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return rezultat;
    }

    public List<String> getTopProduseVandute(int n) {
        List<String> rezultat = new ArrayList<>();
        String sql = "SELECT p.nume AS produs, c.nume AS categorie, SUM(dc.cantitate) AS total_vandut " +
                     "FROM detalii_comanda dc " +
                     "JOIN produse p ON dc.produs_cod = p.cod " +
                     "JOIN categorii c ON p.categorie_id = c.id " +
                     "GROUP BY p.cod " +
                     "ORDER BY total_vandut DESC " +
                     "LIMIT ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, n);
            try (ResultSet rs = ps.executeQuery()) {
                int loc = 1;
                while (rs.next()) {
                    rezultat.add(String.format("%d. %-20s categorie: %-12s total vandut: %d",
                            loc++, rs.getString("produs"), rs.getString("categorie"), rs.getInt("total_vandut")));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return rezultat;
    }
}