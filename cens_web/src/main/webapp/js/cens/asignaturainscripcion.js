$(document).ready(function(){
	
	$("#alumno").autocomplete({     
		   delay: 500,
		   minLength: 4,
		   source : function(request,response){
			   alumnoinscripcion.cargarDatos(request.term,response);
		   },
		  
		   select : function(event,ui){
			   $( "#alumno" ).val(ui.item.label);		

			return false;
			},
			 focus: function (event, ui) {
			        this.value = ui.item.label;
			        event.preventDefault(); // Prevent the default focus behavior.
			  },
			change : function(event,ui){
				if(ui.item != null){
					$( "#alumno" ).val(ui.item.label);	
				}
			return false;
			}
	    });
	
	$("#agregar").on("click",function(){
		
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
	
	this.cargarDatos = function (value,response){
		var self = this;
		$.ajax({
			url: pagePath+"/api/alumno",
			type: "GET",
			contentType :'application/json',
			dataType: "json",
			data:{ 
				requestData:self.prepareJsonRequestData(value)
				},
			success: function(data){		
				response( self.assembleAutocompleteJson(data));
			},
			error: function(errorData){
				errorData = errorConverter(errorData);
				if(errorData.errorDto != undefined && errorData.errorDto){
					addError(field,errorData.message);
				}else{
					alert("Se produjo un error el servidor");
				}
			}
		});	
	}
	
	this.assembleAutocompleteJson =function (data){
		
		var fieldData = [];

			$.each(data.rows,function(index2,value2){
					if(url ==="profesor"){
						fieldData.push({"value":value2.id, "label" : value2.miembroCens.apellido+", "+value2.miembroCens.nombre+" ("+value2.miembroCens.dni+")"});
					}else if(url ==="curso"){
						fieldData.push({"value":value2.id, "label" : value2.nombre+" ("+value2.yearCurso+")"});
					}
				});

		return fieldData;
	}
	
	this.prepareJsonRequestData = function (value){
		
		var request = {
				page: 1,
				row: 10,
				sord:"asc",
				filters:{
					data : value,
					asignaturaRemoveId: this.asignaturaId
				}
		};
		
		return JSON.stringify(request);
	}
}