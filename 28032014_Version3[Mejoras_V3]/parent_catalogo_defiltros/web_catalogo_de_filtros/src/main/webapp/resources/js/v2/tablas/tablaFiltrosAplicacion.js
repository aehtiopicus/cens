//var cookiePage = "";
//var cookieRegsXPage = "";

jQuery(document).ready(function () {

	//restoreState();	
	loadHeaderOnStart();
	
    jQuery("#filtros-aire-table").jqGrid({
    		url:"../aplicacion/list?idAplicacion="+idAplicacion+"&tipoFiltro=Aire",
            datatype: "json",
            contentType :'application/json',
            jsonReader: {repeatitems: false, id:"id"},
            colNames:['Codigo', 'Marca', ''],
            colModel:[
                {name:'codigoCorto',index:'codigo',sortable: false,align: "center"},   
                {name:'marca',index:'marca', sortable: false,align: "center"},          
                { 	
        			name: 'id',  
        			width: 16,
        			editable: false,
        			sortable: false,
        			fixed: true,
        			formatter: goToDetalleCurrencyFmatter,
        			align:"center"
        		}
            ],
            rowNum:1000,
            viewrecords: true,
            caption: "Aire",
            loadComplete: function (data) {
            	grid = $("#filtros-aire-table");
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
    
	    jQuery("#filtros-aceite-table").jqGrid({
			url:"../aplicacion/list?idAplicacion="+idAplicacion+"&tipoFiltro=Aceite",
	        datatype: "json",
	        contentType :'application/json',
	        jsonReader: {repeatitems: false, id:"id"},
	        colNames:['Codigo', 'Marca', ''],
	        colModel:[
	            {name:'codigoCorto',index:'codigo',sortable: false,align: "center"},   
	            {name:'marca',index:'marca', sortable: false,align: "center"},          
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
	        rowNum:1000,
	        viewrecords: true,
	        caption: "Aceite",
            loadComplete: function (data) {
            	grid = $("#filtros-aceite-table");
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
	    
	    jQuery("#filtros-combustible-table").jqGrid({
    		url:"../aplicacion/list?idAplicacion="+idAplicacion+"&tipoFiltro=Combustible",
            datatype: "json",
            contentType :'application/json',
            jsonReader: {repeatitems: false, id:"id"},
            colNames:['Codigo', 'Marca', ''],
            colModel:[
                {name:'codigoCorto',index:'codigo',sortable: false,align: "center"},   
                {name:'marca',index:'marca', sortable: false,align: "center"},          
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
            rowNum:1000,
            viewrecords: true,
            caption: "Combustible",
            loadComplete: function (data) {
            	grid = $("#filtros-combustible-table");
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
	    
	    jQuery("#filtros-habitaculo-table").jqGrid({
    		url:"../aplicacion/list?idAplicacion="+idAplicacion+"&tipoFiltro=Habitaculo",
            datatype: "json",
            contentType :'application/json',
            jsonReader: {repeatitems: false, id:"id"},
            colNames:['Codigo', 'Marca', ''],
            colModel:[
                {name:'codigoCorto',index:'codigo',sortable: false,align: "center"},   
                {name:'marca',index:'marca', sortable: false,align: "center"},          
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
            rowNum:1000,
            viewrecords: true,
            caption: "Habitáculo",
            loadComplete: function (data) {
            	grid = $("#filtros-habitaculo-table");
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
	    
	    jQuery("#filtros-otros-table").jqGrid({
    		url:"../aplicacion/list?idAplicacion="+idAplicacion+"&tipoFiltro=Otros",
            datatype: "json",
            contentType :'application/json',
            jsonReader: {repeatitems: false, id:"id"},
            colNames:['Codigo', 'Marca', ''],
            colModel:[
                {name:'codigoCorto',index:'codigo',sortable: false,align: "center"},   
                {name:'marca',index:'marca', sortable: false,align: "center"},          
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
            rowNum:1000,
            viewrecords: true,
            caption: "Otros",
            loadComplete: function (data) {
            	grid = $("#filtros-otros-table");
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
            $("#filtros-aire-table").setGridWidth($(window).width()/5-3);
            $("#filtros-aire-table").setGridHeight($(window).height()-97 - 13);// footer -13px
            $("#filtros-aceite-table").setGridWidth($(window).width()/5-2);
            $("#filtros-aceite-table").setGridHeight($(window).height()-97 - 13);// footer -13px
            $("#filtros-combustible-table").setGridWidth($(window).width()/5-3);
            $("#filtros-combustible-table").setGridHeight($(window).height()-97 - 13);// footer -13px
            $("#filtros-habitaculo-table").setGridWidth($(window).width()/5-2);
            $("#filtros-habitaculo-table").setGridHeight($(window).height()-97 - 13);// footer -13px
            $("#filtros-otros-table").setGridWidth($(window).width()/5-3);
            $("#filtros-otros-table").setGridHeight($(window).height()-97 - 13);// footer -13px
        }).trigger('resize');
                
    });
 
	
	
function goToDetalleCurrencyFmatter (cellvalue, options, rowObject)
{
	var template = "<a href='../detalle/{ENTITY_ID}' title='Ver'><span class='ui-icon ui-icon-circle-triangle-e'/></a>";
	 
   return template.replace("{ENTITY_ID}",cellvalue);
}
 
 