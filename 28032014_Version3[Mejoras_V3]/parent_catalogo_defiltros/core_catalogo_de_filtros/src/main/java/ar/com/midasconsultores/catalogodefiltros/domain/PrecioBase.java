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
import org.hibernate.annotations.TypeDefs;
import org.jasypt.hibernate4.type.EncryptedStringType;

@Entity
@Table(name = "precio_base")
@TypeDefs({
		@TypeDef(name = "encryptedString", typeClass = EncryptedStringType.class, parameters = { @Parameter(name = "encryptorRegisteredName", value = "hibernateStringEncryptor") }),
		@TypeDef(name = "zeroSaltEncryptedString", typeClass = EncryptedStringType.class, parameters = { @Parameter(name = "encryptorRegisteredName", value = "hibernateStringZeroSaltEncryptor") }) })
public class PrecioBase implements Serializable {

	private static final long serialVersionUID = -552820805692401247L;

	@Id
	@TableGenerator(name = "precio_base_generator", table = "generic_generator", pkColumnName = "sequence_name", valueColumnName = "sequence_value", pkColumnValue = "precio_base_gen", initialValue = 10000, allocationSize = 100)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "precio_base_generator")
	@Index(columnNames = "id", name = "precio_base_id_idx")
	private Long id;

	@Type(type = "encryptedString")
	@Column(name = "precio")
	@Index(columnNames = "precio", name = "precio_base_precio_idx")
	private String precio;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPrecio() {
		return precio;
	}

	public void setPrecio(String precio) {
		this.precio = precio;
	}

}
