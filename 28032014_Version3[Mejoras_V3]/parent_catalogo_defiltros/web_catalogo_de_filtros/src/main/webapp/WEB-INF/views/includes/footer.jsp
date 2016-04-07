<%-- <%@ page language="java" contentType="text/html; charset=ISO-8859-1" --%>
<%--     pageEncoding="ISO-8859-1"%> --%>

		<div id=div_footer>
			<div>
				<label>Desarrollado Por Midas Consultores - <a href=http://www.midasconsultores.com target="_blank">www.midasconsultores.com</a></label>
                                <label id=fecha-actualizacion>Última Actualización: <span id=fecha></span><span id="requiredUpdate" onclick="abrirDialogoActualizaciones();"style="color: #003bb3; padding-left: 15px"></span></label>
			</div>
		</div>
		
		<script type="text/javascript">
			$("#fecha").text(getCookie("fechaActualizacion"));
		</script>
	