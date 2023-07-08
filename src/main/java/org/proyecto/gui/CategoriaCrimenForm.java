package org.proyecto.gui;

import org.proyecto.dao.CategoriaCrimenDao;
import org.proyecto.dao.impl.CategoriaDaoMySql;
import org.proyecto.dto.CategoriaCrimen;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CategoriaCrimenForm extends JDialog {

    private CategoriaCrimenDao categoriaCrimenDao = new CategoriaDaoMySql();
    private CategoriaCrimen categoriaCrimen;
    private JTextField textFieldNombre;

    public CategoriaCrimenForm(JFrame parent, CategoriaCrimen categoriaCrimen) throws Exception {
        super(parent, "Formulario de Categoría de Crimen", true);
        this.categoriaCrimen = categoriaCrimen;

        // Crear los componentes del formulario
        JLabel labelIdCategoria = new JLabel("ID Categoría:");
        JLabel labelNombre = new JLabel("Nombre:");

        textFieldNombre = new JTextField(20);

        JButton buttonGuardar = new JButton("Guardar");
        buttonGuardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    guardarCategoriaCrimen();
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
        constraints.gridy = 1;
        panel.add(labelNombre, constraints);

        constraints.gridx = 1;
        panel.add(textFieldNombre, constraints);

        constraints.gridx = 0;
        constraints.gridy = 2;
        constraints.gridwidth = 2;
        constraints.anchor = GridBagConstraints.CENTER;
        panel.add(buttonGuardar, constraints);

        constraints.gridx = 0;
        constraints.gridy = 3;
        panel.add(buttonCancelar, constraints);

        // Establecer el panel como contenido del diálogo
        getContentPane().add(panel);
        pack();
        setLocationRelativeTo(parent);

        // Si se proporciona una categoría de crimen existente, llenar el formulario con sus datos
        if (categoriaCrimen != null) {
            llenarFormulario();
        }
        setVisible(true);
    }

    private void llenarFormulario() {
        textFieldNombre.setText(categoriaCrimen.getNombre());
    }

    private void guardarCategoriaCrimen() throws Exception {
        String nombre = textFieldNombre.getText();

        // Crear un objeto CategoriaCrimen con los datos del formulario
        CategoriaCrimen nuevaCategoriaCrimen = new CategoriaCrimen();
        nuevaCategoriaCrimen.setNombre(nombre);

        if(categoriaCrimen != null){
            nuevaCategoriaCrimen.setId(categoriaCrimen.getId());
            categoriaCrimenDao.update(nuevaCategoriaCrimen);
            dispose();
        }else{
            categoriaCrimenDao.insert(nuevaCategoriaCrimen);
            dispose();
        }
    }
}
