package com.pao.proiect.magazin.repository;

import com.pao.proiect.magazin.model.Distribuitor;
import com.pao.proiect.magazin.util.DatabaseConnection;

import java.sql.*;
import java.util.*;

public class DistribuitorRepository implements Repository<Distribuitor, Integer> {
    private final Connection conn = DatabaseConnection.getInstance().getConnection();

    @Override
    public void save(Distribuitor d) {
        String sql = "INSERT INTO distribuitori (id, nume, email, telefon, adresa, cui) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, d.getId());
            ps.setString(2, d.getNume());
            ps.setString(3, d.getEmail());
            ps.setString(4, d.getTelefon());
            ps.setString(5, d.getAdresa());
            ps.setString(6, d.getCui());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Eroare la salvarea distribuitorului: " + e.getMessage(), e);
        }
    }

    @Override
    public Optional<Distribuitor> findById(Integer id) {
        String sql = "SELECT * FROM distribuitori WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return Optional.of(mapRow(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }

    @Override
    public List<Distribuitor> findAll() {
        List<Distribuitor> rezultat = new ArrayList<>();
        String sql = "SELECT * FROM distribuitori";
        try (Statement st = conn.createStatement(); ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) rezultat.add(mapRow(rs));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return rezultat;
    }

    @Override
    public void update(Distribuitor d) {
        String sql = "UPDATE distribuitori SET nume=?, email=?, telefon=?, adresa=?, cui=? WHERE id=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, d.getNume());
            ps.setString(2, d.getEmail());
            ps.setString(3, d.getTelefon());
            ps.setString(4, d.getAdresa());
            ps.setString(5, d.getCui());
            ps.setInt(6, d.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Eroare la actualizarea distribuitorului: " + e.getMessage(), e);
        }
    }

    @Override
    public void delete(Integer id) {
        String sql = "DELETE FROM distribuitori WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Eroare la stergerea distribuitorului: " + e.getMessage(), e);
        }
    }

    private Distribuitor mapRow(ResultSet rs) throws SQLException {
        return new Distribuitor(rs.getInt("id"), rs.getString("nume"), rs.getString("email"),
                rs.getString("telefon"), rs.getString("adresa"), rs.getString("cui"));
    }
}