package com.miportafolio.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.io.File;

public class ConexionDB {
    
    // SQLite creará este archivo automáticamente en la raíz del proyecto
    private static final String URL = "jdbc:sqlite:sistema_inventario.db";

    public static Connection obtenerConexion() throws SQLException {
        try {
            // Cargar el driver de SQLite
            Class.forName("org.sqlite.JDBC");
            Connection conn = DriverManager.getConnection(URL);
            
            // Inicializar las tablas automáticamente si es la primera vez que se ejecuta
            inicializarTablas(conn);
            
            return conn;
        } catch (ClassNotFoundException e) {
            throw new SQLException("Driver de SQLite no encontrado.");
        }
    }

    private static void inicializarTablas(Connection conn) {
        String sqlUsuarios = "CREATE TABLE IF NOT EXISTS Usuarios ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "nombre TEXT NOT NULL, "
                + "usuario TEXT NOT NULL UNIQUE, "
                + "contrasena TEXT NOT NULL, "
                + "rol TEXT NOT NULL);";

        String sqlProductos = "CREATE TABLE IF NOT EXISTS Productos ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "codigo TEXT NOT NULL UNIQUE, "
                + "nombre TEXT NOT NULL, "
                + "precio REAL NOT NULL, "
                + "stock INTEGER NOT NULL);";

        String sqlVentas = "CREATE TABLE IF NOT EXISTS Ventas ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "id_producto INTEGER NOT NULL, "
                + "cantidad INTEGER NOT NULL, "
                + "total REAL NOT NULL, "
                + "fecha DATETIME DEFAULT CURRENT_TIMESTAMP, "
                + "FOREIGN KEY (id_producto) REFERENCES Productos(id));";

        String sqlInsert = "INSERT OR IGNORE INTO Productos (codigo, nombre, precio, stock) VALUES "
                + "('SKU-100', 'Laptop Gamer Asus ROG', 1200.50, 10), "
                + "('SKU-101', 'Monitor Ultrawide LG 34\"', 450.00, 15), "
                + "('SKU-102', 'Teclado Mecanico Redragon', 45.99, 50), "
                + "('SKU-103', 'Raton Logi MX Master 3', 99.90, 30), "
                + "('SKU-104', 'Auriculares HyperX Cloud', 75.50, 20);";

        try (Statement stmt = conn.createStatement()) {
            stmt.execute(sqlUsuarios);
            stmt.execute(sqlProductos);
            stmt.execute(sqlVentas);
            stmt.execute(sqlInsert);
        } catch (SQLException e) {
            System.err.println("Error al inicializar las tablas de SQLite: " + e.getMessage());
        }
    }
}
