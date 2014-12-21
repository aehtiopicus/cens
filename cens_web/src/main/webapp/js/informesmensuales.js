var url = "informesmensualesgrilla";
var cookiePage = "";
var cookieRegsXPage = "";

jQuery(document).ready(function () {
	
	restoreState();
	var filtros = getFiltros();
	
    jQuery("#projectTable").jqGrid({
            url: url + filtros,
            datatype: "json",
            contentType :'application/json',
            jsonReader: {repeatitems: false, id:"informeMensualId"},
            colNames:['Periodo', 'Cliente ', 'Estado', 'Acci&oacute;n','Enviar Informe','<span class="ui-icon ui-icon-trash"/>'],
            colModel:[
                {name:'periodo',index:'periodo', sortable: false},
                {name:'nombreCliente',index:'nombreCliente',sortable: false},
                {name:'estado',index:'estado',sortable: false},
                { 	
        			name: 'informeMensualId',   
        			width: 70,
        			editable: false, 
        			sortable: false,
        			fixed: true,
        			align: 'center',
        			formatter: editCurrencyFmatter
        		},
                { 	
        			name: 'informeMensualId',
        			width: 95,
        			editable: false, 
        			sortable: false,
        			fixed: true,
        			align: 'center',
        			formatter: enviarCurrencyFmatter
        		},
                { 	
                	align: 'center',
        			name: 'informeMensualId',  
        			width: 16,
        			editable: false, 
        			sortable: false,
        			fixed: true,
        			formatter: deleteCurrencyFmatter
        		}
            ],
            rowNum:cookieRegsXPage,
            rowList:[5,10,50],
            pager: "#pagingDiv",
            page:cookiePage,
            viewrecords: true,
            caption: "Informes Mensuales",
            loadComplete: function (data) {
            	$(".ui-pg-selbox").val(cookieRegsXPage);
            	$(".ui-pg-input").val(data.page);
            	setCookie('imPage', $('.ui-pg-input').val());
            	cookiePage = $('.ui-pg-input').val();
            },
            onPaging: function(page){
            	setCookie('imRegXPage', $(".ui-pg-selbox").val());
            	cookieRegsXPage = $(".ui-pg-selbox").val();
            }
        });
        
        $(window).bind('resize', function() {
            $("#projectTable").setGridWidth($(window).width()-marginWidthGrid);
            $("#projectTable").setGridHeight($(window).height()-marginHeightGridFull);
        }).trigger('resize');
        
        $( "#enviarDialog" ).dialog({
    		autoOpen: false,
    		width: 400,
    		buttons: [
    			{
    				text: "Ok",
    				click: function() {
    					$( this ).dialog( "close" );
    					enviar();
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
        
        $( "#eliminarDialog" ).dialog({
    		autoOpen: false,
    		width: 400,
    		buttons: [
    			{
    				text: "Ok",
    				click: function() {
    					$( this ).dialog( "close" );
    					eliminar();
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
 

 function editCurrencyFmatter (cellvalue, options, rowObject) {
	
	var template = "<a href='informemensual/{INFORME_MENUSAL_ID}' title='{TOOLTIP_TEXT}'>{ACCION}</a>";

	//var template = "<a href='informemensual/{INFORME_MENUSAL_ID}'>{ACCION}</a>"; 
	var link = template.replace("{INFORME_MENUSAL_ID}",cellvalue);
	
	if(rowObject.estado == "Borrador"){
		link = link.replace("{TOOLTIP_TEXT}","Editar");
		link = link.replace("{ACCION}","<span class='ui-icon ui-icon-pencil' style='margin-left: 38%;'/>");
	}else{
		link = link.replace("{TOOLTIP_TEXT}","Ver");
		link = link.replace("{ACCION}","<span class='ui-icon ui-icon-zoomin' style='margin-left: 38%;'/>");				
	}

	 return link;
 }
 
function enviarCurrencyFmatter (cellvalue, options, rowObject) {
	 if(rowObject.estado == "Borrador"){

		return "<a href='javascript:enviar_dialog("+cellvalue+")' title='Enviar Informe'><span class='ui-icon ui-icon-mail-closed' style='margin-left: 38%;'/></a>";
	}else{
		return "";
	}
}

function deleteCurrencyFmatter (cellvalue, options, rowObject)
{
	var template = "<a href='javascript:deleteInforme_dialog({ENTITY_ID})' title='Eliminar'><span class='ui-icon ui-icon-trash'/></a>";
	
	if(rowObject.estado == "Borrador"){
		return template.replace("{ENTITY_ID}",cellvalue);
	}else{
		return "";
	}
}
 
function saveState(){
	 setCookie('imF1', jQuery("#cliente").val());
	 setCookie('imF2', jQuery("#periodo").val());
}

function restoreState(){
	if(isAValidCookie('imF1')){ 
		jQuery("#cliente").val(getCookie('imF1'));
	}
	if(isAValidCookie('imF2')){
		jQuery("#periodo").val(getCookie('imF2'));
	}
	
	if(getCookie('imRegXPage') != ""){
		$(".ui-pg-selbox").val(getCookie('imRegXPage'));
		cookieRegsXPage = getCookie('imRegXPage');
	}else{
		cookieRegsXPage = 5;
	}
	if(getCookie('imPage') != ""){
		$('.ui-pg-input').val(getCookie('imPage'));
		cookiePage = getCookie('imPage');
	}else{
		cookiePage = 1;
	}
}


 function gridReload(currentPage){

	var filtros = getFiltros();
	
	var pageNro = 1; 
	if(currentPage != null && currentPage > 1){
		pageNro = currentPage;
	}
	
	jQuery("#projectTable").jqGrid('setGridParam',{
		url: url + filtros,
        gridview:true,
        contentType :'application/json',
      	dataType: "json",
      	page:pageNro}).trigger("reloadGrid");
	
	saveState();
 }
 
 function getFiltros(){
	 var filtros = "?";
	 filtros += "cliente=" + $("#cliente").val() + "&";
	 filtros += "periodo=" + $("#periodo").val();
	 return filtros;
 }
 
 var infId = null;
 function enviar_dialog(informeId){
	 infId = informeId;
 	$("#enviarDialog").dialog("open");
 }
 
 function enviar(){
	 var currentPage = $('.ui-pg-input').val();
	
	 $.ajax({
		url : "enviarinformemensual",
		type : "POST",
		data : {
			informeMensualId : infId
		},
		success : function(data) {

			if (data.success) {
				gridReload(currentPage);
			}

		},
		error : function(data) {
			alert("error: (" + data.status + ") " + data.statusText);
		}
	 });
}
 
function deleteInforme_dialog(informeId){
	infId = informeId;
	$("#eliminarDialog").dialog("open");
}
function eliminar(){
	 var currentPage = calculatePageToLoadAfterDelete();
	
	 $.ajax({
		url : "eliminarinformemensual",
		type : "POST",
		data : {
			informeMensualId : infId
		},
		success : function(data) {

			if (data.success) {
				gridReload(currentPage);
			}

		},
		error : function(data) {
			alert("error: (" + data.status + ") " + data.statusText);
		}
	 });
}

function calculatePageToLoadAfterDelete(){
	var nroRegistrosInPage = $('.jqgrow ').size();
	var currentPage = $('.ui-pg-input').val();
	
	if(currentPage > 1 && nroRegistrosInPage == 1){
		return currentPage-1;
	}
	return currentPage;
}