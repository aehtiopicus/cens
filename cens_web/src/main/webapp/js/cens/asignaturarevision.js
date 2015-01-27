jQuery(document).ready(function () {

if(!isNaN(pageId())){
	$.ajax({
		url: pagePath+"/asignatura/"+pageId(),
		type: "GET",
		contentType :'application/json',
		dataType: "json",		
		success: function(data){
			$('#id').val(data.id);
			$('#nombre').val(data.nombre);
			$('#modalidad').val(data.modalidad);
			$('#horarios').val(data.horarios);
			if(data.profesor != null){
				$('#profesor').val(data.profesor.miembroCens.apellido+", "+data.profesor.miembroCens.nombre+" ("+data.profesor.miembroCens.dni+")");
				$('#profesorId').val(data.profesor.id);
				$('#profesorName').val(data.profesor.miembroCens.apellido+", "+data.profesor.miembroCens.nombre+" ("+data.profesor.miembroCens.dni+")");
			}
			if(data.profesorSuplente != null){
				$('#profesorSuplente').val(data.profesorSuplente.miembroCens.apellido+", "+data.profesorSuplente.miembroCens.nombre+" ("+data.profesorSuplente.miembroCens.dni+")");
				$('#profesorSuplenteId').val(data.profesorSuplente.id);
				$('#profesorSuplenteName').val(data.profesorSuplente.miembroCens.apellido+", "+data.profesorSuplente.miembroCens.nombre+" ("+data.profesorSuplente.miembroCens.dni+")");
			}
			$('#curso').val(data.curso.nombre+" ("+data.curso.yearCurso+")");
			$('#cursoId').val(data.curso.id);
			$('#cursoName').val(data.curso.nombre+" ("+data.curso.yearCurso+")");
			$('#vigente').prop("checked",data.vigente);
		},
		error: function(data){
			errorData = errorConverter(value);
			if(errorData.errorDto != undefined && value.errorDto){
				alert(errorConverter(value).message);
			}else{
				 alert("Se produjo un error el servidor");
			}
		}
	});
	}



});



