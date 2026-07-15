package com.miportafolio.ui;

import com.miportafolio.models.Usuario;
import javax.swing.*;
import java.awt.*;

public class DashboardFrame extends JFrame {
    
    private JPanel panelCentral;
    private CardLayout cardLayout;
    
    public DashboardFrame(Usuario usuarioActual) {
        setTitle("ERP Enterprise - Dashboard");
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        JPanel mainPanel = new JPanel(new BorderLayout());
        
        // --- SIDEBAR (Menú Lateral) ---
        JPanel sidebar = new JPanel();
        sidebar.setPreferredSize(new Dimension(250, 0));
        sidebar.setBackground(new Color(44, 62, 80)); // Dark Blue/Grey
        sidebar.setLayout(new BorderLayout());
        
        // Perfil en Sidebar
        JPanel profilePanel = new JPanel(new GridLayout(2, 1));
        profilePanel.setBackground(new Color(34, 49, 63));
        profilePanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        JLabel lblNombre = new JLabel(usuarioActual.getNombre());
        lblNombre.setForeground(Color.WHITE);
        lblNombre.setFont(new Font("Segoe UI", Font.BOLD, 16));
        JLabel lblRol = new JLabel("Rol: " + usuarioActual.getRol());
        lblRol.setForeground(new Color(189, 195, 199));
        
        profilePanel.add(lblNombre);
        profilePanel.add(lblRol);
        sidebar.add(profilePanel, BorderLayout.NORTH);
        
        // Botones de Navegación
        JPanel navPanel = new JPanel(new GridLayout(4, 1, 0, 5));
        navPanel.setBackground(new Color(44, 62, 80));
        navPanel.setBorder(BorderFactory.createEmptyBorder(20, 10, 0, 10));
        
        JButton btnInventario = crearBotonMenu("Gestión de Inventario");
        JButton btnVentas = crearBotonMenu("Punto de Venta");
        JButton btnLogout = crearBotonMenu("Cerrar Sesión");
        
        navPanel.add(btnInventario);
        navPanel.add(btnVentas);
        navPanel.add(new JLabel()); // Espacio
        navPanel.add(btnLogout);
        sidebar.add(navPanel, BorderLayout.CENTER);
        
        mainPanel.add(sidebar, BorderLayout.WEST);

        // --- PANEL CENTRAL (Content) ---
        cardLayout = new CardLayout();
        panelCentral = new JPanel(cardLayout);
        
        panelCentral.add(new PanelInventario(), "INVENTARIO");
        panelCentral.add(new PanelVentas(), "VENTAS");
        
        mainPanel.add(panelCentral, BorderLayout.CENTER);
        add(mainPanel);

        // Eventos de navegación
        btnInventario.addActionListener(e -> cardLayout.show(panelCentral, "INVENTARIO"));
        btnVentas.addActionListener(e -> cardLayout.show(panelCentral, "VENTAS"));
        btnLogout.addActionListener(e -> {
            new LoginFrame().setVisible(true);
            dispose();
        });
    }
    
    private JButton crearBotonMenu(String texto) {
        JButton btn = new JButton(texto);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btn.setForeground(Color.WHITE);
        btn.setBackground(new Color(52, 73, 94));
        btn.setHorizontalAlignment(SwingConstants.LEFT);
        btn.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return btn;
    }
}
