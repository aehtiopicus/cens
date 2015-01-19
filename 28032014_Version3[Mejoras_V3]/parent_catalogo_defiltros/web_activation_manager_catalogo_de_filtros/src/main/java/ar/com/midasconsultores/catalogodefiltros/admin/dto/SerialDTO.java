package ar.com.midasconsultores.catalogodefiltros.admin.dto;

import java.util.List;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class SerialDTO {

	private static final String LA_CANTIDAD_DE_MESES_DEBE_SER_MAYOR_QUE_CERO = "La cantidad de meses debe ser mayor que cero.";

	private static final String EL_ID_DE_EQUIPO_ES_UN_CAMPO_REQUERIDO = "El id de equipo es un campo requerido.";

	private static final String EL_CODIGO_DE_CLIENTE_ES_UN_CAMPO_REQUERIDO = "El codigo de cliente es un campo requerido.";

	@NotNull(message = EL_ID_DE_EQUIPO_ES_UN_CAMPO_REQUERIDO)
	@Size(min=1, message = EL_ID_DE_EQUIPO_ES_UN_CAMPO_REQUERIDO)
	private String idEquipo = "";

	@NotNull(message = EL_CODIGO_DE_CLIENTE_ES_UN_CAMPO_REQUERIDO)
	@Size(min=1, message = EL_CODIGO_DE_CLIENTE_ES_UN_CAMPO_REQUERIDO)
	private String codigoDeCliente = "";

	@Min(value=1, message= LA_CANTIDAD_DE_MESES_DEBE_SER_MAYOR_QUE_CERO )
	private int mesesDeValidez;
        
        private String  vendedorId;

	public String getCodigoDeCliente() {
		return codigoDeCliente;
	}

	public void setCodigoDeCliente(String codigoDeCliente) {
		this.codigoDeCliente = codigoDeCliente;
	}

	public String getIdEquipo() {
		return idEquipo;
	}

	public void setIdEquipo(String idEquipo) {
		this.idEquipo = idEquipo;
	}

	public int getMesesDeValidez() {
		return mesesDeValidez;
	}

	public void setMesesDeValidez(int mesesDeValidez) {
		this.mesesDeValidez = mesesDeValidez;
	}

    public String getVendedorId() {
        return vendedorId;
    }

    public void setVendedorId(String vendedorId) {
        this.vendedorId = vendedorId;
    }

        
}
