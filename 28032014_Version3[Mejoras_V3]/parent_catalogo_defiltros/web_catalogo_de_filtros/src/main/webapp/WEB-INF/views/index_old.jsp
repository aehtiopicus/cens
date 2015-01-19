 <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!doctype html>
<html>
	<head>
		<title>Catalogo de Filtros</title>
		<link rel=stylesheet type="text/css" href='/web_catalogo_de_filtros/resources/css/custom.css'/>
		<link rel=stylesheet href='<c:out value="${senchaCSSPath}"/>' type="text/css">
		<link rel=stylesheet href="resources/css/app-master.css" type="text/css"/>
		<script src='<c:out value="${senchaJSPath}"/>' ></script>
		<script src="/web_catalogo_de_filtros/resources/js/jquery-1.9.1.min.js"></script>
		<script src="/web_catalogo_de_filtros/resources/js/jquery.numeric.js"></script>
		<script src="/web_catalogo_de_filtros/resources/js/catalogodefiltros/common-functions.js"></script>
		<script>
			document.addEventListener('mousewheel',function(e){var el=e.target;var offset,scroller,_results;_results=[];while(el!==document.body){if(el&&el.className&&el.className.indexOf('x-container')>=0){var cmp=Ext.getCmp(el.id);if(cmp&&typeof cmp.getScrollable=='function'&&cmp.getScrollable()){var scroller=cmp.getScrollable().getScroller();if(scroller){var offset={x:0,y:-e.wheelDelta*0.5};scroller.fireEvent('scrollstart',scroller,scroller.position.x,scroller.position.y,e);scroller.scrollBy(offset.x,offset.y);scroller.snapToBoundary();scroller.fireEvent('scrollend',scroller,scroller.position.x,scroller.position.y-offset.y);break;}}}
			_results.push(el=el.parentNode);}
			return _results;},false);
		</script>
		<script> var disabled = ${empresa}; </script>
		<script src=app.js></script>
	</head>
	<body>
		<div id=myHeader style="display:none;">
			<div id=adPlacement>
				<a href='/'>Inicio</a>
				&nbsp;/&nbsp;
				<a href='/Sencha_Touch/'>Sencha_Touch</a>
				<i>Por favor utilice Chrome o una Tablet para testear!</i>
			</div>
		</div>
		<div class=clear></div>
	</body>
</html>