package com.miportafolio.dao;

import com.miportafolio.models.Venta;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class VentaDAO {

    // Manejo de transacciones (Venta y Reducción de Stock)
    public boolean procesarVenta(Venta venta) throws Exception {
        Connection con = null;
        try {
            con = ConexionDB.obtenerConexion();
            con.setAutoCommit(false); // Iniciar Transacción

            // 1. Insertar Venta
            String sqlVenta = "INSERT INTO Ventas (id_producto, cantidad, total) VALUES (?, ?, ?)";
            try (PreparedStatement psVenta = con.prepareStatement(sqlVenta)) {
                psVenta.setInt(1, venta.getIdProducto());
                psVenta.setInt(2, venta.getCantidad());
                psVenta.setDouble(3, venta.getTotal());
                psVenta.executeUpdate();
            }

            // 2. Descontar Stock
            String sqlStock = "UPDATE Productos SET stock = stock - ? WHERE id = ?";
            try (PreparedStatement psStock = con.prepareStatement(sqlStock)) {
                psStock.setInt(1, venta.getCantidad());
                psStock.setInt(2, venta.getIdProducto());
                psStock.executeUpdate();
            }

            con.commit(); // Confirmar Transacción
            return true;
        } catch (Exception ex) {
            if (con != null) con.rollback(); // Revertir si hay error
            throw ex;
        } finally {
            if (con != null) con.setAutoCommit(true);
            if (con != null) con.close();
        }
    }
}
