package com.MiguelSantizo.AiresAcondicionados.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "ventas")
public class Venta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_venta")
    private Integer id_venta;

    @Column(name = "fecha_venta")
    private LocalDate fecha_venta;

    @Column(name = "cantidad")
    private Integer cantidad;

    @Column(name = "total")
    private Double total;

    @Column(name = "id_empleado")
    private Integer id_empleado;

    @Column(name = "id_producto")
    private Integer id_producto;

    public Venta() {
    }

    public Venta(Integer id_venta, LocalDate fecha_venta, Integer cantidad, Double total,
                 Integer id_empleado, Integer id_producto) {
        this.id_venta = id_venta;
        this.fecha_venta = fecha_venta;
        this.cantidad = cantidad;
        this.total = total;
        this.id_empleado = id_empleado;
        this.id_producto = id_producto;
    }

    public Integer getId_venta() {
        return id_venta;
    }

    public void setId_venta(Integer id_venta) {
        this.id_venta = id_venta;
    }

    public LocalDate getFecha_venta() {
        return fecha_venta;
    }

    public void setFecha_venta(LocalDate fecha_venta) {
        this.fecha_venta = fecha_venta;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public Integer getId_empleado() {
        return id_empleado;
    }

    public void setId_empleado(Integer id_empleado) {
        this.id_empleado = id_empleado;
    }

    public Integer getId_producto() {
        return id_producto;
    }

    public void setId_producto(Integer id_producto) {
        this.id_producto = id_producto;
    }
}