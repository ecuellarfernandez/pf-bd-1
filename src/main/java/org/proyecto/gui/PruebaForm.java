package org.proyecto.gui;

import org.proyecto.dao.CrimenDao;
import org.proyecto.dao.PruebaDao;
import org.proyecto.dao.impl.CrimenDaoMySql;
import org.proyecto.dao.impl.PruebaDaoMySql;
import org.proyecto.dto.Crimen;
import org.proyecto.dto.Prueba;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class PruebaForm extends JDialog {
    private PruebaDao pruebaDao = new PruebaDaoMySql();
    private CrimenDao crimenDao = new CrimenDaoMySql();
    private JTextField textFieldDescripcion;
    private JComboBox<Integer> comboBoxIdCrimen;
    private Prueba prueba;

    public PruebaForm(JFrame parent, Prueba prueba) throws Exception {
        super(parent, "Formulario de Prueba", true);
        this.prueba = prueba;

        // Crear los componentes del formulario
        JLabel labelDescripcion = new JLabel("Descripción:");
        JLabel labelIdCrimen = new JLabel("ID Crimen:");

        textFieldDescripcion = new JTextField(20);
        comboBoxIdCrimen = new JComboBox<>();

        JButton buttonGuardar = new JButton("Guardar");
        buttonGuardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    guardarPrueba();
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
        panel.add(labelDescripcion, constraints);

        constraints.gridx = 1;
        panel.add(textFieldDescripcion, constraints);

        constraints.gridx = 0;
        constraints.gridy = 1;
        panel.add(labelIdCrimen, constraints);

        constraints.gridx = 1;
        panel.add(comboBoxIdCrimen, constraints);

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
        cargarIdCrimen();
        if(prueba!=null){
            llenarFormulario();
        }


        setVisible(true);
    }

    private void llenarFormulario(){
        textFieldDescripcion.setText(prueba.getDescripcion());
        comboBoxIdCrimen.setSelectedItem(prueba.getIdCrimen());
    }

    private void cargarIdCrimen() throws Exception {
        ArrayList<Crimen> crimenes = crimenDao.getList();
        comboBoxIdCrimen.removeAllItems();
        for (Crimen crimen : crimenes) {
            comboBoxIdCrimen.addItem(crimen.getId());
        }
    }

    private void guardarPrueba() throws Exception {
        String descripcion = textFieldDescripcion.getText();
        int idCrimen = comboBoxIdCrimen.getSelectedItem() != null ? (int) comboBoxIdCrimen.getSelectedItem() : 0;

        // Validar que los campos no estén vacíos
        if (descripcion.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor, complete la descripción.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Crear un objeto Prueba con los datos del formulario
        Prueba nuevaPrueba = new Prueba();
        nuevaPrueba.setDescripcion(descripcion);
        nuevaPrueba.setIdCrimen(idCrimen);

        if(prueba != null){
            nuevaPrueba.setId(prueba.getId());
            pruebaDao.update(nuevaPrueba);
        }else{
            pruebaDao.insert(nuevaPrueba);
        }
        dispose();
    }

}
