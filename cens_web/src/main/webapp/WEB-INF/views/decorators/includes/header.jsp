<%@ page session="false" contentType="text/html; charset=ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

<script>
var pagePath="<%=request.getContextPath()%>";
$(document).ready(function(){
	responsiveMenu.responsiveChangeListener();
    responsiveMenu.pingCompleteResponsiveCheck(); 
});
</script>
<div id="encabezado">
		<div id="hmMenu"></div>
		<div class="logo">
			<img src="<%=request.getContextPath() %>/css/midasUI-theme/images/logo_cens.png" onclick="location.href='<%=request.getContextPath() %>/mvc/main'" >
		</div>
		<div class="center">
			<h2>Gesti&oacute;n de Material Did&aacute;ctico</h2>
		</div>
		<div class="user">
			<div style="display:inline-block;">
				<div style="  position: relative;">
					<img id="headerImg"  class="avatarimg">
					 <span id="notCant1" class="bubbleRedNotify" style="background-color: rgb(0, 72, 155);top: 5px;left: 49px;">?</span>
					<a id ="closeButton" class="linkBotton exit" href="<c:url value="javascript:logout()" />">Salir</a>
				</div>
				<div class="usertop">
					
					<div id="iam" style="display:none;">
					<div class="iam-pointer"></div>
					<div class="iam-div">
						<div style=" margin-top: 5px; height:50%;">
							<div style="display:inline-block;vertical-align: top;">
								<img id="iam-img" class="avatarimg">
							</div>							<div class="usertop" style=" width: 45%">
								
								<span id="headerUsername" style="height: 25px;"class="username"><security:authentication property="name"></security:authentication></span>
								<span id="notificacionOpen" style="height: 25px;">Notifcaciones <span id="notCant" class="bubbleRedNotify" style="background-color: rgb(0, 72, 155);">?</span></span>		
								<span id="seguimientoOpen" style="height: 25px;">Seguimiento<span id="notCantNoLeida" class="bubbleRedNotify" style="background-color: rgb(240, 50, 75);">?</span></span>
								
							</div>
						</div>
						
						<div style="height:50%;">
							<hr style="  position: relative;top: 24px;">
							<a id ="cerrarIam" class="linkBotton exit" style="top: 102px;float: right;left: 184px;">Cerrar</a>
						</div>
					</div>
					</div>																							
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