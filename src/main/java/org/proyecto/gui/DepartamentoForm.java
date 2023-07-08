package org.proyecto.gui;

import org.proyecto.dao.AgenteDao;
import org.proyecto.dao.DepartamentoDao;
import org.proyecto.dao.impl.AgenteDaoMySql;
import org.proyecto.dao.impl.DepartamentoDaoMySql;
import org.proyecto.dto.Agente;
import org.proyecto.dto.Departamento;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class DepartamentoForm extends JDialog {

    private DepartamentoDao departamentoDao = new DepartamentoDaoMySql();
    private AgenteDao agenteDao = new AgenteDaoMySql();
    private Departamento departamento;
    private JTextField textFieldNombre;
    private JTextField textFieldDescripcion;
    private JComboBox comboBoxIdAdministrador;

    public DepartamentoForm(JFrame parent, Departamento departamento) throws Exception {
        super(parent, "Formulario de Departamento", true);
        this.departamento = departamento;

        // Crear los componentes del formulario
        JLabel labelNombre = new JLabel("Nombre:");
        JLabel labelDescripcion = new JLabel("Descripción:");
        JLabel labelIdAdministrador = new JLabel("ID Administrador:");

        textFieldNombre = new JTextField(20);
        textFieldDescripcion = new JTextField(20);
        comboBoxIdAdministrador= new JComboBox<>();

        JButton buttonGuardar = new JButton("Guardar");
        buttonGuardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                guardarDepartamento();
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
        panel.add(labelDescripcion, constraints);

        constraints.gridx = 1;
        panel.add(textFieldDescripcion, constraints);

        constraints.gridx = 0;
        constraints.gridy = 3;
        panel.add(labelIdAdministrador, constraints);

        constraints.gridx = 1;
        panel.add(comboBoxIdAdministrador, constraints);

        constraints.gridx = 0;
        constraints.gridy = 4;
        constraints.gridwidth = 2;
        constraints.anchor = GridBagConstraints.CENTER;
        panel.add(buttonGuardar, constraints);

        constraints.gridx = 0;
        constraints.gridy = 5;
        panel.add(buttonCancelar, constraints);

        cargarLista("Agente");
        // Establecer el panel como contenido del diálogo
        getContentPane().add(panel);
        pack();
        setLocationRelativeTo(parent);

        // Si se proporciona un departamento existente, llenar el formulario con sus datos
        if (departamento != null) {
            llenarFormulario();
        }
        setVisible(true);
    }

    private void cargarLista(String lista) throws Exception {
        switch (lista){
            case "Agente":
                ArrayList<Agente> agentes = agenteDao.getList();
                comboBoxIdAdministrador.removeAllItems();
                for(Agente agente : agentes){
                    comboBoxIdAdministrador.addItem(String.valueOf(agente.getId()));
                    System.out.println(agente.getId());
                }
                break;
        }
    }

    private void llenarFormulario() {
        textFieldNombre.setText(departamento.getNombre());
        textFieldDescripcion.setText(departamento.getDescripcion());
        comboBoxIdAdministrador.setSelectedItem(String.valueOf(departamento.getIdAdministador()));
    }

    private void guardarDepartamento() {
        String nombre = textFieldNombre.getText();
        String descripcion = textFieldDescripcion.getText();
        int idAdministrador;

        try {
            idAdministrador = Integer.parseInt((String) comboBoxIdAdministrador.getSelectedItem());
        } catch (NumberFormatException e) {
            idAdministrador = 0;
        }

        // Crear un objeto Departamento con los datos del formulario
        Departamento nuevoDepartamento = new Departamento();
        nuevoDepartamento.setNombre(nombre);
        nuevoDepartamento.setDescripcion(descripcion);
        nuevoDepartamento.setIdAdministador(idAdministrador);

        try {
            // Guardar el departamento en la base de datos
            departamentoDao.insert(nuevoDepartamento);
            JOptionPane.showMessageDialog(this, "Departamento guardado exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            dispose();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al guardar el departamento: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

}

