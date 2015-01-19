var busquedaTittle = "";
var lastComboLoaded = "";
var url = "aplicacion/listaplicacionesbyfiltros";

const TIPO_APLICACION_COMBO = "tipoAplicacion";
const TIPO_APLICACION_COMBO_LOADING = "#tipoAplicacion_loading";
const TIPO_APLICACION_COMBO_ID= "#tipoAplicacionSelect";
const TIPO_APLICACION_COMBO_CHOSEN_ID= "#tipoAplicacionSelect_chosen input";
const TIPO_APLICACION_COMBO_CHOSEN_SPAN = "#tipoAplicacionSelect_chosen span";
const TIPO_APLICACION_COMBO_CHOSEN_ABBR = "#tipoAplicacionSelect_chosen a abbr";
const TIPO_APLICACION_COMBO_CHOSEN_A = "#tipoAplicacionSelect_chosen a";

const MARCA_APLICACION_COMBO = "marcaAplicacion";
const MARCA_APLICACION_COMBO_LOADING = "#marcaAplicacion_loading";
const MARCA_APLICACION_COMBO_ID = "#marcaAplicacionSelect";
const MARCA_APLICACION_COMBO_CHOSEN_ID = "#marcaAplicacionSelect_chosen input";
const MARCA_APLICACION_COMBO_CHOSEN_SPAN = "#marcaAplicacionSelect_chosen span";
const MARCA_APLICACION_COMBO_CHOSEN_ABBR = "#marcaAplicacionSelect_chosen a abbr";
const MARCA_APLICACION_COMBO_CHOSEN_A = "#marcaAplicacionSelect_chosen a";

const MODELO_APLICACION_COMBO = "modeloAplicacion";
const MODELO_APLICACION_COMBO_LOADING = "#modeloAplicacion_loading";
const MODELO_APLICACION_COMBO_ID = "#modeloAplicacionSelect";
const MODELO_APLICACION_COMBO_CHOSEN_ID = "#modeloAplicacionSelect_chosen input";
const MODELO_APLICACION_COMBO_CHOSEN_SPAN = "#modeloAplicacionSelect_chosen span";
const MODELO_APLICACION_COMBO_CHOSEN_ABBR = "#modeloAplicacionSelect_chosen a abbr";
const MODELO_APLICACION_COMBO_CHOSEN_A = "#modeloAplicacionSelect_chosen a";

jQuery(document).ready(function () {
	
	restoreState();	
	loadHeaderOnStart();
	
	$(TIPO_APLICACION_COMBO_LOADING).hide();
	$(MARCA_APLICACION_COMBO_LOADING).hide();
	$(MODELO_APLICACION_COMBO_LOADING).hide();	
	
	$( ".button" ).button();
	
	$( ".chosen-select" ).chosen({
		allow_single_deselect: true
	});

	var filtros = getFiltros();
	var urlCompleta = url;
	if(filtros != ""){
		urlCompleta += filtros;
	}
	
    jQuery("#tablaAplicaciones").jqGrid({
    		url: urlCompleta,
            datatype: "json",
            contentType :'application/json',
            jsonReader: {repeatitems: false, id:"id"},
            colNames:['Tipo', 'Marca', 'Modelo', ''],
            colModel:[
                {name:'tipoMotor',index:'tipoMotor',sortable: false,align: "center"},   
                {name:'marca',index:'marca', sortable: false,align: "center"},          
                {name:'modelo',index:'modelo', sortable: false,align: "center"},          
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
            pager: "#tablaAplicaciones_paging",
            page:1,
            viewrecords: true,
            caption: "Aplicaciones",
            loadComplete: function (data) {
            	grid = $("#tablaAplicaciones");
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
                	window.location.assign("aplicacion/"+id);
                }; 
             }
        });
    
	
        
        $(window).bind('resize', function() {
        	$("#tablaAplicaciones").setGridWidth($(window).width()-4);
            $("#tablaAplicaciones").setGridHeight($(window).height()-324-13);// footer -13px

        
            var chosenLeftSize = $("#filtrosLeft").width()-200;
            if(chosenLeftSize < 200){ chosenLeftSize = 200;}
            else if(chosenLeftSize > 400){ chosenLeftSize = 400;}
            $($('#filtrosLeft .chosen-container')).width(chosenLeftSize);

            var chosenRightSize = $("#filtrosRight").width()-200;
            if(chosenRightSize < 200){ chosenRightSize = 200;}
            else if(chosenRightSize > 400){ chosenRightSize = 400;}
            $($('#filtrosRight .chosen-container')).width(chosenRightSize);
        }).trigger('resize');
           
        
        $(MARCA_APLICACION_COMBO_CHOSEN_ID).focus(function () {
        	if(lastComboLoaded != MARCA_APLICACION_COMBO){
        		 lastComboLoaded = MARCA_APLICACION_COMBO;
        		 cargarCombo(MARCA_APLICACION_COMBO);
        	}
        });
        
        $(TIPO_APLICACION_COMBO_CHOSEN_ID).focus(function () {
        	if(lastComboLoaded != TIPO_APLICACION_COMBO){
        		 lastComboLoaded = TIPO_APLICACION_COMBO;
        		 cargarCombo(TIPO_APLICACION_COMBO);
        	}
        });
        
        $(MODELO_APLICACION_COMBO_CHOSEN_ID).focus(function () {
        	if(lastComboLoaded != MODELO_APLICACION_COMBO){
        		 lastComboLoaded = MODELO_APLICACION_COMBO;
        		 cargarCombo(MODELO_APLICACION_COMBO);
        	}
        });
        
    });
 	
function goToDetalleCurrencyFmatter (cellvalue, options, rowObject)
{
	var template = "<a href='aplicacion/{ENTITY_ID}' title='Ver'><span class='ui-icon ui-icon-circle-triangle-e'/></a>";
	 
   return template.replace("{ENTITY_ID}",cellvalue);
}
 
function cargarCombo(nombreCombo){
	var tipoAplicacion = $(TIPO_APLICACION_COMBO_ID).val();
	var marcaAplicacion = $(MARCA_APLICACION_COMBO_ID).val();
	var modeloAplicacion = $(MODELO_APLICACION_COMBO_ID).val();
	
	if(nombreCombo == TIPO_APLICACION_COMBO){
		tipoAplicacion="";
		$(TIPO_APLICACION_COMBO_LOADING).show();	
	}else if(nombreCombo == MARCA_APLICACION_COMBO){
		marcaAplicacion="";
		$(MARCA_APLICACION_COMBO_LOADING).show();	
	}else if(nombreCombo == MODELO_APLICACION_COMBO){
		modeloAplicacion="";
		$(MODELO_APLICACION_COMBO_LOADING).show();	
	}

	
	$.ajax({
		type:"GET",
		url:"filtros/valores",
		data:{
			nombreCombo:nombreCombo,
			marcaFiltro: "",
			tipoFiltro: "",
			subTipoFiltro: "",
			tipoAplicacion: tipoAplicacion,
			marcaAplicacion: marcaAplicacion,
			modeloAplicacion: modeloAplicacion,
			codigoFiltro: "",
		},
		success: function(data){
			
			if(nombreCombo == TIPO_APLICACION_COMBO){
				updateCombo(TIPO_APLICACION_COMBO_ID, data.data);
				$(TIPO_APLICACION_COMBO_LOADING).hide();	
			}else if(nombreCombo == MARCA_APLICACION_COMBO){
				updateCombo(MARCA_APLICACION_COMBO_ID, data.data);
				$(MARCA_APLICACION_COMBO_LOADING).hide();	
			}else if(nombreCombo == MODELO_APLICACION_COMBO){
				updateCombo(MODELO_APLICACION_COMBO_ID, data.data);
				$(MODELO_APLICACION_COMBO_LOADING).hide();	
			}

		}
		
	});
}

function updateCombo(comboId, data){
	var template = '<option value="{ID}">{VALUE}</option>';
	
	$(comboId).html('');
	$(comboId).append('<option value=""></option>');
		
	if(data.length > 0){
		var option;
		for (var index in data){
			if(data[index].valor != null){
				option = template;
				option = option.replace("{ID}", data[index].valor);
				option = option.replace("{VALUE}", data[index].label);
				$(comboId).append(option);						
			}
		}		
	}

	$(comboId).trigger("chosen:updated");
}

function gridReload(currentPage){
	
	var filtros = getFiltros();
	var urlCompleta = url;
	if(filtros != ""){
		urlCompleta += filtros;
	}
	
	jQuery("#tablaAplicaciones").jqGrid('setGridParam',{
           	url:urlCompleta,
            gridview:true,
            contentType :'application/json',
      		dataType: "json",
      		page:1,
      		}).trigger("reloadGrid");



	saveState();
}
 
function getFiltros(){
	var filtros = "";
	filtros += "?tipo=" + $(TIPO_APLICACION_COMBO_ID).val();
	filtros += "&marca=" + $(MARCA_APLICACION_COMBO_ID).val();
	filtros += "&modelo=" + $(MODELO_APLICACION_COMBO_ID).val();
	
	filtros = filtros.replace(/null/g,'');
	filtros = filtros.replace(/undefined/g,'');
	
	return filtros;
}

function saveState(){
	 setCookie('ac1', $(TIPO_APLICACION_COMBO_ID).val());
	 setCookie('ac2', $(MARCA_APLICACION_COMBO_ID).val());
	 setCookie('ac3', $(MODELO_APLICACION_COMBO_ID).val());
}

function restoreState(){
	setComboOldValue(TIPO_APLICACION_COMBO_ID, TIPO_APLICACION_COMBO_CHOSEN_SPAN, TIPO_APLICACION_COMBO_CHOSEN_A, getCookie('ac1'));
	setComboOldValue(MARCA_APLICACION_COMBO_ID, MARCA_APLICACION_COMBO_CHOSEN_SPAN, MARCA_APLICACION_COMBO_CHOSEN_A, getCookie('ac2'));
	setComboOldValue(MODELO_APLICACION_COMBO_ID, MODELO_APLICACION_COMBO_CHOSEN_SPAN, MODELO_APLICACION_COMBO_CHOSEN_A, getCookie('ac3'));
}

function setComboOldValue(comboElement, spanElement, aElement, cookieVal){
	var template = '<option value="{ID}">{VALUE}</option>';
	var optionTodos = '<option value="">Todos</option>';
	var option;
	
	if(cookieVal != "" && cookieVal != "Seleccione una opción"){ 
		$(spanElement).text(cookieVal);
		 
		if($(spanElement).text() != "Todos"){
			option = template;
			option = option.replace("{ID}",cookieVal);
			option = option.replace("{VALUE}",cookieVal);
			
			$(comboElement).html(option);
			$(comboElement).val(cookieVal);
		}else{
			$(comboElement).html(optionTodos);			 
			$(comboElement).val("");
		}
		$(aElement).removeClass("chosen-default");
	}
} 

function limpiar(){
	
	$(TIPO_APLICACION_COMBO_ID).val("");
	$(TIPO_APLICACION_COMBO_CHOSEN_SPAN).text("Seleccione una opción");
	$(TIPO_APLICACION_COMBO_CHOSEN_ABBR).remove();
	$(TIPO_APLICACION_COMBO_CHOSEN_A).addClass("chosen-default");
	
	$(MARCA_APLICACION_COMBO_ID).val("");
	$(MARCA_APLICACION_COMBO_CHOSEN_SPAN).text("Seleccione una opción");
	$(MARCA_APLICACION_COMBO_CHOSEN_ABBR).remove();
	$(MARCA_APLICACION_COMBO_CHOSEN_A).addClass("chosen-default");
	
	$(MODELO_APLICACION_COMBO_ID).val("");
	$(MODELO_APLICACION_COMBO_CHOSEN_SPAN).text("Seleccione una opción");
	$(MODELO_APLICACION_COMBO_CHOSEN_ABBR).remove();
	$(MODELO_APLICACION_COMBO_CHOSEN_A).addClass("chosen-default");
	
	gridReload();
}