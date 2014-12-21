 jQuery(document).ready(function () {
	 var periodo1 = $('#periodo1').val();
	 var periodo2 = $('#periodo2').val();
	 var clienteId = $('#clienteId').val();
	 
        jQuery("#projectTable").jqGrid({
            url: "indicadoresdetallegrilla?periodo1="+periodo1+"&periodo2="+periodo2+"&clienteId="+clienteId,
            datatype: "json",
            contentType :'application/json',
            jsonReader: {repeatitems: false, id:"empleado"},
            colNames:['Empleado','Sueldo Periodo 1','Sueldo Periodo 2','Diferencia'],
            colModel:[
                {name:'empleado',index:'cliente',sortable: false},
                {name:'sueldoP1',index:'cantidadEmpleadosP1', sortable: false},
                {name:'sueldoP2',index:'cantidadEmpleadosP2', sortable: false},
                {name:'diferencia',index:'cantidadSueldoP1', sortable: false}
               
            ],
            pager: "#pagingDiv",
            viewrecords: true,
            caption: "Indicadores",
            rowNum: 0
        });
        
        $(window).bind('resize', function() {
            $("#projectTable").setGridWidth($(window).width()-marginWidthGrid);
            $("#projectTable").setGridHeight($(window).height()-marginHeightGridFull+55);
        }).trigger('resize');
        
    
        
    });
 
 

 
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



	