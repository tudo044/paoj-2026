package com.pao.proiect.magazin.repository;

import com.pao.proiect.magazin.model.Categorie;
import com.pao.proiect.magazin.util.DatabaseConnection;

import java.sql.*;
import java.util.*;

public class CategorieRepository implements Repository<Categorie, Integer> {
    private final Connection conn = DatabaseConnection.getInstance().getConnection();

    @Override
    public void save(Categorie c) {
        String sql = "INSERT INTO categorii (id, nume, descriere, cod_categorie) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, c.getId());
            ps.setString(2, c.getNume());
            ps.setString(3, c.getDescriere());
            ps.setString(4, c.getCodCategorie());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Eroare la salvarea categoriei: " + e.getMessage(), e);
        }
    }

    @Override
    public Optional<Categorie> findById(Integer id) {
        String sql = "SELECT * FROM categorii WHERE id = ?";
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
    public List<Categorie> findAll() {
        List<Categorie> rezultat = new ArrayList<>();
        String sql = "SELECT * FROM categorii";
        try (Statement st = conn.createStatement(); ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) rezultat.add(mapRow(rs));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return rezultat;
    }

    @Override
    public void update(Categorie c) {
        String sql = "UPDATE categorii SET nume=?, descriere=?, cod_categorie=? WHERE id=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, c.getNume());
            ps.setString(2, c.getDescriere());
            ps.setString(3, c.getCodCategorie());
            ps.setInt(4, c.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Eroare la actualizarea categoriei: " + e.getMessage(), e);
        }
    }

    @Override
    public void delete(Integer id) {
        String sql = "DELETE FROM categorii WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Eroare la stergerea categoriei: " + e.getMessage(), e);
        }
    }

    private Categorie mapRow(ResultSet rs) throws SQLException {
        return new Categorie(rs.getInt("id"), rs.getString("nume"), rs.getString("descriere"), rs.getString("cod_categorie"));
    }
}