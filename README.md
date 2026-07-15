# 📦 Enterprise Inventory & Sales Management System

Este es un sistema integral de gestión de inventarios y punto de venta (POS) que desarrollé completamente en Java. 

Mi objetivo principal con este proyecto fue demostrar mi capacidad para construir aplicaciones de escritorio sólidas, escalables y, sobre todo, orientadas a la administración comercial utilizando las mejores prácticas de la industria.

## 🚀 Lo que desarrollé en este proyecto:

*   **Arquitectura MVC y Patrón DAO:** Decidí separar estrictamente la interfaz de usuario de la lógica de acceso a datos. Todo el código de la base de datos está encapsulado en clases DAO, lo que me permite mantener el código limpio y profesional.
*   **Interfaz de Usuario Moderna:** Para alejarme de las interfaces genéricas, implementé la librería `FlatLaf`. Diseñé un Dashboard estilo *Single Page Application* interactivo y usable.
*   **Gestión de Inventario (CRUD):** Programé desde cero la lógica para registrar, leer, actualizar y eliminar productos en tiempo real.
*   **Procesamiento de Transacciones Seguras:** El sistema de ventas calcula totales dinámicamente y reduce el stock. Para evitar corrupción de datos, implementé transacciones SQL reales (`commit` y `rollback`).

## 💻 Tecnologías que utilicé:

*   **Lenguaje:** Java 17 (Estructurando todo bajo principios POO)
*   **GUI:** Java Swing + FlatLaf
*   **Base de Datos:** SQLite (Decidí usar una base embebida para garantizar que el proyecto sea 100% portable y cero configuración).
*   **Conector:** SQLite JDBC

## ⚙️ Cómo probar mi proyecto:

1.  **Ejecución Rápida y Portable:** Haz doble clic en el archivo `ejecutar_inventario.bat`. 
2.  El script que programé se encargará de compilar la arquitectura y generar la base de datos `sistema_inventario.db` automáticamente en tu primer inicio. ¡No necesitas instalar servidores locales!
