package org.proyecto.gui;

import org.proyecto.dao.RangoDao;
import org.proyecto.dao.impl.RangoDaoMySql;
import org.proyecto.dto.Rango;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RangoForm extends JDialog {

    private RangoDao rangoDao = new RangoDaoMySql();
    private Rango rango;
    private JTextField textFieldNombre;

    public RangoForm(JFrame parent, Rango rango) {
        super(parent, "Formulario de Rango", true);
        this.rango = rango;

        // Crear los componentes del formulario
        JLabel labelNombre = new JLabel("Nombre:");
        textFieldNombre = new JTextField(20);

        JButton buttonGuardar = new JButton("Guardar");
        buttonGuardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                guardarRango();
            }
        });

        JButton buttonCancelar = new JButton("Cancelar");
        buttonCancelar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        // Crear el panel principal y establecer el diseño
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.anchor = GridBagConstraints.WEST;
        constraints.insets = new Insets(10, 10, 10, 10);

        // Agregar los componentes al panel
        constraints.gridx = 0;
        constraints.gridy = 0;
        panel.add(labelNombre, constraints);

        constraints.gridx = 1;
        panel.add(textFieldNombre, constraints);

        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.gridwidth = 2;
        constraints.anchor = GridBagConstraints.CENTER;
        panel.add(buttonGuardar, constraints);

        constraints.gridx = 0;
        constraints.gridy = 2;
        panel.add(buttonCancelar, constraints);

        // Establecer el panel como contenido del diálogo
        getContentPane().add(panel);
        pack();
        setLocationRelativeTo(parent);

        // Si se proporciona un rango existente, llenar el formulario con sus datos
        if (rango != null) {
            textFieldNombre.setText(rango.getNombre());
        }

        setVisible(true);
    }

    private void guardarRango() {
        // Obtener el nombre del formulario
        String nombre = textFieldNombre.getText();

        // Crear un nuevo objeto Rango con el nombre del formulario
        Rango nuevoRango = new Rango();
        nuevoRango.setNombre(nombre);

        // Verificar si es una inserción o una actualización
        if (rango != null) {
            // Actualizar el rango existente
            nuevoRango.setId(rango.getId());
            try {
                rangoDao.update(nuevoRango);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } else {
            // Insertar un nuevo rango
            try {
                rangoDao.insert(nuevoRango);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        dispose();
    }
}
