package com.miportafolio;

import com.formdev.flatlaf.FlatIntelliJLaf;
import com.miportafolio.ui.LoginFrame;

import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {
        // Establecer el diseño visual profesional (FlatLaf)
        try {
            UIManager.setLookAndFeel(new FlatIntelliJLaf());
            // Opcional: configurar bordes redondeados y colores
            UIManager.put("Button.arc", 10);
            UIManager.put("Component.arc", 10);
            UIManager.put("ProgressBar.arc", 10);
            UIManager.put("TextComponent.arc", 10);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Iniciar la aplicación mostrando la ventana de Login
        SwingUtilities.invokeLater(() -> {
            LoginFrame login = new LoginFrame();
            login.setVisible(true);
        });
    }
}
