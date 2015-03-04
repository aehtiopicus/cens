$(document).ajaxStart(function() {
	startSpinner();
});
$(document).ajaxStop(function() {
	stopSpinner();	
});


jQuery(document).ready(function () {
			
	$.ajax({
		url: pagePath+"/api/miembro/"+miembroId,
		type: "GET",
		contentType :'application/json',
		dataType: "json",		
		success: function(data){
			$('#id').val(data.id);
			$('#usuarioid').val(data.usuario.id);
			$('#fechaNac').datepicker( "setDate", new Date(new Date(data.fechaNac).getTime()+3*(60*60*1000) ));//val(convertDate(data.fechaNac));
			$('#username').val(data.usuario.username);
			$('#cambiarUsername').append(data.usuario.username);
			$('#nombre').val(data.nombre);
			$('#apellido').val(data.apellido);
			$('#dni').val(data.dni);
			if(data.usuario.avatarImg){
				$('#perfilImg').toggleClass("has-image");
				$('#currentImg').attr("src",pagePath+"/api/usuario/"+$('#usuarioid').val()+"/picture");      	    	
  	    		$('#currentImg').show();
			}else{
				$('#currentImg').hide();	
			}
			
			perfiles="";
			$.each($(data.usuario.perfil),function(index,val){					
				perfiles = perfiles +val.perfilType+", ";
								 
			});
			$('#perfilUsuario').html( perfiles.substring(0,perfiles.length-2));
			
		},
		error: function(data){
			errorData = errorConverter(data);
			if(errorData.errorDto != undefined && data.errorDto){
				alert(errorConverter(data).message);
			}else{
				 alert("Se produjo un error el servidor");
			}
		}
	});
	
	$("#progressbar").progressbar({
	     value: 0,
	     change: function() {
	    	 $(".progress-label").text($("#progressbar").progressbar( "value" ) + "%" );
	     },
	     complete: function() {
	    	 $(".progress-label").text( "Carga completa!" );
	    	 startSpinner();
	     }
	   });
	$( "#cambiarPass" ).on("click",function(){
		$( "#cambiarPassword" ).dialog("open");
	});
	$("#cambiarUsername").on("click",function(){
		$( "#cambiarUser" ).dialog("open");
	});
	$( "#cambiarUser" ).dialog({
		autoOpen: false,
		width: 450,
		modal: true,
		buttons: [
			{
				text: "Guardar",
				click: function() {
					changeUsername($('#usuarioid').val());					
				}
			},
			{
				text: "Cancelar",
				click: function() {
					$( this ).dialog( "close" );					
				}
			}
		]
	});
	$( "#cambiarPassword" ).dialog({
		autoOpen: false,
		width: 450,
		modal: true,
		buttons: [
			{
				text: "Guardar",
				click: function() {
					changePassword($('#usuarioid').val());					
				}
			},
			{
				text: "Cancelar",
				click: function() {
					$( this ).dialog( "close" );					
				}
			}
		]
	});
	$( "#cambiarImagen" ).dialog({
		autoOpen: false,
		width: 250,
		modal: true,
		resizable: false,
		buttons: [
			{
				text: "Guardar",
				click: function() {
					$('#btnGuardarImagen').trigger("click");								
				}
			},
			{
				text: "Cancelar",
				click: function() {
					$( this ).dialog( "close" );					
				}
			}
		]
	});
	
	$('#fileupload').fileupload({
    	
  	  url:null,
  	  
        done: function (e, data) {
            alert("done"); 
        },
        
        // The regular expression for allowed file types, matches
        // against either file type or file name:
        //acceptFileTypes: /(\.|\/)(gif|jpe?g|png)$/i,          
        // The maximum allowed file size in bytes:
        maxFileSize: 10000000, // 10 MB
        // The minimum allowed file size in bytes:
        minFileSize: undefined, // No minimal file size
        // The limit of files to be uploaded:
        maxNumberOfFiles: 1,
        
        add: function (e, data) {
      	  closeAllErrors()
            $('#fileUploadUsed').val("false");
            $('#userImg').hide();
            if(!(/(\.|\/)(jpg|png|gif)$/i).test(data.files[0].name)){
          	  addError('fileupload',"Tipo no Soportado");
          	  return false;
            }
            startSpinner();
            $('#userImg').show("fast","linear",function(){
            	$( "#progressbar" ).progressbar( "option", "value", 0 );
				 $(".progress-label").text( "" );
            	$("#cambiarImagen").dialog("open");	
            	stopSpinner();
            });
            $('#fileUploadUsed').val("true");
           
            $("#btnGuardarImagen").off();
            $("#btnGuardarImagen").click(function () {
          	  data.process().done(function () {            		  
          		  data.submit();
                });
            })              
          
        },
        submit: function(event,data){
      	  var formData = new FormData();
      	  	formData.append("file",data.files[0]);        	  	      	  	

      	  $.ajax({
      	    url:  pagePath+"/api/usuario"+"/"+$('#usuarioid').val()+"/changepicture",
      	    type:  "POST",//post si o si sino no funciona
      	    data: formData,
      	    processData: false,  // tell jQuery not to process the data
      	    contentType: false,   // tell jQuery not to set contentType
      	    xhr: function() {  // Custom XMLHttpRequest
                  var myXhr = $.ajaxSettings.xhr();
                  if(myXhr.upload){ // Check if upload property exists
                      myXhr.upload.addEventListener('progress',progress, false); // For handling the progress of the upload
                  }
                  return myXhr;
              },
      	    success : function(result){
      	    	if(result.message){
      	    		$('#currentImg').attr("src",pagePath+"/api/usuario/"+$('#usuarioid').val()+"/picture");      	    	
      	    		$('#currentImg').show();
      	    		$('#perfilImg').toggleClass("has-image");
      	    	}
      	    	$( "#cambiarImagen" ).dialog("close");    	    
         		 
      	    },
              error: function(value){
              	stopSpinner();
              	 $( "#progressbar" ).progressbar( "option", "value", 0 );
          		 $(".progress-label").text( "" );
          		 $("#cambiarImagen").dialog("close");
          		 errorData = errorConverter(value);
       			if(errorData.errorDto != undefined && value.errorDto){
       				alert(errorConverter(value).message);
       			}else{
       				 alert("Se produjo un error el servidor");
       			}
              }
      	  });        	  
        },          
        send: function(e,data){
      	  return false;
      	  
        },
        processData: false,
        contentType: false,
        cache: false,
        autoUpload: false,
 
        dropZone: $('body') 
  }).on('always', function (e, data) {
      var currentFile = data.files[data.index];
      if (data.files.error && currentFile.error) {
        // there was an error, do something about it
        console.log(currentFile.error);
      }
    }); 
	
	function readURL(input) {

	    if (input.files && input.files[0]) {
	        var reader = new FileReader();

	        reader.onload = function (e) {	        	
	            $('#userImg').attr('src', e.target.result);
	        }

	        reader.readAsDataURL(input.files[0]);
	    }
	}

	$("#fileupload").change(function(){
	    readURL(this);
	});
});


function changeUsername(id){
	closeAllErrors();
	$.ajax({
		  type: "POST",
		  url: pagePath+"/api/usuario"+"/"+id+"/changeusername",
		  data: JSON.stringify({id:id, username:$('#username').val()}),
		  dataType:"json",
		  contentType:"application/json", 
		  success: function(value){			 		 
			 logout();
		  },
		  error:function(value){
			  dataError = errorConverter(value);
			  if(dataError.errorDto != undefined && dataError.errorDto){
					if(!validationError(dataError,true)){				  					  	  	  
			  	  		alert("Se produjo un error el servidor");
			  	  	}
				  
			  }else{
		  	  		alert("Se produjo un error el servidor");
		  	  }
			  
		  }
		  
	
	});
}

function submitMiembro(){
			
	$.ajax({
		  type: "PUT",
		  url: pagePath+"/api/miembro"+"/"+ $('#id').val(),
		  data: JSON.stringify(preparePerfilData()),
		  dataType:"json",
		  contentType:"application/json", 
		  success: function(value){
			 $('#cancelar').trigger("onclick");
		  },
		  error:function(value){
			  dataError = errorConverter(value);
			  if(dataError.errorDto != undefined && dataError.errorDto){
					if(!validationError(dataError)){				  					  	  	  
			  	  		alert("Se produjo un error el servidor");
			  	  	}
			  }else{
				  alert("Se produjo un error el servidor");	  
			  }
			  
		  }
		  
		});
	
}	

function preparePerfilData(){
		var tipo =[];
		$.each($('#perfilUsuario').html().split(","),function(index,val){
			tipo.push({perfilType :val.trim()});
		});
	
		
		var usuario={ 
				id:($("#usuarioid").length == 0 ? null : $("#usuarioid").val()),
				username:$('#username').val(),			
				perfil:tipo
		};
				
		var miembroCens ={
				id:($("#id").length == 0 ? null : $("#id").val()),
				nombre:$('#nombre').val(),
				apellido:$('#apellido').val(),
				dni:$('#dni').val(),
				fechaNac:$('#fechaNac').datepicker("getDate").toISOString(),
				usuario:usuario
				};
		
		return miembroCens;
}


function checkForm(post){
	var error = true;
	if(!checkDate($('#fechaNac').val())){
		addError('fechaNac',"Fecha invalida");
		error = false;
	}
		
	return error;
 
}

function progress (data) {
    var progress = parseInt(data.loaded / data.total * 100, 10);
    $( "#progressbar" ).progressbar( "option", "value", progress );
};



