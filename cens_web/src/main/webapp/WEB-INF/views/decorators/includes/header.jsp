<%@ page session="false" contentType="text/html; charset=ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>


<div id="encabezado">
	<table width="100%">
		<tr>
			
			<td align="left" width="15%">
				<img src="css/midasUI-theme/images/logo_cens.png"  style="margin-left: 5px;">
			</td>
			<td  align="center" style="font-style:italic;">
				<h2>Gesti&oacute;n de Material Did&aacute;ctico</h2>	
			</td>
			<td align="right"  width="15%" >
	<table width="100%">
		<tr>
			<td align="center" style="text-align: center;">
					<security:authentication property="name"></security:authentication> 					

			</td>
		</tr>
				<tr>
			<td align="center" style="text-align: center;">
		<a class="linkBotton" href="<c:url value="javascript:logout()" />">Salir</a>
		</td>
		</tr>
	</table>
	
			</td>
		</tr>
	</table>
</div>
<div id="topNavigation">
</div>
<style>
a.linkBotton{
	text-decoration: none; 
	color: gray;
}
</style>