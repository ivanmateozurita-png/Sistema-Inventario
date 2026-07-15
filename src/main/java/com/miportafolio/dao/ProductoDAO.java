package com.miportafolio.dao;

import com.miportafolio.models.Producto;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ProductoDAO {

    public List<Producto> listarTodos() throws Exception {
        List<Producto> lista = new ArrayList<>();
        String sql = "SELECT * FROM Productos";
        
        try (Connection con = ConexionDB.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                lista.add(new Producto(
                    rs.getInt("id"),
                    rs.getString("codigo"),
                    rs.getString("nombre"),
                    rs.getDouble("precio"),
                    rs.getInt("stock")
                ));
            }
        }
        return lista;
    }

    public List<Producto> buscarPorNombre(String nombre) throws Exception {
        List<Producto> lista = new ArrayList<>();
        String sql = "SELECT * FROM Productos WHERE nombre LIKE ?";
        
        try (Connection con = ConexionDB.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setString(1, "%" + nombre + "%");
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    lista.add(new Producto(
                        rs.getInt("id"), rs.getString("codigo"), rs.getString("nombre"),
                        rs.getDouble("precio"), rs.getInt("stock")
                    ));
                }
            }
        }
        return lista;
    }

    public boolean insertar(Producto p) throws Exception {
        String sql = "INSERT INTO Productos (codigo, nombre, precio, stock) VALUES (?, ?, ?, ?)";
        try (Connection con = ConexionDB.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, p.getCodigo());
            ps.setString(2, p.getNombre());
            ps.setDouble(3, p.getPrecio());
            ps.setInt(4, p.getStock());
            return ps.executeUpdate() > 0;
        }
    }

    public boolean actualizar(Producto p) throws Exception {
        String sql = "UPDATE Productos SET codigo=?, nombre=?, precio=?, stock=? WHERE id=?";
        try (Connection con = ConexionDB.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, p.getCodigo());
            ps.setString(2, p.getNombre());
            ps.setDouble(3, p.getPrecio());
            ps.setInt(4, p.getStock());
            ps.setInt(5, p.getId());
            return ps.executeUpdate() > 0;
        }
    }

    public boolean eliminar(int id) throws Exception {
        String sql = "DELETE FROM Productos WHERE id=?";
        try (Connection con = ConexionDB.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        }
    }
}
