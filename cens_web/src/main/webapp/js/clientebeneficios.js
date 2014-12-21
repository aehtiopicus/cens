jQuery(document).ready(function () {

        jQuery("#projectTable").jqGrid({
            url: "../beneficiosgrilla?clienteId=" + $('#clienteId').val(),
            datatype: "json",
            contentType :'application/json',
            jsonReader: {repeatitems: false, id:"beneficioClienteId"},
            colNames:['Beneficio', 'Tipo', 'Valor','Habilitado','Acci&oacute;n','<span class="ui-icon ui-icon-pencil"/>'],
            colModel:[
                {name:'titulo',index:'titulo',sortable: false},
                {name:'tipo',index:'tipo',sortable: false},
                {name:'valor',index:'valor',sortable: false, align: 'right', formatter: numeroCon2DecimalesCurrencyFmatter},
                { 	
                	align: 'center',
        			name: 'habilitado',  
        			width: 60,
        			editable: false, 
        			sortable: false,
        			fixed: true,
        			formatter: habilitadoCurrencyFmatter
        		},
                { 	
                	align: 'center',
        			name: 'habilitado',  
        			width: 65,
        			editable: false, 
        			sortable: false,
        			fixed: true,
        			formatter: accionCurrencyFmatter
        		},
                { 	
                	align: 'center',
        			name: 'beneficioClienteId',  
        			width: 16,
        			editable: false, 
        			sortable: false,
        			fixed: true,
        			formatter: editCurrencyFmatter
        		}

            ],
            rowNum: 0,
            viewrecords: false,
            caption: "Beneficios del Cliente"
        });
        
        $(window).bind('resize', function() {
            $("#projectTable").setGridWidth($(window).width()-marginWidthGrid);
            $("#projectTable").setGridHeight($(window).height()-marginHeightGrid+20);
        }).trigger('resize');
        
        
        $( "#remBeneficio" ).dialog({
			autoOpen: false,
			width: 400,
			buttons: [
				{
					text: "Ok",
					click: function() {
						$( this ).dialog( "close" );
						deleteBeneficio();
					}
				},
				{
					text: "Cancelar",
					click: function() {
						$( this ).dialog( "close" );
						beneficioIdToRemove = null;
					}
				}
			]
		});
        
    });
 
 function habilitadoCurrencyFmatter (cellvalue, options, rowObject){
	 if(cellvalue == true){
		 return "Si";
	 }else{
		 return "No";
	 }
 }

 function accionCurrencyFmatter (cellvalue, options, rowObject){
	 var template = "<a href='javascript:changeEstado({ENTITY_ID})' title='{TOOLTIP_TEXT}'>{ACCION_NAME}</a>";
	 var link = template.replace("{ENTITY_ID}",rowObject.beneficioClienteId);
	 if(cellvalue == true){
		 link = link.replace("{ACCION_NAME}","<span class='ui-icon ui-icon-close' style='margin-left: 38%;'/>");
		 link = link.replace("{TOOLTIP_TEXT}", "Deshabilitar Beneficio");
	 }else{
		 link = link.replace("{ACCION_NAME}","<span class='ui-icon ui-icon-check' style='margin-left: 38%;'/>");
		 link = link.replace("{TOOLTIP_TEXT}", "Habilitar Beneficio");
	 }
	 return link;
 }
 
 function editCurrencyFmatter (cellvalue, options, rowObject)
 {
	var template = "<a href='../../clientes/beneficio/{CLIENTE_ID}/{ENTITY_ID}'><span class='ui-icon ui-icon-pencil'/></a>";
	var link = template.replace("{ENTITY_ID}",cellvalue);
	link = link.replace("{CLIENTE_ID}",rowObject.clienteId);
	
    return link;
 }
 
 function numeroCon2DecimalesCurrencyFmatter (cellvalue, options, rowObject){
	 return  Number(cellvalue).toFixed(2);
 }
 
 function changeEstado(beneficioClienteId){
	 
		var pageToLoad = $('.ui-pg-input').val();
		
		usuarioIdToRemove = null;
		$('#message').removeClass('msgSuccess');
		$('#message').removeClass('msgError');
		
		$.ajax({
			type:"POST",
			url:"changeestado",
			data:{
				beneficioClienteId:beneficioClienteId
			},
			success: function(data){
				if(data.success == true){
					//refresco datos enla grilla
					gridReload(pageToLoad);
					$('#message').addClass('msgSuccess');
				}else{
					$('#message').addClass('msgError');
				}
				//cargo mensaje en pantalla
				$('#message').text(data.message)
				setTimeout("$('#message').text('')", 5000);
			}
			
		});
	} 
 
 var timeoutHnd;
 var flAuto = false;
 function doSearch(ev){
 	if(!flAuto)
 		return;

 	if(timeoutHnd)
 		clearTimeout(timeoutHnd)
 	timeoutHnd = setTimeout(gridReload,500)
 }
 
 



 
 
 function gridReload(currentPage){
	var estado = jQuery("#estado").val();
	
	var pageNro = 1; 
	if(currentPage != null && currentPage > 1){
		pageNro = currentPage;
	}
	
	jQuery("#projectTable").jqGrid(
           'setGridParam',
           {
           	url: "../beneficiosgrilla?clienteId=" + $('#clienteId').val(),
            gridview:true,
            contentType :'application/json',
      		dataType: "json",
      		page:pageNro})
      		.trigger("reloadGrid");
}

function calculatePageToLoadAfterDelete(){
	var nroRegistrosInPage = $('.jqgrow ').size();
	var currentPage = $('.ui-pg-input').val();
	

	if(currentPage > 1 && nroRegistrosInPage == 1){
		return currentPage-1;
	}
	
	return currentPage;
}

function agregarBeneficio(){
	var beneficioId = $("#beneficio").val();
	var clienteId = $("#clienteId").val();
	
	$.ajax({
	    type: "POST",
	    url: "/novatium_web/asignarbeneficio",
	    data: {
	    	'beneficioId': beneficioId,
	    	'clienteId': clienteId
	    },      
	    success: function(data) {         
	    	gridReload();
	  },
	  error: function(rs, e) {
	     alert(rs.responseText);
	     return false;
	  }
	  });
}
	
	
var beneficioIdToRemove = null;
function deleteBeneficio_dialog(beneficioId){
	beneficioIdToRemove = beneficioId;
	$("#remBeneficio").dialog("open");
}

function deleteBeneficio(){
	
	var beneficioId =beneficioIdToRemove;
	var clienteId = $("#clienteId").val();
	var pageToLoad = calculatePageToLoadAfterDelete();
	
	usuarioIdToRemove = null;
	$('#message').removeClass('msgSuccess');
	$('#message').removeClass('msgError');
	
	$.ajax({
		type:"POST",
		url:"../removebeneficiocliente",
		data:{
			clienteId:clienteId,
			beneficioId:beneficioId
		},
		success: function(data){
			if(data.success == true){
				//refresco datos enla grilla
				gridReload(pageToLoad);
				$('#message').addClass('msgSuccess');
			}else{
				$('#message').addClass('msgError');
			}
			//cargo mensaje en pantalla
			$('#message').text(data.message)
			setTimeout("$('#message').text('')", 5000);
		}
		
	});
} 

	