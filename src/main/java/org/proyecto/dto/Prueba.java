package org.proyecto.dto;

public class Prueba {
    private int id;
    private String descripcion;
    private int idCrimen;

    public Prueba() {
    }

    public Prueba(int id, String descripcion, int idCrimen) {
        this.id = id;
        this.descripcion = descripcion;
        this.idCrimen = idCrimen;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getIdCrimen() {
        return idCrimen;
    }

    public void setIdCrimen(int idCrimen) {
        this.idCrimen = idCrimen;
    }

    @Override
    public String toString() {
        return "Prueba{" +
                "id=" + id +
                ", descripcion='" + descripcion + '\'' +
                ", idCrimen=" + idCrimen +
                '}';
    }
}
