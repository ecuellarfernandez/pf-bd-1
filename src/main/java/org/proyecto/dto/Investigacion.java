package org.proyecto.dto;

import java.sql.Date;

public class Investigacion {
    private int id;
    private Date fechaInicio;
    private Date fechaFin;
    private int idAgente;
    private int idCrimen;

    public Investigacion() {
    }
    public Investigacion(int id, Date fechaInicio, Date fechaFin, int idAgente, int idCrimen) {
        this.id = id;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.idAgente = idAgente;
        this.idCrimen = idCrimen;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    public int getIdAgente() {
        return idAgente;
    }

    public void setIdAgente(int idAgente) {
        this.idAgente = idAgente;
    }

    public int getIdCrimen() {
        return idCrimen;
    }

    public void setIdCrimen(int idCrimen) {
        this.idCrimen = idCrimen;
    }

    @Override
    public String toString() {
        return "{"+"id:"+id+", fechaInicio:"+fechaInicio+", fechaFin:"+fechaFin+", idAgente:"+idAgente+", idCrimen:"+idCrimen+"}";
    }
}
