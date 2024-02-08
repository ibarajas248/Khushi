package com.example.khushi.clasesinfo;

public class menuClase {
    private String titulo;
    private int foto;

    public menuClase(String titulo) {
        this.titulo = titulo;
    }

    public menuClase(String titulo, int foto) {
        this.titulo = titulo;
        this.foto = foto;
    }

    public int getFoto() {
        return foto;
    }

    public void setFoto(int foto) {
        this.foto = foto;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
}
