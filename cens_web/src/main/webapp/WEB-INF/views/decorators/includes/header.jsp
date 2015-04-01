<%@ page session="false" contentType="text/html; charset=ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

<script>
var pagePath="<%=request.getContextPath()%>";
</script>
<div id="encabezado">
	
		<div class="logo">
			<img src="<%=request.getContextPath() %>/css/midasUI-theme/images/logo_cens.png" onclick="location.href='<%=request.getContextPath() %>/mvc/main'" >
		</div>
		<div class="center">
			<h2>Gesti&oacute;n de Material Did&aacute;ctico</h2>
		</div>
		<div style="width: 18%;">
			<div style="display:inline-block;">
				<div style="display:inline-block; margin-top: 10px;">
					<img id="headerImg"  class="avatarimg">
				</div>
				<div class="usertop">
					<span id="headerUsername" class="username"><security:authentication property="name"></security:authentication></span>										
					<a id ="closeButton" class="linkBotton" href="<c:url value="javascript:logout()" />">Salir</a>
				</div>
			</div>
			
		</div>
	
	
</div>
<div id="topNavigation">
</div>
<jsp:include page="notificacion.jsp"/>
<style>
a.linkBotton{
	text-decoration: none; 
	color: gray;
}
</style>