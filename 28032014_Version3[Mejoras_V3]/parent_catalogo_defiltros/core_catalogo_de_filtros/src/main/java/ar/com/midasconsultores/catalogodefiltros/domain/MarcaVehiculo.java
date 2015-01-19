package ar.com.midasconsultores.catalogodefiltros.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

import org.hibernate.annotations.Index;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.jasypt.hibernate4.type.EncryptedStringType;

@Entity
@Table(name = "marca_vehiculo")
public class MarcaVehiculo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8228636696502923660L;

	@Id
	@TableGenerator(name = "marca_vehiculo_generator", table = "generic_generator", pkColumnName = "sequence_name", valueColumnName = "sequence_value", pkColumnValue = "marca_vehiculo_gen", initialValue = 10000, allocationSize = 100)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "marca_vehiculo_generator")
	@Index(columnNames="id", name="marca_vehiculo_id_idx")
	private Long id;
	
	
	@Column(name = "nombre")
	@Index(columnNames="nombre", name="marca_vehiculo_nombre_idx")
	private String nombre;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

}
