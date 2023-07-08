package org.proyecto.dto;

public class SospechosoTieneCrimen{
    private int id;
    private int idCrimen;
    private int idSospechoso;

    public SospechosoTieneCrimen() {
    }

    public SospechosoTieneCrimen(int id, int idCrimen, int idSospechoso) {
        this.id = id;
        this.idCrimen = idCrimen;
        this.idSospechoso = idSospechoso;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdCrimen() {
        return idCrimen;
    }

    public void setIdCrimen(int idCrimen) {
        this.idCrimen = idCrimen;
    }

    public int getIdSospechoso() {
        return idSospechoso;
    }

    public void setIdSospechoso(int idSospechoso) {
        this.idSospechoso = idSospechoso;
    }

    @Override
    public String toString() {
        return "SospechosoTieneCrimen{" +
                "id=" + id +
                ", idCrimen=" + idCrimen +
                ", idSospechoso=" + idSospechoso +
                '}';
    }
}
