
var ftpdown = false;
var updateType;
var updateTypeEnum = {
    NONE : "NONE",
    DATABASE : "DATABASE",
    IMAGES : "IMAGES",
    ALL: "ALL",
    DB_SYS : "DB_SYS",
    DB_IMG: "DB_IMG"
};
jQuery(document).ready(function () {		
	

           
       $(window).bind('resize', function() {
        
            var chosenLeftSize = $("#updateLeft").width()-200;
            if(chosenLeftSize < 200){ chosenLeftSize = 200;}
            else if(chosenLeftSize > 400){ chosenLeftSize = 300;}
            $($('#updateLeft .chosen-container')).width(chosenLeftSize);

           
        }).trigger('resize');    
    
        
    });

function limpiarDatosUpdate(){
    $("#message").text("");    
    $("#updateTypeWarning").text("");
    $('#actualizarButton').button("disable");
    $('#actualizacionBotones').show();
    $('#actualizarDialog_progressBar').hide();
    $("#updateTypeDiv").hide();
}
function abrirDialogoActualizaciones(){    
    $("#actualizarDialog").dialog("open");
    $("#updateManual").removeClass("ui-state-focus");    
}
function buscarActualizacionesFTP(){
	$.ajax({
		type:"POST",
		url: applicationPath + "buscar_actualizaciones",
		success: function(result){
			 $('#actualizacionBotones').hide(); 
                         stopSpinner();
			if(!result.serverError){
                            ftpdown= false;
                            if(result.update === updateTypeEnum.NONE){
                                $("#message").text("No hay nuevas actualizaciones para descargar.");                                
                                $('#actualizarButton').button("disable");                                        
                            }else{
                                var text;
                                $("#updateTypeWarning").text("Adevertencia: El proceso puede demorar muchos minutos acorde a su velocidad de Internet.");
                                 $("#message").text("Hay actualizaciones disponibles para descargar.");                                
                                 $('#actualizarButton').button("enable");
                                
                                     if(updateTypeEnum.DATABASE === result.update){
                                         updateType = updateTypeEnum.DATABASE;
                                         text = "<ul><li>Base de datos</li></ul>";
                                     }
                                     if(updateTypeEnum.IMAGES === result.update){
                                         updateType = updateTypeEnum.IMAGES;
                                         text = "<ul><li>Imagenes</li></ul>";
                                     }
                                     if(updateTypeEnum.ALL === result.update){
                                         updateType = updateTypeEnum.ALL;
                                         text = "<ul><li>Sistema (Requiere reinicio)</li><li>Base de datos</li><li>Imagenes</li></ul>";
                                     }
                                     if(updateTypeEnum.DB_SYS === result.update){
                                         updateType = updateTypeEnum.DB_SYS;
                                         text = "<ul><li>Sistema (Requiere reinicio)</li><li>Base de datos</li></ul>";
                                     }
                                     if(updateTypeEnum.DB_IMG === result.update){
                                         updateType = updateTypeEnum.DB_IMG;
                                         text = "<ul><li>Base de datos</li><li>Imagenes</li></ul>";
                                     }
                                 $("#updateTypeDiv").show();
                                 $("#updateTypeDiv").html(text);									
                            }			
					
			}else{
				$("#message").text("No se pudo conectar con el servidor, intente carga manual.");                                
				$('#actualizarButton').button("enable");                                                                   
                                ftpdown = true;
			}			
			
		}
		
	});
}

function fileUpload(){      
        $("#message").text("");	
	$('#updateSistemaFileupload').click();
	$('#actualizarDialog_progressBar').show();
}

function actualizarPorInternet(){
	startSpinner();
	
	$.ajax({
		type:"POST",
		url: applicationPath + "descargar_volcado"+"/"+updateType,
		success: function(result){
                    $("#updateTypeDiv").hide();
                    if(result.success===true){
                        if(result.wait!==0){
                            $('.spiner_text').html("Actualización en processo. Reinicio en 2 minutos ");
                            
                        }else{
                        $('.spiner_text').html("Proceso terminado");
			$('.spiner_text').hide();
			$('.spiner_text2').show();
                        setTimeout(function() {location.href= applicationPath + "index";},1500);    
                        }
                    }else{
                         stopSpinner();
                         $("#updateTypeWarning").text("");
                        $("#message").text("Error en el proceso. Intente Carga manual");                                
                    }
			
		}
		
	});
}
function actualizar(value){
    if(1===value){
        fileUpload();
    }else{
        actualizarPorInternet();
    }
}
function actualizarManual(){
    
    $('#actualizacionBotones').hide();
    $('#actualizarButton').button("enable");  
    $('#actualizarDialog_progressBar').show();
    $('#updateSistemaFileupload').click();
}

function actualizarAutomatico(){
 
    startSpinner();
    $('.spiner_text').html("Buscando...");
    buscarActualizacionesFTP();
}