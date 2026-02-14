package com.MiguelSantizo.AiresAcondicionados.service;

import com.MiguelSantizo.AiresAcondicionados.entity.Venta;
import com.MiguelSantizo.AiresAcondicionados.repository.VentaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VentaServiceImplements implements VentaService {

    private final VentaRepository ventaRepository;

    public VentaServiceImplements(VentaRepository ventaRepository) {
        this.ventaRepository = ventaRepository;
    }

    @Override
    public List<Venta> getAllVentas() {
        return ventaRepository.findAll();
    }

    @Override
    public Venta getVentaById(Integer id) {
        return ventaRepository.findById(id).orElse(null);
    }

    @Override
    public Venta saveVenta(Venta venta) throws RuntimeException {
        if(venta.getFecha_venta() == null) {
            throw new IllegalArgumentException("La fecha de venta es requerida");
        }

        if(venta.getCantidad() == null || venta.getCantidad() <= 0) {
            throw new IllegalArgumentException("La cantidad debe ser mayor a 0");
        }

        if(venta.getTotal() == null || venta.getTotal() <= 0) {
            throw new IllegalArgumentException("El total debe ser mayor a 0");
        }

        if(venta.getId_empleado() == null) {
            throw new IllegalArgumentException("El ID del empleado es requerido");
        }

        if(venta.getId_producto() == null) {
            throw new IllegalArgumentException("El ID del producto es requerido");
        }

        return ventaRepository.save(venta);
    }

    @Override
    public Venta updateVenta(Integer id, Venta venta) {
        Venta ventaExistente = ventaRepository.findById(id).orElse(null);

        if(ventaExistente == null) {
            return null;
        }

        if(venta.getFecha_venta() != null) {
            ventaExistente.setFecha_venta(venta.getFecha_venta());
        }

        if(venta.getCantidad() != null && venta.getCantidad() > 0) {
            ventaExistente.setCantidad(venta.getCantidad());
        }

        if(venta.getTotal() != null && venta.getTotal() > 0) {
            ventaExistente.setTotal(venta.getTotal());
        }

        if(venta.getId_empleado() != null) {
            ventaExistente.setId_empleado(venta.getId_empleado());
        }

        if(venta.getId_producto() != null) {
            ventaExistente.setId_producto(venta.getId_producto());
        }

        return ventaRepository.save(ventaExistente);
    }

    @Override
    public void deleteVenta(Integer id) {
        ventaRepository.deleteById(id);
    }
}