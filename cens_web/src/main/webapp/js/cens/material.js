var cookiePage = "";
var cookieRegsXPage = "";

jQuery(document).ready(function () {

	restoreState();
	var year = jQuery("#year").val();

	
    jQuery("#projectTable").jqGrid({    	
    		url:pagePath+"/api/programa/"+programaId+"/material",    		
            datatype: "json",
            hidegrid:false,
            contentType :'application/json',
            jsonReader: {
                repeatitems: false,
                id: "id",
                cell: "",
                root: function (obj) { 
                	return obj.rows; 
                	},
              
            },
            colNames:['Nombre','N&uacute;mero','Semestre', 'Material','Estado','<span class="ui-icon ui-icon-pencil"/>','<span class="ui-icon ui-icon-trash"/>'],
            colModel:[                
                {name:'nombre',index:'Nombre',sortable: false},
                {name:'nro',index:'N&uacute,mero',sortable: false},
                {name:'divisionPeriodoName',index:'Semestre',sortable: false},
                {name:'cartillaAdjunta',index:'Material',sortable: false,formatter:cartillaAdjunta},
                {name:'estadoRevisionType',index:'Estado',sortable: false,formatter:estadoFormatter},
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
                return requestData=JSON.stringify({"page": $("#projectTable").getGridParam("page"),"row": $("#projectTable").getGridParam("rowNum"),"sord": $("#projectTable").getGridParam("sortorder"),"filters":{"programaId":programaId}});
            }},
            viewrecords: true,
            caption: "Material Did&aacute;ctico",            
            loadComplete: function (data) {
            	$(".ui-pg-selbox").val(cookieRegsXPage);
            	$(".ui-pg-input").val(data.page);
            	setCookie('materialPage', $('.ui-pg-input').val());
            	cookiePage = $('.ui-pg-input').val();
            },
            onPaging: function(page){
            	setCookie('materialRegXPage', $(".ui-pg-selbox").val());
            	cookieRegsXPage = $(".ui-pg-selbox").val();
            }
        });
        
        $(window).bind('resize', function() {
            $("#projectTable").setGridWidth($(window).width()-marginWidthGrid);
            $("#projectTable").setGridHeight($(window).height()-marginHeightGridFull);
        }).trigger('resize');
        
        
        $( "#remMaterial" ).dialog({
			autoOpen: false,
			width: 400,
			buttons: [
				{
					text: "Ok",
					click: function() {
						$( this ).dialog( "close" );
						deleteMaterial();
					}
				},
				{
					text: "Cancelar",
					click: function() {
						$( this ).dialog( "close" );
						materialIdToRemove = null;
					}
				}
			]
		});
        fixTable();
    });

 function cartillaAdjunta(cellValue,options,rowObject){
	 itemDownloadLink =$('<a></a>');
	 itemDownloadLink.addClass("comments-link");
	 itemDownloadLink.addClass("bold");
	 itemDownloadLink.html(cellValue);
	 itemDownloadLink.prop("download",cellValue),
	 itemDownloadLink.prop("href",pagePath+"/api/programa/"+programaId+"/material/"+rowObject.id+"/archivo");
	 itemDownloadLink.css("display", "block");
	 itemDownloadLink.css("text-align", "-webkit-center");
	 return itemDownloadLink.prop('outerHTML');
 }
 
 function estadoFormatter(cellvalue,options,rowObject){
	 itemCartilla=$('<span></span>');
	 itemCartilla.addClass("estadoMaterial");
	 itemCartilla.addClass(cellvalue.toLowerCase());
	 itemCartilla.html(cellvalue);
	 itemCartilla.css("display", "block");
	 itemCartilla.css("text-align", "-webkit-center");
	 
	 return itemCartilla.prop('outerHTML');
 } 
 
 function editCurrencyFmatter (cellvalue, options, rowObject)
 {
	var template = "<a href='"+pagePath+"/mvc/programa/"+programaId+"/materialABM/{ENTITY_ID}?asignatura="+asignatura+"&programaId="+programaId+"&nro="+rowObject.nro+"' title='Editar'><span class='ui-icon ui-icon-pencil'/></a>";
	 
    return template.replace("{ENTITY_ID}",cellvalue);
 }

 
 function deleteCurrencyFmatter (cellvalue, options, rowObject)
 {
	var template = "<a href='javascript:deleteMaterial_dialog({ENTITY_ID})' title='Eliminar'><span class='ui-icon ui-icon-trash'/></a>";
	 
    return template.replace("{ENTITY_ID}",cellvalue);
 }
 
 
function restoreState(){
	
	
	if(getCookie('materialRegXPage') != ""){
		$(".ui-pg-selbox").val(getCookie('materialRegXPage'));
		cookieRegsXPage = getCookie('materialRegXPage');
	}else{
		cookieRegsXPage = 5;
	}
	if(getCookie('materialPage') != ""){
		$('.ui-pg-input').val(getCookie('materialPage'));
		cookiePage = getCookie('materialPage');
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
	var pageNro = 1; 
	if(currentPage != null && currentPage > 1){
		pageNro = currentPage;
	}
	
	jQuery("#projectTable").jqGrid(
           'setGridParam',
           {
       		url:pagePath+"/api/programa/"+programaId+"/material",
            gridview:true,
            contentType :'application/json',
      		dataType: "json",
      		 postData:{requestData:function(postData) {            	
                 return requestData=JSON.stringify({"page": $("#projectTable").getGridParam("page"),"row": $("#projectTable").getGridParam("rowNum"),"sord": $("#projectTable").getGridParam("sortorder"),"filters":{"programaId":programaId}});
             }},
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
 
var materialIdToRemove = null;
function deleteMaterial_dialog(materialId){
	materialIdToRemove = materialId;
	$("#remMaterial").dialog("open");
}
function crearMaterialDidacticto(URL){
	var nro = [];
	for(i = 1; i< $('#projectTable tr').length ; i++){		
		nro.push(parseInt($('#projectTable tr:eq('+i+') td:eq(1)').html()));		
	}
	
	var anterior = 1;
	var changed = false;
	if(nro.length > 0){
		anterior = nro[0];
		for (i = 1 ; i < nro.length; i++){
			
			if(((anterior+1)) < nro[i]){
				anterior++;
				changed = true;
				break;
			}else{
				anterior++;
			}
		}
	}
	if(!changed){
		anterior++;
	}
	window.location = URL+"&nro="+anterior;
	
	
}
function deleteMaterial(){
	var materialId = materialIdToRemove;
	
	var pageToLoad = calculatePageToLoadAfterDelete();
	
	materialIdToRemove = null;
	$('#message').removeClass('msgSuccess');
	$('#message').removeClass('msgError');
	
	$.ajax({
		type:"DELETE",
		url:pagePath+"/api/programa/"+programaId+"/material/"+materialId,
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

