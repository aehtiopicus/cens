jQuery(document).ready(function () {
	if(!isNaN((parseInt(window.location.pathname.substring(window.location.pathname.lastIndexOf("/")+1))))){
	$.ajax({
		url: "/miembro/"+detalledId,
		type: "GET",
		contentType :'application/json',
		dataType: "json",		
		success: function(data){	
			if(data.success){
				var template = "<p><label id='adName'>{AD_NAME}:</label> <label id='adVal' style='float:right;'>{AD_VALUE}</label></p>";
				
				$("#adicionalesDialog").html("");
				var adicional;
				for(var i = 0; i < data.adicionales.length; i++){
					adicional = template.replace("{AD_NAME}",data.adicionales[i].beneficio);
					adicional = adicional.replace("{AD_VALUE}",data.adicionales[i].valor.toFixed(2));
					$("#adicionalesDialog").append(adicional);
				}
				
				
				$("#adicionalesDialog").dialog("open");
			}else{
				$("#adicionalesDialog").html("Error");
				$("#adicionalesDialog").dialog("open");
			}
			
		},
		error: function(data){
			alert("error: (" + data.status + ") " + data.statusText);
		}
	});
	}
});