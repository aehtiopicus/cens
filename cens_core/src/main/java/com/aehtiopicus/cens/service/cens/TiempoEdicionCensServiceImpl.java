package com.aehtiopicus.cens.service.cens;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.aehtiopicus.cens.domain.entities.AsignaturaTiempoEdicion;
import com.aehtiopicus.cens.domain.entities.ProgramaTiempoEdicion;
import com.aehtiopicus.cens.domain.entities.TiempoEdicion;



@Service
public class TiempoEdicionCensServiceImpl implements TiempoEdicionCensService{
	
	/**
	 * Paso 1 buscar asginaturas sin programas pero con profesor que lleven mas de n tiempo
	 * Paso 2 buscar asignaturas con programas cuyo estado lleve mas de lleven mas de n tiempo
	 * Paso 3 buscar asignaturas Sin material cuyo programa este en estado aprobado
	 * Paso 4 buscar asignaturas con programas 
	 */
	
	private final String ASIGNATURA_PROGRAMA_SQL = "SELECT DISTINCT(ca.id)asignatura_id,(cpr.miembrocens_id)miembro_id,(ca.asignacion_profesor_date)asignacion_date, (cp.id)programa_id,(cp.estadorevisiontype)estado_type,(cp.cantcartillas)cantCartillas,(cp.fecha_cambio_estado)update_date FROM cens_asignatura AS ca LEFT OUTER JOIN cens_programa AS cp ON cp.asignatura_id = ca.id inner join cens_profesor as cpr on (cpr.id = ca.profesor_id or cpr.id = ca.profesorsuplente_id) WHERE (ca.profesor_id IS NOT NULL OR ca.profesorsuplente_id IS NOT NULL) AND ca.vigente = true  AND (cp.notificado is null or cp.notificado = false) AND (cp.notificado = false or cp.notificado is null) and ( cp.estadorevisiontype = 'NUEVO' or cp.estadorevisiontype = 'LISTO' or  cp.estadorevisiontype = 'CAMBIOS' or cp.estadorevisiontype = 'RECHAZADO' or cp.estadorevisiontype = 'ASIGNADO' or cp.estadorevisiontype is null) ORDER BY ca.id DESC";
	private final String PROGRAMA_MATERIAL_SQL ="SELECT (cp.id)as programa_id, (cp.cantcartillas),cp.asignatura_id,cmd.id as material_id,cpc.miembrocens_id,cmd.estadorevisiontype,cmd.nro as cartilla_nro,cmd.fecha_cambio_estado,cp.fecha_cambio_estado as fecha_cambio_esado_programa,cpc2.miembrocens_id FROM cens_programa as cp LEFT OUTER JOIN cens_material_didactico as cmd ON cmd.programa_id = cp.id INNER JOIN cens_profesor AS cpc ON cpc.id = cmd.profesor_id INNER JOIN cens_profesor AS cpc2 ON cpc2.id = cp.profesor_id WHERE cp.estadorevisiontype = 'ACEPTADO' AND cp.notificado = false AND (cmd.notificado = false OR cmd.notificado is null)  ORDER BY cp.id DESC";
	
	private static final String TIEMPO_EDICION_PROGRAMA_INICIO = "#{tiempoEdicionProperties['tiempo_edicion_programa_inicio']}";
	private static final String TIEMPO_EDICION_PROGRAMA_MISMO_ESTADO = "#{tiempoEdicionProperties['tiempo_edicion_programa_mismo_estado']}";
	
	private static final String TIEMPO_EDICION_MATERIAL_INICIO = "#{tiempoEdicionProperties['tiempo_edicion_material_didactico_inicio']}";
	private static final String TIEMPO_EDICION_MATERIAL_MISMO_ESTADO = "#{tiempoEdicionProperties['tiempo_edicion_material_didactico_mismo_estado']}";
	
	@Value(TIEMPO_EDICION_PROGRAMA_INICIO)
	private Integer programaInicio;
	@Value(TIEMPO_EDICION_PROGRAMA_MISMO_ESTADO)
	private Integer programaMismoEstado;
	@Value(TIEMPO_EDICION_MATERIAL_INICIO)
	private Integer materialInicio;
	@Value(TIEMPO_EDICION_MATERIAL_MISMO_ESTADO)
	private Integer materialMismoEstado;
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@Autowired
	private UsuarioCensService usuarioCens;
	
	@Autowired
	private TiempoEdicionServiceMapper mapper;
	
	@Override
	public void generarEntradas(){
		List<Long> asesoresId = usuarioCens.asesoresId();
		if(CollectionUtils.isNotEmpty(asesoresId)){
			List<AsignaturaTiempoEdicion> asignaturaTiempoEdicion = buscarAsignaturasConProgramas();
			List<ProgramaTiempoEdicion> programaTiempoEdicion = buscarProgramasConMaterial();
			
			List<TiempoEdicion> tiempoEdicionList = new ArrayList<>();
			mapper.assembleTiempoEdicionAsignaturasSinPrograma(asignaturaTiempoEdicion,tiempoEdicionList,asesoresId,programaInicio);
			mapper.assembleTiempoEdicionAsignaturasConPrograma(asignaturaTiempoEdicion,tiempoEdicionList,asesoresId,programaMismoEstado);
			
			mapper.assembleTiempoEdicionProgramaSinMaterial(programaTiempoEdicion,tiempoEdicionList,asesoresId,materialInicio);
			mapper.assembleTiempoEdicionProgramaConMaterial(programaTiempoEdicion,tiempoEdicionList,asesoresId,materialMismoEstado);
		}
		
		
	}
	
	@SuppressWarnings("unchecked")
	private List<AsignaturaTiempoEdicion> buscarAsignaturasConProgramas(){
		
		return mapper.getAsignaturasTiempoEdicion(entityManager.createNativeQuery(ASIGNATURA_PROGRAMA_SQL).getResultList());		
	}
	
	@SuppressWarnings("unchecked")
	private List<ProgramaTiempoEdicion> buscarProgramasConMaterial(){
		return mapper.getProgamaTiempoEdicion(entityManager.createNativeQuery(PROGRAMA_MATERIAL_SQL).getResultList());		
	}
	
	
}
