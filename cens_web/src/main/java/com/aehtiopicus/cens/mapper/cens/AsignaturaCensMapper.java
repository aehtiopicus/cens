package com.aehtiopicus.cens.mapper.cens;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Component;

import com.aehtiopicus.cens.domain.entities.Asignatura;
import com.aehtiopicus.cens.dto.cens.AsignaturaDto;
import com.aehtiopicus.cens.util.Utils;

@Component
public class AsignaturaCensMapper {

	public AsignaturaDto convertAsignaturaToDto(Asignatura asignatura) {
		return Utils.getMapper().map(asignatura, AsignaturaDto.class);

	}

	public Asignatura convertAsignaturaDtoToEntity(AsignaturaDto AsignaturaDto) {
		return Utils.getMapper().map(AsignaturaDto, Asignatura.class);

	}

	public List<AsignaturaDto> convertAsignaturaListToDtoList(
			List<Asignatura> asignaturaList) {
		List<AsignaturaDto> AsignaturaDtoList = new ArrayList<>();
		if (CollectionUtils.isNotEmpty(asignaturaList))
			for (Asignatura a : asignaturaList) {
				AsignaturaDtoList.add(convertAsignaturaToDto(a));
			}
		return AsignaturaDtoList;
	}

	public List<Asignatura> convertAsignaturaDtoListToEntityList(
			List<AsignaturaDto> AsignaturaDtoList) {
		List<Asignatura> asignaturaList = new ArrayList<Asignatura>();
		if (CollectionUtils.isNotEmpty(AsignaturaDtoList))
			for (AsignaturaDto aDto : AsignaturaDtoList) {
				asignaturaList.add(convertAsignaturaDtoToEntity(aDto));
			}
		return asignaturaList;
	}
}
