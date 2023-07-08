package org.proyecto.gui;

import org.proyecto.dao.EquipoDao;
import org.proyecto.dao.impl.EquipoDaoMySql;
import org.proyecto.dto.Equipo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EquipoForm extends JDialog {

    private EquipoDao equipoDao = new EquipoDaoMySql();
    private Equipo equipo;
    private JTextField textFieldNombre;

    public EquipoForm(JFrame parent, Equipo equipo) throws Exception {
        super(parent, "Formulario de Equipo", true);
        this.equipo = equipo;

        // Crear los componentes del formulario
        JLabel labelNombre = new JLabel("Nombre:");

        textFieldNombre = new JTextField(20);

        JButton buttonGuardar = new JButton("Guardar");
        buttonGuardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    guardarEquipo();
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
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

        // Si se proporciona un equipo existente, llenar el formulario con sus datos
        if (equipo != null) {
            llenarFormulario();
        }
        setVisible(true);
    }

    private void llenarFormulario() {
        textFieldNombre.setText(equipo.getNombre());
    }

    private void guardarEquipo() throws Exception {
        String nombre = textFieldNombre.getText();

        // Validar que el campo no esté vacío
        if (nombre.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor, complete el campo 'Nombre'.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Crear un objeto Equipo con los datos del formulario
        Equipo nuevoEquipo = new Equipo();
        nuevoEquipo.setNombre(nombre);

        if(equipo != null){
            nuevoEquipo.setId(equipo.getId());
            equipoDao.update(nuevoEquipo);
        }else{
            equipoDao.insert(nuevoEquipo);
        }
        dispose();
        }
}