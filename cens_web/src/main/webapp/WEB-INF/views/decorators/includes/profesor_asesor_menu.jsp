<%@ page session="false" contentType="text/html; charset=ISO-8859-1"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %> 


<ul class="menu">

	<li>
		<a href="<%=request.getContextPath()%>/mvc/perfil">Mi Perfil</a>

	</li>
	<li>
		<a href="<%=request.getContextPath() %>/mvc/administracion">Administraci&oacute;n del Sistema</a>
	</li>

	<li>
       <a href="<%=request.getContextPath() %>/mvc/profesor/asignaturaList">Profesor</a>
	</li>
	
	<li>
        <a href="<%=request.getContextPath() %>/mvc/asesor/dashboard">Asesor</a>

	</li>
	
	


</ul>