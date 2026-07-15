package com.miportafolio.ui;

import com.miportafolio.dao.UsuarioDAO;
import com.miportafolio.models.Usuario;
import javax.swing.*;
import java.awt.*;

public class LoginFrame extends JFrame {
    
    private JTextField txtUsuario;
    private JPasswordField txtContrasena;

    public LoginFrame() {
        setTitle("Enterprise ERP - Inicio de Sesión");
        setSize(400, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.WHITE);

        // Header
        JPanel headerPanel = new JPanel(new GridBagLayout());
        headerPanel.setBackground(Color.WHITE);
        headerPanel.setPreferredSize(new Dimension(400, 180));
        
        try {
            ImageIcon icon = new ImageIcon(new ImageIcon("assets/logo.png").getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH));
            JLabel lblLogo = new JLabel(icon);
            headerPanel.add(lblLogo);
        } catch (Exception e) {
            JLabel lblTitulo = new JLabel("ERP System");
            lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 36));
            headerPanel.add(lblTitulo);
        }
        mainPanel.add(headerPanel, BorderLayout.NORTH);

        // Form
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 30, 10, 30);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        JLabel lblUser = new JLabel("Usuario");
        lblUser.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblUser.setForeground(Color.DARK_GRAY);
        gbc.gridy = 0;
        formPanel.add(lblUser, gbc);

        txtUsuario = new JTextField();
        txtUsuario.setPreferredSize(new Dimension(300, 40));
        txtUsuario.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        gbc.gridy = 1;
        formPanel.add(txtUsuario, gbc);

        JLabel lblPass = new JLabel("Contraseña");
        lblPass.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblPass.setForeground(Color.DARK_GRAY);
        gbc.gridy = 2;
        formPanel.add(lblPass, gbc);

        txtContrasena = new JPasswordField();
        txtContrasena.setPreferredSize(new Dimension(300, 40));
        txtContrasena.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        gbc.gridy = 3;
        formPanel.add(txtContrasena, gbc);

        // Botón Login
        JButton btnIngresar = new JButton("INICIAR SESIÓN");
        btnIngresar.setPreferredSize(new Dimension(300, 45));
        btnIngresar.setBackground(new Color(25, 118, 210));
        btnIngresar.setForeground(Color.WHITE);
        btnIngresar.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnIngresar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        gbc.gridy = 4;
        gbc.insets = new Insets(20, 30, 10, 30);
        formPanel.add(btnIngresar, gbc);
        
        // Link a Register
        JLabel lblRegistro = new JLabel("¿No tienes cuenta? Regístrate aquí");
        lblRegistro.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblRegistro.setForeground(new Color(25, 118, 210));
        lblRegistro.setCursor(new Cursor(Cursor.HAND_CURSOR));
        lblRegistro.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.gridy = 5;
        gbc.insets = new Insets(0, 30, 20, 30);
        formPanel.add(lblRegistro, gbc);

        mainPanel.add(formPanel, BorderLayout.CENTER);
        add(mainPanel);

        // Eventos
        btnIngresar.addActionListener(e -> autenticar());
        lblRegistro.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                new RegisterFrame().setVisible(true);
                dispose();
            }
        });
    }

    private void autenticar() {
        try {
            UsuarioDAO dao = new UsuarioDAO();
            Usuario u = dao.autenticar(txtUsuario.getText(), new String(txtContrasena.getPassword()));
            if (u != null) {
                // JOptionPane.showMessageDialog(this, "Bienvenido " + u.getNombre());
                new DashboardFrame(u).setVisible(true);
                this.dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Credenciales incorrectas.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error BD", JOptionPane.ERROR_MESSAGE);
        }
    }
}
