/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.com.midasconsultores.catalogodefiltros.admin.mapper;

import ar.com.midasconsultores.catalogodefiltros.admin.dto.VendedorDto;
import ar.com.midasconsultores.catalogodefiltros.domain.Vendedor;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;

/**
 *
 * @author Javier
 */
@Component
public class VendedorMapper {

    public List<VendedorDto> convertDomainToDto(List<Vendedor> vendedores) {
        List<VendedorDto> vendedoresDto = new ArrayList<VendedorDto>();
        if (vendedores != null && !vendedores.isEmpty()) {
            VendedorDto vendedorDto;
            for (Vendedor vendedor : vendedores) {
                vendedorDto = new VendedorDto();
                vendedorDto.setCodigoInternoVendedor(vendedor.getCodigoVendedor());
                vendedorDto.setId(vendedor.getId());
                vendedorDto.setNombreVendedor(vendedor.getNombre());
                vendedoresDto.add(vendedorDto);
            }
        }
        return vendedoresDto;
    }
}
