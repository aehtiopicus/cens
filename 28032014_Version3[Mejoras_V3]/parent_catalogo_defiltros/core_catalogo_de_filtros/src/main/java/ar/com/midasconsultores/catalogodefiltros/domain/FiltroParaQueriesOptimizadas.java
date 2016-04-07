package ar.com.midasconsultores.catalogodefiltros.domain;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

import org.hibernate.annotations.Index;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;
import org.jasypt.hibernate4.type.EncryptedStringType;

@Entity
@Table(name = "filtroparaqueriesoptimizadas")
public class FiltroParaQueriesOptimizadas implements Serializable {

	private static final long serialVersionUID = -7912277433372868589L;

	@Id
	@TableGenerator(name = "filtroparaqueriesoptimizadas_generator", table = "generic_generator", pkColumnName = "sequence_name", valueColumnName = "sequence_value", pkColumnValue = "filtroparaqueriesoptimizadas_gen", initialValue = 10000, allocationSize = 100)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "filtroparaqueriesoptimizadas_generator")
	@Index(columnNames = "id", name = "filtroparaqueriesoptimizadas_id_idx")
	private Long id;

	@Column(name = "id_filtro")
	private Long id_filtro;

	@Column(name = "codigocorto")
	@Index(columnNames = "codigocorto", name = "filtroparaqueriesoptimizadas_codigocorto_idx")
	private String codigoCorto;

	@Column(name = "codigolargo")
	@Index(columnNames = "codigocorto", name = "filtroparaqueriesoptimizadas_codigolargo_idx")
	private String codigoLargo;

	@Column(name = "codigocortolimpio")
	@Index(columnNames = "codigocortolimpio", name = "filtroparaqueriesoptimizadas_codigocortolimpio_idx")
	private String codigoCortoLimpio;

	@Column(name = "codigolargolimpio")
	@Index(columnNames = "codigolargolimpio", name = "filtroparaqueriesoptimizadas_codigolargolimpio_idx")
	private String codigoLargoLimpio;

	@Column(name = "marca")
	@Index(columnNames = "marca", name = "filtroparaqueriesoptimizadas_marca_idx")
	private String marca;
        
    @Column(name = "prioridadmarca")
    private Integer prioridadMarca;


	@Column(name = "descripcion")
	private String descripcion;

	
	@Column(name = "medidas")
	private String medidas;


	@Column(name = "tipo")
	@Index(columnNames = "tipo", name = "filtroparaqueriesoptimizadas_tipo_idx")
	private String tipo;

	
	@Column(name = "subtipo")
	@Index(columnNames = "subtipo", name = "filtroparaqueriesoptimizadas_subtipo_idx")
	private String subTipo;

	
	@Column(name = "tipoAplicacion")
	@Index(columnNames = "tipoAplicacion", name = "filtroparaqueriesoptimizadas_tipoAplicacion_idx")
	private String tipoAplicacion;


	@Column(name = "marcaAplicacion")
	@Index(columnNames = "marcaAplicacion", name = "filtroparaqueriesoptimizadas_marcaAplicacion_idx")
	private String marcaAplicacion;

	
	@Column(name = "modeloAplicacion")
	@Index(columnNames = "modeloAplicacion", name = "filtroparaqueriesoptimizadas_modeloAplicacion_idx")
	private String modeloAplicacion;

	@Column(name = "original")
	private Boolean original;

	@Column(name = "foto")
	private String foto;

	@Column(name = "propio")
	@Index(columnNames = "propio", name = "filtroparaqueriesoptimizadas_propio_idx")
	private Boolean propio;


	@Column(name = "empresa")
	@Index(columnNames = "empresa", name = "filtroparaqueriesoptimizadas_empresa_idx")
	private String empresa;


	@Column(name = "codigodebarra")
	@Index(columnNames = "codigoDeBarra", name = "filtroparaqueriesoptimizadas_codigoDeBarra_idx")
	private String codigoDeBarra;


	@Column(name = "largofiltro")
	private String largoFiltro;

	
	@Column(name = "anchofiltro")
	private String anchoFiltro;

	
	@Column(name = "roscafiltro")
	private String roscaFiltro;


	@Column(name = "alturafiltro")
	private String alturaFiltro;


	@Column(name = "roscasensorfiltro")
	private String roscaSensorFiltro;

	@Column(name = "precio")
	@Index(columnNames = "precio", name = "filtroparaqueriesoptimizadas_precio_idx")
	private String precio;
	
	@OneToMany
	@JoinTable(name = "filtroparaqueriesoptimizadas_filtro", joinColumns = { @JoinColumn(name = "filtro_id", referencedColumnName = "id") })
	private List<Filtro> reemplazos;

	public String getCodigoCortoLimpio() {
		return codigoCortoLimpio;
	}

	public void setCodigoCortoLimpio(String codigoCortoLimpio) {
		this.codigoCortoLimpio = codigoCortoLimpio;
	}

	public String getCodigoLargoLimpio() {
		return codigoLargoLimpio;
	}

	public void setCodigoLargoLimpio(String codigoLargoLimpio) {
		this.codigoLargoLimpio = codigoLargoLimpio;
	}

	public String getLargoFiltro() {
		return largoFiltro;
	}

	public void setLargoFiltro(String largoFiltro) {
		this.largoFiltro = largoFiltro;
	}

	public String getAnchoFiltro() {
		return anchoFiltro;
	}

	public void setAnchoFiltro(String anchoFiltro) {
		this.anchoFiltro = anchoFiltro;
	}

	public String getRoscaFiltro() {
		return roscaFiltro;
	}

	public void setRoscaFiltro(String roscaFiltro) {
		this.roscaFiltro = roscaFiltro;
	}

	public String getAlturaFiltro() {
		return alturaFiltro;
	}

	public void setAlturaFiltro(String alturaFiltro) {
		this.alturaFiltro = alturaFiltro;
	}

	public String getRoscaSensorFiltro() {
		return roscaSensorFiltro;
	}

	public void setRoscaSensorFiltro(String roscaSensorFiltro) {
		this.roscaSensorFiltro = roscaSensorFiltro;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCodigoCorto() {
		return codigoCorto;
	}

	public void setCodigoCorto(String codigoCorto) {
		this.codigoCorto = codigoCorto;
	}

	public String getCodigoLargo() {
		return codigoLargo;
	}

	public void setCodigoLargo(String codigoLargo) {
		this.codigoLargo = codigoLargo;
	}

	public String getMarca() {
		return marca;
	}

	public void setMarca(String marca) {
		this.marca = marca;
	}

        public Integer getPrioridadMarca() {
            return prioridadMarca;
        }

        public void setPrioridadMarca(Integer prioridadMarca) {
            this.prioridadMarca = prioridadMarca;
        }

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getMedidas() {
		return medidas;
	}

	public void setMedidas(String medidas) {
		this.medidas = medidas;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getSubTipo() {
		return subTipo;
	}

	public void setSubTipo(String subTipo) {
		this.subTipo = subTipo;
	}

	public Boolean getOriginal() {
		return original;
	}

	public void setOriginal(Boolean original) {
		this.original = original;
	}

	public String getFoto() {
		return foto;
	}

	public void setFoto(String foto) {
		this.foto = foto;
	}

	public Boolean getPropio() {
		return propio;
	}

	public void setPropio(Boolean propio) {
		this.propio = propio;
	}

	public String getEmpresa() {
		return empresa;
	}

	public void setEmpresa(String empresa) {
		this.empresa = empresa;
	}

	public String getCodigoDeBarra() {
		return codigoDeBarra;
	}

	public void setCodigoDeBarra(String codigoDeBarra) {
		this.codigoDeBarra = codigoDeBarra;
	}

	public List<Filtro> getReemplazos() {
		return reemplazos;
	}

	public void setReemplazos(List<Filtro> reemplazos) {
		this.reemplazos = reemplazos;
	}

	public String getTipoAplicacion() {
		return tipoAplicacion;
	}

	public void setTipoAplicacion(String tipoAplicacion) {
		this.tipoAplicacion = tipoAplicacion;
	}

	public String getMarcaAplicacion() {
		return marcaAplicacion;
	}

	public void setMarcaAplicacion(String marcaAplicacion) {
		this.marcaAplicacion = marcaAplicacion;
	}

	public String getModeloAplicacion() {
		return modeloAplicacion;
	}

	public void setModeloAplicacion(String modeloAplicacion) {
		this.modeloAplicacion = modeloAplicacion;
	}

	public Long getId_filtro() {
		return id_filtro;
	}

	public void setId_filtro(Long id_filtro) {
		this.id_filtro = id_filtro;
	}

	public String getPrecio() {
		return precio;
	}

	public void setPrecio(String precio) {
		this.precio = precio;
	}
	
}
