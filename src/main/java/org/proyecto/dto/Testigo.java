package org.proyecto.dto;

public class Testigo {
    private int id;
    private int ciTestigo;
    private String nombre;
    private String apellidoPaterno;
    private String apellidoMaterno;
    public Testigo() {
    }
    public Testigo(int id, int ciTestigo, String nombre, String apellidoPaterno, String apellidoMaterno) {
        this.id = id;
        this.ciTestigo = ciTestigo;
        this.nombre = nombre;
        this.apellidoPaterno = apellidoPaterno;
        this.apellidoMaterno = apellidoMaterno;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCiTestigo() {
        return ciTestigo;
    }

    public void setCiTestigo(int ciTestigo) {
        this.ciTestigo = ciTestigo;
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
        return "Testigo{" +
                "id=" + id +
                ", ciTestigo=" + ciTestigo +
                ", nombre='" + nombre + '\'' +
                ", apellidoPaterno='" + apellidoPaterno + '\'' +
                ", apellidoMaterno='" + apellidoMaterno + '\'' +
                '}';
    }
}
