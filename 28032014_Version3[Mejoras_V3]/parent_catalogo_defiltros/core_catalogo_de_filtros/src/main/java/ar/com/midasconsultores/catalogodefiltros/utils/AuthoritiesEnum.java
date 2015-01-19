/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.com.midasconsultores.catalogodefiltros.utils;

import ar.com.midasconsultores.catalogodefiltros.domain.Authorities;

/**
 *
 * @author bsasschetti
 */
public enum AuthoritiesEnum {
    
    ADMIN("admin"),
    USER("user");    
    
    private String authority;
    
    AuthoritiesEnum(String authority){
        this.authority = authority;
    }

    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }
    
    public Authorities getAuthorityObject () {
        Authorities authorities = new Authorities();
        authorities.setId(null);
        authorities.setAuthority(this.getAuthority());
        
        return authorities;
    }
    
}
