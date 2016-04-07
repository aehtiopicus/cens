/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ar.com.midasconsultores.catalogodefiltros.repository;

import ar.com.midasconsultores.catalogodefiltros.domain.Vendedor;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author Javier
 */
public interface VendedorRepository extends JpaRepository<Vendedor, Long>{
    
}
