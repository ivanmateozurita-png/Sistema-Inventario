package com.miportafolio.ui;

import com.miportafolio.dao.UsuarioDAO;
import com.miportafolio.models.Usuario;
import javax.swing.*;
import java.awt.*;

public class RegisterFrame extends JFrame {

    private JTextField txtNombre, txtUsuario;
    private JPasswordField txtContrasena;

    public RegisterFrame() {
        setTitle("Enterprise ERP - Registro");
        setSize(400, 550);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.WHITE);

        // Header
        JPanel headerPanel = new JPanel(new GridBagLayout());
        headerPanel.setBackground(new Color(46, 204, 113)); // Material Green
        headerPanel.setPreferredSize(new Dimension(400, 130));
        
        JLabel lblTitulo = new JLabel("Crear Cuenta");
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 32));
        headerPanel.add(lblTitulo);
        mainPanel.add(headerPanel, BorderLayout.NORTH);

        // Form
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 30, 8, 30);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        agregarCampo(formPanel, gbc, "Nombre Completo", txtNombre = new JTextField(), 0);
        agregarCampo(formPanel, gbc, "Nombre de Usuario", txtUsuario = new JTextField(), 2);
        
        gbc.gridy = 4;
        JLabel lblPass = new JLabel("Contraseña");
        lblPass.setFont(new Font("Segoe UI", Font.BOLD, 14));
        formPanel.add(lblPass, gbc);
        
        gbc.gridy = 5;
        txtContrasena = new JPasswordField();
        txtContrasena.setPreferredSize(new Dimension(300, 35));
        formPanel.add(txtContrasena, gbc);

        // Botón Registro
        JButton btnRegistrar = new JButton("REGISTRARSE");
        btnRegistrar.setPreferredSize(new Dimension(300, 45));
        btnRegistrar.setBackground(new Color(46, 204, 113));
        btnRegistrar.setForeground(Color.WHITE);
        btnRegistrar.setFont(new Font("Segoe UI", Font.BOLD, 14));
        gbc.gridy = 6;
        gbc.insets = new Insets(20, 30, 10, 30);
        formPanel.add(btnRegistrar, gbc);
        
        // Link a Login
        JLabel lblLogin = new JLabel("¿Ya tienes cuenta? Inicia sesión");
        lblLogin.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblLogin.setForeground(new Color(46, 204, 113));
        lblLogin.setCursor(new Cursor(Cursor.HAND_CURSOR));
        lblLogin.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.gridy = 7;
        gbc.insets = new Insets(0, 30, 20, 30);
        formPanel.add(lblLogin, gbc);

        mainPanel.add(formPanel, BorderLayout.CENTER);
        add(mainPanel);

        // Eventos
        btnRegistrar.addActionListener(e -> registrar());
        lblLogin.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                new LoginFrame().setVisible(true);
                dispose();
            }
        });
    }

    private void agregarCampo(JPanel panel, GridBagConstraints gbc, String label, JTextField field, int y) {
        gbc.gridy = y;
        JLabel lbl = new JLabel(label);
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 14));
        panel.add(lbl, gbc);
        
        gbc.gridy = y + 1;
        field.setPreferredSize(new Dimension(300, 35));
        field.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        panel.add(field, gbc);
    }

    private void registrar() {
        if (txtNombre.getText().isEmpty() || txtUsuario.getText().isEmpty() || txtContrasena.getPassword().length == 0) {
            JOptionPane.showMessageDialog(this, "Todos los campos son obligatorios.");
            return;
        }

        Usuario u = new Usuario(0, txtNombre.getText(), txtUsuario.getText(), new String(txtContrasena.getPassword()), "USER");
        try {
            UsuarioDAO dao = new UsuarioDAO();
            if (dao.registrar(u)) {
                JOptionPane.showMessageDialog(this, "¡Cuenta creada con éxito! Por favor inicia sesión.");
                new LoginFrame().setVisible(true);
                this.dispose();
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: El usuario ya existe o hubo un fallo en la red.\n" + ex.getMessage(), "Error BD", JOptionPane.ERROR_MESSAGE);
        }
    }
}
