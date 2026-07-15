package com.miportafolio.dao;

import com.miportafolio.models.Usuario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UsuarioDAO {

    public Usuario autenticar(String usuario, String contrasena) throws Exception {
        String sql = "SELECT * FROM Usuarios WHERE usuario = ? AND contrasena = ?";
        try (Connection con = ConexionDB.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setString(1, usuario);
            ps.setString(2, contrasena);
            
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Usuario(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getString("usuario"),
                        rs.getString("contrasena"),
                        rs.getString("rol")
                    );
                }
            }
        }
        return null;
    }

    public boolean registrar(Usuario usuario) throws Exception {
        // Verificar duplicados primero
        String sqlCheck = "SELECT count(*) FROM Usuarios WHERE usuario = ?";
        try (Connection con = ConexionDB.obtenerConexion();
             PreparedStatement psCheck = con.prepareStatement(sqlCheck)) {
            psCheck.setString(1, usuario.getUsuario());
            try (ResultSet rs = psCheck.executeQuery()) {
                if (rs.next() && rs.getInt(1) > 0) {
                    throw new Exception("El nombre de usuario '" + usuario.getUsuario() + "' ya está registrado.");
                }
            }
        }

        String sql = "INSERT INTO Usuarios (nombre, usuario, contrasena, rol) VALUES (?, ?, ?, ?)";
        try (Connection con = ConexionDB.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setString(1, usuario.getNombre());
            ps.setString(2, usuario.getUsuario());
            ps.setString(3, usuario.getContrasena());
            ps.setString(4, usuario.getRol());
            
            return ps.executeUpdate() > 0;
        }
    }
}
