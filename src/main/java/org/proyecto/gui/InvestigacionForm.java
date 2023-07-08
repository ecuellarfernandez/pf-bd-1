package org.proyecto.gui;

import org.proyecto.dao.AgenteDao;
import org.proyecto.dao.CrimenDao;
import org.proyecto.dao.InvestigacionDao;
import org.proyecto.dao.impl.AgenteDaoMySql;
import org.proyecto.dao.impl.CrimenDaoMySql;
import org.proyecto.dao.impl.InvestigacionDaoMySql;
import org.proyecto.dto.Agente;
import org.proyecto.dto.Crimen;
import org.proyecto.dto.Investigacion;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.util.ArrayList;

public class InvestigacionForm extends JDialog {

    private InvestigacionDao investigacionDao = new InvestigacionDaoMySql();
    private CrimenDao crimenDao = new CrimenDaoMySql();
    private AgenteDao agenteDao = new AgenteDaoMySql();
    private Investigacion investigacion;
    private JTextField textFieldFechaInicio;
    private JTextField textFieldFechaFin;
    private JComboBox<Integer> comboBoxIdAgente;
    private JComboBox<Integer> comboBoxIdCrimen;

    public InvestigacionForm(JFrame parent, Investigacion investigacion) throws Exception {
        super(parent, "Formulario de Investigación", true);
        this.investigacion = investigacion;

        // Crear los componentes del formulario
        JLabel labelNombre = new JLabel("Nombre:");
        JLabel labelFechaInicio = new JLabel("Fecha de Inicio:");
        JLabel labelFechaFin = new JLabel("Fecha de Fin:");
        JLabel labelIdAgente = new JLabel("ID de Agente:");
        JLabel labelIdCrimen = new JLabel("ID de Crimen:");

        textFieldFechaInicio = new JTextField(20);
        textFieldFechaFin = new JTextField(20);
        comboBoxIdAgente = new JComboBox<>();
        comboBoxIdCrimen = new JComboBox<>();

        JButton buttonGuardar = new JButton("Guardar");
        buttonGuardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    guardarInvestigacion();
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
        panel.add(labelFechaInicio, constraints);

        constraints.gridx = 1;
        panel.add(textFieldFechaInicio, constraints);

        constraints.gridx = 0;
        constraints.gridy = 2;
        panel.add(labelFechaFin, constraints);

        constraints.gridx = 1;
        panel.add(textFieldFechaFin, constraints);

        constraints.gridx = 0;
        constraints.gridy = 3;
        panel.add(labelIdAgente, constraints);

        constraints.gridx = 1;
        panel.add(comboBoxIdAgente, constraints);

        constraints.gridx = 0;
        constraints.gridy = 4;
        panel.add(labelIdCrimen, constraints);

        constraints.gridx = 1;
        panel.add(comboBoxIdCrimen, constraints);

        constraints.gridx = 0;
        constraints.gridy = 5;
        constraints.gridwidth = 2;
        constraints.anchor = GridBagConstraints.CENTER;
        panel.add(buttonGuardar, constraints);

        constraints.gridx = 0;
        constraints.gridy = 6;
        panel.add(buttonCancelar, constraints);

        // Establecer el panel como contenido del diálogo
        getContentPane().add(panel);
        pack();
        setLocationRelativeTo(parent);

        cargarLista("Agente");
        cargarLista("Crimen");

        // Si se proporciona una investigación existente, llenar el formulario con sus datos
        if (investigacion != null) {
            llenarFormulario();
        }
        setVisible(true);
    }

    private void llenarFormulario() {
        textFieldFechaInicio.setText(String.valueOf(investigacion.getFechaInicio()));
        textFieldFechaFin.setText(String.valueOf(investigacion.getFechaFin()));
        comboBoxIdAgente.setSelectedItem(investigacion.getIdAgente());
        comboBoxIdCrimen.setSelectedItem(investigacion.getIdCrimen());
    }

    private void cargarLista(String lista) throws Exception {
        switch (lista) {
            case "Agente":
                ArrayList<Agente> agentes = agenteDao.getList();
                comboBoxIdAgente.removeAllItems();
                for (Agente agente : agentes) {
                    comboBoxIdAgente.addItem(agente.getId());
                }
                break;
            case "Crimen":
                ArrayList<Crimen> crimenes = crimenDao.getList();
                comboBoxIdCrimen.removeAllItems();
                for (Crimen crimen : crimenes) {
                    comboBoxIdCrimen.addItem(crimen.getId());
                }
                break;
        }
    }

    private void guardarInvestigacion() throws Exception {
        // Obtener los datos del formulario
        String fechaInicio = (textFieldFechaInicio.getText().isEmpty()) ? "1970-01-01" : textFieldFechaInicio.getText();
        String fechaFin = (textFieldFechaFin.getText().isEmpty()) ? "9999-12-31" : textFieldFechaFin.getText();
        int idAgente = comboBoxIdAgente.getSelectedItem() != null ? (int) comboBoxIdAgente.getSelectedItem() : 0;
        int idCrimen = comboBoxIdCrimen.getSelectedItem() != null ? (int) comboBoxIdCrimen.getSelectedItem() : 0;

        // Crear un nuevo objeto Investigacion con los datos del formulario
        Investigacion nuevaInvestigacion = new Investigacion();
        nuevaInvestigacion.setFechaInicio(Date.valueOf(fechaInicio));
        nuevaInvestigacion.setFechaFin(Date.valueOf(fechaFin));
        nuevaInvestigacion.setIdAgente(idAgente);
        nuevaInvestigacion.setIdCrimen(idCrimen);

        // Verificar si es una inserción o una actualización
        if (investigacion != null) {
            // Actualizar la investigación existente
            nuevaInvestigacion.setId(investigacion.getId());
            investigacionDao.update(nuevaInvestigacion);
        } else {
            // Insertar una nueva investigación
            investigacionDao.insert(nuevaInvestigacion);
        }

        // Cerrar el diálogo
        dispose();
    }
}