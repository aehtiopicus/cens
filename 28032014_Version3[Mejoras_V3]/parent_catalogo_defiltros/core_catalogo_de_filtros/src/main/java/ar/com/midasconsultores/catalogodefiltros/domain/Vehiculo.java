package ar.com.midasconsultores.catalogodefiltros.domain;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

import org.hibernate.annotations.Index;

@Entity
@Table(name = "vehiculo")
public class Vehiculo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3819211766925640666L;

	@Id
	@TableGenerator(name = "vehiculo_generator", table = "generic_generator", pkColumnName = "sequence_name", valueColumnName = "sequence_value", pkColumnValue = "vehiculo_gen", initialValue = 10000, allocationSize = 100)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "vehiculo_generator")
	@Index(columnNames="id", name="vehiculo_id_idx")
	private Long id;
	
	@ManyToOne
	@JoinColumn(name="marca")
	private MarcaVehiculo marca;

	@ManyToOne
	@JoinColumn(name="modelo")
	private ModeloVehiculo modelo;

	@ManyToOne
	@JoinColumn(name="tipoVehiculo")
	private TipoVehiculo tipoVehiculo;
	
	@Column(name = "image")
	private String image;

	@ManyToMany
	private List<Filtro> repuestos;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public MarcaVehiculo getMarca() {
		return marca;
	}

	public void setMarca(MarcaVehiculo marca) {
		this.marca = marca;
	}

	public ModeloVehiculo getModelo() {
		return modelo;
	}

	public void setModelo(ModeloVehiculo modelo) {
		this.modelo = modelo;
	}

	public TipoVehiculo getTipoVehiculo() {
		return tipoVehiculo;
	}

	public void setTipoVehiculo(TipoVehiculo tipoVehiculo) {
		this.tipoVehiculo = tipoVehiculo;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public List<Filtro> getRepuestos() {
		return repuestos;
	}

	public void setRepuestos(List<Filtro> repuestos) {
		this.repuestos = repuestos;
	}

}
