var cookiePage = "";
var cookieRegsXPage = "";

jQuery(document).ready(function () {
     
	 restoreState();
	 
	 var nombre = jQuery("#nombre").val();
	 
     jQuery("#projectTable").jqGrid({
            url: "prepagasgrilla?nombre="+nombre,
            datatype: "json",
            contentType :'application/json',
            jsonReader: {repeatitems: false, id:"entityId"},
            colNames:['Nombre','<span class="ui-icon ui-icon-pencil"/>','<span class="ui-icon ui-icon-trash"/>'],
            colModel:[
                {name:'parametro',index:'parametro', sortable: false},
                { 	
        			name: 'entityId',   
        			width: 16,
        			editable: false, 
        			sortable: false,
        			fixed: true,
        			align: 'center',
        			formatter: editCurrencyFmatter
        		},
                { 	
        			name: 'entityId',  
        			width: 16,
        			editable: false, 
        			sortable: false,
        			fixed: true,
        			align: 'center',
        			formatter: deleteCurrencyFmatter
        		}
                
            ],
            rowList:[5,10,50],
            rowNum:cookieRegsXPage,
            pager: "#pagingDiv",
            page:cookiePage,
            viewrecords: true,
            caption: "Prepagas",
            loadComplete: function (data) {
            	$(".ui-pg-selbox").val(cookieRegsXPage);
            	$(".ui-pg-input").val(data.page);
            	setCookie('prepagaPage', $('.ui-pg-input').val());
            	cookiePage = $('.ui-pg-input').val();
            },
            onPaging: function(page){
            	setCookie('prepagaRegXPage', $(".ui-pg-selbox").val());
            	cookieRegsXPage = $(".ui-pg-selbox").val();
            }
        });
          
        $(window).bind('resize', function() {
            $("#projectTable").setGridWidth($(window).width()-marginWidthGrid);
            $("#projectTable").setGridHeight($(window).height()-marginHeightGridFull);
        }).trigger('resize');
        
        
        $( "#remPrepaga" ).dialog({
			autoOpen: false,
			width: 400,
			buttons: [
				{
					text: "Ok",
					click: function() {
						$( this ).dialog( "close" );
						deletePrepaga();
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
	var template = "<a href='prepaga/{ENTITY_ID}' title='Editar'><span class='ui-icon ui-icon-pencil'/></a>";
    return template.replace("{ENTITY_ID}",cellvalue);
 }

 function deleteCurrencyFmatter (cellvalue, options, rowObject){
	//if(!rowObject.accionBeneficios){return "";}
	var template = "<a href='javascript:deletePrepaga_dialog({ENTITY_ID})' title='Eliminar'><span class='ui-icon ui-icon-trash'/></a>";
	return template.replace("{ENTITY_ID}",cellvalue);	
 }
 
function saveState(){
	 setCookie('prepagaF1', jQuery("#nombre").val());
 }
 
function restoreState(){
	if(isAValidCookie('prepagaF1')){
		jQuery("#nombre").val(getCookie('prepagaF1'));
	}
	
	if(getCookie('prepagaRegXPage') != ""){
		$(".ui-pg-selbox").val(getCookie('prepagaRegXPage'));
		cookieRegsXPage = getCookie('prepagaRegXPage');
	}else{
		cookieRegsXPage = 5;
	}
	if(getCookie('prepagaPage') != ""){
		$('.ui-pg-input').val(getCookie('prepagaPage'));
		cookiePage = getCookie('prepagaPage');
	}else{
		cookiePage = 1;
	}
}
 
 function gridReload(currentPage){
	var nombre = jQuery("#nombre").val();
	var pageNro = 1; 
	if(currentPage != null && currentPage > 1){
		pageNro = currentPage;
	}
	
	jQuery("#projectTable").jqGrid(
           'setGridParam',
           {
           	url:"prepagasgrilla?nombre="+nombre,
            gridview:true,
            contentType :'application/json',
      		dataType: "json",
      		page:pageNro})
      		.trigger("reloadGrid");
	
	saveState();
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
function deletePrepaga_dialog(entityId){
	eId = entityId;
	$("#remPrepaga").dialog("open");
}
function deletePrepaga(){
	 var currentPage = calculatePageToLoadAfterDelete();
	 var entityId = eId;
	 
	 $('#message').removeClass('msgSuccess');
	 $('#message').removeClass('msgError');
     eId = null;

	 $.ajax({
		url : "removeprepaga",
		type : "POST",
		data : {
			prepagaId : entityId
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

