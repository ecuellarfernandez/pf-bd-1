package org.proyecto.dto;

import java.util.Date;

public class Rango {
    private int id;
    private String nombre;

    public Rango() {
    }

    public Rango(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public String toString() {
        return "Rango{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                '}';
    }
}
