package com.miportafolio.ui;

import com.miportafolio.dao.ProductoDAO;
import com.miportafolio.models.Producto;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class PanelInventario extends JPanel {
    
    private JTable tabla;
    private DefaultTableModel modelo;
    private JTextField txtId, txtCodigo, txtNombre, txtPrecio, txtStock, txtBuscar;
    private ProductoDAO dao;

    public PanelInventario() {
        dao = new ProductoDAO();
        setLayout(new BorderLayout(15, 15));
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // --- Título y Buscador ---
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(Color.WHITE);
        JLabel lblTitulo = new JLabel("Gestión de Inventario");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 28));
        lblTitulo.setForeground(new Color(41, 128, 185));
        
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        searchPanel.setBackground(Color.WHITE);
        searchPanel.add(new JLabel("🔍 Buscar: "));
        txtBuscar = new JTextField(15);
        searchPanel.add(txtBuscar);
        
        headerPanel.add(lblTitulo, BorderLayout.WEST);
        headerPanel.add(searchPanel, BorderLayout.EAST);
        add(headerPanel, BorderLayout.NORTH);

        // --- Formulario Izquierdo ---
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(new Color(245, 246, 250));
        formPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(220, 221, 225)),
            BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 5, 8, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        
        txtId = new JTextField(); txtId.setEnabled(false); // Oculto o deshabilitado
        
        agregarCampo(formPanel, gbc, "Código SKU:", txtCodigo = new JTextField(), 0);
        agregarCampo(formPanel, gbc, "Nombre del Producto:", txtNombre = new JTextField(), 2);
        agregarCampo(formPanel, gbc, "Precio Unitario ($):", txtPrecio = new JTextField(), 4);
        agregarCampo(formPanel, gbc, "Unidades en Stock:", txtStock = new JTextField(), 6);

        // Botones Formulario
        JPanel btnPanel = new JPanel(new GridLayout(1, 3, 5, 0));
        btnPanel.setOpaque(false);
        JButton btnGuardar = new JButton("Guardar");
        JButton btnActualizar = new JButton("Actualizar");
        JButton btnEliminar = new JButton("Eliminar");
        
        btnPanel.add(btnGuardar); btnPanel.add(btnActualizar); btnPanel.add(btnEliminar);
        gbc.gridy = 8;
        formPanel.add(btnPanel, gbc);
        
        JButton btnLimpiar = new JButton("Limpiar Selección");
        gbc.gridy = 9;
        formPanel.add(btnLimpiar, gbc);

        add(formPanel, BorderLayout.WEST);

        // --- Tabla Central ---
        modelo = new DefaultTableModel(new String[]{"ID", "Código", "Nombre", "Precio", "Stock"}, 0);
        tabla = new JTable(modelo);
        tabla.setRowHeight(30);
        tabla.setSelectionBackground(new Color(41, 128, 185));
        tabla.setSelectionForeground(Color.WHITE);
        add(new JScrollPane(tabla), BorderLayout.CENTER);

        // --- Eventos ---
        cargarDatos("");
        
        txtBuscar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                cargarDatos(txtBuscar.getText());
            }
        });
        
        tabla.getSelectionModel().addListSelectionListener(e -> {
            if (tabla.getSelectedRow() != -1) {
                int row = tabla.getSelectedRow();
                txtId.setText(modelo.getValueAt(row, 0).toString());
                txtCodigo.setText(modelo.getValueAt(row, 1).toString());
                txtNombre.setText(modelo.getValueAt(row, 2).toString());
                txtPrecio.setText(modelo.getValueAt(row, 3).toString());
                txtStock.setText(modelo.getValueAt(row, 4).toString());
            }
        });

        btnLimpiar.addActionListener(e -> limpiarFormulario());
        btnGuardar.addActionListener(e -> {
            try {
                if (txtCodigo.getText().isEmpty() || txtNombre.getText().isEmpty()) {
                    throw new Exception("El código y nombre no pueden estar vacíos.");
                }
                Producto p = new Producto(0, txtCodigo.getText(), txtNombre.getText(), Double.parseDouble(txtPrecio.getText()), Integer.parseInt(txtStock.getText()));
                if(dao.insertar(p)) { cargarDatos(""); limpiarFormulario(); JOptionPane.showMessageDialog(this, "Producto guardado con éxito."); }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Error: Precio y Stock deben ser números válidos.", "Error de Formato", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) { 
                JOptionPane.showMessageDialog(this, "Error al guardar: " + ex.getMessage(), "Error BD", JOptionPane.ERROR_MESSAGE); 
            }
        });
        
        btnActualizar.addActionListener(e -> {
            try {
                if(txtId.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Seleccione un producto de la tabla primero.");
                    return;
                }
                Producto p = new Producto(Integer.parseInt(txtId.getText()), txtCodigo.getText(), txtNombre.getText(), Double.parseDouble(txtPrecio.getText()), Integer.parseInt(txtStock.getText()));
                if(dao.actualizar(p)) { cargarDatos(""); limpiarFormulario(); JOptionPane.showMessageDialog(this, "Producto actualizado con éxito."); }
            } catch (Exception ex) { JOptionPane.showMessageDialog(this, "Error al actualizar: " + ex.getMessage()); }
        });
        
        btnEliminar.addActionListener(e -> {
            try {
                if(txtId.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Seleccione un producto de la tabla primero.");
                    return;
                }
                if(JOptionPane.showConfirmDialog(this, "¿Eliminar producto permanentemente?") == 0) {
                    dao.eliminar(Integer.parseInt(txtId.getText()));
                    cargarDatos(""); limpiarFormulario();
                }
            } catch (Exception ex) { JOptionPane.showMessageDialog(this, "Error al eliminar: " + ex.getMessage()); }
        });
    }

    private void agregarCampo(JPanel panel, GridBagConstraints gbc, String label, JTextField field, int y) {
        gbc.gridy = y;
        panel.add(new JLabel(label), gbc);
        gbc.gridy = y + 1;
        field.setPreferredSize(new Dimension(250, 30));
        panel.add(field, gbc);
    }
    
    private void limpiarFormulario() {
        txtId.setText(""); txtCodigo.setText(""); txtNombre.setText(""); txtPrecio.setText(""); txtStock.setText("");
        tabla.clearSelection();
    }

    private void cargarDatos(String busqueda) {
        modelo.setRowCount(0);
        try {
            List<Producto> lista = busqueda.isEmpty() ? dao.listarTodos() : dao.buscarPorNombre(busqueda);
            for (Producto p : lista) {
                modelo.addRow(new Object[]{p.getId(), p.getCodigo(), p.getNombre(), p.getPrecio(), p.getStock()});
            }
        } catch (Exception e) {}
    }
}
