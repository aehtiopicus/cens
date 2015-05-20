alumnos.namespace("al");
alumnos.al.cargamasiva = alumnos.makeClass();

alumnos.al.cargamasiva.prototype.init = function(param){
	this.dialog = '#'+param.dialogName;	
	this.fileSelector = '#'+param.fileSelection;
	this.recargar = false;
	var self = this;
		
		$(this.dialog).dialog({
			autoOpen: false,
			minWidth: 700,
			maxWidth: 700,
			modal: true,
			buttons: [
			    {
					text: "Cargar Alumnos",
					id: "cmaB",
					disabled: true,
					click: function() {
						alumnoResult.guardarAlumnos();
					}
				},				
				{
					text: "Cancelar",
					click: function() {
						$(this).dialog( "close" );
						$("#cmaB").button( "option", "disabled", true );
						if(selft.recargar){
							document.location.href = document.location.href;
						}
					}
				}
			]
		}); 	
		
	this.openDialgo = function (){
		$(this.dialog).dialog("open");
	}	
		
	$(this.fileSelector).change(function(event){
		var f = event.target.files[0]
		 var reader = new FileReader();
		    var name = f.name;
		    $("#fileUploadName").val(name);
		    reader.onload = function(e) {
		      var data = e.target.result;

		      /* if binary string, read with type 'binary' */
		      var workbook = XLS.read(data, {type: 'binary'});

		      /* DO SOMETHING WITH workbook HERE */
		     var result = cargaMasivaAlumnos.jsonFormer(workbook);
		     if(result != null){		    	
		    	 if(!cargaMasivaAlumnos.checkResult(result)){
		    	 	alert("Formato de achivo incorrecto. Descargue la plantilla");
		     	}else{	
		    	 alumnoResult = new alumnos.al.alumnos(result);
		    	 $(document).trigger("cmaComplete");
		     	}
		     }
		    };
		    reader.readAsBinaryString(f);
	})	;
	
	this.jsonFormer = function(workbook) {
		var result = {};
		var name = "index";
		workbook.SheetNames.forEach(function(sheetName) {
			var xlsData = XLS.utils.sheet_to_row_object_array(workbook.Sheets[sheetName]);
			if(xlsData.length > 0){
				result[name] = xlsData;
			}else{
				result = null;
				alert("No hay datos en la lista");
			}
		});
		$(cargaMasivaAlumnos.fileSelector).val('');
		return result;
	}
	
	this.checkResult = function(result){
		if(typeof result.index[0].apellido === "undefined" || typeof result.index[0].dni === "undefined" || typeof result.index[0].nombre === "undefined" ||typeof result.index[0].nac === "undefined" ){
			return false;
		}
		return true;
	}
	
	
}

alumnos.namespace("al");
alumnos.al.alumnos = alumnos.makeClass();

alumnos.al.alumnos.prototype.init = function(param){
	this.alumnosRaw =[];
	var i = 1;
	
	var self = this;		
	
	this.armarAlumno = function(alumnoRaw){
		var alumnoNew = {
				apellido:null,dni:null,fechaNac:null,id:null,nombre:null,usuario:null
		};
		var usuarioNew = {
				id:null,username:null,perfil:[{perfilType:"ALUMNO"}]
		};
		
		alumnoNew.apellido = alumnoRaw.apellido;
		alumnoNew.dni = alumnoRaw.dni;
		alumnoNew.nombre = alumnoRaw.nombre;
		alumnoNew.fechaNac = new Date(alumnoRaw.nac).toISOString();
		
		usuarioNew.username = alumnoNew.nombre.substring(0,1).toLowerCase()+ alumnoNew.apellido.substring(0,1).toLowerCase()+alumnoNew.dni;
		
		alumnoNew.usuario = usuarioNew;
		
		return alumnoNew;
		
	}
	
	this.crearAlumnosHtml = function(divToAppend){
		var self = this;
		var alumnoDataDiv = $('#'+divToAppend);
		alumnoDataDiv.empty();
		$.each(self.alumnosRaw,function(index,value){
			alumnoDataDiv.append(self.crearAlumnoHtml(value));
		});
	}
	this.crearAlumnoHtml = function(alumno){
		
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
		actionInnerDiv.attr("id",alumno.alumnosCompiled[0].usuario.username);
		actionInnerDiv.attr("title","No Procesado");
		
		var deleteCurrentDiv = $("<div></div>");
		deleteCurrentDiv.attr("style","display:inline-block;  margin-right: -8px; margin-left: 8px;");		
		deleteCurrentDiv.addClass("ui-icon");
		deleteCurrentDiv.addClass("ui-icon-closethick-red");
		deleteCurrentDiv.addClass("img");
		deleteCurrentDiv.attr("id",alumno.alumnosCompiled[0].usuario.username+"x");
		deleteCurrentDiv.on("click",function(){
			indexToRemove = -1;
			for(i= 0 ; i<alumnoResult.alumnosRaw.length;i++){
				 if(alumnoResult.alumnosRaw[i].idEasy === $('#'+alumno.alumnosCompiled[0].usuario.username).attr("id")){
				     indexToRemove = i;
				     break;
				 }
			}
			if(indexToRemove!=-1){
				alumnoResult.alumnosRaw.splice(indexToRemove,1);
			
				$('#'+alumno.alumnosCompiled[0].usuario.username).parent().parent().remove();
				if($("#alumnoData").children().length == 0 ){
					$("#alumnoHeader").addClass("none");
					$("#cmaNoData").removeClass("none");
					$("#cmaB").button( "option", "disabled", true );
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
	
	this.guardarAlumnos = function(){
		var self = this;
		$.each(this.alumnosRaw,function(index,value){
			
			$.ajax({				  
						  url: pagePath+"/api/miembro",
						  data: JSON.stringify(value.alumnosCompiled),
						  dataType:"json",
						  type: "POST",
						  contentType:"application/json",
						  beforeSend: function( xhr ) {
							  $("#"+value.alumnosCompiled[0].usuario.username+"x").hide();
							  $("#"+value.alumnosCompiled[0].usuario.username).attr( "class","cmaLoading");
							  $("#"+value.alumnosCompiled[0].usuario.username).css("margin-right","28px");
						  },
						  success: function(data, textStatus, jqXHR){
							  $("#"+value.alumnosCompiled[0].usuario.username).attr( "class","cmaSuccess");							  
							  $("#"+value.alumnosCompiled[0].usuario.username).attr("title","Alumno guardado");
							  self.recargar = true;
						  },
						  error:function(error,textStatus){
							  var message = " ";
							  var dataError = error.responseJSON;
							  var info = false;
							  if(typeof dataError.errorDto !== "undefined" &&  dataError.errorDto){
								  
								  for(var key in dataError.errors) {
									  if(dataError.errors[key] === "Existe un miembro con el documento indicado"){
										  info = true;										  
									  }
									  message = message+dataError.errors[key]+" ";
									  
								  }
								    
							  }else{
								  message = "Error General";
							  }
							  if(info){
								  $("#"+value.alumnosCompiled[0].usuario.username).attr( "class","cmaInfo");	
							  }else{
								  $("#"+value.alumnosCompiled[0].usuario.username).attr( "class","cmaError");							  	
							  }
							  $("#"+value.alumnosCompiled[0].usuario.username).attr("title",message);
							  $("#"+value.alumnosCompiled[0].usuario.username).css("margin-right","28px");
							  
						  }
						  
			});
		});
	}
	
	$.each( param.index,function(index,value){
		value.id = i		
		value.text = value.nombre.toUpperCase()+" "+value.apellido.toUpperCase()+", "+value.dni+" "+value.nac;
		value.alumnosCompiled = [self.armarAlumno(value)];
		value.idEasy=value.alumnosCompiled[0].usuario.username;
		self.alumnosRaw.push(value);
		i++;
	});
}


