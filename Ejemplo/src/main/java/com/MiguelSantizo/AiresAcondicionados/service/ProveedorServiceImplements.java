package com.MiguelSantizo.AiresAcondicionados.service;

import com.MiguelSantizo.AiresAcondicionados.entity.Proveedor;
import com.MiguelSantizo.AiresAcondicionados.repository.ProveedorRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProveedorServiceImplements implements ProveedorService {

    private final ProveedorRepository proveedorRepository;

    public ProveedorServiceImplements(ProveedorRepository proveedorRepository) {
        this.proveedorRepository = proveedorRepository;
    }

    @Override
    public List<Proveedor> getAllProveedores() {
        return proveedorRepository.findAll();
    }

    @Override
    public Proveedor getProveedorById(Integer id) {
        return proveedorRepository.findById(id).orElse(null);
    }

    @Override
    public Proveedor saveProveedor(Proveedor proveedor) throws RuntimeException {
        if(proveedor.getNombre_proveedor() == null || proveedor.getNombre_proveedor().trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del proveedor es requerido");
        }

        if(proveedor.getTelefono_proveedor() == null) {
            throw new IllegalArgumentException("El teléfono del proveedor es requerido");
        }

        if(proveedor.getDireccion() == null || proveedor.getDireccion().trim().isEmpty()) {
            throw new IllegalArgumentException("La dirección del proveedor es requerida");
        }

        if(proveedor.getEmail_proveedor() == null || proveedor.getEmail_proveedor().trim().isEmpty()) {
            throw new IllegalArgumentException("El email del proveedor es requerido");
        }

        return proveedorRepository.save(proveedor);
    }

    @Override
    public Proveedor updateProveedor(Integer id, Proveedor proveedor) {
        Proveedor proveedorExistente = proveedorRepository.findById(id).orElse(null);

        if(proveedorExistente == null) {
            return null;
        }

        if(proveedor.getNombre_proveedor() != null && !proveedor.getNombre_proveedor().trim().isEmpty()) {
            proveedorExistente.setNombre_proveedor(proveedor.getNombre_proveedor());
        }

        if(proveedor.getTelefono_proveedor() != null) {
            proveedorExistente.setTelefono_proveedor(proveedor.getTelefono_proveedor());
        }

        if(proveedor.getDireccion() != null && !proveedor.getDireccion().trim().isEmpty()) {
            proveedorExistente.setDireccion(proveedor.getDireccion());
        }

        if(proveedor.getEmail_proveedor() != null && !proveedor.getEmail_proveedor().trim().isEmpty()) {
            proveedorExistente.setEmail_proveedor(proveedor.getEmail_proveedor());
        }

        return proveedorRepository.save(proveedorExistente);
    }

    @Override
    public void deleteProveedor(Integer id) {
        proveedorRepository.deleteById(id);
    }
}