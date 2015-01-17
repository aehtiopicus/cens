var cookiePage = "";
var cookieRegsXPage = "";

jQuery(document).ready(function () {

	restoreState();
	var nombre = jQuery("#nombre").val();
	var modalidad = jQuery("#modalidad").val();
	var year = jQuery("#year").val();
	
    jQuery("#projectTable").jqGrid({
    		url:pagePath+"/asignatura",    		
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
            colNames:['Nombre','Modalidad', 'Horarios', 'Profesor', 'Profesor Suplente','Curso','<span class="ui-icon ui-icon-pencil"/>','<span class="ui-icon ui-icon-trash"/>'],
            colModel:[
                {name:'nombre',index:'Nombre', sortable: false},
                {name:'modalidad',index:'Modalidad',sortable: false},
                {name:'horarios',index:'Horarios',sortable: false},                
                {name:function(val){
                	if(val.profesor !=null){
                		return val.profesor.miembroCens.apellido+", ("+val.profesor.miembroCens.dni+")";
                	}else{
                		return "";
                	}
                },index:'Profesor',sortable: false}, 
                {name:function(val){
                	if(val.profesorSuplente !=null){
                		return val.profesorSuplente.miembroCens.apellido+", ("+val.profesorSuplente.miembroCens.dni+")";
                	}else{
                		return "";
                	}
                },index:'Profesor Suplente',sortable: false},
                {name:function(val){
                	return val.curso.nombre+" ("+val.curso.yearCurso+")";
                },index:'Profesor',sortable: false},                 
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
                return requestData=JSON.stringify({"page": $("#projectTable").getGridParam("page"),"row": $("#projectTable").getGridParam("rowNum"),"sord": $("#projectTable").getGridParam("sortorder"),"filters":{"year":year,"nombre":nombre,"modalidad":modalidad}});
            }},
            viewrecords: true,
            caption: "Asignaturas",            
            loadComplete: function (data) {
            	$(".ui-pg-selbox").val(cookieRegsXPage);
            	$(".ui-pg-input").val(data.page);
            	setCookie('asignaturaPage', $('.ui-pg-input').val());
            	cookiePage = $('.ui-pg-input').val();            	
            },
            onPaging: function(page){
            	setCookie('asignaturaRegXPage', $(".ui-pg-selbox").val());
            	cookieRegsXPage = $(".ui-pg-selbox").val();
            }
        });
        
        $(window).bind('resize', function() {
            $("#projectTable").setGridWidth($(window).width()-marginWidthGrid);
            $("#projectTable").setGridHeight($(window).height()-marginHeightGridFull);
        }).trigger('resize');
        
        
        $( "#remAsignatura" ).dialog({
			autoOpen: false,
			width: 400,
			buttons: [
				{
					text: "Ok",
					click: function() {
						$( this ).dialog( "close" );
						deleteAsignatura();
					}
				},
				{
					text: "Cancelar",
					click: function() {
						$( this ).dialog( "close" );
						asignaturaIdToRemove = null;
					}
				}
			]
		});         
        fixTable();
    });
 
 function editCurrencyFmatter (cellvalue, options, rowObject)
 {
	var template = "<a href='asignaturaABM/{ENTITY_ID}' title='Editar'><span class='ui-icon ui-icon-pencil'/></a>";
	 
    return template.replace("{ENTITY_ID}",cellvalue);
 }

 
 function deleteCurrencyFmatter (cellvalue, options, rowObject)
 {
	var template = "<a href='javascript:deleteAsignatura_dialog({ENTITY_ID})' title='Eliminar'><span class='ui-icon ui-icon-trash'/></a>";
	 
    return template.replace("{ENTITY_ID}",cellvalue);
 }
 
 function saveState(){
	 setCookie('asignaturaF1', jQuery("#nombre").val());
	 setCookie('asignaturaF2', jQuery("#modalidad").val());
	 setCookie('asignaturaF3', jQuery("#year").val());
 }
 
function restoreState(){
	if(isAValidCookie('asignaturaF1')){ 
		jQuery("#nombre").val(getCookie('asignaturaF1'));
	}
	if(isAValidCookie('asignaturaF2')){
		jQuery("#modalidad").val(getCookie('asignaturaF2'));
	}
	
	if(isAValidCookie('asignaturaF3')){
		jQuery("#year").val(getCookie('asignaturaF3'));
	}
	
	if(getCookie('asignaturaRegXPage') != ""){
		$(".ui-pg-selbox").val(getCookie('asignaturaRegXPage'));
		cookieRegsXPage = getCookie('asignaturaRegXPage');
	}else{
		cookieRegsXPage = 5;
	}
	if(getCookie('asignaturaPage') != ""){
		$('.ui-pg-input').val(getCookie('asignaturaPage'));
		cookiePage = getCookie('asignaturaPage');
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
	 var nombre = jQuery("#nombre").val();
		var modalidad = jQuery("#modalidad").val();
		var year = jQuery("#year").val();
	var pageNro = 1; 
	if(currentPage != null && currentPage > 1){
		pageNro = currentPage;
	}
	
	jQuery("#projectTable").jqGrid(
           'setGridParam',
           {
       		url:pagePath+"/asignatura",
            gridview:true,
            contentType :'application/json',
      		dataType: "json",
      		 postData:{requestData:function(postData) {            	
      			return requestData=JSON.stringify({"page": $("#projectTable").getGridParam("page"),"row": $("#projectTable").getGridParam("rowNum"),"sord": $("#projectTable").getGridParam("sortorder"),"filters":{"year":year,"nombre":nombre,"modalidad":modalidad}});
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
 
var asignaturaIdToRemove = null;
function deleteAsignatura_dialog(asignaturaId){
	asignaturaIdToRemove = asignaturaId;
	$("#remAsignatura").dialog("open");
}

function deleteAsignatura(){
	var asignaturaId = asignaturaIdToRemove;
	
	var pageToLoad = calculatePageToLoadAfterDelete();
	
	asignaturaIdToRemove = null;
	$('#message').removeClass('msgSuccess');
	$('#message').removeClass('msgError');
	
	$.ajax({
		type:"DELETE",
		url:pagePath+"/asignatura/"+asignaturaId,
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

