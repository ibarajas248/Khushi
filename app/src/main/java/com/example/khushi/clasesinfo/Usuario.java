package com.example.khushi.clasesinfo;

public class Usuario {
    private int id_empleado,fijo, celular,anioVinculacion;
    private String nombre, Apellidos, correo_electronico,eps, rol, usuario, contrasenia;

    public Usuario(int id_empleado, int fijo, int celular, int anioVinculacion, String nombre,
                   String apellidos, String correo_electronico, String eps, String rol, String usuario,
                   String contrasenia) {
        this.id_empleado = id_empleado;
        this.fijo = fijo;
        this.celular = celular;
        this.anioVinculacion = anioVinculacion;
        this.nombre = nombre;
        Apellidos = apellidos;
        this.correo_electronico = correo_electronico;
        this.eps = eps;
        this.rol = rol;
        this.usuario = usuario;
        this.contrasenia = contrasenia;
    }

    public Usuario(int id_empleado, String rol, String usuario) {
        this.id_empleado = id_empleado;
        this.rol = rol;
        this.usuario = usuario;
    }

    public int getId_empleado() {
        return id_empleado;
    }

    public void setId_empleado(int id_empleado) {
        this.id_empleado = id_empleado;
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

    public int getAnioVinculacion() {
        return anioVinculacion;
    }

    public void setAnioVinculacion(int anioVinculacion) {
        this.anioVinculacion = anioVinculacion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return Apellidos;
    }

    public void setApellidos(String apellidos) {
        Apellidos = apellidos;
    }

    public String getCorreo_electronico() {
        return correo_electronico;
    }

    public void setCorreo_electronico(String correo_electronico) {
        this.correo_electronico = correo_electronico;
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
