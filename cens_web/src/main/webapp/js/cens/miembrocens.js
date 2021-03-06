var cookiePage = "";
var cookieRegsXPage = "";
var alumnoResult;

jQuery(document).ready(function () {

	restoreState();
	var perfil = jQuery("#perfil").val();
	var apellido = jQuery("#apellido").val();
	
    jQuery("#projectTable").jqGrid({
    		url:pagePath+"/api/miembro",    		
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
            colNames:['Usuario','Nombre', 'Apellido', 'DNI', 'Perfil','<span class="ui-icon ui-icon-locked"/>','<span class="ui-icon ui-icon-pencil"/>','<span class="ui-icon ui-icon-trash"/>'],
            colModel:[
                {name:'usuario.username',index:'Usuario', sortable: false},
                {name:'nombre',index:'Nombre',sortable: false},
                {name:'apellido',index:'Apellido',sortable: false},
                {name:'dni',index:'DNI',sortable: false},
                {name:function(val){
                	var tipo="";
                	$.each(val.usuario.perfil ,function(index,val) {
                		  tipo=tipo+val.perfilType+",";
                		}); 
                	return tipo.substring(0,tipo.length-1);
                },index:'Perfil',sortable: false}, 
                { 	
        			name: 'usuario.id',   
        			width: 16,
        			editable: false, 
        			sortable: false,
        			fixed: true,
        			formatter: resetPasswordCurrencyFmatter
        		},
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
                return requestData=JSON.stringify({"page": $("#projectTable").getGridParam("page"),"row": $("#projectTable").getGridParam("rowNum"),"sord": $("#projectTable").getGridParam("sortorder"),"filters":{"perfil":perfil,"apellido":apellido}});
            }},
            viewrecords: true,
            caption: "Miembros Cens",            
            loadComplete: function (data) {
            	$(".ui-pg-selbox").val(cookieRegsXPage);
            	$(".ui-pg-input").val(data.page);
            	setCookie('usuarioPage', $('.ui-pg-input').val());
            	cookiePage = $('.ui-pg-input').val();            	
            },
            onPaging: function(page){
            	setCookie('usuarioRegXPage', $(".ui-pg-selbox").val());
            	cookieRegsXPage = $(".ui-pg-selbox").val();
            }
        });
        
        $(window).bind('resize', function() {
            $("#projectTable").setGridWidth($(window).width()-marginWidthGrid);
            $("#projectTable").setGridHeight($(window).height()-marginHeightGridFull);
        }).trigger('resize');
        
        
        $( "#remUser" ).dialog({
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
						usuarioIdToRemove = null;
					}
				}
			]
		});          
        fixTable();
        cargaMasivaAlumnos = new alumnos.al.cargamasiva({dialogName:'cargaMasivaAlumnos', fileSelection:'fileSelection'});
        $('#cma').on("click",function(){
        	$("#alumnoHeader").addClass("none");
        	$("#alumnoData").addClass("none");
        	$("#cmaNoData").removeClass("none");
        	$("#fileUploadName").val("");
        	cargaMasivaAlumnos.openDialgo();        	
        });
        $(document).bind("cmaComplete",function(){
        	assembleAlumnos();
        	$("#cmaB").button( "option", "disabled", false );
        });
    });
 
 function editCurrencyFmatter (cellvalue, options, rowObject)
 {
	var template = "<a href='miembroABM/{ENTITY_ID}' title='Editar'><span class='ui-icon ui-icon-pencil'/></a>";
	 
    return template.replace("{ENTITY_ID}",cellvalue);
 }

 function resetPasswordCurrencyFmatter (cellvalue, options, rowObject)
 {
	 
	var template = "<a onclick=\"resetPassword('"+pagePath+"/api/usuario/{ENTITY_ID}/reset')\" title='Resetear Password'><span class='ui-icon ui-icon-locked'/></a>";
	 
    return template.replace("{ENTITY_ID}",cellvalue);
 }
 
 function deleteCurrencyFmatter (cellvalue, options, rowObject)
 {
	var template = "<a href='javascript:deleteUsuario_dialog({ENTITY_ID})' title='Eliminar'><span class='ui-icon ui-icon-trash'/></a>";
	 
    return template.replace("{ENTITY_ID}",cellvalue);
 }
 
 function saveState(){
	 setCookie('usuarioF1', jQuery("#perfil").val());
	 setCookie('usuarioF2', jQuery("#apellido").val());
 }
 
function restoreState(){
	if(isAValidCookie('usuarioF1')){ 
		jQuery("#perfil").val(getCookie('usuarioF1'));
	}
	if(isAValidCookie('usuarioF2')){
		jQuery("#apellido").val(getCookie('usuarioF2'));
	}
	
	if(getCookie('usuarioRegXPage') != ""){
		$(".ui-pg-selbox").val(getCookie('usuarioRegXPage'));
		cookieRegsXPage = getCookie('usuarioRegXPage');
	}else{
		cookieRegsXPage = 5;
	}
	if(getCookie('usuarioPage') != ""){
		$('.ui-pg-input').val(getCookie('usuarioPage'));
		cookiePage = getCookie('usuarioPage');
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
	var perfil = jQuery("#perfil").val();
	var apellido = jQuery("#apellido").val();
	var pageNro = 1; 
	if(currentPage != null && currentPage > 1){
		pageNro = currentPage;
	}
	
	jQuery("#projectTable").jqGrid(
           'setGridParam',
           {
       		url:pagePath+"/api/miembro",
            gridview:true,
            contentType :'application/json',
      		dataType: "json",
      		 postData:{requestData:function(postData) {            	
                 return requestData=JSON.stringify({"page": $("#projectTable").getGridParam("page"),"row": $("#projectTable").getGridParam("rowNum"),"sord": $("#projectTable").getGridParam("sortorder"),"filters":{"perfil":perfil,"apellido":apellido}});
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
 
var usuarioIdToRemove = null;
function deleteUsuario_dialog(usuarioId){
	usuarioIdToRemove = usuarioId;
	$("#remUser").dialog("open");
}

function deleteUsuario(){
	var usuarioId = usuarioIdToRemove;
	
	var pageToLoad = calculatePageToLoadAfterDelete();
	
	usuarioIdToRemove = null;
	$('#message').removeClass('msgSuccess');
	$('#message').removeClass('msgError');
	
	$.ajax({
		type:"DELETE",
		url:pagePath+"/api/miembro/"+usuarioId,
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



function resetPassword(url){

	$.ajax({
		type:"POST",
		url:url,
		contentType :'application/json',
		dataType:"json",
		success: function(data){			
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

function assembleAlumnos(){
	alumnoResult.crearAlumnosHtml("alumnoData");
	$(".none").toggleClass("none");
	$("#cmaNoData").addClass("none");
}