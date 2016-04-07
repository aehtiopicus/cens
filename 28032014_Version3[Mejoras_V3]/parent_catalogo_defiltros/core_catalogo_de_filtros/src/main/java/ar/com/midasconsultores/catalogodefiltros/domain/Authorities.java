package ar.com.midasconsultores.catalogodefiltros.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import org.springframework.security.core.GrantedAuthority;

/**
 *
 * @author cgaia
 */
@Entity
public class Authorities implements GrantedAuthority {

    /**
     *
     */
    private static final long serialVersionUID = 6385326184285562729L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "authority", nullable = false, unique = false)
    private String authority;

    /**
     *
     * @return
     */
    @Override
    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        return (o instanceof Authorities && this.hashCode() == o.hashCode());
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + (this.authority != null ? this.authority.toLowerCase().hashCode() : 0);
        return hash;
    }
}
