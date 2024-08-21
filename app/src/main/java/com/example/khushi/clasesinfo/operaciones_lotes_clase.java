package com.example.khushi.clasesinfo;

public class operaciones_lotes_clase {

     String producto, subparte, operaciones,empleado;
    //para otro constructor
    String nombre, apellido, completado,habilitado,  fecha;
    int id_lotes_operaciones, cantidad, id_operacione_subparte_producto, lotes,id_producto_oc;


    public operaciones_lotes_clase(String producto, String subparte, String operaciones, int idLotesOperaciones,
                                   int cantidad, String empleado, int idOperacionesSubparteProducto, int lote, int idProductoOc,  String completado) {
        this.producto = producto;
        this.subparte = subparte;
        this.operaciones = operaciones;
        this.id_lotes_operaciones = idLotesOperaciones;
        this.cantidad = cantidad;
        this.empleado= empleado;
        this.id_operacione_subparte_producto=idOperacionesSubparteProducto;
        this.lotes=lote;
        this.id_producto_oc=idProductoOc;
        this.completado=completado;

    }

    public operaciones_lotes_clase(String producto, String subparte, String operaciones, int idLotesOperaciones,
                                   int cantidad, String empleado, int idOperacionesSubparteProducto, int lote,
                                   int idProductoOc,  String completado, String nombre, String Apellido, String habilitado, String fecha) {
        this.producto = producto;
        this.subparte = subparte;
        this.operaciones = operaciones;
        this.id_lotes_operaciones = idLotesOperaciones;
        this.cantidad = cantidad;
        this.empleado= empleado;
        this.id_operacione_subparte_producto=idOperacionesSubparteProducto;
        this.lotes=lote;
        this.id_producto_oc=idProductoOc;
        this.completado=completado;
        this.nombre=nombre;
        this.apellido=Apellido;
        this.habilitado=habilitado;
        this.fecha=fecha;

    }







    public operaciones_lotes_clase(String producto, String subparte, String operaciones, int idLotesOperaciones, int cantidad, String empleado, int idOperacionesSubparteProducto, String nombreEmpleado, String apellidoEmpleado, int lote, int idProductoOc, String completado, String habilitado, String fecha) {

        this.producto = producto;
        this.subparte = subparte;
        this.operaciones = operaciones;
        this.id_lotes_operaciones = idLotesOperaciones;
        this.cantidad = cantidad;
        this.empleado= empleado;
        this.id_operacione_subparte_producto=idOperacionesSubparteProducto;
        this.lotes=lote;
        this.id_producto_oc=idProductoOc;
        this.completado=completado;
        this.fecha=fecha;
        this.nombre=nombreEmpleado;
        this.apellido=apellidoEmpleado;
        this.habilitado=habilitado;
    }



    public String getHabilitado() {
        return habilitado;
    }

    public void setHabilitado(String habilitado) {
        this.habilitado = habilitado;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getCompletado() {
        return completado;
    }

    public void setCompletado(String completado) {
        this.completado = completado;
    }

    public int getId_producto_oc() {
        return id_producto_oc;
    }

    public void setId_producto_oc(int id_producto_oc) {
        this.id_producto_oc = id_producto_oc;
    }

    public int getLotes() {
        return lotes;
    }

    public void setLotes(int lotes) {
        this.lotes = lotes;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getProducto() {
        return producto;
    }

    public void setProducto(String producto) {
        this.producto = producto;
    }

    public String getSubparte() {
        return subparte;
    }

    public void setSubparte(String subparte) {
        this.subparte = subparte;
    }

    public String getOperaciones() {
        return operaciones;
    }

    public void setOperaciones(String operaciones) {
        this.operaciones = operaciones;
    }

    public int getId_lotes_operaciones() {
        return id_lotes_operaciones;
    }

    public void setId_lotes_operaciones(int id_lotes_operaciones) {
        this.id_lotes_operaciones = id_lotes_operaciones;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public String getEmpleado() {
        return empleado;
    }

    public void setEmpleado(String empleado) {
        this.empleado = empleado;
    }

    public int getId_operacione_subparte_producto() {
        return id_operacione_subparte_producto;
    }

    public void setId_operacione_subparte_producto(int id_operacione_subparte_producto) {
        this.id_operacione_subparte_producto = id_operacione_subparte_producto;
    }
}
