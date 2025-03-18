package com.example.models;

public class PersonaDTO {
    private long id;
    private String nombre, appellidos, correo, sexo, ip;

    public PersonaDTO(long id, String nombre, String appellidos, String correo, String sexo, String ip) {
        this.id = id;
        this.nombre = nombre;
        this.appellidos = appellidos;
        this.correo = correo;
        this.sexo = sexo;
        this.ip = ip;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getAppellidos() {
        return appellidos;
    }

    public void setAppellidos(String appellidos) {
        this.appellidos = appellidos;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (int) (id ^ (id >>> 32));
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        PersonaDTO other = (PersonaDTO) obj;
        if (id != other.id)
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "PersonaDTO [id=" + id + ", nombre=" + nombre + ", appellidos=" + appellidos + ", correo=" + correo
                + ", sexo=" + sexo + ", ip=" + ip + "]";
    }       
}
