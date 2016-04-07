//var cookiePage = "";
//var cookieRegsXPage = "";

jQuery(document).ready(function () {

	//restoreState();	
	
	loadHeaderOnStart();
	
    jQuery("#aplicaciones-table").jqGrid({
    		url:"../aplicaciones/list?idFiltro="+idFiltro,
            datatype: "json",
            contentType :'application/json',
            jsonReader: {repeatitems: false, id:"id"},
            colNames:['Marca', 'Modelo',''],
            colModel:[
                {name:'marca',index:'marca', sortable: false,align: "center"},
                {name:'modelo',index:'modelo',sortable: false,align: "center"},             
                { 	
        			name: 'id',  
        			width: 16,
        			editable: false, 
        			sortable: false,
        			fixed: true,
        			formatter: goToFiltrosAplicacionList,
        			align: "center"
        		}
            ],
            rowList:[10,25,50],
            rowNum:10,
            pager: "#aplicaciones-table-paging",
            page:1,
            viewrecords: true,
            caption: "Aplicaciones",
            loadComplete: function (data) {
            	grid = $("#aplicaciones-table");
            	grid.resize();
            	var container = grid.parents('.ui-jqgrid-view'); // find the grid's container
            	if(jQuery(this)[0].rows.length == 1){
            		if(container.find("#texto-vacio").length == 0){
            			container.find('.ui-jqgrid-bdiv').append("<span id=texto-vacio>No se encontraron resultados.</span>");// insert the empty data text
            		} 
            	} else {
            		container.find('.ui-jqgrid-bdiv #texto-vacio').remove();
            	}
            },
            onSelectRow: function(id){ 
                if(id){ 
                	window.location.assign("../aplicacion/"+id);
                }; 
             }
        });
        
        jQuery("#reemplazos-table").jqGrid({
    		url:"../filtros/list?idFiltro="+idFiltro,
    	    datatype: "json",
    	    contentType :'application/json',
    	    jsonReader: {repeatitems: false, id:"id"},
    	    colNames:['Codigo', 'Marca',''],
    	    colModel:[
    	        {name:'codigoCorto',index:'codigoCorto', sortable: false,align: "center"},
    	        {name:'marca',index:'marca',sortable: false,align: "center"},             
                { 	
        			name: 'id',  
        			width: 16,
        			editable: false, 
        			sortable: false,
        			fixed: true,
        			formatter: goToDetalleCurrencyFmatter,
        			align: "center"
        		}
    	    ],
    	    rowList:[10,25,50],
    	    rowNum:10,
    	    pager: "#reemplazos-table-paging",
    	    page:1,
    	    viewrecords: true,
    	    caption: "Reemplazos",
            loadComplete: function (data) {
            	grid = $("#reemplazos-table");
            	grid.resize();
            	var container = grid.parents('.ui-jqgrid-view'); // find the grid's container
            	if(jQuery(this)[0].rows.length == 1){
            		if(container.find("#texto-vacio").length == 0){
            			container.find('.ui-jqgrid-bdiv').append("<span id=texto-vacio>No se encontraron resultados.</span>");// insert the empty data text
            		} 
            	} else {
            		container.find('.ui-jqgrid-bdiv #texto-vacio').remove();
            	}
            },
            onSelectRow: function(id){ 
                if(id){ 
                	window.location.assign("../detalle/"+id);
                }; 
             }
    	});
        
        $(window).bind('resize', function() {
            $("#aplicaciones-table").setGridWidth($(window).width()/2 + 3); 
            $("#aplicaciones-table").setGridHeight($(window).height() - 370 - 13); // footer -13px
            $("#reemplazos-table").setGridWidth($(window).width()/2 - 7);
            $("#reemplazos-table").setGridHeight($(window).height() - 370 - 13); // footer -13px
        }).trigger('resize');
        
    });
 
	
	
function goToFiltrosAplicacionList (cellvalue, options, rowObject)
{
	var template = "<a href='../aplicacion/{ENTITY_ID}' title='Ver'><span class='ui-icon ui-icon-circle-triangle-e'/></a>";
	 
   return template.replace("{ENTITY_ID}",cellvalue);
}

function goToDetalleCurrencyFmatter (cellvalue, options, rowObject)
{
	var template = "<a href='../detalle/{ENTITY_ID}' title='Ver'><span class='ui-icon ui-icon-circle-triangle-e'/></a>";
	 
   return template.replace("{ENTITY_ID}",cellvalue);
}
