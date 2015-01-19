package ar.com.midasconsultores.catalogodefiltros.domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.TableGenerator;

@Entity(name ="precio_venta_configuracion")
public class PrecioVenta implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6878755669196466544L;
	@Id
	@TableGenerator(name = "precio_venta_configuracion_generator", table = "generic_generator", pkColumnName = "sequence_name", valueColumnName = "sequence_value", pkColumnValue = "precio_venta_configuracion_gen", initialValue = 10000, allocationSize = 100)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "precio_venta_configuracion_generator")
	private Long id;
	
	private boolean ajuste;
	
	private double porcentaje;
			
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public double getPorcentaje() {
		return porcentaje;
	}
	public void setPorcentaje(double porcentaje) {
		this.porcentaje = porcentaje;
	}
	public boolean isAjuste() {
		return ajuste;
	}
	public void setAjuste(boolean ajuste) {
		this.ajuste = ajuste;
	}
	
}
