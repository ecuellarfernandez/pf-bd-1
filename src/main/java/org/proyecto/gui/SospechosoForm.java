package org.proyecto.gui;

import org.proyecto.dao.SospechosoDao;
import org.proyecto.dao.impl.SospechosoDaoMySql;
import org.proyecto.dto.Sospechoso;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SospechosoForm extends JDialog {

    private SospechosoDao sospechosoDao = new SospechosoDaoMySql();
    private Sospechoso sospechoso;
    private JTextField textFieldNombre;
    private JTextField textFieldApellidoPaterno;
    private JTextField textFieldApellidoMaterno;
    private JTextField textFieldCI;

    public SospechosoForm(JFrame parent, Sospechoso sospechoso) {
        super(parent, "Formulario de Sospechoso", true);
        this.sospechoso = sospechoso;

        // Crear los componentes del formulario
        JLabel labelNombre = new JLabel("Nombre:");
        JLabel labelApellidoPaterno = new JLabel("Apellido Paterno:");
        JLabel labelApellidoMaterno = new JLabel("Apellido Materno:");
        JLabel labelCI = new JLabel("CI:");

        textFieldNombre = new JTextField(20);
        textFieldApellidoPaterno = new JTextField(20);
        textFieldApellidoMaterno = new JTextField(20);
        textFieldCI = new JTextField(20);

        JButton buttonGuardar = new JButton("Guardar");
        buttonGuardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    guardarSospechoso();
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
        panel.add(labelCI, constraints);

        constraints.gridx = 1;
        panel.add(textFieldCI, constraints);

        constraints.gridx = 0;
        constraints.gridy = 4;
        constraints.gridwidth = 2;
        constraints.anchor = GridBagConstraints.CENTER;
        panel.add(buttonGuardar, constraints);

        constraints.gridx = 0;
        constraints.gridy = 5;
        panel.add(buttonCancelar, constraints);

        // Establecer el panel como contenido del diálogo
        getContentPane().add(panel);
        pack();
        setLocationRelativeTo(parent);

        // Si se proporciona un sospechoso existente, llenar el formulario con sus datos
        if (sospechoso != null) {
            llenarFormulario();
        }
        setVisible(true);
    }

    private void llenarFormulario() {
        textFieldNombre.setText(sospechoso.getNombre());
        textFieldApellidoPaterno.setText(sospechoso.getApellidoPaterno());
        textFieldApellidoMaterno.setText(sospechoso.getApellidoMaterno());
        textFieldCI.setText(String.valueOf(sospechoso.getCiSospechoso()));
    }

    private void guardarSospechoso() throws Exception {
        String nombre = textFieldNombre.getText();
        String apellidoPaterno = textFieldApellidoPaterno.getText();
        String apellidoMaterno = textFieldApellidoMaterno.getText();
        String ci = textFieldCI.getText();

        // Validar que los campos no estén vacíos
        if (nombre.isEmpty() || apellidoPaterno.isEmpty() || apellidoMaterno.isEmpty() || ci.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor, complete todos los campos.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Crear un objeto Sospechoso con los datos del formulario
        Sospechoso nuevoSospechoso = new Sospechoso();
        nuevoSospechoso.setNombre(nombre);
        nuevoSospechoso.setApellidoPaterno(apellidoPaterno);
        nuevoSospechoso.setApellidoMaterno(apellidoMaterno);
        nuevoSospechoso.setCiSospechoso(Integer.parseInt(ci));

        // Guardar el sospechoso en la base de datos
        if(sospechoso != null){
            nuevoSospechoso.setId(sospechoso.getId());
            sospechosoDao.update(nuevoSospechoso);
        }else {
            sospechosoDao.insert(nuevoSospechoso);
        }

        dispose();
        }
    }

