package org.proyecto.gui;

import org.proyecto.dao.TestigoDao;
import org.proyecto.dao.impl.TestigoDaoMySql;
import org.proyecto.dto.Testigo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TestigoForm extends JDialog {

    private TestigoDao testigoDao = new TestigoDaoMySql();
    private Testigo testigo;
    private JTextField textFieldCI;
    private JTextField textFieldNombre;
    private JTextField textFieldApellidoPaterno;
    private JTextField textFieldApellidoMaterno;

    public TestigoForm(JFrame parent, Testigo testigo) {
        super(parent, "Formulario de Testigo", true);
        this.testigo = testigo;

        // Crear los componentes del formulario
        JLabel labelCI = new JLabel("CI:");
        JLabel labelNombre = new JLabel("Nombre:");
        JLabel labelApellidoPaterno = new JLabel("Apellido Paterno:");
        JLabel labelApellidoMaterno = new JLabel("Apellido Materno:");

        textFieldCI = new JTextField(20);
        textFieldNombre = new JTextField(20);
        textFieldApellidoPaterno = new JTextField(20);
        textFieldApellidoMaterno = new JTextField(20);

        JButton buttonGuardar = new JButton("Guardar");
        buttonGuardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    guardarTestigo();
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
        panel.add(labelCI, constraints);

        constraints.gridx = 1;
        panel.add(textFieldCI, constraints);

        constraints.gridx = 0;
        constraints.gridy = 1;
        panel.add(labelNombre, constraints);

        constraints.gridx = 1;
        panel.add(textFieldNombre, constraints);

        constraints.gridx = 0;
        constraints.gridy = 2;
        panel.add(labelApellidoPaterno, constraints);

        constraints.gridx = 1;
        panel.add(textFieldApellidoPaterno, constraints);

        constraints.gridx = 0;
        constraints.gridy = 3;
        panel.add(labelApellidoMaterno, constraints);

        constraints.gridx = 1;
        panel.add(textFieldApellidoMaterno, constraints);

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

        // Si se proporciona un testigo existente, llenar el formulario con sus datos
        if (testigo != null) {
            llenarFormulario();
        }
        setVisible(true);
    }

    private void llenarFormulario() {
        textFieldCI.setText(String.valueOf(testigo.getCiTestigo()));
        textFieldNombre.setText(testigo.getNombre());
        textFieldApellidoPaterno.setText(testigo.getApellidoPaterno());
        textFieldApellidoMaterno.setText(testigo.getApellidoMaterno());
    }

    private void guardarTestigo() throws Exception {
        String ci = textFieldCI.getText();
        String nombre = textFieldNombre.getText();
        String apellidoPaterno = textFieldApellidoPaterno.getText();
        String apellidoMaterno = textFieldApellidoMaterno.getText();

        // Crear un objeto Testigo con los datos del formulario
        Testigo nuevoTestigo = new Testigo();
        nuevoTestigo.setCiTestigo(Integer.parseInt(ci));
        nuevoTestigo.setNombre(nombre);
        nuevoTestigo.setApellidoPaterno(apellidoPaterno);
        nuevoTestigo.setApellidoMaterno(apellidoMaterno);

        if(testigo != null){
            nuevoTestigo.setId(testigo.getId());
            testigoDao.update(nuevoTestigo);
        }else {
            testigoDao.insert(nuevoTestigo);
        }
        dispose();
    }
}
