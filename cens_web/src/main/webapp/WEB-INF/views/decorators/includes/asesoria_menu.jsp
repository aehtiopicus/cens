<%@ page session="false" contentType="text/html; charset=ISO-8859-1"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %> 


<ul class="menu">

    <li>
        <a href="<%=request.getContextPath() %>/mvc/miembroList">Administraci&oacute;n de Miembros</a>
<!--         <ul> -->
<%--             <li><a href="<%=request.getContextPath() %>/asesoresList">Gesti&oacute;n de Asesores</a></li> --%>
<%--             <li><a href="<%=request.getContextPath() %>/enteList.jsp">Gesti&oacute;n de Profesores</a></li> --%>
<%--            	<li><a href="<%=request.getContextPath() %>/usuarios">Gesti&oacute;n de Preceptores</a></li>            --%>
<!--         </ul> -->
    </li>

	<li>
        <a href="#">Preceptores</a>
		<ul>
			<li><a href="<%=request.getContextPath() %>/mvc/asignaturaList">Gesti&oacute;n de Asignatura</a></li>
			<li><a href="<%=request.getContextPath() %>/mvc/cursoList">Gesti&oacute;n de Cursos</a></li>
		</ul>
	</li>
	<li>
        <a href="#">Profesores</a>
		<ul>
			<li><a href="<%=request.getContextPath() %>/mvc/usuario/${profesorId}/planes">Gesti&oacute;n de planes de estudio</a></li>
			<li><a href="<%=request.getContextPath() %>/mvc/cursoList">Gesti&oacute;n de Material Did&aacute;ctico</a></li>
		</ul>
	</li>
	
	<li>
        <a href="#">Gesti&oacute;n de Cursos</a>
		<ul>
			<li><a href="<%=request.getContextPath() %>/mvc/expedienteList">Gesti&oacute;n de Expedientes</a></li>

		</ul>
	</li>


</ul>