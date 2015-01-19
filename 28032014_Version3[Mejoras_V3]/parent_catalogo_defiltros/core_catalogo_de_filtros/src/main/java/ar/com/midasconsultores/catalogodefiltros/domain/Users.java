package ar.com.midasconsultores.catalogodefiltros.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
@Table(name = "users", uniqueConstraints = { @UniqueConstraint(columnNames = { "username" }) })
public class Users implements Serializable, UserDetails {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8991734116998865861L;
	@Id
	@Column(name = "username", nullable = false, unique = true)
	@NotEmpty(message = "{constraint.violation.notnull}")
	@NotNull(message = "{constraint.violation.notnull}")
	@Size(min = 1, max = 25, message = "{constraint.violation.size.1_25}")
	private String username;
	@Column(name = "password", nullable = true)
	private String password;
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "username")
	private List<Authorities> authorities = new ArrayList<Authorities>();
	@NotEmpty(message = "{constraint.violation.notnull}")
	@NotNull(message = "{constraint.violation.notnull}")
	@Size(min = 1, max = 400, message = "{constraint.violation.size.1_25}")
	@Email(message = "{constraint.violation.email.format}")
	private String email;
	private Boolean enabled = true;        
        private Boolean vendedor;

	private String idEquipo;
	
	@Column(name = "udate_time_stamp", nullable = true)
	private long udateTimeStamp;

	@Column(name = "fechadecaducidaddelicencia", nullable = true)
	private long fechaDeCaducidadDeLicencia;

	@Column(name = "fechadeiniciodelicencia", nullable = true)
	private long fechaDeInicioDelicencia;

	@Column(name = "codigo_de_usuario", nullable = true)
	private String codigoDeUsuario;

	public String getIdEquipo() {
		return idEquipo;
	}

	public void setIdEquipo(String idEquipo) {
		this.idEquipo = idEquipo;
	}

	public long getFechaDeInicioDelicencia() {
		return fechaDeInicioDelicencia;
	}

	public void setFechaDeInicioDelicencia(long fechaDeInicioDelicencia) {
		this.fechaDeInicioDelicencia = fechaDeInicioDelicencia;
	}

	public long getFechaDeCaducidadDeLicencia() {
		return fechaDeCaducidadDeLicencia;
	}

	public void setFechaDeCaducidadDeLicencia(long fechaDeCaducidadDeLicencia) {
		this.fechaDeCaducidadDeLicencia = fechaDeCaducidadDeLicencia;
	}

	@Override
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public List<Authorities> getAuthorities() {
		return this.authorities;
	}

	@Override
	public boolean isEnabled() {
		return enabled;
	}

	public void setAuthorities(List<Authorities> authorities) {
		this.authorities = authorities;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	public boolean isAccountNonExpired() {
		return true;
	}

	public boolean isAccountNonLocked() {
		return true;
	}

	public boolean isCredentialsNonExpired() {
		return true;
	}

	public String getCodigoDeUsuario() {
		return codigoDeUsuario;
	}

	public void setCodigoDeUsuario(String codigoDeUsuario) {
		this.codigoDeUsuario = codigoDeUsuario;
	}

	public long getUdateTimeStamp() {
		return udateTimeStamp;
	}

	public void setUdateTimeStamp(long udateTimeStamp) {
		this.udateTimeStamp = udateTimeStamp;
	}

        public Boolean isVendedor() {
            return vendedor;
        }

        public void setVendedor(Boolean vendedor) {
             this.vendedor = vendedor;
        }
        
}
