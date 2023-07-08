package org.proyecto.dto;

import java.sql.Date;
import java.sql.Time;

public class Crimen {
    private int id;
    private String descripcion;
    private Date fecha;
    private Time hora;
    private int idCategoria;

    public Crimen(){
    }
    public Crimen(int id, String descripcion, Date fecha, Time hora, int idCategoria){
        this.id = id;
        this.descripcion = descripcion;
        this.fecha = fecha;
        this.hora = hora;
        this.idCategoria = idCategoria;
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

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Time getHora() {
        return hora;
    }

    public void setHora(Time hora) {
        this.hora = hora;
    }

    public int getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(int idCategoria) {
        this.idCategoria = idCategoria;
    }

    @Override
    public String toString() {
        return "{"+"id:"+id+", descripcion:"+descripcion+", fecha:"+fecha+", hora:"+hora+", idCategoria:"+idCategoria+"}";
    }
}

