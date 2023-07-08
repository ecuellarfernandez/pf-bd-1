package org.proyecto.gui;

import org.proyecto.dao.AgenteDao;
import org.proyecto.dao.DepartamentoDao;
import org.proyecto.dao.EquipoDao;
import org.proyecto.dao.RangoDao;
import org.proyecto.dao.impl.AgenteDaoMySql;
import org.proyecto.dao.impl.DepartamentoDaoMySql;
import org.proyecto.dao.impl.EquipoDaoMySql;
import org.proyecto.dao.impl.RangoDaoMySql;
import org.proyecto.dto.Agente;
import org.proyecto.dto.Departamento;
import org.proyecto.dto.Equipo;
import org.proyecto.dto.Rango;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;

public class AgenteForm extends JDialog {

    private AgenteDao agenteDao = new AgenteDaoMySql();
    private Agente agente;
    private JTextField textFieldCi;
    private JTextField textFieldNombre;
    private JTextField textFieldApellidoPaterno;
    private JTextField textFieldApellidoMaterno;
    private JComboBox<Integer> comboBoxDepartamento;
    private JComboBox<Integer> comboBoxRango;
    private JComboBox<Integer> comboBoxIdEquipo;
    private DepartamentoDao departamentoDao = new DepartamentoDaoMySql();
    private RangoDao rangoDao = new RangoDaoMySql();
    private EquipoDao equipoDao = new EquipoDaoMySql();



    public AgenteForm(JFrame parent, Agente agente) throws Exception {
        super(parent, "Formulario de Agente", true);
        this.agente = agente;
        System.out.println("AgenteForm");
        // Crear los componentes del formulario
        JLabel labelNombre = new JLabel("Nombre:");
        JLabel labelApellidoPaterno = new JLabel("Apellido Paterno:");
        JLabel labelApellidoMaterno = new JLabel("Apellido Materno:");
        JLabel labelDepartamento = new JLabel("Departamento:");
        JLabel labelRango = new JLabel("Id Rango:");
        JLabel labelIdEquipo = new JLabel("Id Equipo:");

        textFieldNombre = new JTextField(20);
        textFieldApellidoPaterno = new JTextField(20);
        textFieldApellidoMaterno = new JTextField(20);
        comboBoxDepartamento = new JComboBox<>();
        comboBoxRango = new JComboBox<>();
        comboBoxIdEquipo = new JComboBox<>();

        JButton buttonGuardar = new JButton("Guardar");
        buttonGuardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    guardarAgente();
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
        panel.add(labelApellidoPaterno, constraints);

        constraints.gridx = 1;
        panel.add(textFieldApellidoPaterno, constraints);

        constraints.gridx = 0;
        constraints.gridy = 2;
        panel.add(labelApellidoMaterno, constraints);

        constraints.gridx = 1;
        panel.add(textFieldApellidoMaterno, constraints);

        constraints.gridx = 0;
        constraints.gridy = 3;
        panel.add(labelDepartamento, constraints);

        constraints.gridx = 1;
        panel.add(comboBoxDepartamento, constraints);

        constraints.gridx = 0;
        constraints.gridy = 4;
        panel.add(labelRango, constraints);

        constraints.gridx = 1;
        panel.add(comboBoxRango, constraints);

        constraints.gridx = 0;
        constraints.gridy = 5;
        panel.add(labelIdEquipo, constraints);

        constraints.gridx = 1;
        panel.add(comboBoxIdEquipo, constraints);

        constraints.gridx = 0;
        constraints.gridy = 6;
        constraints.gridwidth = 2;
        constraints.anchor = GridBagConstraints.CENTER;
        panel.add(buttonGuardar, constraints);

        constraints.gridx = 0;
        constraints.gridy = 7;
        panel.add(buttonCancelar, constraints);

        // Establecer el panel como contenido del diálogo
        getContentPane().add(panel);
        pack();
        setLocationRelativeTo(parent);
        cargarLista("Departamento");
        cargarLista("Rango");
        cargarLista("Equipo");

        // Si se proporciona un agente existente, llenar el formulario con sus datos
        if (agente != null) {
            textFieldNombre.setText(agente.getNombre());
            textFieldApellidoPaterno.setText(agente.getApellidoPaterno());
            textFieldApellidoMaterno.setText(agente.getApellidoMaterno());
            comboBoxDepartamento.setSelectedItem(agente.getIdDepartamento());
            comboBoxRango.setSelectedItem(agente.getIdRango());
            comboBoxIdEquipo.setSelectedItem(agente.getIdEquipo());
        }
        setVisible(true);
    }

    private void cargarLista(String lista) throws Exception {
        switch (lista){
            case "Departamento":
                ArrayList<Departamento> departamentos = departamentoDao.getList();
                comboBoxDepartamento.removeAllItems();
                for (Departamento departamento : departamentos) {
                    comboBoxDepartamento.addItem(departamento.getId());
                }
                break;
            case "Rango":
                ArrayList<Rango> rangos = rangoDao.getList();
                comboBoxRango.removeAllItems();
                for (Rango rango : rangos) {
                    comboBoxRango.addItem(rango.getId());
                }
                break;
            case "Equipo":
                ArrayList<Equipo> equipos = equipoDao.getList();
                comboBoxIdEquipo.removeAllItems();
                for (Equipo equipo : equipos) {
                    comboBoxIdEquipo.addItem(equipo.getId());
                }
                break;
        }
    }

    private void guardarAgente() throws Exception {
        // Obtener los datos del formulario
        String nombre = textFieldNombre.getText();
        String apellidoPaterno = textFieldApellidoPaterno.getText();
        String apellidoMaterno = textFieldApellidoMaterno.getText();
        int departamento = (int) comboBoxDepartamento.getSelectedItem();
        int rango = (int) comboBoxRango.getSelectedItem();
        int idEquipo = (int) comboBoxIdEquipo.getSelectedItem();
        System.out.println("comboBoxSeleccionado: " + comboBoxIdEquipo.getSelectedItem());
        // Crear un nuevo objeto Agente con los datos del formulario
        Agente nuevoAgente = new Agente();
        nuevoAgente.setNombre(nombre);
        nuevoAgente.setApellidoPaterno(apellidoPaterno);
        nuevoAgente.setApellidoMaterno(apellidoMaterno);
        nuevoAgente.setIdDepartamento(departamento);
        nuevoAgente.setIdRango(rango);
        nuevoAgente.setIdEquipo(idEquipo);

        // Verificar si es una inserción o una actualización
        if (agente != null) {
            // Actualizar el agente existente
            nuevoAgente.setId(agente.getId());
            agenteDao.update(nuevoAgente);
        } else {
            // Insertar un nuevo agente
            agenteDao.insert(nuevoAgente);
        }

        // Cerrar el diálogo
        dispose();
    }
}
