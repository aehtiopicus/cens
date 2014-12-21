 jQuery(document).ready(function () {
  var empleadoId = $('#empleadoId').val();
        jQuery("#projectTable").jqGrid({
            url: "../historialgrilla?empleadoId="+empleadoId,
            datatype: "json",
            contentType :'application/json',
            jsonReader: {repeatitems: false, id:"empleadoId"},
            colNames:['Fecha','Concepto', 'Cliente', 'Puesto','Sueldo Basico', 'Sueldo Presentismo'],
            colModel:[
                {name:'fecha',index:'fecha', sortable: false, width: 35},
                {name:'concepto',index:'concepto',sortable: false},
                {name:'cliente',index:'cliente',sortable: false, width: 100},
                {name:'nombrePuesto',index:'nombrePuesto',sortable: false, width: 100},
                {name:'sueldoBasico',index:'sueldoBasico',sortable: false, width: 50},
                {name:'sueldoPresentismo',index:'sueldoPresentismo',sortable: false, width: 50}, 
               
            ],
            viewrecords: true,
            caption: "Historial",
            rowNum: 0,
        });
        
        $(window).bind('resize', function() {
            $("#projectTable").setGridWidth($(window).width()-marginWidthGrid);
            $("#projectTable").setGridHeight($(window).height()-marginHeightGrid+60);
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
 

