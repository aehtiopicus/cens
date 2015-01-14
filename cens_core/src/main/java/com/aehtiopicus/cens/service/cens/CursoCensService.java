package com.aehtiopicus.cens.service.cens;

import java.util.List;

import com.aehtiopicus.cens.domain.entities.Curso;
import com.aehtiopicus.cens.domain.entities.RestRequest;
import com.aehtiopicus.cens.utils.CensException;

public interface CursoCensService {

	public Curso findCursoByYearAndNombre(int year, String nombre);

	public List<Curso> save(List<Curso> cursoList) throws CensException;

	public List<Curso> listCursos(RestRequest rr);

	public long getTotalCursos(RestRequest rr);

	public Curso getCurso(Long cursoId);

	public void deleteCurso(Long cursoId) throws CensException;

}
