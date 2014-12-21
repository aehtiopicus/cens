
jQuery(document).ready(function () {
	 var empleadoId = jQuery("#empleadoId").val();
        jQuery("#projectTable").jqGrid({
            url: "vacacionesgrilla/"+empleadoId,
            datatype: "json",
            contentType :'application/json',
            jsonReader: {repeatitems: false, id:"vacacionesId"},
            colNames:['Fecha de Inicio','Fecha de Fin', 'Observaciones','<span class="ui-icon ui-icon-pencil"/>','<span class="ui-icon ui-icon-trash"/>'],
            colModel:[
                {name:'fechaInicio',align:'center',width:100,fixed:true, index:'fechaInicio', sortable: false},
                {name:'fechaFin',align:'center',width:100,fixed:true, index:'fechaFin',sortable: false},
                {name:'observaciones',index:'observaciones',sortable: false}, 
                { 	
        			name: 'vacacionesId',   
        			width: 16,
        			editable: false, 
        			sortable: false,
        			fixed: true,
        			formatter: editCurrencyFmatter
        		},
                { 	
        			name: 'vacacionesId',  
        			width: 16,
        			editable: false, 
        			sortable: false,
        			fixed: true,
        			formatter: deleteCurrencyFmatter
        		}
            ],
            rowNum:5,
            rowList:[5,10,50],
            pager: "#pagingDiv",
            viewrecords: true,
            caption: "Vacaciones"
        });
        
        $(window).bind('resize', function() {
            $("#projectTable").setGridWidth($(window).width()-marginWidthGrid);
            $("#projectTable").setGridHeight($(window).height()-marginHeightGrid);
        }).trigger('resize');
        
        
        $( "#remVacaciones" ).dialog({
			autoOpen: false,
			width: 300,
			buttons: [
				{
					text: "Ok",
					click: function() {
						$( this ).dialog( "close" );
						deleteVacaciones();
					}
				},
				{
					text: "Cancelar",
					click: function() {
						$( this ).dialog( "close" );
						vacacionesIdToRemove = null;
					}
				}
			]
		});
        
    });
 
 function editCurrencyFmatter (cellvalue, options, rowObject)
 {
	var template = "<a href='../vacacion/{ENTITY_ID}'><span class='ui-icon ui-icon-pencil'/></a>";
	 
    return template.replace("{ENTITY_ID}",cellvalue);
 }

 function deleteCurrencyFmatter (cellvalue, options, rowObject)
 {
	var template = "<a href='javascript:deleteVacaciones_dialog({ENTITY_ID})'><span class='ui-icon ui-icon-trash'/></a>";
	 
    return template.replace("{ENTITY_ID}",cellvalue);
    
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

	var pageNro = 1; 
	if(currentPage != null && currentPage > 1){
		pageNro = currentPage;
	}
	 var empleadoId = jQuery("#empleadoId").val();
	jQuery("#projectTable").jqGrid(
           'setGridParam',
           {
        	url: "vacacionesgrilla/"+empleadoId,
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
 
var vacacionesIdToRemove = null;
function deleteVacaciones_dialog(vacacionesId){
	vacacionesIdToRemove = vacacionesId;
	$("#remVacaciones").dialog("open");
}

function deleteVacaciones(){
	var vacacionesId = vacacionesIdToRemove;
	
	var pageToLoad = calculatePageToLoadAfterDelete();
	
	vacacionesIdToRemove = null;
	$('#message').removeClass('msgSuccess');
	$('#message').removeClass('msgError');
	
	$.ajax({
		type:"POST",
		url:"removevacaciones",
		data:{
			vacacionesId:vacacionesId
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
		
	})
} 