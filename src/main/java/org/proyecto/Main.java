package org.proyecto;

import org.proyecto.gui.MainFrame;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {

        try {
            // select Look and Feel
            UIManager.setLookAndFeel("com.jtattoo.plaf.noire.NoireLookAndFeel");
            // start application
            new MainFrame();
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}