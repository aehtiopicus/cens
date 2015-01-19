/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

jQuery(document).ready(function () {	
   
    inicializaDialogoActualizacion();
    if(getCookie("firstLoad")!=="true"){
        setCookie("actualizar",false);
        buscarActualizacionesFTP();
        startSpinner();
        setCookie('firstLoad',true,-0.1);
    }

});

function abrirNotificadorActualizaciones(){    
    $("#actualizarDialog").dialog("open");       
}

function buscarActualizacionesFTP(){
	$.ajax({
		type:"POST",
		url: getCookie("appPath") + "buscar_actualizaciones",
		success: function(result){
			 $('#actualizacionBotones').hide(); 
                         stopSpinner();
			if(!result.serverError){
                            ftpdown= false;
                            if(result.update === updateTypeEnum.NONE){
                                $("#message").text("No hay nuevas actualizaciones para descargar.");                                
                                $('#actualizarButton').button("disable");                                                                        
                            }else{
                                setCookie("actualizar",true);
                                 $("#actualizarDialog").dialog("open");
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
				$("#message").text("No se pudo conectar con el servidor");                                
				$('#actualizarButton').button("enable");                                                                   
                                ftpdown = true;
			}			
			
		}
		
	});
}