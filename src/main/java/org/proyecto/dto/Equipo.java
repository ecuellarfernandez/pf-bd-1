package org.proyecto.dto;

public class Equipo {
    private int id;
    private String nombre;

    public Equipo() {
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
        return "{"+"id="+id+", nombre:"+nombre+"}";
    }
}
