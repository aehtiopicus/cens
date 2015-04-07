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
		<div style="width: 10%;padding-top: 4px;padding-right: 10px;">
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
						<div style="display:inline-block; margin-top: -8px;">
							<div style="display:inline-block;">
								<img id="iam-img" class="avatarimg">
							</div>							
							<div class="usertop" style="position: relative;top: 26px;">
								
								<span id="headerUsername" style="height: 25px;"class="username"><security:authentication property="name"></security:authentication></span>
								<span style="height: 25px;">Notifcaciones <span id="notCant" class="bubbleRedNotify" style="background-color: rgb(0, 72, 155);">?</span></span>		
								<span style="height: 25px;">Seguimiento<span id="notCantNoLeida" class="bubbleRedNotify" style="background-color: rgb(240, 50, 75);">?</span></span>
								
							</div>
						</div>
						<hr style="  margin-top: 25px;">
						<a id ="cerrarIam" class="linkBotton exit" style="top: 102px;float: right;left: 184px;">Cerrar</a>
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