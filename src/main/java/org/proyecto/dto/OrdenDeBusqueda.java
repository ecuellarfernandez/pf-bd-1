package org.proyecto.dto;

public class OrdenDeBusqueda {
    private int id;
    private boolean estado;
    private int idAgente;
    private int idSospechoso;
    private int idInvestigacion;

    public OrdenDeBusqueda() {
    }

    public OrdenDeBusqueda(int id, boolean estado, int idAgente, int idSospechoso, int idInvestigacion) {
        this.id = id;
        this.estado = estado;
        this.idAgente = idAgente;
        this.idSospechoso = idSospechoso;
        this.idInvestigacion = idInvestigacion;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean getEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public int getIdAgente() {
        return idAgente;
    }

    public void setIdAgente(int idAgente) {
        this.idAgente = idAgente;
    }

    public int getIdSospechoso() {
        return idSospechoso;
    }

    public void setIdSospechoso(int idSospechoso) {
        this.idSospechoso = idSospechoso;
    }

    public int getIdInvestigacion() {
        return idInvestigacion;
    }

    public void setIdInvestigacion(int idInvestigacion) {
        this.idInvestigacion = idInvestigacion;
    }

    @Override
    public String toString() {
        return "{"+"id:"+id+", estado:"+estado+", idAgente:"+idAgente+", idSospechoso:"+idSospechoso+", idInvestigacion:"+idInvestigacion+"}";
    }
}
