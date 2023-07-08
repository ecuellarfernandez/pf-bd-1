package org.proyecto.gui;

import org.proyecto.dao.*;
import org.proyecto.dao.impl.*;
import org.proyecto.dto.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainFrame extends JFrame {
    private JComboBox<String> comboBoxTablas;
    private DefaultListModel<String> listModel;
    private JList<String> listTabla;
    private JButton btnInsertar;
    private JButton btnActualizar;
    private JButton btnEliminar;

    // Instancias de los DAO
    private AgenteDao agenteDao;
    private CategoriaCrimenDao categoriaCrimenDao;
    private CrimenDao crimenDao;
    private DepartamentoDao departamentoDao;
    private EquipoDao equipoDao;
    private InvestigacionDao investigacionDao;
    private InvestigacionTieneTestigoDao investigacionTieneTestigoDao;
    private OrdenDeBusquedaDao ordenDeBusquedaDao;
    private PruebaDao pruebaDao;
    private RangoDao rangoDao;
    private SospechosoDao sospechosoDao;
    private SospechosoTieneCrimenDao sospechosoTieneCrimenDao;
    private TestigoDao testigoDao;
    private String entidadSeleccionada = "";
    public MainFrame() {
        JFrame ref = this;
        agenteDao = new AgenteDaoMySql();
        categoriaCrimenDao = new CategoriaDaoMySql();
        crimenDao = new CrimenDaoMySql();
        departamentoDao = new DepartamentoDaoMySql();
        equipoDao = new EquipoDaoMySql();
        investigacionDao = new InvestigacionDaoMySql();
        investigacionTieneTestigoDao = new InvestigacionTieneTestigoDaoMySql();
        ordenDeBusquedaDao = new OrdenDeBusquedaDaoMySql();
        pruebaDao = new PruebaDaoMySql();
        rangoDao = new RangoDaoMySql();
        sospechosoDao = new SospechosoDaoMySql();
        sospechosoTieneCrimenDao = new SospechosoTieneCrimenDaoMySql();
        testigoDao = new TestigoDaoMySql();

        // Configuración de la ventana principal
        setTitle("FBI");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Configuración de la lista de tablas
        listModel = new DefaultListModel<>();
        listTabla = new JList<>(listModel);
        listTabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(listTabla);
        scrollPane.setPreferredSize(new Dimension(800, 400));
        add(scrollPane, BorderLayout.WEST);
        setResizable(false);

        // Configuración del JComboBox
        comboBoxTablas = new JComboBox<>();
        comboBoxTablas.addItem("Agente");
        comboBoxTablas.addItem("CategoriaCrimen");
        comboBoxTablas.addItem("Crimen");
        comboBoxTablas.addItem("Departamento");
        comboBoxTablas.addItem("Equipo");
        comboBoxTablas.addItem("Investigacion");
        comboBoxTablas.addItem("InvestigacionTieneTestigo");
        comboBoxTablas.addItem("OrdenDeBusqueda");
        comboBoxTablas.addItem("Prueba");
        comboBoxTablas.addItem("Rango");
        comboBoxTablas.addItem("Sospechoso");
        comboBoxTablas.addItem("SospechosoTieneCrimen");
        comboBoxTablas.addItem("Testigo");

        entidadSeleccionada = (String) comboBoxTablas.getSelectedItem();
        try {
            cargarTabla(entidadSeleccionada);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        comboBoxTablas.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedTable = (String) comboBoxTablas.getSelectedItem();

                listModel.clear();
                entidadSeleccionada = (String) comboBoxTablas.getSelectedItem();
                try {
                    cargarTabla(entidadSeleccionada);
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        add(comboBoxTablas, BorderLayout.NORTH);

        //BOTONES ==============================================================
        JPanel panelBotones = new JPanel();
        JButton insertar = new JButton("Insertar");
        JButton actualizar = new JButton("Actualizar");
        JButton eliminar = new JButton("Eliminar");
        panelBotones.add(insertar);
        panelBotones.add(actualizar);
        panelBotones.add(eliminar);

        add(panelBotones, BorderLayout.SOUTH);

        insertar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Boton accionado insertar");
                try {
                    insertarRegistro(entidadSeleccionada);
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        actualizar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    actualizarRegistro(entidadSeleccionada, listTabla.getSelectedValue());
                } catch (Exception ex) {
                    //mostrar dialogo de error
                    JOptionPane.showMessageDialog(MainFrame.this,
                            "Error al actualizar el registro, no hay ningún registro seleccionado:\n" + ex.getMessage(), "Error",
                            JOptionPane.ERROR_MESSAGE);
                    throw new RuntimeException(ex);
                }
            }
        });

        eliminar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    eliminarRegistro(entidadSeleccionada, listTabla.getSelectedValue());
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        setVisible(true);
    }

    private void eliminarRegistro(String entidadSeleccionada, String registro) throws Exception {
        int id = obtenerIdDeRegistro(registro);
        switch (entidadSeleccionada) {
            case "Agente":
                agenteDao.delete(id);
                break;
            case "Rango":
                rangoDao.delete(id);
                break;
            case "CategoriaCrimen":
                categoriaCrimenDao.delete(id);
                break;
            case "Crimen":
                crimenDao.delete(id);
                break;
            case "Departamento":
                departamentoDao.delete(id);
                break;
            case "Equipo":
                equipoDao.delete(id);
                break;
            case "Investigacion":
                investigacionDao.delete(id);
                break;
            case "InvestigacionTieneTestigo":
                investigacionTieneTestigoDao.delete(id);
                break;
            case "OrdenDeBusqueda":
                ordenDeBusquedaDao.delete(id);
                break;
            case "Prueba":
                pruebaDao.delete(id);
                break;
            case "Sospechoso":
                sospechosoDao.delete(id);
                break;
            case "SospechosoTieneCrimen":
                sospechosoTieneCrimenDao.delete(id);
                break;
            case "Testigo":
                testigoDao.delete(id);
                break;
            default:
                throw new IllegalArgumentException("Entidad no válida: " + entidadSeleccionada);
        }
        cargarTabla(entidadSeleccionada);
    }
        private int obtenerIdDeRegistro(String registro) {
            System.out.println("Registro: " + registro);
            String regex = "id[:=]['\"]?(\\w+)['\"]?";
            Pattern patron = Pattern.compile(regex);
            Matcher matcher = patron.matcher(registro);
            if (matcher.find()) {
                String id = matcher.group(1);
                System.out.println("ID encontrado: " + id);
                return Integer.parseInt(id);
            } else {
                System.out.println("No se encontró ningún id");
                return -1;
            }
        }



    private void actualizarRegistro(String entidadSeleccionada, String registro) throws Exception {
        int id = obtenerIdDeRegistro(registro);
        switch (entidadSeleccionada){
            case "Agente":
                Agente agente = agenteDao.get(id);
                new AgenteForm(this, agente);
                break;
            case "Rango":
                Rango rango = rangoDao.get(id);
                new RangoForm(this, rango);
                break;
            case "CategoriaCrimen":
                CategoriaCrimen categoriaCrimen = categoriaCrimenDao.get(id);
                new CategoriaCrimenForm(this, categoriaCrimen);
                break;
            case "Crimen":
                Crimen crimen = crimenDao.get(id);
                new CrimenForm(this, crimen);
                break;
            case "Departamento":
                Departamento departamento = departamentoDao.get(id);
                new DepartamentoForm(this, departamento);
                break;
            case "Equipo":
                Equipo equipo = equipoDao.get(id);
                new EquipoForm(this, equipo);
                break;
            case "Investigacion":
                Investigacion investigacion = investigacionDao.get(id);
                new InvestigacionForm(this, investigacion);
                break;
            case "InvestigacionTieneTestigo":
                InvestigacionTieneTestigo investigacionTieneTestigo = investigacionTieneTestigoDao.get(id);
                new InvestigacionTieneTestigoForm(this, investigacionTieneTestigo);
                break;
            case "OrdenDeBusqueda":
                OrdenDeBusqueda ordenDeBusqueda = ordenDeBusquedaDao.get(id);
                new OrdenDeBusquedaForm(this, ordenDeBusqueda);
                break;
            case "Prueba":
                Prueba prueba = pruebaDao.get(id);
                new PruebaForm(this, prueba);
                break;
            case "Sospechoso":
                Sospechoso sospechoso = sospechosoDao.get(id);
                new SospechosoForm(this, sospechoso);
                break;
            case "SospechosoTieneCrimen":
                SospechosoTieneCrimen sospechosoTieneCrimen = sospechosoTieneCrimenDao.get(id);
                new SospechosoTieneCrimenForm(this, sospechosoTieneCrimen);
                break;
            case "Testigo":
                Testigo testigo = testigoDao.get(id);
                new TestigoForm(this, testigo);
                break;
            default:
                throw new IllegalArgumentException("Entidad no válida: " + entidadSeleccionada);
        }
        cargarTabla(entidadSeleccionada);
    }

    private void insertarRegistro(String entidadSeleccionada) throws Exception {
        switch (entidadSeleccionada) {
            case "Agente":
                new AgenteForm(this, null);
                break;
            case "Rango":
                new RangoForm(this, null);
                break;
            case "CategoriaCrimen":
                new CategoriaCrimenForm(this, null);
                break;
            case "Crimen":
                new CrimenForm(this, null);
                break;
            case "Departamento":
                new DepartamentoForm(this, null);
                break;
            case "Equipo":
                new EquipoForm(this, null);
                break;
            case "Investigacion":
                new InvestigacionForm(this, null);
                break;
            case "InvestigacionTieneTestigo":
                new InvestigacionTieneTestigoForm(this, null);
                break;
            case "OrdenDeBusqueda":
                new OrdenDeBusquedaForm(this, null);
                break;
            case "Prueba":
                new PruebaForm(this, null);
                break;
            case "Sospechoso":
                new SospechosoForm(this, null);
                break;
            case "SospechosoTieneCrimen":
                new SospechosoTieneCrimenForm(this, null);
                break;
            case "Testigo":
                new TestigoForm(this, null);
                break;
            default:
                throw new IllegalArgumentException("Entidad no válida: " + entidadSeleccionada);
        }
        cargarTabla(entidadSeleccionada);
    }
    private void cargarTabla(String entidad) throws Exception {
        listModel.clear();
        System.out.println("Cargando tabla: " + entidad + "...");
        switch (entidad){
            case "Agente":
                agenteDao.getList().forEach(agente -> listModel.addElement(agente.toString()));
                break;
            case "CategoriaCrimen":
                categoriaCrimenDao.getList().forEach(categoriaCrimen -> listModel.addElement(categoriaCrimen.toString()));
                break;
            case "Crimen":
                crimenDao.getList().forEach(crimen -> listModel.addElement(crimen.toString()));
                break;
            case "Departamento":
                departamentoDao.getList().forEach(departamento -> listModel.addElement(departamento.toString()));
                break;
            case "Equipo":
                equipoDao.getList().forEach(equipo -> listModel.addElement(equipo.toString()));
                break;
            case "Investigacion":
                investigacionDao.getList().forEach(investigacion -> listModel.addElement(investigacion.toString()));
                break;
            case "InvestigacionTieneTestigo":
                investigacionTieneTestigoDao.getList().forEach(investigacionTieneTestigo -> listModel.addElement(investigacionTieneTestigo.toString()));
                break;
            case "OrdenDeBusqueda":
                ordenDeBusquedaDao.getList().forEach(ordenDeBusqueda -> listModel.addElement(ordenDeBusqueda.toString()));
                break;
            case "Prueba":
                pruebaDao.getList().forEach(prueba -> listModel.addElement(prueba.toString()));
                break;
            case "Rango":
                rangoDao.getList().forEach(rango -> listModel.addElement(rango.toString()));
                break;
            case "Sospechoso":
                sospechosoDao.getList().forEach(sospechoso -> listModel.addElement(sospechoso.toString()));
                break;
            case "SospechosoTieneCrimen":
                sospechosoTieneCrimenDao.getList().forEach(sospechosoTieneCrimen -> listModel.addElement(sospechosoTieneCrimen.toString()));
                break;
            case "Testigo":
                testigoDao.getList().forEach(testigo -> listModel.addElement(testigo.toString()));
                break;
        }
    }

}