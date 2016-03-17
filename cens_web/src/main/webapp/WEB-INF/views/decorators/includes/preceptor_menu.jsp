<%@ page session="false" contentType="text/html; charset=ISO-8859-1"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %> 


<ul class="menu">

	<li>
		<a href="<%=request.getContextPath()%>/mvc/perfil">Mi Perfil</a>

	</li>
    <li>
        <a href="<%=request.getContextPath() %>/mvc/miembroList">Administraci&oacute;n de Miembros</a>
    </li>

	
        <li><a href="<%=request.getContextPath() %>/mvc/asignaturaList">Gesti&oacute;n de Asignatura</a></li>
			<li><a href="<%=request.getContextPath() %>/mvc/cursoList">Gesti&oacute;n de Cursos</a></li>
		
</ul>