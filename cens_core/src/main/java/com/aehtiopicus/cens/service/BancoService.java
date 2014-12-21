package com.aehtiopicus.cens.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.aehtiopicus.cens.domain.Banco;
import com.aehtiopicus.cens.domain.Prepaga;

@Service
public interface BancoService {

	
	public List<Banco> getBancos();
	
	public void saveBancos(List<Banco> bancos);
	
	public Banco save(Banco banco);
	
	public Banco findById(Long bancoId);
	
	public Banco findByNombre(String nombre);
	
	public List<Banco> search(String nombre, Integer page, Integer rows);
	
	public int getTotal(String nombre);

	public boolean deleteBanco(Long bancoId);
	
}
