package com.aehtiopicus.cens.service.cens;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.aehtiopicus.cens.domain.entities.AsignaturaTiempoEdicion;
import com.aehtiopicus.cens.domain.entities.MaterialDidacticoTiempoEdicion;
import com.aehtiopicus.cens.domain.entities.ProgramaTiempoEdicion;
import com.aehtiopicus.cens.enumeration.cens.EstadoRevisionType;

@Service
public class TiempoEdicionCensServiceImpl implements TiempoEdicionCensService{
	
	/**
	 * Paso 1 buscar asginaturas sin programas pero con profesor que lleven mas de n tiempo
	 * Paso 2 buscar asignaturas con programas cuyo estado lleve mas de lleven mas de n tiempo
	 * Paso 3 buscar asignaturas Sin material cuyo programa este en estado aprobado
	 * Paso 4 buscar asignaturas con programas 
	 */
	
	private final String ASIGNATURA_PROGRAMA_SQL = "SELECT DISTINCT(ca.id)asignatura_id,(cpr.miembrocens_id)miembro_id,(ca.asignacion_profesor_date)asignacion_date, (cp.id)programa_id,(cp.estadorevisiontype)estado_type,(cp.cantcartillas)cantCartillas,(cp.fecha_cambio_estado)update_date FROM cens_asignatura AS ca LEFT OUTER JOIN cens_programa AS cp ON cp.asignatura_id = ca.id inner join cens_profesor as cpr on (cpr.id = ca.profesor_id or cpr.id = ca.profesorsuplente_id) WHERE (ca.profesor_id IS NOT NULL OR ca.profesorsuplente_id IS NOT NULL) AND ca.vigente = true  AND (cp.notificado is null or cp.notificado = false) AND (cp.notificado = false or cp.notificado is null) and ( cp.estadorevisiontype = 'NUEVO' or cp.estadorevisiontype = 'LISTO' or  cp.estadorevisiontype = 'CAMBIOS' or cp.estadorevisiontype = 'RECHAZADO' or cp.estadorevisiontype = 'ASIGNADO' or cp.estadorevisiontype is null) ORDER BY ca.id DESC";
	private final String PROGRAMA_MATERIAL_SQL ="SELECT (cp.id)as programa_id, (cp.cantcartillas),cp.asignatura_id,cmd.id as material_id,cpc.miembrocens_id,cmd.estadorevisiontype,cmd.nro as cartilla_nro,cmd.fecha_cambio_estado from cens_programa as cp left outer join cens_material_didactico as cmd ON cmd.programa_id = cp.id inner join cens_profesor AS cpc ON cpc.id = cmd.profesor_id WHERE cp.estadorevisiontype = 'ACEPTADO' and cp.notificado = false and (cmd.notificado = false or cmd.notificado is null)  ORDER BY cp.id DESC";
	
	private static final String TIEMPO_EDICION_PROGRAMA_INICIO = "#{tiempoEdicionProperties['tiempo_edicion_programa_inicio']}";
	private static final String TIEMPO_EDICION_PROGRAMA_MISMO_ESTADO = "#{tiempoEdicionProperties['tiempo_edicion_programa_mismo_estado']}";
	
	private static final String TIEMPO_EDICION_MATERIAL_INICIO = "#{tiempoEdicionProperties['tiempo_edicion_material_didactico_inicio']}";
	private static final String TIEMPO_EDICION_MATERIAL_MISMO_ESTADO = "#{tiempoEdicionProperties['tiempo_edicion_material_didactico_mismo_estado']}";
	
	@Value(TIEMPO_EDICION_PROGRAMA_INICIO)
	private String programaInicio;
	@Value(TIEMPO_EDICION_PROGRAMA_MISMO_ESTADO)
	private String programaMismoEstado;
	@Value(TIEMPO_EDICION_MATERIAL_INICIO)
	private String materialInicio;
	@Value(TIEMPO_EDICION_MATERIAL_MISMO_ESTADO)
	private String materialMismoEstado;
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@Autowired
	private UsuarioCensService usuarioCens;
	
	@Override
	public void generarEntradas(){
		List<AsignaturaTiempoEdicion> asignaturaTiempoEdicion = buscarAsignaturasConProgramas();
		List<ProgramaTiempoEdicion> programaTiempoEdicion = buscarProgramasConMaterial();
		usuarioCens.asesoresId();
		
	}
	
	private void tiempoEdicionAsignaturasSinPrograma(List<AsignaturaTiempoEdicion> asignaturaTiempoEdicion){
		
		
	}
	@SuppressWarnings("unchecked")
	private List<AsignaturaTiempoEdicion> buscarAsignaturasConProgramas(){
		
		List<Object[]> asignaturaList = entityManager.createNativeQuery(ASIGNATURA_PROGRAMA_SQL).getResultList();
		List<AsignaturaTiempoEdicion> asignaturaTiempoEdicionList = null;
		if(CollectionUtils.isNotEmpty(asignaturaList)){
			AsignaturaTiempoEdicion result = null;	
			asignaturaTiempoEdicionList = new ArrayList<>();
			for(Object[] obj : asignaturaList){
				result = new AsignaturaTiempoEdicion();
				result.setAsignaturaId(getObject(Long.class,obj[0]));
				result.setMiembroId(getObject(Long.class,obj[1]));			
				result.setFechaAsignacion(getObject(Date.class,obj[2]));
				result.setProgramaId(getObject(Long.class,obj[3]));
				result.setEstadoRevision(getObject(EstadoRevisionType.class,obj[4]));
				result.setCantidadCartillas(getObject(Long.class,obj[5]));
				result.setProgramaFechaUpdate(getObject(Date.class,obj[6]));
				asignaturaTiempoEdicionList.add(result);
			}
		}
		return asignaturaTiempoEdicionList;
	}
	
	@SuppressWarnings("unchecked")
	private List<ProgramaTiempoEdicion> buscarProgramasConMaterial(){
		
		List<Object[]> asignaturaList = entityManager.createNativeQuery(PROGRAMA_MATERIAL_SQL).getResultList();
		HashSet<ProgramaTiempoEdicion> programaTiempoEdicionList = null;
		if(CollectionUtils.isNotEmpty(asignaturaList)){
			ProgramaTiempoEdicion result = null;	
			programaTiempoEdicionList = new HashSet<>();
			for(Object[] obj : asignaturaList){
				result = new ProgramaTiempoEdicion();
				result.setProgramaId(getObject(Long.class,obj[0]));
				result.setCartillas(getObject(Long.class,obj[1]));
				result.setAsignaturaId(getObject(Long.class,obj[2]));
				
				boolean add = true;
				if(programaTiempoEdicionList.contains(result)){
					Iterator<ProgramaTiempoEdicion> pi = programaTiempoEdicionList.iterator();
					while(pi.hasNext()){
						ProgramaTiempoEdicion pte = pi.next();
						if(pte.equals(result)){
							result = pte;
							add = false;
							break;
						}
					}
				}
				if(result.getMaterial() == null){
					result.setMaterial(new ArrayList<MaterialDidacticoTiempoEdicion>());
				}
				List<MaterialDidacticoTiempoEdicion> materialList = result.getMaterial();
				MaterialDidacticoTiempoEdicion material = new MaterialDidacticoTiempoEdicion();
							
				material.setId(getObject(Long.class,obj[3]));
				material.setMiembroId(getObject(Long.class,obj[4]));
				material.setEstadoRevision(getObject(EstadoRevisionType.class,obj[5]));
				material.setNroCartilla(getObject(Long.class,obj[6]));
				material.setFechaCambioEstado(getObject(Date.class,obj[7]));
				materialList.add(material);
				if(add){
					programaTiempoEdicionList.add(result);
				}
				
			}
		}

		return programaTiempoEdicionList != null ? new ArrayList<ProgramaTiempoEdicion>(programaTiempoEdicionList) : new ArrayList<ProgramaTiempoEdicion>();
	}
	
	private <T,K> T  getObject(Class<T> data, K obj){
		if(obj != null){
			if(obj instanceof Number){				
				return (data.cast(((Number)obj).longValue()));
			}else{
				if(data.isEnum()){
					for(T enums : data.getEnumConstants()){
						if(enums.toString().equals(obj.toString())){
							return enums;
						}
					}
				}
				return data.cast(obj);
			}
		}
		return null;
	}
}
