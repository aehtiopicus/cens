
jQuery(document).ready(function () {
     
	 var nombre = jQuery("#nombre").val();
	 
     jQuery("#projectTable").jqGrid({
            url: "motivosbajagrilla",
            datatype: "json",
            contentType :'application/json',
            jsonReader: {repeatitems: false, id:"motivoBajaId"},
            colNames:['Motivo','Artículo LCT','<span class="ui-icon ui-icon-pencil"/>','<span class="ui-icon ui-icon-trash"/>'],
            colModel:[
                {name:'motivo',index:'motivo', sortable: false},
                {name:'articuloLct',index:'articuloLct', sortable: false, width: 100, fixed:true, align: 'center'},
                { 	
        			name: 'motivoBajaId',   
        			width: 16,
        			editable: false, 
        			sortable: false,
        			fixed: true,
        			align: 'center',
        			formatter: editCurrencyFmatter
        		},
                { 	
        			name: 'motivoBajaId',  
        			width: 16,
        			editable: false, 
        			sortable: false,
        			fixed: true,
        			align: 'center',
        			formatter: deleteCurrencyFmatter
        		}
                
            ],

            rowNum:0,
            viewrecords: false,
            caption: "Motivos de Baja",
        });
          
        $(window).bind('resize', function() {
            $("#projectTable").setGridWidth($(window).width()-marginWidthGrid);
            $("#projectTable").setGridHeight($(window).height()-marginHeightGridFull);
        }).trigger('resize');
        
        
        $( "#remMotivoBaja" ).dialog({
			autoOpen: false,
			width: 400,
			buttons: [
				{
					text: "Ok",
					click: function() {
						$( this ).dialog( "close" );
						deleteMotivoBaja();
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
 
 function editCurrencyFmatter (cellvalue, options, rowObject){
	//if(!rowObject.accionEditar){return "";}
	var template = "<a href='motivobaja/{ENTITY_ID}' title='Editar'><span class='ui-icon ui-icon-pencil'/></a>";
    return template.replace("{ENTITY_ID}",cellvalue);
 }

 function deleteCurrencyFmatter (cellvalue, options, rowObject){
	//if(!rowObject.accionBeneficios){return "";}
	var template = "<a href='javascript:deleteMotivoBaja_dialog({ENTITY_ID})' title='Eliminar'><span class='ui-icon ui-icon-trash'/></a>";
	return template.replace("{ENTITY_ID}",cellvalue);	
 }
 

 function gridReload(currentPage){
	var pageNro = 1; 
	if(currentPage != null && currentPage > 1){
		pageNro = currentPage;
	}
	
	jQuery("#projectTable").jqGrid(
           'setGridParam',
           {
           	url:"motivosbajagrilla",
            gridview:true,
            contentType :'application/json',
      		dataType: "json",
      		pageNro:1})
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

var eId = null;
function deleteMotivoBaja_dialog(entityId){
	eId = entityId;
	$("#remMotivoBaja").dialog("open");
}

function deleteMotivoBaja(){
	 var currentPage = calculatePageToLoadAfterDelete();
	 var entityId = eId;
	 
	 $('#message').removeClass('msgSuccess');
	 $('#message').removeClass('msgError');
     eId = null;

	 $.ajax({
		url : "removemotivobaja",
		type : "POST",
		data : {
			motivoBajaId : entityId
		},
		success : function(data) {

			if (data.success) {
				gridReload(currentPage);
				$('#message').addClass('msgSuccess');
			}else{
				$('#message').addClass('msgError');
			}
			//cargo mensaje en pantalla
			$('#message').text(data.message)
			setTimeout("$('#message').text('')", 5000);
		},
		error : function(data) {
			alert("error: (" + data.status + ") " + data.statusText);
		}
	 });
}

