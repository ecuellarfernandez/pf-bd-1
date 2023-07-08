package org.proyecto.gui;

import org.proyecto.dao.CategoriaCrimenDao;
import org.proyecto.dao.CrimenDao;
import org.proyecto.dao.impl.CategoriaDaoMySql;
import org.proyecto.dao.impl.CrimenDaoMySql;
import org.proyecto.dto.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;

public class CrimenForm extends JDialog {

    private CrimenDao crimenDao = new CrimenDaoMySql();
    private CategoriaCrimenDao categoriaCrimenDao = new CategoriaDaoMySql();
    private Crimen crimen;
    private JComboBox comboBoxIdCategoria;
    private JTextField textFieldDescripcion;
    private JTextField textFieldFecha;
    private JTextField textFieldHora;
    public CrimenForm(JFrame parent, Crimen crimen) throws Exception {
        super(parent, "Formulario de Crimen", true);
        this.crimen = crimen;

        // Crear los componentes del formulario
        JLabel labelIdCategoria = new JLabel("ID Categoría:");
        JLabel labelDescripcion = new JLabel("Descripción:");
        JLabel labelFecha = new JLabel("Fecha (YYYY-MM-DD):");
        JLabel labelHora = new JLabel("Hora (HH:MM:SS):");

        comboBoxIdCategoria = new JComboBox<>();
        textFieldDescripcion = new JTextField(20);
        textFieldFecha = new JTextField(20);
        textFieldHora = new JTextField(20);

        JButton buttonGuardar = new JButton("Guardar");
        buttonGuardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    guardarCrimen();
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
        panel.add(labelIdCategoria, constraints);

        constraints.gridx = 1;
        panel.add(comboBoxIdCategoria, constraints);

        constraints.gridx = 0;
        constraints.gridy = 1;
        panel.add(labelDescripcion, constraints);

        constraints.gridx = 1;
        panel.add(textFieldDescripcion, constraints);

        constraints.gridx = 0;
        constraints.gridy = 2;
        panel.add(labelFecha, constraints);

        constraints.gridx = 1;
        panel.add(textFieldFecha, constraints);

        constraints.gridx = 0;
        constraints.gridy = 3;
        panel.add(labelHora, constraints);

        constraints.gridx = 1;
        panel.add(textFieldHora, constraints);

        constraints.gridx = 0;
        constraints.gridy = 4;
        constraints.gridwidth = 2;
        constraints.anchor = GridBagConstraints.CENTER;
        panel.add(buttonGuardar, constraints);

        constraints.gridx = 0;
        constraints.gridy = 5;
        panel.add(buttonCancelar, constraints);
        cargarLista("CategoriaCrimen");

        // Establecer el panel como contenido del diálogo
        getContentPane().add(panel);
        pack();
        setLocationRelativeTo(parent);

        // Si se proporciona un crimen existente, llenar el formulario con sus datos
        if (crimen != null) {
            llenarFormulario();
        }
        setVisible(true);
    }

    private void cargarLista(String lista) throws Exception {
        switch (lista){
            case "CategoriaCrimen":
                ArrayList<CategoriaCrimen> categorias = categoriaCrimenDao.getList();
                comboBoxIdCategoria.removeAllItems();
                for (CategoriaCrimen categoriaCrimen : categorias) {
                    comboBoxIdCategoria.addItem(categoriaCrimen.getId());
                }
                break;
            default:
                break;
        }
    }

    private void llenarFormulario() {
        comboBoxIdCategoria.setSelectedItem(crimen.getIdCategoria());
        textFieldDescripcion.setText(crimen.getDescripcion());
        textFieldFecha.setText(crimen.getFecha().toString());
        textFieldHora.setText(crimen.getHora().toString());
    }

    private void guardarCrimen() throws Exception {
        int idCategoria = Integer.parseInt(String.valueOf(comboBoxIdCategoria.getSelectedItem()));
        String descripcion = textFieldDescripcion.getText();
        Date fecha = (textFieldFecha.getText().isEmpty()) ? Date.valueOf("1970-01-01") : Date.valueOf(textFieldFecha.getText());
        Time hora = (textFieldHora.getText().isEmpty()) ? Time.valueOf("00:00:00") : Time.valueOf(textFieldHora.getText());

        // Crear un objeto Crimen con los datos del formulario
        Crimen nuevoCrimen = new Crimen();
        nuevoCrimen.setIdCategoria(idCategoria);
        nuevoCrimen.setDescripcion(descripcion);
        nuevoCrimen.setFecha(fecha);
        nuevoCrimen.setHora(hora);

        if(crimen != null){
            nuevoCrimen.setId(crimen.getId());
            crimenDao.update(nuevoCrimen);
        }else {
            crimenDao.insert(nuevoCrimen);
        }
        dispose();
    }


}
