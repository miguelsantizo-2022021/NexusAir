package com.MiguelSantizo.AiresAcondicionados.controller;

import com.MiguelSantizo.AiresAcondicionados.entity.Proveedor;
import com.MiguelSantizo.AiresAcondicionados.service.ProveedorService;
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
@RequestMapping("/api/proveedores")
public class ProveedorController {

    private final ProveedorService proveedorService;

    public ProveedorController(ProveedorService proveedorService) {
        this.proveedorService = proveedorService;
    }

    @GetMapping
    public ResponseEntity<Object> getProveedores() {
        try {
            List<Proveedor> proveedores = proveedorService.getAllProveedores();
            if(proveedores.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT)
                        .body("No hay proveedores registrados en el sistema");
            }
            return ResponseEntity.ok(proveedores);
        } catch(Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al obtener proveedores: " + e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getProveedorById(@PathVariable("id") Integer id) {
        try {
            if(id == null || id <= 0) {
                return ResponseEntity.badRequest()
                        .body("El ID del proveedor debe ser un número positivo");
            }

            Proveedor proveedor = proveedorService.getProveedorById(id);

            if(proveedor != null) {
                return ResponseEntity.ok(proveedor);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Proveedor no encontrado con ID: " + id);
            }
        } catch(IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch(Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al buscar proveedor: " + e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<Object> createProveedor(@Valid @RequestBody Proveedor proveedor) {
        try {
            if(proveedor.getNombre_proveedor() == null || proveedor.getNombre_proveedor().trim().isEmpty()) {
                return ResponseEntity.badRequest()
                        .body("El nombre del proveedor no puede estar vacío");
            }

            if(proveedor.getTelefono_proveedor() == null) {
                return ResponseEntity.badRequest()
                        .body("El teléfono del proveedor es obligatorio");
            }

            if(proveedor.getDireccion() == null || proveedor.getDireccion().trim().isEmpty()) {
                return ResponseEntity.badRequest()
                        .body("La dirección del proveedor no puede estar vacía");
            }

            if(proveedor.getEmail_proveedor() == null || proveedor.getEmail_proveedor().trim().isEmpty()) {
                return ResponseEntity.badRequest()
                        .body("El email del proveedor no puede estar vacío");
            }

            // Validar que el email sea de los dominios permitidos
            String email = proveedor.getEmail_proveedor().toLowerCase();
            if(!email.endsWith("@gmail.com") &&
                    !email.endsWith("@yahoo.com") &&
                    !email.endsWith("@outlook.com")) {
                return ResponseEntity.badRequest()
                        .body("El email debe ser de Gmail, Yahoo o Outlook");
            }

            Proveedor createdProveedor = proveedorService.saveProveedor(proveedor);
            return new ResponseEntity<>(createdProveedor, HttpStatus.CREATED);

        } catch(IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch(Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al crear proveedor: " + e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateProveedor(@PathVariable("id") Integer id, @Valid @RequestBody Proveedor proveedor) {
        try {
            if(id == null || id <= 0) {
                return ResponseEntity.badRequest()
                        .body("El ID del proveedor debe ser un número positivo");
            }

            if(proveedor.getNombre_proveedor() != null && proveedor.getNombre_proveedor().trim().isEmpty()) {
                return ResponseEntity.badRequest()
                        .body("El nombre del proveedor no puede estar vacío");
            }

            if(proveedor.getDireccion() != null && proveedor.getDireccion().trim().isEmpty()) {
                return ResponseEntity.badRequest()
                        .body("La dirección del proveedor no puede estar vacía");
            }

            if(proveedor.getEmail_proveedor() != null && proveedor.getEmail_proveedor().trim().isEmpty()) {
                return ResponseEntity.badRequest()
                        .body("El email del proveedor no puede estar vacío");
            }

            // Validar email si viene en la petición
            if(proveedor.getEmail_proveedor() != null) {
                String email = proveedor.getEmail_proveedor().toLowerCase();
                if(!email.endsWith("@gmail.com") &&
                        !email.endsWith("@yahoo.com") &&
                        !email.endsWith("@outlook.com")) {
                    return ResponseEntity.badRequest()
                            .body("El email debe ser de Gmail, Yahoo o Outlook");
                }
            }

            Proveedor updatedProveedor = proveedorService.updateProveedor(id, proveedor);

            if(updatedProveedor != null) {
                return ResponseEntity.ok(updatedProveedor);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Proveedor no encontrado con ID: " + id);
            }

        } catch(IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch(Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al actualizar proveedor: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteProveedor(@PathVariable("id") Integer id) {
        try {
            if(id == null || id <= 0) {
                return ResponseEntity.badRequest()
                        .body("El ID del proveedor debe ser un número positivo");
            }

            Proveedor proveedor = proveedorService.getProveedorById(id);
            if(proveedor == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Proveedor no encontrado con ID: " + id);
            }

            proveedorService.deleteProveedor(id);
            return ResponseEntity.ok("Proveedor eliminado exitosamente");

        } catch(IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch(Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al eliminar proveedor: " + e.getMessage());
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