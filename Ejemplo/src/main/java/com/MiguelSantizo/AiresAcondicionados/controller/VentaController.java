package com.MiguelSantizo.AiresAcondicionados.controller;

import com.MiguelSantizo.AiresAcondicionados.entity.Venta;
import com.MiguelSantizo.AiresAcondicionados.service.VentaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ventas")
public class VentaController {

    private final VentaService ventaService;

    public VentaController(VentaService ventaService) {
        this.ventaService = ventaService;
    }

    @GetMapping
    public List<Venta> getVentas() {
        return ventaService.getAllVentas();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getVentaById(@PathVariable("id") Integer id) {
        try {
            Venta venta = ventaService.getVentaById(id);
            if(venta != null) {
                return ResponseEntity.ok(venta);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Venta no encontrada con ID: " + id);
            }
        } catch(Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al buscar venta: " + e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<Object> createVenta(@Valid @RequestBody Venta venta) {
        try {
            Venta createdVenta = ventaService.saveVenta(venta);
            return new ResponseEntity<>(createdVenta, HttpStatus.CREATED);
        } catch(IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch(Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al crear venta: " + e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateVenta(@PathVariable("id") Integer id, @Valid @RequestBody Venta venta) {
        try {
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

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteVenta(@PathVariable("id") Integer id) {
        try {
            Venta venta = ventaService.getVentaById(id);
            if(venta != null) {
                ventaService.deleteVenta(id);
                return ResponseEntity.ok("Venta eliminada exitosamente");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Venta no encontrada con ID: " + id);
            }
        } catch(Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al eliminar venta: " + e.getMessage());
        }
    }
}