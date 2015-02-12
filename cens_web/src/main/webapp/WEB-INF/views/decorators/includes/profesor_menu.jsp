<%@ page session="false" contentType="text/html; charset=ISO-8859-1"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %> 


<ul class="menu">

	<li>
        <a href="#">Profesores</a>
		<ul>
			<li><a href="<%=request.getContextPath() %>/mvc/profesor/asignaturaList">Gesti&oacute;n de planes de estudio</a></li>
		</ul>
	</li>
		


</ul>