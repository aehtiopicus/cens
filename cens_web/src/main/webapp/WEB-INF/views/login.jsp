<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

 

 
	<form name='f' action="<c:url value='j_spring_security_check' />"
		method='POST'>
		
 		<fieldset>
			<div class="center">
				<img src="<%=request.getContextPath()%>/css/midasUI-theme/images/logo_cens.png">
			</div>
	
			<div>
				<label for="username">Usuario:</label>
				<input type='text' name='j_username' value=''>
			</div>
			
			<div>
				<label for="username">Constraseña:</label>
				<input type='password' name='j_password' />
			</div>
			
			<div class="footerForm">
				<button class="button" type="submit" >Ingresar</button>
			</div>
			
			<c:if test="${error == true}">
				<div class="errorblock">
					Usuario o contraseña incorrecta<br />
				</div>
			</c:if>
 		</fieldset>
	</form>
	
	<script type="text/javascript">
		window.onload=document.f.j_username.focus();
		
		document.addEventListener('DOMContentLoaded',function(){
			for(var key in localStorage) {
				localStorage.removeItem(key);
			}	
		},false);
	</script>

