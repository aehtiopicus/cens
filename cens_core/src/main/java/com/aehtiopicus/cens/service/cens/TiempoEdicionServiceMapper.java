package com.aehtiopicus.cens.service.cens;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.SerializationUtils;
import org.springframework.stereotype.Component;

import com.aehtiopicus.cens.domain.entities.AsignaturaTiempoEdicion;
import com.aehtiopicus.cens.domain.entities.MaterialDidacticoTiempoEdicion;
import com.aehtiopicus.cens.domain.entities.ProgramaTiempoEdicion;
import com.aehtiopicus.cens.domain.entities.TiempoEdicion;
import com.aehtiopicus.cens.enumeration.cens.EstadoRevisionType;
import com.aehtiopicus.cens.enumeration.cens.TiempoEdicionReporteType;
import com.aehtiopicus.cens.utils.Utils;

@Component
public class TiempoEdicionServiceMapper {

	public List<AsignaturaTiempoEdicion> getAsignaturasTiempoEdicion(List<Object[]> asignaturaList) {

		List<AsignaturaTiempoEdicion> asignaturaTiempoEdicionList = null;
		if (CollectionUtils.isNotEmpty(asignaturaList)) {
			AsignaturaTiempoEdicion result = null;
			asignaturaTiempoEdicionList = new ArrayList<>();
			for (Object[] obj : asignaturaList) {
				result = new AsignaturaTiempoEdicion();
				result.setAsignaturaId(getObject(Long.class, obj[0]));
				result.setMiembroId(getObject(Long.class, obj[1]));
				result.setFechaAsignacion(getObject(Date.class, obj[2]));
				result.setProgramaId(getObject(Long.class, obj[3]));
				result.setEstadoRevision(getObject(EstadoRevisionType.class, obj[4]));
				result.setCantidadCartillas(getObject(Long.class, obj[5]));
				result.setProgramaFechaUpdate(getObject(Date.class, obj[6]));
				asignaturaTiempoEdicionList.add(result);
			}
		}
		return asignaturaTiempoEdicionList;
	}

	private <T, K> T getObject(Class<T> data, K obj) {
		if (obj != null) {
			if (obj instanceof Number) {
				return (data.cast(((Number) obj).longValue()));
			} else {
				if (data.isEnum()) {
					for (T enums : data.getEnumConstants()) {
						if (enums.toString().equals(obj.toString())) {
							return enums;
						}
					}
				}
				return data.cast(obj);
			}
		}
		return null;
	}

	public List<ProgramaTiempoEdicion> getProgamaTiempoEdicion(List<Object[]> asignaturaList) {
		HashSet<ProgramaTiempoEdicion> programaTiempoEdicionList = null;
		if (CollectionUtils.isNotEmpty(asignaturaList)) {
			ProgramaTiempoEdicion result = null;
			programaTiempoEdicionList = new HashSet<>();
			for (Object[] obj : asignaturaList) {
				result = new ProgramaTiempoEdicion();
				result.setProgramaId(getObject(Long.class, obj[0]));
				result.setCartillas(getObject(Long.class, obj[1]));
				result.setAsignaturaId(getObject(Long.class, obj[2]));
				result.setFechaCambioEstado(getObject(Date.class, obj[8]));
				result.setMiembroId(getObject(Long.class, obj[9]));
				boolean add = true;
				if (programaTiempoEdicionList.contains(result)) {
					Iterator<ProgramaTiempoEdicion> pi = programaTiempoEdicionList.iterator();
					while (pi.hasNext()) {
						ProgramaTiempoEdicion pte = pi.next();
						if (pte.equals(result)) {
							result = pte;
							add = false;
							break;
						}
					}
				}
				if (result.getMaterial() == null) {
					result.setMaterial(new ArrayList<MaterialDidacticoTiempoEdicion>());
				}
				List<MaterialDidacticoTiempoEdicion> materialList = result.getMaterial();
				MaterialDidacticoTiempoEdicion material = new MaterialDidacticoTiempoEdicion();

				material.setId(getObject(Long.class, obj[3]));
				material.setMiembroId(getObject(Long.class, obj[4]));
				material.setEstadoRevision(getObject(EstadoRevisionType.class, obj[5]));
				material.setNroCartilla(getObject(Long.class, obj[6]));
				material.setFechaCambioEstado(getObject(Date.class, obj[7]));
				materialList.add(material);
				if (add) {
					programaTiempoEdicionList.add(result);
				}

			}
		}
		return programaTiempoEdicionList != null ? new ArrayList<ProgramaTiempoEdicion>(programaTiempoEdicionList)
				: new ArrayList<ProgramaTiempoEdicion>();
	}

	/**
	 * Ver las asginaturas sin programas por x tiempo
	 * 
	 * @param asignaturaTiempoEdicion
	 */
	public void assembleTiempoEdicionAsignaturasSinPrograma(List<AsignaturaTiempoEdicion> asignaturaTiempoEdicion,
			List<TiempoEdicion> tiempoEdicionList, List<Long> asesorIdList, int programaInicio) {
		if (CollectionUtils.isNotEmpty(asignaturaTiempoEdicion)) {
			Iterator<AsignaturaTiempoEdicion> asignaturaIterator = asignaturaTiempoEdicion.iterator();
			TiempoEdicion tiempoEdicion = null;
			while (asignaturaIterator.hasNext()) {
				AsignaturaTiempoEdicion asignatura = asignaturaIterator.next();
				if (asignatura.getProgramaId() == null) {

					if (Utils.dateDiff(new Date(), asignatura.getFechaAsignacion()) > programaInicio) {
						tiempoEdicion = new TiempoEdicion();
						tiempoEdicion.setTiempoEdicionReporteType(TiempoEdicionReporteType.ASIGNATURA);
						tiempoEdicion.setFromId(asignatura.getMiembroId());
						tiempoEdicion.setTipoId(asignatura.getAsignaturaId());
						tiempoEdicion.setAsignaturaId(asignatura.getAsignaturaId());
						tiempoEdicion
								.setFechaVencido(Utils.plusDate(asignatura.getFechaAsignacion(), programaInicio + 1));
						tiempoEdicion.setEstadoRevisionType(EstadoRevisionType.INEXISTENTE);
						for (Long asesorId : asesorIdList) {
							TiempoEdicion tiempoEdicionCloned = SerializationUtils.clone(tiempoEdicion);
							tiempoEdicionCloned.setToId(tiempoEdicionCloned.getFromId().equals(asesorId) ? -1l : asesorId);
							tiempoEdicionList.add(tiempoEdicionCloned);
						}
						asignaturaIterator.remove();
					}
				}
			}
		}

	}

	public void assembleTiempoEdicionAsignaturasConPrograma(List<AsignaturaTiempoEdicion> asignaturaTiempoEdicion,
			List<TiempoEdicion> tiempoEdicionList, List<Long> asesorIdList, Integer programaMismoEstado) {
		if (CollectionUtils.isNotEmpty(asignaturaTiempoEdicion)) {
			Iterator<AsignaturaTiempoEdicion> asignaturaIterator = asignaturaTiempoEdicion.iterator();
			TiempoEdicion tiempoEdicion = null;
			while (asignaturaIterator.hasNext()) {
				AsignaturaTiempoEdicion asignatura = asignaturaIterator.next();
				if (asignatura.getProgramaId() != null) {

					if (Utils.dateDiff(new Date(), asignatura.getProgramaFechaUpdate()) > programaMismoEstado) {
						tiempoEdicion = new TiempoEdicion();
						tiempoEdicion.setTiempoEdicionReporteType(TiempoEdicionReporteType.PROGRAMA);
						tiempoEdicion.setFromId(asignatura.getMiembroId());
						tiempoEdicion.setTipoId(asignatura.getAsignaturaId());
						tiempoEdicion.setAsignaturaId(asignatura.getAsignaturaId());
						tiempoEdicion.setFechaVencido(
								Utils.plusDate(asignatura.getProgramaFechaUpdate(), programaMismoEstado + 1));
						tiempoEdicion.setEstadoRevisionType(asignatura.getEstadoRevision());
						tiempoEdicion.setCantidadCartillas(asignatura.getCantidadCartillas());
						for (Long asesorId : asesorIdList) {
							TiempoEdicion tiempoEdicionCloned = SerializationUtils.clone(tiempoEdicion);
							tiempoEdicionCloned
									.setToId(tiempoEdicionCloned.getFromId().equals(asesorId) ? -1l : asesorId);
							tiempoEdicionList.add(tiempoEdicionCloned);
						}
						asignaturaIterator.remove();
					}
				}
			}
		}

	}

	public void assembleTiempoEdicionProgramaSinMaterial(List<ProgramaTiempoEdicion> programaTiempoEdicion,
			List<TiempoEdicion> tiempoEdicionList, List<Long> asesorIdList, Integer materialInicio) {
		if (CollectionUtils.isNotEmpty(programaTiempoEdicion)) {
			Iterator<ProgramaTiempoEdicion> programaIterator = programaTiempoEdicion.iterator();
			TiempoEdicion tiempoEdicion = null;
			while (programaIterator.hasNext()) {
				ProgramaTiempoEdicion programa = programaIterator.next();
				if (CollectionUtils.isNotEmpty(programa.getMaterial())) {

					if (Utils.dateDiff(new Date(), programa.getFechaCambioEstado()) > materialInicio) {

						tiempoEdicion = new TiempoEdicion();
						tiempoEdicion.setTiempoEdicionReporteType(TiempoEdicionReporteType.PROGRAMA);
						tiempoEdicion.setFromId(programa.getMiembroId());
						tiempoEdicion.setTipoId(programa.getProgramaId());
						tiempoEdicion.setAsignaturaId(programa.getAsignaturaId());
						tiempoEdicion
								.setFechaVencido(Utils.plusDate(programa.getFechaCambioEstado(), materialInicio + 1));
						tiempoEdicion.setEstadoRevisionType(EstadoRevisionType.INEXISTENTE);
						for (Long asesorId : asesorIdList) {
							TiempoEdicion cloned = SerializationUtils.clone(tiempoEdicion);
							cloned.setToId(cloned.getFromId().equals(asesorId) ? -1l : asesorId);
							tiempoEdicionList.add(cloned);
						}

					}
					programaIterator.remove();
				}
			}
		}

	}

	public void assembleTiempoEdicionProgramaConMaterial(List<ProgramaTiempoEdicion> programaTiempoEdicion,
			List<TiempoEdicion> tiempoEdicionList, List<Long> asesorIdList, Integer materialMismoEstado,
			Integer materialInicio) {
		if (CollectionUtils.isNotEmpty(programaTiempoEdicion)) {
			Iterator<ProgramaTiempoEdicion> programaIterator = programaTiempoEdicion.iterator();
			TiempoEdicion tiempoEdicion = null;
			while (programaIterator.hasNext()) {
				ProgramaTiempoEdicion programa = programaIterator.next();
				if (CollectionUtils.isNotEmpty(programa.getMaterial())) {

					tiempoEdicion = new TiempoEdicion();
					tiempoEdicion.setAsignaturaId(programa.getAsignaturaId());
					tiempoEdicion.setCantidadCartillas(programa.getCartillas());
					tiempoEdicion.setProgramaId(programa.getProgramaId());
					tiempoEdicion.setTiempoEdicionReporteType(TiempoEdicionReporteType.MATERIAL);

					int cantidad = 0;
					boolean aceptado = false;
					Calendar c = Calendar.getInstance();
					c.set(Calendar.YEAR, 2013);
					Date fechaAceptadaUltimaCartilla = c.getTime();

					for (MaterialDidacticoTiempoEdicion material : programa.getMaterial()) {
						cantidad++;
						if (Utils.dateDiff(new Date(), material.getFechaCambioEstado()) > materialMismoEstado
								&& !material.getEstadoRevision().equals(EstadoRevisionType.ACEPTADO)) {
							TiempoEdicion tiempoCloned = SerializationUtils.clone(tiempoEdicion);
							tiempoCloned.setTipoId(material.getId());
							tiempoCloned.setFromId(material.getMiembroId());
							tiempoCloned.setEstadoRevisionType(material.getEstadoRevision());
							tiempoCloned.setFechaVencido(
									Utils.plusDate(material.getFechaCambioEstado(), materialMismoEstado + 1));
							for (Long asesorId : asesorIdList) {
								TiempoEdicion tiempoClonedForToId = SerializationUtils.clone(tiempoCloned);
								tiempoClonedForToId
										.setToId(tiempoClonedForToId.getFromId().equals(asesorId) ? -1l : asesorId);
								tiempoEdicionList.add(tiempoClonedForToId);
							}
						}
						if (material.getEstadoRevision().equals(EstadoRevisionType.ACEPTADO)) {
							aceptado = true;
							fechaAceptadaUltimaCartilla = fechaAceptadaUltimaCartilla
									.before(material.getFechaCambioEstado()) ? material.getFechaCambioEstado()
											: fechaAceptadaUltimaCartilla;
						}
					}

					if (cantidad < tiempoEdicion.getCantidadCartillas() && aceptado
							&& (Utils.dateDiff(new Date(), fechaAceptadaUltimaCartilla) > materialInicio)) {

						tiempoEdicion.setCartillasMissing(tiempoEdicion.getCantidadCartillas() - cantidad);
						tiempoEdicion.setFromId(programa.getMiembroId());
						tiempoEdicion.setEstadoRevisionType(EstadoRevisionType.INEXISTENTE);
						tiempoEdicion.setTipoId(programa.getProgramaId());
						tiempoEdicion.setTiempoEdicionReporteType(TiempoEdicionReporteType.PROGRAMA);
						tiempoEdicion
								.setFechaVencido(Utils.plusDate(fechaAceptadaUltimaCartilla, materialMismoEstado + 1));
						for (Long asesorId : asesorIdList) {
							TiempoEdicion tiempoClonedForToId = SerializationUtils.clone(tiempoEdicion);
							tiempoClonedForToId.setToId(tiempoEdicion.getFromId().equals(asesorId) ? -1l : asesorId);
							tiempoEdicionList.add(tiempoEdicion);
						}						
					}

				}
			}

		}

	}
}
