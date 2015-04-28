$(document).ready(function(){
	
	$("#alumno").autocomplete({     
		   delay: 500,
		   minLength: 4,
		   source : function(request,response){
			   cargarDatos('profesor',request.term,'profesor',response);
		   },
		  
		   select : function(event,ui){
			   $( "#profesor" ).val(ui.item.label);		
			   $( "#profesorId" ).val(ui.item.value);
			   $( "#profesorName" ).val(ui.item.label);
			return false;
			},
			 focus: function (event, ui) {
			        this.value = ui.item.label;
			        event.preventDefault(); // Prevent the default focus behavior.
			  },
			change : function(event,ui){
				if(ui.item != null){
					$( "#profesor" ).val(ui.item.label);	
					$( "#profesorId" ).val(ui.item.value);
					$( "#profesorName" ).val(ui.item.label);
				}else{
					if($( "#profesor" ).val().length==0){
						$( "#profesorId" ).val("");
						$( "#profesorName" ).val("");
					}else{
						$( "#profesor" ).val($( "#profesorName" ).val());						
					}
				}
			return false;
			}
	    });
});

alumnos.namespace("as");
alumnos.as.inscripcion = alumnos.makeClass();

alumnos.as.inscripcion.prototype.init = function(param){
	this.alumnos = [];
	this.alumnosId=[];
	this.asignaturaId = param.asignaturaId;
	
	this.agregarAlumno = function(alumno){
		this.alumnosId.push(alumno.id);
		var alumno={
			id: alumno.id,
			nombre:alumno.nombre,
			apellido:alumno.apellido,
			dni:alumno.dni
		};
		alumnos.push(alumno);
	}
	
	this.alumnoIdList = function(){
		return alumnosId;
	}
		
	
	this.removeAlumno = function(alumnoIdStr){
		alumnoId = parseInt(alumnoIdStr);
		var indexToRemove = -1;
		for(i = 0 ; i<alumnos.length;i++){
			if(alumnosId[i]== alumnoId){
				indexToRemove = i;
				break;
			}
		}
		if(i!=-1){
			this.alumnos.splice(indexToRemove,1);
			this.alumnosId.splice(indexToRemove,1);			
		}
	}
}