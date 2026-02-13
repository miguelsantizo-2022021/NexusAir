package com.MiguelSantizo.AiresAcondicionados.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "productos")
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_producto")
    private Integer id_producto;

    @Column(name = "nombre_producto")
    private String nombre_producto;

    @Column(name = "modelo")
    private String modelo;

    @Column(name = "categoria")
    private String categoria;

    @Column(name = "precio_compra")
    private Double precio_compra;

    @Column(name = "precio_venta")
    private Double precio_venta;

    @Column(name = "stock")
    private Integer stock;

    @Column(name = "id_proveedor")
    private Integer id_proveedor;

    public Producto() {
    }

    public Producto(Integer id_producto, String nombre_producto, String modelo, String categoria,
                    Double precio_compra, Double precio_venta, Integer stock, Integer id_proveedor) {
        this.id_producto = id_producto;
        this.nombre_producto = nombre_producto;
        this.modelo = modelo;
        this.categoria = categoria;
        this.precio_compra = precio_compra;
        this.precio_venta = precio_venta;
        this.stock = stock;
        this.id_proveedor = id_proveedor;
    }

    public Integer getId_producto() {
        return id_producto;
    }

    public void setId_producto(Integer id_producto) {
        this.id_producto = id_producto;
    }

    public String getNombre_producto() {
        return nombre_producto;
    }

    public void setNombre_producto(String nombre_producto) {
        this.nombre_producto = nombre_producto;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public Double getPrecio_compra() {
        return precio_compra;
    }

    public void setPrecio_compra(Double precio_compra) {
        this.precio_compra = precio_compra;
    }

    public Double getPrecio_venta() {
        return precio_venta;
    }

    public void setPrecio_venta(Double precio_venta) {
        this.precio_venta = precio_venta;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public Integer getId_proveedor() {
        return id_proveedor;
    }

    public void setId_proveedor(Integer id_proveedor) {
        this.id_proveedor = id_proveedor;
    }
}