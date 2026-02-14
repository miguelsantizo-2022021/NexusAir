package com.MiguelSantizo.AiresAcondicionados.controller;

import com.MiguelSantizo.AiresAcondicionados.entity.Producto;
import com.MiguelSantizo.AiresAcondicionados.service.ProductoService;
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
@RequestMapping("/api/productos")
public class ProductoController {

    private final ProductoService productoService;

    public ProductoController(ProductoService productoService) {
        this.productoService = productoService;
    }

    // Listar todos los productos
    @GetMapping
    public ResponseEntity<Object> getProductos() {
        try {
            List<Producto> productos = productoService.getAllProductos();
            if(productos.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT)
                        .body("No hay productos registrados en el sistema");
            }
            return ResponseEntity.ok(productos);
        } catch(Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al obtener productos: " + e.getMessage());
        }
    }

    // Obtener producto por ID
    @GetMapping("/{id}")
    public ResponseEntity<Object> getProductoById(@PathVariable("id") Integer id) {
        try {
            // Validar que el ID sea válido
            if(id == null || id <= 0) {
                return ResponseEntity.badRequest()
                        .body("El ID del producto debe ser un número positivo");
            }

            Producto producto = productoService.getProductoById(id);

            if(producto != null) {
                return ResponseEntity.ok(producto);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Producto no encontrado con ID: " + id);
            }
        } catch(IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch(Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al buscar producto: " + e.getMessage());
        }
    }

    // Crear producto
    @PostMapping
    public ResponseEntity<Object> createProducto(@Valid @RequestBody Producto producto) {
        try {
            // Validaciones adicionales
            if(producto.getNombre_producto() == null || producto.getNombre_producto().trim().isEmpty()) {
                return ResponseEntity.badRequest()
                        .body("El nombre del producto no puede estar vacío");
            }

            if(producto.getModelo() == null || producto.getModelo().trim().isEmpty()) {
                return ResponseEntity.badRequest()
                        .body("El modelo del producto no puede estar vacío");
            }

            if(producto.getCategoria() == null || producto.getCategoria().trim().isEmpty()) {
                return ResponseEntity.badRequest()
                        .body("La categoría del producto no puede estar vacía");
            }

            // Validar que la categoría sea válida
            if(!producto.getCategoria().equals("calefactor") && !producto.getCategoria().equals("aire acondicionado")) {
                return ResponseEntity.badRequest()
                        .body("La categoría debe ser 'calefactor' o 'aire acondicionado'");
            }

            if(producto.getPrecio_compra() == null || producto.getPrecio_compra() <= 0) {
                return ResponseEntity.badRequest()
                        .body("El precio de compra debe ser mayor a 0");
            }

            if(producto.getPrecio_venta() == null || producto.getPrecio_venta() <= 0) {
                return ResponseEntity.badRequest()
                        .body("El precio de venta debe ser mayor a 0");
            }

            if(producto.getStock() == null || producto.getStock() < 0) {
                return ResponseEntity.badRequest()
                        .body("El stock no puede ser negativo");
            }

            if(producto.getId_proveedor() == null || producto.getId_proveedor() <= 0) {
                return ResponseEntity.badRequest()
                        .body("El ID del proveedor debe ser un número positivo");
            }

            Producto createdProducto = productoService.saveProducto(producto);
            return new ResponseEntity<>(createdProducto, HttpStatus.CREATED);

        } catch(IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch(Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al crear producto: " + e.getMessage());
        }
    }

    // Actualizar producto
    @PutMapping("/{id}")
    public ResponseEntity<Object> updateProducto(@PathVariable("id") Integer id, @Valid @RequestBody Producto producto) {
        try {
            // Validar que el ID sea válido
            if(id == null || id <= 0) {
                return ResponseEntity.badRequest()
                        .body("El ID del producto debe ser un número positivo");
            }

            // Validar que no se envíen campos vacíos (solo si se envían)
            if(producto.getNombre_producto() != null && producto.getNombre_producto().trim().isEmpty()) {
                return ResponseEntity.badRequest()
                        .body("El nombre del producto no puede estar vacío");
            }

            if(producto.getModelo() != null && producto.getModelo().trim().isEmpty()) {
                return ResponseEntity.badRequest()
                        .body("El modelo del producto no puede estar vacío");
            }

            if(producto.getCategoria() != null && producto.getCategoria().trim().isEmpty()) {
                return ResponseEntity.badRequest()
                        .body("La categoría del producto no puede estar vacía");
            }

            // Validar categoría si viene en la petición
            if(producto.getCategoria() != null) {
                if(!producto.getCategoria().equals("calefactor") && !producto.getCategoria().equals("aire acondicionado")) {
                    return ResponseEntity.badRequest()
                            .body("La categoría debe ser 'calefactor' o 'aire acondicionado'");
                }
            }

            if(producto.getPrecio_compra() != null && producto.getPrecio_compra() <= 0) {
                return ResponseEntity.badRequest()
                        .body("El precio de compra debe ser mayor a 0");
            }

            if(producto.getPrecio_venta() != null && producto.getPrecio_venta() <= 0) {
                return ResponseEntity.badRequest()
                        .body("El precio de venta debe ser mayor a 0");
            }

            if(producto.getStock() != null && producto.getStock() < 0) {
                return ResponseEntity.badRequest()
                        .body("El stock no puede ser negativo");
            }

            if(producto.getId_proveedor() != null && producto.getId_proveedor() <= 0) {
                return ResponseEntity.badRequest()
                        .body("El ID del proveedor debe ser un número positivo");
            }

            Producto updatedProducto = productoService.updateProducto(id, producto);

            if(updatedProducto != null) {
                return ResponseEntity.ok(updatedProducto);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Producto no encontrado con ID: " + id);
            }

        } catch(IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch(Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al actualizar producto: " + e.getMessage());
        }
    }

    // Eliminar producto
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteProducto(@PathVariable("id") Integer id) {
        try {
            // Validar que el ID sea válido
            if(id == null || id <= 0) {
                return ResponseEntity.badRequest()
                        .body("El ID del producto debe ser un número positivo");
            }

            // Verificar que el producto existe
            Producto producto = productoService.getProductoById(id);
            if(producto == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Producto no encontrado con ID: " + id);
            }

            productoService.deleteProducto(id);
            return ResponseEntity.ok("Producto eliminado exitosamente");

        } catch(IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch(Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al eliminar producto: " + e.getMessage());
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