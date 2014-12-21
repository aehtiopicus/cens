var cookiePage = "";
var cookieRegsXPage = "";

jQuery(document).ready(function () {
     
	 restoreState();
	 
	 var estado = jQuery("#estado").val();
	 var nombre = jQuery("#nombre").val();
	 
     jQuery("#projectTable").jqGrid({
            url: "clientesgrilla?estado="+estado+"&nombre="+nombre,
            datatype: "json",
            contentType :'application/json',
            jsonReader: {repeatitems: false, id:"clienteId"},
            colNames:['Nombre','Raz&oacute;n Social', 'Direcci&oacute;n', 'Tel&eacute;fono', 'Fecha Alta', 'Contacto', 'Gerente','Jefe','Estado', 'Beneficios', '<span class="ui-icon ui-icon-pencil"/>'],
            colModel:[
                {name:'nombre',index:'nombre', sortable: false},
                {name:'razonSocial',index:'razonSocial',sortable: false},                
                {name:'direccion',index:'direccion',sortable: false},
                {name:'telefono',index:'telefono',sortable: false},
                {name:'fecha_alta',index:'fecha_alta',sortable: false},
                {name:'nombre_contacto',index:'nombre_contacto',sortable: false}, 
                {name:'gerenteName',index:'gerenteName',sortable: false},
                {name:'jefeName',index:'jefeName',sortable: false},
                {name:'estadoCliente',index:'estadoCliente', align:'center', sortable: false},
        		{ 	
        			name: 'clienteId',   
        			width: 60,
        			align: 'center',
        			editable: false, 
        			sortable: false,
        			fixed: true,
        			formatter: listarBeneficios
        		},
        		{ 	
        			name: 'clienteId',   
        			width: 16,
        			editable: false, 
        			sortable: false,
        			fixed: true,
        			formatter: editCurrencyFmatter
        		}
                
            ],
            rowList:[5,10,50],
            rowNum:cookieRegsXPage,
            pager: "#pagingDiv",
            page:cookiePage,
            viewrecords: true,
            caption: "Clientes",
            loadComplete: function (data) {
            	$(".ui-pg-selbox").val(cookieRegsXPage);
            	$(".ui-pg-input").val(data.page);
            	setCookie('clientePage', $('.ui-pg-input').val());
            	cookiePage = $('.ui-pg-input').val();
            },
            onPaging: function(page){
            	setCookie('clienteRegXPage', $(".ui-pg-selbox").val());
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
        
        
    });
 
 function editCurrencyFmatter (cellvalue, options, rowObject){
	if(!rowObject.accionEditar){return "";}
	 
	var template = "<a href='cliente/{ENTITY_ID}' title='Editar'><span class='ui-icon ui-icon-pencil'/></a>";
	 
    return template.replace("{ENTITY_ID}",cellvalue);
 }

 function listarBeneficios (cellvalue, options, rowObject){
	if(!rowObject.accionBeneficios){return "";}
	 
	var template = "<a href='clientes/beneficios/{ENTITY_ID}' title='Beneficios'><span class='ui-icon ui-icon-document' style='margin-left: 38%;'/></a>";
	 
    return template.replace("{ENTITY_ID}",cellvalue);
 }
 
function saveState(){
	 setCookie('clienteF1', jQuery("#estado").val());
	 setCookie('clienteF2', jQuery("#nombre").val());
 }
 
function restoreState(){
	if(isAValidCookie('clienteF1')){ 
		jQuery("#estado").val(getCookie('clienteF1'));
	}
	if(isAValidCookie('clienteF2')){
		jQuery("#nombre").val(getCookie('clienteF2'));
	}
	
	if(getCookie('clienteRegXPage') != ""){
		$(".ui-pg-selbox").val(getCookie('clienteRegXPage'));
		cookieRegsXPage = getCookie('clienteRegXPage');
	}else{
		cookieRegsXPage = 5;
	}
	if(getCookie('clientePage') != ""){
		$('.ui-pg-input').val(getCookie('clientePage'));
		cookiePage = getCookie('clientePage');
	}else{
		cookiePage = 1;
	}
}
 
 function gridReload(currentPage){
	var estado = jQuery("#estado").val();
	var nombre = jQuery("#nombre").val();
	var pageNro = 1; 
	if(currentPage != null && currentPage > 1){
		pageNro = currentPage;
	}
	
	jQuery("#projectTable").jqGrid(
           'setGridParam',
           {
           	url:"clientesgrilla?estado="+estado+"&nombre="+nombre,
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
