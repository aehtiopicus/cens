 jQuery(document).ready(function () {

        jQuery("#projectTable").jqGrid({
            url: "indicadoresgrilla",
            datatype: "json",
            contentType :'application/json',
            jsonReader: {repeatitems: false, id:"clienteId"},
            colNames:['Cliente','Empleados Periodo 1','Empleados Periodo 2','Sueldos Periodo 1','Sueldos Periodo 2','Diferencia Empleados','Diferencia Sueldos' ,'Ver Detalle'],
            colModel:[
                {name:'cliente',index:'cliente',sortable: false},
                {name:'cantidadEmpleadosP1',index:'cantidadEmpleadosP1', sortable: false},
                {name:'cantidadEmpleadosP2',index:'cantidadEmpleadosP2', sortable: false},
                {name:'cantidadSueldoP1',index:'cantidadSueldoP1', sortable: false},
                {name:'cantidadSueldoP2',index:'cantidadSueldoP2', sortable: false},
                {name:'diferenciaEmpleados',index:'diferenciaEmpleados', sortable: false},
                {name:'diferenciaSueldos',index:'diferenciaSueldos', sortable: false},
                { 	
        			name: 'clienteId',  
        			width: 90,
        			editable: false, 
        			sortable: false,
        			fixed: true,
        			formatter: detalleCurrencyFmatter
        		}
            ],
            viewrecords: true,
            caption: "Indicadores",
            rowNum: 0
        });
        
        $(window).bind('resize', function() {
            $("#projectTable").setGridWidth($(window).width()-marginWidthGrid);
            $("#projectTable").setGridHeight($(window).height()-marginHeightGridFull+55);
        }).trigger('resize');
        
    
        
    });
 
 
 function detalleCurrencyFmatter (cellvalue, options, rowObject)
 {
	var periodo1 = $("#periodo1").val();
	var periodo2 = $("#periodo2").val();
	var template = "<a href='indicadoresdetalle?clienteId={ENTITY_ID}&periodo1={PERIODO1}&periodo2={PERIODO2}' title='Detalle'><span class='ui-icon ui-icon-zoomin' style='margin-left: 38%;'/></a>"; 
	var link = template.replace("{ENTITY_ID}",rowObject.clienteId).replace("{PERIODO1}",periodo1).replace("{PERIODO2}",periodo2);
	
    return link;
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
 
 

function calculatePageToLoadAfterDelete(){
	var nroRegistrosInPage = $('.jqgrow ').size();
	var currentPage = $('.ui-pg-input').val();
	

	if(currentPage > 1 && nroRegistrosInPage == 1){
		return currentPage-1;
	}
	
	return currentPage;
}

function gridReload(currentPage){

	var filtros = getFiltros();
	
	var pageNro = 1; 
	if(currentPage != null && currentPage > 1){
		pageNro = currentPage;
	}
	
	jQuery("#projectTable").jqGrid('setGridParam',{
		url: "indicadoresgrilla" + filtros,
        gridview:true,
        contentType :'application/json',
      	dataType: "json",
      	page:pageNro}).trigger("reloadGrid");
	
 }

function getFiltros(){
	 var filtros = "?";
	 filtros += "periodo1=" + $("#periodo1").val();
	 filtros += "&periodo2=" + $("#periodo2").val();
	 return filtros;
}
	



	