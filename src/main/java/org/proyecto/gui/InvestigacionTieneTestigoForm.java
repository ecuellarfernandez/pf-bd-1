package org.proyecto.gui;

import org.proyecto.dao.InvestigacionDao;
import org.proyecto.dao.InvestigacionTieneTestigoDao;
import org.proyecto.dao.TestigoDao;
import org.proyecto.dao.impl.InvestigacionDaoMySql;
import org.proyecto.dao.impl.InvestigacionTieneTestigoDaoMySql;
import org.proyecto.dao.impl.TestigoDaoMySql;
import org.proyecto.dto.Investigacion;
import org.proyecto.dto.InvestigacionTieneTestigo;
import org.proyecto.dto.Testigo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.util.ArrayList;

public class InvestigacionTieneTestigoForm extends JDialog {

    private InvestigacionDao investigacionDao = new InvestigacionDaoMySql();
    private InvestigacionTieneTestigoDao investigacionTieneTestigoDao= new InvestigacionTieneTestigoDaoMySql();
    private TestigoDao testigoDao = new TestigoDaoMySql();
    private InvestigacionTieneTestigo investigacionTieneTestigo;
    private JComboBox<Integer> comboBoxInvestigacion;
    private JComboBox<Integer> comboBoxTestigo;
    private JTextArea textAreaDeclaracion;
    private JTextField textFieldFecha;

    public InvestigacionTieneTestigoForm(JFrame parent, InvestigacionTieneTestigo investigacionTieneTestigo) throws Exception {
        super(parent, "Formulario de InvestigacionTieneTestigo", true);
        this.investigacionTieneTestigo = investigacionTieneTestigo;

        // Crear los componentes del formulario
        JLabel labelInvestigacion = new JLabel("Investigacion:");
        JLabel labelTestigo = new JLabel("Testigo:");
        JLabel labelDeclaracion = new JLabel("Declaracion:");
        JLabel labelFecha = new JLabel("Fecha:");

        comboBoxInvestigacion = new JComboBox<>();
        comboBoxTestigo = new JComboBox<>();
        textAreaDeclaracion = new JTextArea(5, 20);
        textFieldFecha = new JTextField(10);

        JButton buttonGuardar = new JButton("Guardar");
        buttonGuardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    guardarInvestigacionTieneTestigo();
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

        constraints.gridx = 0;
        constraints.gridy = 1;
        panel.add(labelInvestigacion, constraints);

        constraints.gridx = 1;
        panel.add(comboBoxInvestigacion, constraints);

        constraints.gridx = 0;
        constraints.gridy = 2;
        panel.add(labelTestigo, constraints);

        constraints.gridx = 1;
        panel.add(comboBoxTestigo, constraints);

        constraints.gridx = 0;
        constraints.gridy = 3;
        panel.add(labelDeclaracion,constraints);

        constraints.gridx = 1;
        panel.add(new JScrollPane(textAreaDeclaracion), constraints);

        constraints.gridx = 0;
        constraints.gridy = 4;
        panel.add(labelFecha, constraints);

        constraints.gridx = 1;
        panel.add(textFieldFecha, constraints);

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

        cargarLista("Testigo");
        cargarLista("Investigacion");

        // Si se proporciona un objeto InvestigacionTieneTestigo existente, llenar el formulario con sus datos
        if (investigacionTieneTestigo != null) {
            llenarFormulario();
        }
        setVisible(true);
    }

    private void cargarLista(String entidad) throws Exception {
        switch (entidad){
            case "Testigo":
                ArrayList<Testigo> testigos = testigoDao.getList();
                comboBoxTestigo.removeAllItems();
                for (Testigo testigo : testigos) {
                    comboBoxTestigo.addItem(testigo.getId());
                }
                break;
                case "Investigacion":
                    ArrayList<Investigacion> investigaciones = investigacionDao.getList();
                    comboBoxInvestigacion.removeAllItems();
                    for (Investigacion investigacion : investigaciones) {
                        comboBoxInvestigacion.addItem(investigacion.getId());
                    }
                break;
        }
    }

    private void llenarFormulario() {
        comboBoxInvestigacion.setSelectedItem(investigacionTieneTestigo.getIdInvestigacion());
        comboBoxTestigo.setSelectedItem(investigacionTieneTestigo.getIdTestigo());
        textAreaDeclaracion.setText(investigacionTieneTestigo.getDeclaracion());
        textFieldFecha.setText(String.valueOf(investigacionTieneTestigo.getFecha()));
    }

    private void guardarInvestigacionTieneTestigo() throws Exception {
        int idInvestigacion = comboBoxInvestigacion.getSelectedItem() != null ? (int) comboBoxInvestigacion.getSelectedItem() : 0;
        int idTestigo = comboBoxTestigo.getSelectedItem() != null ? (int) comboBoxTestigo.getSelectedItem() : 0;
        String declaracion = textAreaDeclaracion.getText();
        String fecha = textFieldFecha.getText();

        // Validar que los campos no estén vacíos
        if (idInvestigacion == 0 || idTestigo == 0 || declaracion.isEmpty() || fecha.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor, complete todos los campos.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Crear un objeto InvestigacionTieneTestigo con los datos del formulario
        InvestigacionTieneTestigo nuevaInvestigacionTieneTestigo = new InvestigacionTieneTestigo();
        nuevaInvestigacionTieneTestigo.setIdInvestigacion(idInvestigacion);
        nuevaInvestigacionTieneTestigo.setIdTestigo(idTestigo);
        nuevaInvestigacionTieneTestigo.setDeclaracion(declaracion);
        nuevaInvestigacionTieneTestigo.setFecha(Date.valueOf(fecha));

        // Guardar el objeto InvestigacionTieneTestigo en la base de datos
        dispose();
        if(investigacionTieneTestigo != null){
            nuevaInvestigacionTieneTestigo.setId(investigacionTieneTestigo.getId());
            investigacionTieneTestigoDao.update(nuevaInvestigacionTieneTestigo);
        } else {
            investigacionTieneTestigoDao.insert(nuevaInvestigacionTieneTestigo);
        }
    }


}
