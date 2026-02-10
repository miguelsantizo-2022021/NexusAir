package com.MiguelSantizo.AiresAcondicionados.service;

import com.MiguelSantizo.AiresAcondicionados.Entity.Empleado;
import com.MiguelSantizo.AiresAcondicionados.repository.EmpleadoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmpleadoServiceImplements implements EmpleadoService {
    private final EmpleadoRepository empleadoRepository;

    public EmpleadoServiceImplements(EmpleadoRepository empleadoRepository){
        this.empleadoRepository = empleadoRepository;
    }

    @Override
    public List<Empleado> getAllEmpleados() {
        return empleadoRepository.findAll();
    }

    @Override
    public Empleado getEmpleadoById(Integer id) {
        return empleadoRepository.findById(id).orElse(null);
    }

    @Override
    public Empleado saveEmpleado(Empleado empleado) throws RuntimeException {
        if(empleado.getNombre_empleado() == null || empleado.getNombre_empleado().trim().isEmpty()){
            throw new IllegalArgumentException("El nombre del empleado es requerido");
        }
        if(empleado.getApellido_empleado() == null || empleado.getApellido_empleado().trim().isEmpty()){
            throw new IllegalArgumentException("El apellido del empleado es requerido");
        }
        if(empleado.getEmail_empleado() == null || empleado.getEmail_empleado().trim().isEmpty()){
            throw new IllegalArgumentException("El email del empleado es requerido");
        }
        return empleadoRepository.save(empleado);
    }

    @Override
    public Empleado updateEmpleado(Integer id, Empleado empleado) {
        Empleado empleadoExistente = empleadoRepository.findById(id).orElse(null);

        if(empleadoExistente == null){
            return null;
        }

        if(empleado.getNombre_empleado() != null && !empleado.getNombre_empleado().trim().isEmpty()){
            empleadoExistente.setNombre_empleado(empleado.getNombre_empleado());
        }

        if(empleado.getApellido_empleado() != null && !empleado.getApellido_empleado().trim().isEmpty()){
            empleadoExistente.setApellido_empleado(empleado.getApellido_empleado());
        }

        if(empleado.getPuesto_empleado() != null){
            empleadoExistente.setPuesto_empleado(empleado.getPuesto_empleado());
        }

        if(empleado.getEmail_empleado() != null && !empleado.getEmail_empleado().trim().isEmpty()){
            empleadoExistente.setEmail_empleado(empleado.getEmail_empleado());
        }

        return empleadoRepository.save(empleadoExistente);
    }

    @Override
    public void deleteEmpleado(Integer id) {
        empleadoRepository.deleteById(id);
    }
}