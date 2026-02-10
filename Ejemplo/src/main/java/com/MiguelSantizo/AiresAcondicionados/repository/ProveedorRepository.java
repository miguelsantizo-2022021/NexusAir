package com.MiguelSantizo.AiresAcondicionados.repository;

import com.MiguelSantizo.AiresAcondicionados.Entity.Proveedor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProveedorRepository extends JpaRepository<Proveedor, Integer> {

}