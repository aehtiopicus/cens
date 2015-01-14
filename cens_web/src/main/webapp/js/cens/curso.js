var cookiePage = "";
var cookieRegsXPage = "";

jQuery(document).ready(function () {

	restoreState();
	var year = jQuery("#year").val();

	
    jQuery("#projectTable").jqGrid({    	
    		url:"curso",    		
            datatype: "json",
            contentType :'application/json',
            jsonReader: {
                repeatitems: false,
                id: "id",
                cell: "",
                root: function (obj) { 
                	return obj.rows; 
                	},
              
            },
            colNames:['Nombre', 'A&ntilde;o','<span class="ui-icon ui-icon-pencil"/>','<span class="ui-icon ui-icon-trash"/>'],
            colModel:[                
                {name:'nombre',index:'Nombre',sortable: false},
                {name:'yearCurso',index:'A&ntilde;o',sortable: false},
                { 	
        			name: 'id',   
        			width: 16,
        			editable: false, 
        			sortable: false,
        			fixed: true,
        			formatter: editCurrencyFmatter
        		},
                { 	
        			name: 'id',  
        			width: 16,
        			editable: false, 
        			sortable: false,
        			fixed: true,
        			formatter: deleteCurrencyFmatter
        		}
            ],
            rowList:[5,10,50],
            rowNum:cookieRegsXPage,
            pager: "#pagingDiv",
            page:cookiePage,
            postData:{requestData:function(postData) {            	
                return requestData=JSON.stringify({"page": $("#projectTable").getGridParam("page"),"row": $("#projectTable").getGridParam("rowNum"),"sord": $("#projectTable").getGridParam("sortorder"),"filters":{"year":year}});
            }},
            viewrecords: true,
            caption: "Curso",            
            loadComplete: function (data) {
            	$(".ui-pg-selbox").val(cookieRegsXPage);
            	$(".ui-pg-input").val(data.page);
            	setCookie('cursoPage', $('.ui-pg-input').val());
            	cookiePage = $('.ui-pg-input').val();
            },
            onPaging: function(page){
            	setCookie('cursoRegXPage', $(".ui-pg-selbox").val());
            	cookieRegsXPage = $(".ui-pg-selbox").val();
            }
        });
        
        $(window).bind('resize', function() {
            $("#projectTable").setGridWidth($(window).width()-marginWidthGrid);
            $("#projectTable").setGridHeight($(window).height()-marginHeightGridFull);
        }).trigger('resize');
        
        
        $( "#remCurso" ).dialog({
			autoOpen: false,
			width: 400,
			buttons: [
				{
					text: "Ok",
					click: function() {
						$( this ).dialog( "close" );
						deleteUsuario();
					}
				},
				{
					text: "Cancelar",
					click: function() {
						$( this ).dialog( "close" );
						cursoIdToRemove = null;
					}
				}
			]
		});
        fixTable();
    });
 
 function editCurrencyFmatter (cellvalue, options, rowObject)
 {
	var template = "<a href='cursoABM/{ENTITY_ID}' title='Editar'><span class='ui-icon ui-icon-pencil'/></a>";
	 
    return template.replace("{ENTITY_ID}",cellvalue);
 }

 
 function deleteCurrencyFmatter (cellvalue, options, rowObject)
 {
	var template = "<a href='javascript:deleteCurso_dialog({ENTITY_ID})' title='Eliminar'><span class='ui-icon ui-icon-trash'/></a>";
	 
    return template.replace("{ENTITY_ID}",cellvalue);
 }
 
 function saveState(){
	 setCookie('cursoF1', jQuery("#year").val());
 }
 
function restoreState(){
	if(isAValidCookie('cursoF1')){ 
		jQuery("#year").val(getCookie('usuarioF1'));
	}	
	
	if(getCookie('cursoRegXPage') != ""){
		$(".ui-pg-selbox").val(getCookie('cursoRegXPage'));
		cookieRegsXPage = getCookie('cursoRegXPage');
	}else{
		cookieRegsXPage = 5;
	}
	if(getCookie('cursoPage') != ""){
		$('.ui-pg-input').val(getCookie('cursoPage'));
		cookiePage = getCookie('cursoPage');
	}else{
		cookiePage = 1;
	}
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
	var year = jQuery("#year").val();	
	var pageNro = 1; 
	if(currentPage != null && currentPage > 1){
		pageNro = currentPage;
	}
	
	jQuery("#projectTable").jqGrid(
           'setGridParam',
           {
       		url:"curso",
            gridview:true,
            contentType :'application/json',
      		dataType: "json",
      		 postData:{requestData:function(postData) {            	
                 return requestData=JSON.stringify({"page": $("#projectTable").getGridParam("page"),"row": $("#projectTable").getGridParam("rowNum"),"sord": $("#projectTable").getGridParam("sortorder"),"filters":{"year":year}});
             }},
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
 
var cursoIdToRemove = null;
function deleteCurso_dialog(cursoId){
	cursoIdToRemove = cursoId;
	$("#remCurso").dialog("open");
}

function deleteUsuario(){
	var cursoId = cursoIdToRemove;
	
	var pageToLoad = calculatePageToLoadAfterDelete();
	
	cursoIdToRemove = null;
	$('#message').removeClass('msgSuccess');
	$('#message').removeClass('msgError');
	
	$.ajax({
		type:"DELETE",
		url:"curso/"+cursoId,
		contentType :'application/json',
		dataType:"json",
		success: function(data){
			gridReload(pageToLoad);
			$('#message').addClass('msgSuccess');
			cargarMensaje(data,true);
		},
		error: function(data){
			$('#message').addClass('msgError');	
			cargarMensaje(errorConverter(data));
		}								

		}
		
	);
	
} 

