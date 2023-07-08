package org.proyecto.dto;

import java.sql.Date;

public class InvestigacionTieneTestigo {
    private int id;
    private int idInvestigacion;
    private int idTestigo;
    private String declaracion;
    private Date fecha;

    public InvestigacionTieneTestigo() {
    }

    public InvestigacionTieneTestigo(int id, int idInvestigacion, int idTestigo, String declaracion, Date fecha) {
        this.id = id;
        this.idInvestigacion = idInvestigacion;
        this.idTestigo = idTestigo;
        this.declaracion = declaracion;
        this.fecha = fecha;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdInvestigacion() {
        return idInvestigacion;
    }

    public void setIdInvestigacion(int idInvestigacion) {
        this.idInvestigacion = idInvestigacion;
    }

    public int getIdTestigo() {
        return idTestigo;
    }

    public void setIdTestigo(int idTestigo) {
        this.idTestigo = idTestigo;
    }

    public String getDeclaracion() {
        return declaracion;
    }

    public void setDeclaracion(String declaracion) {
        this.declaracion = declaracion;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    @Override
    public String toString() {
        return "{" +
            " id=" + getId() +
            ", idInvestigacion='" + getIdInvestigacion() + "'" +
            ", idTestigo='" + getIdTestigo() + "'" +
            ", declaracion='" + getDeclaracion() + "'" +
            ", fecha='" + getFecha() + "'" +
            "}";
    }
}
