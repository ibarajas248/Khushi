package com.example.khushi.clasesinfo;

public class Filtro {
    private String campo;  // Nombre del campo a filtrar
    private String valor;  // Valor a buscar

    public Filtro(String campo, String valor) {
        this.campo = campo;
        this.valor = valor;
    }

    public String getCampo() {
        return campo.toLowerCase(); // Convertir a minúsculas para asegurar consistencia
    }

    public String getValor() {
        return valor;
    }
}
