package com.MiguelSantizo.AiresAcondicionados.controller;

import com.MiguelSantizo.AiresAcondicionados.entity.Proveedor;
import com.MiguelSantizo.AiresAcondicionados.service.ProveedorService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/proveedores")
public class ProveedorController {

    private final ProveedorService proveedorService;

    public ProveedorController(ProveedorService proveedorService) {
        this.proveedorService = proveedorService;
    }

    @GetMapping
    public List<Proveedor> getProveedores() {
        return proveedorService.getAllProveedores();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getProveedorById(@PathVariable("id") Integer id) {
        try {
            Proveedor proveedor = proveedorService.getProveedorById(id);
            if(proveedor != null) {
                return ResponseEntity.ok(proveedor);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Proveedor no encontrado con ID: " + id);
            }
        } catch(Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al buscar proveedor: " + e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<Object> createProveedor(@Valid @RequestBody Proveedor proveedor) {
        try {
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
            Proveedor proveedor = proveedorService.getProveedorById(id);
            if(proveedor != null) {
                proveedorService.deleteProveedor(id);
                return ResponseEntity.ok("Proveedor eliminado exitosamente");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Proveedor no encontrado con ID: " + id);
            }
        } catch(Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al eliminar proveedor: " + e.getMessage());
        }
    }
}