var cookiePage = "";
var cookieRegsXPage = "";

jQuery(document).ready(function () {

	restoreState();
	var apellido = jQuery("#apellido").val();
	
    jQuery("#projectTable").jqGrid({
    		url:pagePath+"/api/asignatura/"+asignaturaId+"/alumno",    		
            datatype: "json",
            contentType :'application/json',
            jsonReader: {
                repeatitems: false,
                id: "id",
                cell: "",
                root: function (obj) { 
                	asignaturaData(obj);
                	return obj.alumnos.rows; 
                },
              
            },
            colNames:['Nombre', 'Apellido', 'DNI', '<span class="ui-icon ui-icon-pencil"/>','<span class="ui-icon ui-icon-trash"/>'],
            colModel:[              
                {name:'nombre',index:'Nombre',sortable: false},
                {name:'apellido',index:'Apellido',sortable: false},
                {name:'dni',index:'DNI',sortable: false},                        
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
                return requestData=JSON.stringify({"page": $("#projectTable").getGridParam("page"),"row": $("#projectTable").getGridParam("rowNum"),"sord": $("#projectTable").getGridParam("sortorder"),"filters":{"perfil":"ALUMNO","data":apellido}});
            }},
            viewrecords: true,
            caption: "Alumnos de la Asignatura",            
            loadComplete: function (data) {
            	$(".ui-pg-selbox").val(cookieRegsXPage);
            	$(".ui-pg-input").val(data.page);
            	setCookie('asignaturaUsuarioPage', $('.ui-pg-input').val());
            	cookiePage = $('.ui-pg-input').val();            	
            },
            onPaging: function(page){
            	setCookie('asignaturaUsuarioRegXPage', $(".ui-pg-selbox").val());
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
    });
 
 function editCurrencyFmatter (cellvalue, options, rowObject)
 {
	var template = "<a href='miembroABM/{ENTITY_ID}' title='Editar'><span class='ui-icon ui-icon-pencil'/></a>";
	 
    return template.replace("{ENTITY_ID}",cellvalue);
 }

 
 function deleteCurrencyFmatter (cellvalue, options, rowObject)
 {
	var template = "<a href='javascript:deleteUsuario_dialog({ENTITY_ID})' title='Eliminar'><span class='ui-icon ui-icon-trash'/></a>";
	 
    return template.replace("{ENTITY_ID}",cellvalue);
 }
 
 function saveState(){
	 setCookie('usuarioF1', jQuery("#apellido").val());
 }
 
function restoreState(){
	if(isAValidCookie('usuarioF1')){ 
		jQuery("#apellido").val(getCookie('usuarioF1'));
	}
	
	
	if(getCookie('asignaturaUsuarioRegXPage') != ""){
		$(".ui-pg-selbox").val(getCookie('asignaturaUsuarioRegXPage'));
		cookieRegsXPage = getCookie('asignaturaUsuarioRegXPage');
	}else{
		cookieRegsXPage = 5;
	}
	if(getCookie('asignaturaUsuarioPage') != ""){
		$('.ui-pg-input').val(getCookie('asignaturaUsuarioPage'));
		cookiePage = getCookie('asignaturaUsuarioPage');
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

	var apellido = jQuery("#apellido").val();
	var pageNro = 1; 
	if(currentPage != null && currentPage > 1){
		pageNro = currentPage;
	}
	
	jQuery("#projectTable").jqGrid(
           'setGridParam',
           {
        	url:pagePath+"/api/asignatura/"+asignaturaId+"/alumno",
            gridview:true,
            contentType :'application/json',
      		dataType: "json",
      		 postData:{requestData:function(postData) {            	
                 return requestData=JSON.stringify({"page": $("#projectTable").getGridParam("page"),"row": $("#projectTable").getGridParam("rowNum"),"sord": $("#projectTable").getGridParam("sortorder"),"filters":{"perfil":"ALUMNO","data":apellido}});
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
 
function asignaturaData(asignaturaData){
	var profesor = typeof asignaturaData.profesorSuplente == "undefined" ? profNombre(asignaturaData.profesor) :  profNombre(asignaturaData.profesorSuplente);
	var curso = asignaturaData.curso.nombre.toUpperCase()+" ("+asignaturaData.curso.yearCurso+")";
	$("#projectTable").jqGrid("setCaption","Asignatura "+asignaturaData.nombre+" Curso"+curso+" "+" Profesor: "+profesor);
}

function profNombre(prof){
	return prof.miembroCens.apellido.toUpperCase()+", "+prof.miembroCens.nombre.toUpperCase();
}
