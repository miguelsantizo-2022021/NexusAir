package com.MiguelSantizo.AiresAcondicionados.controller;

import com.MiguelSantizo.AiresAcondicionados.entity.Venta;
import com.MiguelSantizo.AiresAcondicionados.service.VentaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/ventas")
public class VentaController {

    private final VentaService ventaService;

    public VentaController(VentaService ventaService) {
        this.ventaService = ventaService;
    }

    // Listar todas las ventas
    @GetMapping
    public ResponseEntity<Object> getVentas() {
        try {
            List<Venta> ventas = ventaService.getAllVentas();
            if(ventas.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT)
                        .body("No hay ventas registradas en el sistema");
            }
            return ResponseEntity.ok(ventas);
        } catch(Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al obtener ventas: " + e.getMessage());
        }
    }

    // Obtener venta por ID
    @GetMapping("/{id}")
    public ResponseEntity<Object> getVentaById(@PathVariable("id") Integer id) {
        try {
            // Validar que el ID sea válido
            if(id == null || id <= 0) {
                return ResponseEntity.badRequest()
                        .body("El ID de la venta debe ser un número positivo");
            }

            Venta venta = ventaService.getVentaById(id);

            if(venta != null) {
                return ResponseEntity.ok(venta);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Venta no encontrada con ID: " + id);
            }
        } catch(IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch(Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al buscar venta: " + e.getMessage());
        }
    }

    // Crear venta
    @PostMapping
    public ResponseEntity<Object> createVenta(@Valid @RequestBody Venta venta) {
        try {
            // Validaciones adicionales
            if(venta.getFecha_venta() == null) {
                return ResponseEntity.badRequest()
                        .body("La fecha de venta es obligatoria");
            }

            // Validar que la fecha no sea futura
            if(venta.getFecha_venta().isAfter(LocalDate.now())) {
                return ResponseEntity.badRequest()
                        .body("La fecha de venta no puede ser futura");
            }

            if(venta.getCantidad() == null || venta.getCantidad() <= 0) {
                return ResponseEntity.badRequest()
                        .body("La cantidad debe ser mayor a 0");
            }

            if(venta.getTotal() == null || venta.getTotal() <= 0) {
                return ResponseEntity.badRequest()
                        .body("El total debe ser mayor a 0");
            }

            if(venta.getId_empleado() == null || venta.getId_empleado() <= 0) {
                return ResponseEntity.badRequest()
                        .body("El ID del empleado debe ser un número positivo");
            }

            if(venta.getId_producto() == null || venta.getId_producto() <= 0) {
                return ResponseEntity.badRequest()
                        .body("El ID del producto debe ser un número positivo");
            }

            Venta createdVenta = ventaService.saveVenta(venta);
            return new ResponseEntity<>(createdVenta, HttpStatus.CREATED);

        } catch(IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch(Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al crear venta: " + e.getMessage());
        }
    }

    // Actualizar venta
    @PutMapping("/{id}")
    public ResponseEntity<Object> updateVenta(@PathVariable("id") Integer id, @Valid @RequestBody Venta venta) {
        try {
            // Validar que el ID sea válido
            if(id == null || id <= 0) {
                return ResponseEntity.badRequest()
                        .body("El ID de la venta debe ser un número positivo");
            }

            // Validar fecha si viene en la petición
            if(venta.getFecha_venta() != null) {
                if(venta.getFecha_venta().isAfter(LocalDate.now())) {
                    return ResponseEntity.badRequest()
                            .body("La fecha de venta no puede ser futura");
                }
            }

            if(venta.getCantidad() != null && venta.getCantidad() <= 0) {
                return ResponseEntity.badRequest()
                        .body("La cantidad debe ser mayor a 0");
            }

            if(venta.getTotal() != null && venta.getTotal() <= 0) {
                return ResponseEntity.badRequest()
                        .body("El total debe ser mayor a 0");
            }

            if(venta.getId_empleado() != null && venta.getId_empleado() <= 0) {
                return ResponseEntity.badRequest()
                        .body("El ID del empleado debe ser un número positivo");
            }

            if(venta.getId_producto() != null && venta.getId_producto() <= 0) {
                return ResponseEntity.badRequest()
                        .body("El ID del producto debe ser un número positivo");
            }

            Venta updatedVenta = ventaService.updateVenta(id, venta);

            if(updatedVenta != null) {
                return ResponseEntity.ok(updatedVenta);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Venta no encontrada con ID: " + id);
            }

        } catch(IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch(Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al actualizar venta: " + e.getMessage());
        }
    }

    // Eliminar venta
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteVenta(@PathVariable("id") Integer id) {
        try {
            // Validar que el ID sea válido
            if(id == null || id <= 0) {
                return ResponseEntity.badRequest()
                        .body("El ID de la venta debe ser un número positivo");
            }

            // Verificar que la venta existe
            Venta venta = ventaService.getVentaById(id);
            if(venta == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Venta no encontrada con ID: " + id);
            }

            ventaService.deleteVenta(id);
            return ResponseEntity.ok("Venta eliminada exitosamente");

        } catch(IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch(Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al eliminar venta: " + e.getMessage());
        }
    }

    // Manejador de errores de validación
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