package com.MiguelSantizo.AiresAcondicionados.controller;

import com.MiguelSantizo.AiresAcondicionados.entity.Empleado;
import com.MiguelSantizo.AiresAcondicionados.service.EmpleadoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/empleados")
public class EmpleadoController {

    private final EmpleadoService empleadoService;

    public EmpleadoController(EmpleadoService empleadoService) {
        this.empleadoService = empleadoService;
    }

    @GetMapping
    public ResponseEntity<Object> getEmpleados() {
        try {
            List<Empleado> empleados = empleadoService.getAllEmpleados();
            if(empleados.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT)
                        .body("No hay empleados registrados en el sistema");
            }
            return ResponseEntity.ok(empleados);
        } catch(Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al obtener empleados: " + e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getEmpleadoById(@PathVariable("id") Integer id) {
        try {
            if(id == null || id <= 0) {
                return ResponseEntity.badRequest()
                        .body("El ID del empleado debe ser un número positivo");
            }

            Empleado empleado = empleadoService.getEmpleadoById(id);

            if(empleado != null) {
                return ResponseEntity.ok(empleado);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Empleado no encontrado con ID: " + id);
            }
        } catch(IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch(Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al buscar empleado: " + e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<Object> createEmpleado(@Valid @RequestBody Empleado empleado) {
        try {
            if(empleado.getNombre_empleado() == null || empleado.getNombre_empleado().trim().isEmpty()) {
                return ResponseEntity.badRequest()
                        .body("El nombre del empleado no puede estar vacío");
            }

            if(empleado.getApellido_empleado() == null || empleado.getApellido_empleado().trim().isEmpty()) {
                return ResponseEntity.badRequest()
                        .body("El apellido del empleado no puede estar vacío");
            }

            if(empleado.getEmail_empleado() == null || empleado.getEmail_empleado().trim().isEmpty()) {
                return ResponseEntity.badRequest()
                        .body("El email del empleado no puede estar vacío");
            }

            // Validar que el email sea de los dominios permitidos
            String email = empleado.getEmail_empleado().toLowerCase();
            if(!email.endsWith("@gmail.com") &&
                    !email.endsWith("@yahoo.com") &&
                    !email.endsWith("@outlook.com")) {
                return ResponseEntity.badRequest()
                        .body("El email debe ser de Gmail, Yahoo o Outlook");
            }

            Empleado createdEmpleado = empleadoService.saveEmpleado(empleado);
            return new ResponseEntity<>(createdEmpleado, HttpStatus.CREATED);

        } catch(IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch(Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al crear empleado: " + e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateEmpleado(@PathVariable("id") Integer id, @Valid @RequestBody Empleado empleado) {
        try {
            if(id == null || id <= 0) {
                return ResponseEntity.badRequest()
                        .body("El ID del empleado debe ser un número positivo");
            }

            if(empleado.getNombre_empleado() != null && empleado.getNombre_empleado().trim().isEmpty()) {
                return ResponseEntity.badRequest()
                        .body("El nombre del empleado no puede estar vacío");
            }

            if(empleado.getApellido_empleado() != null && empleado.getApellido_empleado().trim().isEmpty()) {
                return ResponseEntity.badRequest()
                        .body("El apellido del empleado no puede estar vacío");
            }

            if(empleado.getEmail_empleado() != null && empleado.getEmail_empleado().trim().isEmpty()) {
                return ResponseEntity.badRequest()
                        .body("El email del empleado no puede estar vacío");
            }

            // Validar email si viene en la petición
            if(empleado.getEmail_empleado() != null) {
                String email = empleado.getEmail_empleado().toLowerCase();
                if(!email.endsWith("@gmail.com") &&
                        !email.endsWith("@yahoo.com") &&
                        !email.endsWith("@outlook.com")) {
                    return ResponseEntity.badRequest()
                            .body("El email debe ser de Gmail, Yahoo o Outlook");
                }
            }

            Empleado updatedEmpleado = empleadoService.updateEmpleado(id, empleado);

            if(updatedEmpleado != null) {
                return ResponseEntity.ok(updatedEmpleado);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Empleado no encontrado con ID: " + id);
            }

        } catch(IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch(Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al actualizar empleado: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteEmpleado(@PathVariable("id") Integer id) {
        try {
            if(id == null || id <= 0) {
                return ResponseEntity.badRequest()
                        .body("El ID del empleado debe ser un número positivo");
            }

            Empleado empleado = empleadoService.getEmpleadoById(id);
            if(empleado == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Empleado no encontrado con ID: " + id);
            }

            empleadoService.deleteEmpleado(id);
            return ResponseEntity.ok("Empleado eliminado exitosamente");

        } catch(IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch(Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al eliminar empleado: " + e.getMessage());
        }
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return ResponseEntity.badRequest().body(errors);
    }
}