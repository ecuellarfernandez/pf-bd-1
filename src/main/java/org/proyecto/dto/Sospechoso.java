package org.proyecto.dto;

import org.proyecto.dao.SospechosoDao;

public class Sospechoso {
    private int id;
    private int ciSospechoso;
    private String nombre;
    private String apellidoPaterno;
    private String apellidoMaterno;

    public Sospechoso(int id, int ciSospechoso, String nombre, String apellidoPaterno, String apellidoMaterno) {
        this.id = id;
        this.ciSospechoso = ciSospechoso;
        this.nombre = nombre;
        this.apellidoPaterno = apellidoPaterno;
        this.apellidoMaterno = apellidoMaterno;
    }

    public Sospechoso(){
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCiSospechoso() {
        return ciSospechoso;
    }

    public void setCiSospechoso(int ciSospechoso) {
        this.ciSospechoso = ciSospechoso;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidoPaterno() {
        return apellidoPaterno;
    }

    public void setApellidoPaterno(String apellidoPaterno) {
        this.apellidoPaterno = apellidoPaterno;
    }

    public String getApellidoMaterno() {
        return apellidoMaterno;
    }

    public void setApellidoMaterno(String apellidoMaterno) {
        this.apellidoMaterno = apellidoMaterno;
    }

    @Override
    public String toString() {
        return "Sospechoso{" +
                "id=" + id +
                ", ciSospechoso=" + ciSospechoso +
                ", nombre='" + nombre + '\'' +
                ", apellidoPaterno='" + apellidoPaterno + '\'' +
                ", apellidoMaterno='" + apellidoMaterno + '\'' +
                '}';
    }
}
