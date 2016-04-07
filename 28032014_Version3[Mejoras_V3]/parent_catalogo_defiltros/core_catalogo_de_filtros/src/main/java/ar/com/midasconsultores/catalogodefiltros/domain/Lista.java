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
@Table(name = "lista")
@TypeDefs({
		@TypeDef(name = "encryptedString", typeClass = EncryptedStringType.class, parameters = { @Parameter(name = "encryptorRegisteredName", value = "hibernateStringEncryptor") }),
		@TypeDef(name = "zeroSaltEncryptedString", typeClass = EncryptedStringType.class, parameters = { @Parameter(name = "encryptorRegisteredName", value = "hibernateStringZeroSaltEncryptor") }) })
public class Lista implements Serializable {

	private static final long serialVersionUID = -552820805692401247L;

	@Id
	@TableGenerator(name = "lista_generator", table = "generic_generator", pkColumnName = "sequence_name", valueColumnName = "sequence_value", pkColumnValue = "lista_gen", initialValue = 10000, allocationSize = 100)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "lista_generator")
	@Index(columnNames = "id", name = "lista_id_idx")
	private Long id;

	@Type(type = "encryptedString")
	@Column(name = "nombrelista")
	@Index(columnNames = "nombrelista", name = "lista_nombrelista_idx")
	private String nombreLista;
	
	@Type(type = "encryptedString")
	@Column(name = "porcentaje")
	@Index(columnNames = "porcentaje", name = "lista_porcentaje_idx")
	private String porcentaje;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombreLista() {
		return nombreLista;
	}

	public void setNombreLista(String nombreLista) {
		this.nombreLista = nombreLista;
	}

	public String getPorcentaje() {
		return porcentaje;
	}

	public void setPorcentaje(String porcentaje) {
		this.porcentaje = porcentaje;
	}	

}
