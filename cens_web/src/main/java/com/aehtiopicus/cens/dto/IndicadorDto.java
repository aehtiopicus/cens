package com.aehtiopicus.cens.dto;





public class IndicadorDto implements Comparable<IndicadorDto>{

	private Long clienteId;
	private String cliente;
	private Long cantidadEmpleadosP1;
	private Long cantidadEmpleadosP2;
	private Double cantidadSueldoP1;
	private Double cantidadSueldoP2;
	private Long diferenciaEmpleados;
	private Double diferenciaSueldos;
	public Long getClienteId() {
		return clienteId;
	}
	public void setClienteId(Long clienteId) {
		this.clienteId = clienteId;
	}
	public String getCliente() {
		return cliente;
	}
	public void setCliente(String cliente) {
		this.cliente = cliente;
	}
	public Long getCantidadEmpleadosP1() {
		return cantidadEmpleadosP1;
	}
	public void setCantidadEmpleadosP1(Long cantidadEmpleadosP1) {
		this.cantidadEmpleadosP1 = cantidadEmpleadosP1;
	}
	public Long getCantidadEmpleadosP2() {
		return cantidadEmpleadosP2;
	}
	public void setCantidadEmpleadosP2(Long cantidadEmpleadosP2) {
		this.cantidadEmpleadosP2 = cantidadEmpleadosP2;
	}
	public Double getCantidadSueldoP1() {
		return cantidadSueldoP1;
	}
	public void setCantidadSueldoP1(Double cantidadSueldoP1) {
		this.cantidadSueldoP1 = cantidadSueldoP1;
	}
	public Double getCantidadSueldoP2() {
		return cantidadSueldoP2;
	}
	public void setCantidadSueldoP2(Double cantidadSueldoP2) {
		this.cantidadSueldoP2 = cantidadSueldoP2;
	}
	public Long getDiferenciaEmpleados() {
		return diferenciaEmpleados;
	}
	public void setDiferenciaEmpleados(Long diferenciaEmpleados) {
		this.diferenciaEmpleados = diferenciaEmpleados;
	}
	public Double getDiferenciaSueldos() {
		return diferenciaSueldos;
	}
	public void setDiferenciaSueldos(Double diferenciaSueldos) {
		this.diferenciaSueldos = diferenciaSueldos;
	}
	@Override
	public int compareTo(IndicadorDto o) {
			return this.getCliente().compareTo(o.getCliente());

	}
	
	

}
