$(document).ready(function(){
	
	$("#alumno").autocomplete({     
		   delay: 500,
		   minLength: 4,
		   source : function(request,response){
			   $( "#agregar" ).button( "option", "disabled", true );
			   alumnoinscripcion.cargarDatos(request.term,response);
		   },
		  
		   select : function(event,ui){
			   $( "#alumno" ).val(ui.item.label);	
			   alumnoinscripcion.setTemporalAlumnoId(ui.item.value);
			   $( "#agregar" ).button( "option", "disabled", false );

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
	$( "#agregar" ).button( "option", "disabled", true );
	$("#agregar").on("click",function(){
		alumnoinscripcion.agregarSelected();
		$( "#agregar" ).button( "option", "disabled", true );
		$("#alumno").val("");
		if($("#alumnoHeader").hasClass("none")){
			$("#alumnoHeader").toggleClass("none");
			$("#cmaNoData").addClass("none");
			$("#btnGuardar").button( "option", "disabled", false );
		}
	});
	$("#alumnoHeader").addClass("none");
	$("#btnGuardar").button( "option", "disabled", true );
	$("#btnGuardar").on("click",function(){
		alumnoinscripcion.guardar();
	})
});

alumnos.namespace("as");
alumnos.as.inscripcion = alumnos.makeClass();

alumnos.as.inscripcion.prototype.init = function(param){
	this.alumnosRetrieved =[];
	this.alumnos = [];
	this.alumnosId = [];
	this.alumnoCurrentId;
	this.asignaturaId = param.asignaturaId;
	
	this.agregarSelected = function(){
		var self = this;
		var alumnoIndex;
		$.each(this.alumnosRetrieved,function(index,value){
			if(value.id == self.alumnoCurrentId){
				alumnoIndex = index;			
			}
		});
		this.agregarAlumno(this.alumnosRetrieved[alumnoIndex]);
	}
	this.agregarAlumno = function(alumno){
		
		var fechaNac = new Date(alumno.miembroCens.fechaNac);
		var alumno={
			id: alumno.id,
			nombre:alumno.miembroCens.nombre,
			apellido:alumno.miembroCens.apellido,
			dni:alumno.miembroCens.dni,
			text:alumno.miembroCens.nombre.toUpperCase()+" "+alumno.miembroCens.apellido.toUpperCase()+", "+alumno.miembroCens.dni+" "+fechaNac.getUTCDate()+"/"+(fechaNac.getUTCMonth()+1)+"/"+fechaNac.getUTCFullYear()
		};
		this.alumnos.push(alumno);
		this.alumnosId.push(alumno.id);
		$('#alumnoData').append(this.agregarAlumoHtml(alumno));
	}
	
	this.alumnoIdList = function(){
		return this.alumnosId;
	}
	
	this.setTemporalAlumnoId = function(alumnoId){
		this.alumnoCurrentId =alumnoId;
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
		
		var itemIndex = [];
		var self = this;
		$.each(data.rows,function(index2,value2){
			$.each(self.alumnoIdList(),function(index,value){
				if(value2.id == value){
					itemIndex.push(index2);
				}
			});
		});
		
		
		
		
		var rowsResult = $.grep(data.rows, function(n, i) {
		    return $.inArray(i, itemIndex) ==-1;
		});
		
		data.rows = rowsResult;
		
		this.alumnosRetrieved = data.rows; 
		
		var fieldData = [];

			$.each(data.rows,function(index2,value2){
				
				fieldData.push({"value":value2.id, "label" : value2.miembroCens.apellido+", "+value2.miembroCens.nombre+" ("+value2.miembroCens.dni+")"});
					
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
	
this.agregarAlumoHtml = function(alumno){
		
		var mainDiv = $("<div></div>");
		mainDiv.css("margin","3px");
		
		var dataDiv = $("<div></div>");
		dataDiv.css("display","inline-block");
		dataDiv.css("margin-top","4px");
		dataDiv.html(alumno.text);
		
		var actionMainDiv = $("<div></div>");
		actionMainDiv.css("display","inline-block");
		actionMainDiv.css("float","right");
		
		var actionInnerDiv = $("<div></div>");
		actionInnerDiv.css("margin-right","10px");
		actionInnerDiv.css("display","inline-block");
		actionInnerDiv.addClass("cmaPending");
		actionInnerDiv.attr("id",alumno.id);
		actionInnerDiv.attr("title","No Procesado");
		
		var deleteCurrentDiv = $("<div></div>");
		deleteCurrentDiv.attr("style","display:inline-block;  margin-right: -8px; margin-left: 8px;");		
		deleteCurrentDiv.addClass("ui-icon");
		deleteCurrentDiv.addClass("ui-icon-closethick-red");
		deleteCurrentDiv.addClass("img");
		deleteCurrentDiv.attr("id",alumno.id+"x");
		deleteCurrentDiv.on("click",function(){
			indexToRemove = -1;
			for(i= 0 ; i<alumnoinscripcion.alumnos.length;i++){
				 if(alumnoinscripcion.alumnos[i].id == parseInt( $('#'+alumno.id).attr("id"))){
				     indexToRemove = i;
				     break;
				 }
			}
			if(indexToRemove!=-1){
				alumnoinscripcion.alumnos.splice(indexToRemove,1);
				alumnoinscripcion.alumnosId.splice(indexToRemove,1);
			
				$('#'+alumno.id).parent().parent().remove();
				if($("#alumnoData").children().length == 0 ){
					$("#alumnoHeader").addClass("none");
					$("#cmaNoData").removeClass("none");
					$("#btnGuardar").button( "option", "disabled", true );
				}
			}else{
				alert("No se puede eliminar el registro");
				}
			
		});
		
		actionMainDiv.append(actionInnerDiv);
		actionMainDiv.append(deleteCurrentDiv);
		
		var clearBothDiv = $("<div></div>");
		clearBothDiv.css("clear","both");
		
		mainDiv.append(dataDiv);
		mainDiv.append(actionMainDiv);
		mainDiv.append(clearBothDiv);
		
		return mainDiv;
	}

this.guardar = function(){
	var self = this;
		
		$.ajax({				  
					  url: pagePath+"/api/asignatura/"+self.asignaturaId+"/inscripcion",
					  data: JSON.stringify({ asignaturaId : self.asignaturaId,
						  					alumnos:{alumnoId:self.alumnosId}}),
					  
					  dataType:"json",
					  type: "POST",
					  contentType:"application/json",
					  beforeSend: function( xhr ) {
							$.each(self.alumnosId,function(index,alumnoId){
								$("#"+alumnoId+"x").hide();
								$("#"+alumnoId).attr( "class","cmaLoading");
								$("#"+alumnoId).css("margin-right","28px");
							});
								
							},
					  success: function(data, textStatus, jqXHR){
						  $.each(self.alumnosId,function(index,alumnoId){
								$("#"+alumnoId+"x").attr( "class","cmaSuccess");
								$("#"+alumnoId).attr("title","Alumno inscripto");
							});
						
					  },
					  error:function(error,textStatus){
						  var message = " ";
						  var dataError = error.responseJSON;

						  if(typeof dataError.errorDto !== "undefined" &&  dataError.errorDto){
							  
							  for(var key in dataError.errors) {
								  message = message+dataError.errors[key]+" ";
								  
							  }
							    
						  }else{
							  message = "Error General";
						  }
						  $.each(self.alumnosId,function(index,alumnoId){
							  $("#"+alumnoId).attr( "class","cmaError");							  	
							  
							  $("#"+alumnoId).attr("title",message);
							  $("#"+alumnoId).css("margin-right","28px");
						  });
						 
						  
					  }
					  
		});

}
}