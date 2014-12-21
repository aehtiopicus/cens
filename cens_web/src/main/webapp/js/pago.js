jQuery(document).ready(function () {
	$( "#empleadoSinBancoDialog" ).dialog({
		autoOpen: false,
		width: 500,
		buttons: [
			{
				text: "Ok",
				click: function() {
					$( this ).dialog( "close" );
					$('#message').removeClass('msgError');
					$('#message').addClass('msgSuccess');
					$('#message').text("Generando archivo ZIP");
					setTimeout(function(){$('#message').text("")}, 5000);
					descargar();
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
});

function exportar(){	
	var periodo = jQuery("#periodo").val();
	var concepto = jQuery("#concepto").val();
	var fechaAcr = jQuery("#fecha").val();
	$('#spiner').show();
	
	$.ajax({
		url: "validarpago",
		type: "POST",
		data:{
			periodo : periodo,
			fechaAcr : fechaAcr,
			concepto : concepto
		},
		success: function(data){
			$('#spiner').hide();
			if(data.success == true){
				$('#message').removeClass('msgError');
				$('#message').addClass('msgSuccess');
				$('#message').text("Generando archivo ZIP");
				setTimeout(function(){$('#message').text("")}, 5000);
				descargar();
			}else{
				$('#message').addClass('msgError');
				$('#message').removeClass('msgSuccess');
				
				if(data.empleados == undefined || data.empleados == null){
					//cargo mensaje en pantalla
					$('#message').text(data.message);
					setTimeout("$('#message').text('')", 5000);						
				}else{
					var template = '<li>{EMP}</li>';
					$("#empleadoSinBanco").html('');
					for(var i = 0; i<data.empleados.length; i++){
						$("#empleadoSinBanco").append(template.replace("{EMP}",data.empleados[i]));						
					}	
					
					$("#empleadoSinBancoDialog").dialog("open");
				}
			}
			
		},
		error: function(data){
			alert("error");
			alert("error: (" + data.status + ") " + data.statusText);
		}
	});
	
}

function descargar(){
	
	var periodo = jQuery("#periodo").val();
	var concepto = jQuery("#concepto").val();
	var fechaAcr = jQuery("#fecha").val();
	//window.location= window.location.href+'/../crearpago?periodo='+periodo+'&concepto='+concepto+'&fechaAcr='+fecha;
	window.location= '../administracion/crearpago?periodo='+periodo+'&concepto='+concepto+'&fechaAcr='+fechaAcr;
}