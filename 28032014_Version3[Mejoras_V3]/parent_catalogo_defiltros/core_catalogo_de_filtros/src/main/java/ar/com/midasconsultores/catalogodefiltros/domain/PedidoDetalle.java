package ar.com.midasconsultores.catalogodefiltros.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Index;

@Entity
@Table(name = "pedido_detalle")
public class PedidoDetalle implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1705629105342535309L;


	@Id
	@TableGenerator(name = "tipo_vehiculo_generator", table = "generic_generator", pkColumnName = "sequence_name", valueColumnName = "sequence_value", pkColumnValue = "tipo_vehiculo_gen", initialValue = 10000, allocationSize = 100)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "tipo_vehiculo_generator")
	@Index(columnNames = "id", name = "tipo_vehiculo_id_idx")
	private Long id;

	
	@OneToOne
	@NotNull
	private Filtro filtro;
	
	private String precio;

	@Column(name = "cantidad")
	@NotNull
	private int cantidad;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Filtro getFiltro() {
		return filtro;
	}

	public void setFiltro(Filtro filtro) {
		this.filtro = filtro;
	}

	public int getCantidad() {
		return cantidad;
	}

	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}

	public String getPrecio() {
		return precio;
	}

	public void setPrecio(String precio) {
		this.precio = precio;
	}
	
	

}
