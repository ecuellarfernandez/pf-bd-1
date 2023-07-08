package org.proyecto.gui;

import org.proyecto.dao.CrimenDao;
import org.proyecto.dao.SospechosoDao;
import org.proyecto.dao.SospechosoTieneCrimenDao;
import org.proyecto.dao.impl.CrimenDaoMySql;
import org.proyecto.dao.impl.SospechosoDaoMySql;
import org.proyecto.dao.impl.SospechosoTieneCrimenDaoMySql;
import org.proyecto.dto.Crimen;
import org.proyecto.dto.Sospechoso;
import org.proyecto.dto.SospechosoTieneCrimen;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class SospechosoTieneCrimenForm extends JDialog {

    private CrimenDao crimenDao = new CrimenDaoMySql();
    private SospechosoDao sospechosoDao = new SospechosoDaoMySql();
    private SospechosoTieneCrimenDao sospechosoTieneCrimenDao = new SospechosoTieneCrimenDaoMySql();
    private SospechosoTieneCrimen sospechosoTieneCrimen;
    private JComboBox<Integer> comboBoxIdCrimen;
    private JComboBox<Integer> comboBoxIdSospechoso;

    public SospechosoTieneCrimenForm(JFrame parent, SospechosoTieneCrimen sospechosoTieneCrimen) throws Exception {
        super(parent, "Relación Sospechoso-Tiene-Crimen", true);
        this.sospechosoTieneCrimen = sospechosoTieneCrimen;

        // Crear los componentes del formulario
        JLabel labelIdCrimen = new JLabel("ID Crimen:");
        JLabel labelIdSospechoso = new JLabel("ID Sospechoso:");

        comboBoxIdCrimen = new JComboBox<>();
        comboBoxIdSospechoso = new JComboBox<>();

        JButton buttonGuardar = new JButton("Guardar");
        buttonGuardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    guardarRelacion();
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
        panel.add(labelIdCrimen, constraints);

        constraints.gridx = 1;
        panel.add(comboBoxIdCrimen, constraints);

        constraints.gridx = 0;
        constraints.gridy = 1;
        panel.add(labelIdSospechoso, constraints);

        constraints.gridx = 1;
        panel.add(comboBoxIdSospechoso, constraints);

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

        cargarListaCrimenes();
        cargarListaSospechosos();

        // Si se proporciona una relación existente, seleccionar los IDs de crimen y sospechoso si están asociados
        if (sospechosoTieneCrimen != null) {
            seleccionarIds();
        }
        setVisible(true);
    }

    private void cargarListaCrimenes() throws Exception {
        ArrayList<Crimen> crimenes = crimenDao.getList();
        comboBoxIdCrimen.removeAllItems();
        for (Crimen crimen : crimenes) {
            comboBoxIdCrimen.addItem(crimen.getId());
        }
    }

    private void cargarListaSospechosos() throws Exception {
        ArrayList<Sospechoso> sospechosos = sospechosoDao.getList();
        comboBoxIdSospechoso.removeAllItems();
        for (Sospechoso sospechoso : sospechosos) {
            comboBoxIdSospechoso.addItem(sospechoso.getId());
        }
    }

    private void seleccionarIds() {
        int idCrimen = sospechosoTieneCrimen.getIdCrimen();
        int idSospechoso = sospechosoTieneCrimen.getIdSospechoso();
        comboBoxIdCrimen.setSelectedItem(idCrimen);
        comboBoxIdSospechoso.setSelectedItem(idSospechoso);
    }

    private void guardarRelacion() throws Exception {
        int idCrimen = (int) comboBoxIdCrimen.getSelectedItem();
        int idSospechoso = (int) comboBoxIdSospechoso.getSelectedItem();

        // Crear una nueva relación Sospechoso-Tiene-Crimen con los IDs seleccionados
        SospechosoTieneCrimen nuevaRelacion = new SospechosoTieneCrimen();
        nuevaRelacion.setIdCrimen(idCrimen);
        nuevaRelacion.setIdSospechoso(idSospechoso);

        if (sospechosoTieneCrimen != null) {
            // Actualizar la relación existente
            nuevaRelacion.setId(sospechosoTieneCrimen.getId());
            sospechosoTieneCrimenDao.update(nuevaRelacion);
        } else {
            // Insertar una nueva relación
            sospechosoTieneCrimenDao.insert(nuevaRelacion);
        }

        dispose();
    }
}
