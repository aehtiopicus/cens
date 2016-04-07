/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.com.midasconsultores.catalogodefiltros.admin.dto;

/**
 *
 * @author Javier
 */
public class VendedorDto {

    private Long id;
    private String nombreVendedor;
    private String codigoInternoVendedor;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombreVendedor() {
        return nombreVendedor;
    }

    public void setNombreVendedor(String nombreVendedor) {
        this.nombreVendedor = nombreVendedor;
    }

    public String getCodigoInternoVendedor() {
        return codigoInternoVendedor;
    }

    public void setCodigoInternoVendedor(String codigoInternoVendedor) {
        this.codigoInternoVendedor = codigoInternoVendedor;
    }

}
