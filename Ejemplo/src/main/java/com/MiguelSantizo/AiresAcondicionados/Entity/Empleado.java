package com.MiguelSantizo.AiresAcondicionados.model;

import jakarta.persistence.*;

@Entity
@Table(name = "Empleados")
public class Empleado {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_empleado")
    private Integer id_empleado;

    @Column(name = "nombre_empleado")
    private String nombre_empleado;

    @Column(name = "apellido_empleado")
    private String apellido_empleado;

    @Column(name = "puesto_empleado")
    private String puesto_empleado;

    @Column(name = "email_empleado")
    private String email_empleado;


    //generar getters y setters

    public Integer getIdEmpleado() {
        return id_empleado;
    }

    public void setIdEmpleado(Integer idEmpleado) {
        this.id_empleado = idEmpleado;
    }

    public String getApellido_empleado() {
        return apellido_empleado;
    }

    public void setApellido_empleado(String apellido_empleado) {
        this.apellido_empleado = apellido_empleado;
    }

    public String getNombre_empleado() {
        return nombre_empleado;
    }

    public void setNombre_empleado(String nombre_empleado) {
        this.nombre_empleado = nombre_empleado;
    }

    public String getPuesto_empleado() {
        return puesto_empleado;
    }

    public void setPuesto_empleado(String puesto_empleado) {
        this.puesto_empleado = puesto_empleado;
    }

    public String getEmail_empleado() {
        return email_empleado;
    }

    public void setEmail_empleado(String email_empleado) {
        this.email_empleado = email_empleado;
    }
}


