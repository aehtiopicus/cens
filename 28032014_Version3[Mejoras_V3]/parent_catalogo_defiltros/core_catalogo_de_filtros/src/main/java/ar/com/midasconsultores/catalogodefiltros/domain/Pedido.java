package ar.com.midasconsultores.catalogodefiltros.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "pedido")
public class Pedido implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2200738927315817273L;

	@Id
	@TableGenerator(name = "pedido_generator", table = "generic_generator", pkColumnName = "sequence_name", valueColumnName = "sequence_value", pkColumnValue = "pedido_gen", initialValue = 10000, allocationSize = 100)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "pedido_generator")
	private Long id;
	
	@Column(name ="fecha_inicio")
	@Temporal(TemporalType.TIMESTAMP)
	private Date fechaInicioPedido;
	
	@Column(name ="fecha_fin")
	@Temporal(TemporalType.TIMESTAMP)
	private Date fechaPedidoRealizado;
		
	@OneToMany(cascade = CascadeType.ALL,fetch =FetchType.EAGER, orphanRemoval=true)
	@NotNull
	private List<PedidoDetalle> detalles = new ArrayList<PedidoDetalle>();
	
	@Column(name ="eliminado")
	private boolean baja;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getFechaInicioPedido() {
		return fechaInicioPedido;
	}

	public void setFechaInicioPedido(Date fechaInicioPedido) {
		this.fechaInicioPedido = fechaInicioPedido;
	}

	public Date getFechaPedidoRealizado() {
		return fechaPedidoRealizado;
	}

	public void setFechaPedidoRealizado(Date fechaPedidoRealizado) {
		this.fechaPedidoRealizado = fechaPedidoRealizado;
	}

	public List<PedidoDetalle> getDetalles() {
		return detalles;
	}

	public void setDetalles(List<PedidoDetalle> detalles) {
		this.detalles = detalles;
	}

	public boolean isBaja() {
		return baja;
	}

	public void setBaja(boolean baja) {
		this.baja = baja;
	}
	
	
	
	
	
}
