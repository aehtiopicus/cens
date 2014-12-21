package com.aehtiopicus.cens.service;

import org.springframework.stereotype.Service;

@Service
public interface InitLoadService {

	public void inicializarDataBase();

	public void cargarDatosPrueba();

	public void updateHistorialEmpleados();
}
