package org.proyecto.dto;

public class Departamento {
    private int id;
    private String nombre;
    private String descripcion;
    private int idAdministador;

    public Departamento(){
    }

    public Departamento(int id, String nombre, String descripcion, int idAdministador){
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.idAdministador = idAdministador;
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

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getIdAdministador() {
        return idAdministador;
    }

    public void setIdAdministador(int idAdministador) {
        this.idAdministador = idAdministador;
    }

    @Override
    public String toString() {
        return "{"+"id:"+id+", nombre:"+nombre+", descripcion:"+descripcion+", idAdministador:"+idAdministador+"}";
    }
}
