var indice = {};
var inicial = true;
$(document).ready(function () {
	getData();
	
    $(window).bind('resize', function() {
        $(".excelGridContainer").attr("width",$(window).width()-marginWidthGrid);
        $(".excelGridContainer").attr("height",$(window).height()-marginHeightGrid);
    }).trigger('resize');
});

function mostrarInforme(informes){
	$("#grid").handsontable({
		colHeaders: ["Empleado", "id","Direccion", "Empresa", "Departamento"], 
		 columns: [
		           {
			             data: "empleado",
			             readOnly: true
		           },
		           {
		             data: "id",
		             readOnly: true
		           },
		           {
		             data: "direccion",
		           
		           },
		           {
		             data: "empresa"
		           },
		           {
		             data: "departamento"
		           }
		         ],
	      onChange: function (data) {
	    	  if(inicial == false){
	    		  indice[data[0][0]] = data[0][0];  
	    	  }else{
	    		  inicial = false;
	    	  }
	           
	       }
	  });
	
	  $("#grid").handsontable("loadData", informes);	
	  $('#grid td:nth-child(2),th:nth-child(2)').hide();
}

/*
 * recupera las filas actualizadas y se guardan
 */
function save(){
	var informes = new Array();
	var i = 0;
	for (key in indice){
		 informes[i] = $("#grid").data("handsontable").getData()[key];	
		 i++;
	}
	$.ajax({
		url: "save",
		type: "POST",
		data:
			"{ \"informes\" :"+JSON.stringify(informes)+"}"
		,
		contentType :'application/json',
		dataType: "json",
		success: function(){
			indice = {}; 
		},
		error: function(data){
			alert("error: (" + data.status + ") " + data.statusText);
		}
	});
}	


/**
 * Obtiene los datos necesarios para llenar la grilla excel :)
 * 
 */
   function getData(){
	$.ajax({
		url: "informes",
		type: "GET",
		data: {
			
		},
		dataType: "text",
		success: function(data){
			
			var oData = JSON.parse(data);
			
			mostrarInforme(oData);
		},
		error: function(data){
			alert("error: (" + data.status + ") " + data.statusText);
		}
	});
		 
}
