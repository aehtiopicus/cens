 jQuery(document).ready(function () {

        jQuery("#projectTable").jqGrid({
            url: "beneficiosgrilla",
            datatype: "json",
            contentType :'application/json',
            jsonReader: {repeatitems: false, id:"beneficioId"},
            colNames:['T&iacute;tulo','Descripci&oacute;n','Remunerativo?','<span class="ui-icon ui-icon-pencil"/>', '<span class="ui-icon ui-icon-trash"/>'],
            colModel:[
                {name:'titulo', index:'titulo', sortable: false},
                {name:'descripcion', index:'descripcion', sortable: false},
                {
                	name:'remunerativo', 
                	width: 100, 
                	fixed:true, 
                	align: 'center', 
                	sortable: false,
                	editable: false,
                	formatter: tipoCurrencyFmatter
                },
                { 	
        			name: 'beneficioId',  
        			width: 16,
        			editable: false, 
        			sortable: false,
        			fixed: true,
        			formatter: editCurrencyFmatter
        		},
                { 	
        			name: 'beneficioId',  
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
            caption: "Beneficios"
        });
        
        $(window).bind('resize', function() {
            $("#projectTable").setGridWidth($(window).width()-marginWidthGrid);
            $("#projectTable").setGridHeight($(window).height()-marginHeightGridFull);
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
 
 function tipoCurrencyFmatter (cellvalue, options, rowObject){
	 if(cellvalue == true){
		 return "Si";
	 }

	 return "No";
 }
 
 function deleteCurrencyFmatter (cellvalue, options, rowObject)
 {
	var template = "<a href='javascript:deleteBeneficio_dialog({ENTITY_ID})'><span class='ui-icon ui-icon-trash'/></a>";
	 
    return template.replace("{ENTITY_ID}",cellvalue);
 }
 function editCurrencyFmatter (cellvalue, options, rowObject)
 {
	var template = "<a href='beneficio/{ENTITY_ID}'><span class='ui-icon ui-icon-pencil'/></a>";
	var link = template.replace("{ENTITY_ID}",cellvalue);
	
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
 
 
 function gridReload(currentPage){
	//var estado = jQuery("#estado").val();
	
	var pageNro = 1; 
	if(currentPage != null && currentPage > 1){
		pageNro = currentPage;
	}
	
	jQuery("#projectTable").jqGrid('setGridParam',{
           	url:"beneficiosgrilla",
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

	
var beneficioIdToRemove = null;
function deleteBeneficio_dialog(beneficioId){
	beneficioIdToRemove = beneficioId;
	$("#remBeneficio").dialog("open");
}

function deleteBeneficio(){
	
	var beneficioId =beneficioIdToRemove;
	var pageToLoad = calculatePageToLoadAfterDelete();
	
	beneficioIdToRemove = null;
	$('#message').removeClass('msgSuccess');
	$('#message').removeClass('msgError');
	
	$.ajax({
		type:"POST",
		url:"removebeneficio",
		data:{
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

	