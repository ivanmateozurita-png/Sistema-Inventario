package com.miportafolio.ui;

import com.miportafolio.dao.ProductoDAO;
import com.miportafolio.dao.VentaDAO;
import com.miportafolio.models.Producto;
import com.miportafolio.models.Venta;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.List;

public class PanelVentas extends JPanel {
    
    private JComboBox<String> comboProductos;
    private JTextField txtCantidad;
    private JLabel lblTotal;
    
    private HashMap<String, Producto> mapProductos = new HashMap<>();
    private ProductoDAO productoDAO = new ProductoDAO();
    private VentaDAO ventaDAO = new VentaDAO();

    public PanelVentas() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createEmptyBorder(40, 60, 40, 60));

        // Tarjeta central estilo checkout
        JPanel cardPanel = new JPanel(new GridBagLayout());
        cardPanel.setBackground(Color.WHITE);
        cardPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(224, 224, 224), 1, true),
            BorderFactory.createEmptyBorder(30, 40, 30, 40)
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 15, 15, 15);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        // Título
        JLabel lblTitulo = new JLabel("Punto de Venta");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 32));
        lblTitulo.setForeground(new Color(39, 174, 96));
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        cardPanel.add(lblTitulo, gbc);

        gbc.gridwidth = 1;
        gbc.gridy = 1;
        JLabel lblProd = new JLabel("Seleccionar Producto:");
        lblProd.setFont(new Font("Segoe UI", Font.BOLD, 16));
        cardPanel.add(lblProd, gbc);

        gbc.gridx = 1;
        comboProductos = new JComboBox<>();
        comboProductos.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        comboProductos.setPreferredSize(new Dimension(300, 40));
        cardPanel.add(comboProductos, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        JLabel lblCant = new JLabel("Cantidad:");
        lblCant.setFont(new Font("Segoe UI", Font.BOLD, 16));
        cardPanel.add(lblCant, gbc);

        gbc.gridx = 1;
        txtCantidad = new JTextField();
        txtCantidad.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        txtCantidad.setPreferredSize(new Dimension(300, 40));
        cardPanel.add(txtCantidad, gbc);

        // Divider
        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 2;
        JSeparator sep = new JSeparator();
        cardPanel.add(sep, gbc);

        gbc.gridwidth = 1;
        gbc.gridx = 0; gbc.gridy = 4;
        JLabel lblTotLabel = new JLabel("Total a Pagar:");
        lblTotLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        cardPanel.add(lblTotLabel, gbc);

        gbc.gridx = 1;
        lblTotal = new JLabel("$0.00");
        lblTotal.setFont(new Font("Segoe UI", Font.BOLD, 36));
        lblTotal.setForeground(new Color(192, 57, 43));
        lblTotal.setHorizontalAlignment(SwingConstants.RIGHT);
        cardPanel.add(lblTotal, gbc);

        gbc.gridx = 0; gbc.gridy = 5; gbc.gridwidth = 2;
        JButton btnVender = new JButton("COMPLETAR TRANSACCIÓN");
        btnVender.setPreferredSize(new Dimension(0, 50));
        btnVender.setBackground(new Color(39, 174, 96));
        btnVender.setForeground(Color.WHITE);
        btnVender.setFont(new Font("Segoe UI", Font.BOLD, 18));
        btnVender.setFocusPainted(false);
        cardPanel.add(btnVender, gbc);

        add(cardPanel, BorderLayout.CENTER);

        // Listeners y Logica
        cargarProductos();

        txtCantidad.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) { calcularTotal(); }
        });
        comboProductos.addActionListener(e -> calcularTotal());

        btnVender.addActionListener(e -> procesarVenta());
    }

    private void cargarProductos() {
        comboProductos.removeAllItems();
        mapProductos.clear();
        try {
            List<Producto> lista = productoDAO.listarTodos();
            for (Producto p : lista) {
                comboProductos.addItem(p.getNombre());
                mapProductos.put(p.getNombre(), p);
            }
        } catch (Exception e) {}
    }

    private void calcularTotal() {
        try {
            String nombre = (String) comboProductos.getSelectedItem();
            if (nombre != null && !txtCantidad.getText().isEmpty()) {
                Producto p = mapProductos.get(nombre);
                int cantidad = Integer.parseInt(txtCantidad.getText());
                lblTotal.setText("$" + String.format("%.2f", cantidad * p.getPrecio()));
            } else {
                lblTotal.setText("$0.00");
            }
        } catch (NumberFormatException e) {
            lblTotal.setText("$0.00");
        }
    }

    private void procesarVenta() {
        String nombre = (String) comboProductos.getSelectedItem();
        if (nombre == null) return;
        
        try {
            int cantidad = Integer.parseInt(txtCantidad.getText());
            Producto p = mapProductos.get(nombre);
            
            if (cantidad <= 0 || cantidad > p.getStock()) {
                JOptionPane.showMessageDialog(this, "Cantidad inválida o excede el stock actual (" + p.getStock() + ").", "Error", JOptionPane.WARNING_MESSAGE);
                return;
            }

            Venta v = new Venta(p.getId(), cantidad, cantidad * p.getPrecio());
            if (ventaDAO.procesarVenta(v)) {
                JOptionPane.showMessageDialog(this, "Transacción completada exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                txtCantidad.setText("");
                cargarProductos(); // Refrescar stock
                lblTotal.setText("$0.00");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error en la transacción: " + ex.getMessage());
        }
    }
}
