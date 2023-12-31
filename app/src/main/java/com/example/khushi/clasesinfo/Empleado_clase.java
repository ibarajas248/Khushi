package com.example.khushi.clasesinfo;

public class Empleado_clase {

    private int  fijo, celular, anio_vinculacion;
    private String id, nombre, apellidos, correoElectronico, eps, rol, usuario, contrasenia;

    public Empleado_clase(String id, int fijo, int celular, int anio_vinculacion, String nombre, String apellidos, String correoElectronico, String eps, String rol, String usuario, String contrasenia) {
        this.id = String.valueOf(id);
        this.fijo = fijo;
        this.celular = celular;
        this.anio_vinculacion = anio_vinculacion;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.correoElectronico = correoElectronico;
        this.eps = eps;
        this.rol = rol;
        this.usuario = usuario;
        this.contrasenia = contrasenia;
    }

    public Empleado_clase(String id, String nombre, String apellidos) {
        this.id = String.valueOf(id);
        this.nombre = nombre;
        this.apellidos = apellidos;
    }

    public String getId() {
        return id;
    }

    public void setId(int id) {
        this.id = String.valueOf(id);
    }

    public int getFijo() {
        return fijo;
    }

    public void setFijo(int fijo) {
        this.fijo = fijo;
    }

    public int getCelular() {
        return celular;
    }

    public void setCelular(int celular) {
        this.celular = celular;
    }

    public int getAnio_vinculacion() {
        return anio_vinculacion;
    }

    public void setAnio_vinculacion(int anio_vinculacion) {
        this.anio_vinculacion = anio_vinculacion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getCorreoElectronico() {
        return correoElectronico;
    }

    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }

    public String getEps() {
        return eps;
    }

    public void setEps(String eps) {
        this.eps = eps;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getContrasenia() {
        return contrasenia;
    }

    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }
}
