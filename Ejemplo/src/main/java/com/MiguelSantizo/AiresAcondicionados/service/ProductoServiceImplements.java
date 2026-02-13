package com.MiguelSantizo.AiresAcondicionados.service;

import com.MiguelSantizo.AiresAcondicionados.entity.Producto;
import com.MiguelSantizo.AiresAcondicionados.repository.ProductoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductoServiceImplements implements ProductoService {

    private final ProductoRepository productoRepository;

    public ProductoServiceImplements(ProductoRepository productoRepository) {
        this.productoRepository = productoRepository;
    }

    @Override
    public List<Producto> getAllProductos() {
        return productoRepository.findAll();
    }

    @Override
    public Producto getProductoById(Integer id) {
        return productoRepository.findById(id).orElse(null);
    }

    @Override
    public Producto saveProducto(Producto producto) throws RuntimeException {
        if(producto.getNombre_producto() == null || producto.getNombre_producto().trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del producto es requerido");
        }

        if(producto.getModelo() == null || producto.getModelo().trim().isEmpty()) {
            throw new IllegalArgumentException("El modelo del producto es requerido");
        }

        if(producto.getCategoria() == null || producto.getCategoria().trim().isEmpty()) {
            throw new IllegalArgumentException("La categoría del producto es requerida");
        }

        if(producto.getPrecio_compra() == null || producto.getPrecio_compra() <= 0) {
            throw new IllegalArgumentException("El precio de compra debe ser mayor a 0");
        }

        if(producto.getPrecio_venta() == null || producto.getPrecio_venta() <= 0) {
            throw new IllegalArgumentException("El precio de venta debe ser mayor a 0");
        }

        if(producto.getStock() == null || producto.getStock() < 0) {
            throw new IllegalArgumentException("El stock no puede ser negativo");
        }

        if(producto.getId_proveedor() == null) {
            throw new IllegalArgumentException("El ID del proveedor es requerido");
        }

        return productoRepository.save(producto);
    }

    @Override
    public Producto updateProducto(Integer id, Producto producto) {
        Producto productoExistente = productoRepository.findById(id).orElse(null);

        if(productoExistente == null) {
            return null;
        }

        if(producto.getNombre_producto() != null && !producto.getNombre_producto().trim().isEmpty()) {
            productoExistente.setNombre_producto(producto.getNombre_producto());
        }

        if(producto.getModelo() != null && !producto.getModelo().trim().isEmpty()) {
            productoExistente.setModelo(producto.getModelo());
        }

        if(producto.getCategoria() != null && !producto.getCategoria().trim().isEmpty()) {
            productoExistente.setCategoria(producto.getCategoria());
        }

        if(producto.getPrecio_compra() != null && producto.getPrecio_compra() > 0) {
            productoExistente.setPrecio_compra(producto.getPrecio_compra());
        }

        if(producto.getPrecio_venta() != null && producto.getPrecio_venta() > 0) {
            productoExistente.setPrecio_venta(producto.getPrecio_venta());
        }

        if(producto.getStock() != null && producto.getStock() >= 0) {
            productoExistente.setStock(producto.getStock());
        }

        if(producto.getId_proveedor() != null) {
            productoExistente.setId_proveedor(producto.getId_proveedor());
        }

        return productoRepository.save(productoExistente);
    }

    @Override
    public void deleteProducto(Integer id) {
        productoRepository.deleteById(id);
    }
}