package com.MiguelSantizo.AiresAcondicionados.controller;

import com.MiguelSantizo.AiresAcondicionados.model.Empleado;
import com.MiguelSantizo.AiresAcondicionados.service.EmpleadoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/empleados")
public class EmpleadoController {

    private final EmpleadoService empleadoService;

    public EmpleadoController(EmpleadoService empleadoService){
        this.empleadoService = empleadoService;
    }

    @GetMapping
    public List<Empleado> getEmpleados(){
        return empleadoService.getAllEmpleados();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getEmpleadoById(@PathVariable("id") Integer id){
        try{
            Empleado empleado = empleadoService.getEmpleadoById(id);
            if(empleado != null){
                return ResponseEntity.ok(empleado);
            }else{
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Empleado no encontrado con ID: " + id);
            }
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al buscar empleado: " + e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<Object> createEmpleado(@Valid @RequestBody Empleado empleado){
        try{
            Empleado createdEmpleado = empleadoService.saveEmpleado(empleado);
            return new ResponseEntity<>(createdEmpleado, HttpStatus.CREATED);
        }catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateEmpleado(@PathVariable("id") Integer id, @Valid @RequestBody Empleado empleado){
        try{
            Empleado updatedEmpleado = empleadoService.updateEmpleado(id, empleado);
            if(updatedEmpleado != null){
                return ResponseEntity.ok(updatedEmpleado);
            }else{
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Empleado no encontrado con ID: " + id);
            }
        }catch(IllegalArgumentException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al actualizar empleado: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteEmpleado(@PathVariable("id") Integer id){
        try{
            Empleado empleado = empleadoService.getEmpleadoById(id);
            if(empleado != null){
                empleadoService.deleteEmpleado(id);
                return ResponseEntity.ok("Empleado eliminado exitosamente");
            }else{
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Empleado no encontrado con ID: " + id);
            }
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al eliminar empleado: " + e.getMessage());
        }
    }
}