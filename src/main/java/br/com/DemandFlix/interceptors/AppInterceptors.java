package br.com.DemandFlix.interceptors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import br.com.DemandFlix.annotation.Publico;


@Component
public class AppInterceptors implements HandlerInterceptor{

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {// response PARA REDIRECIONAR, handler 

		// variavel para obter, uri indentificador do que estou tentando acessar
		String uri = request.getRequestURI();
//		System.out.println("uri "+uri);
//		System.out.println("handler: "+handler);
		
		// VERIFICANDO SE ESTA INDO PARA UM LUGAR VALIDO
		HttpSession session = request.getSession(); // PARA SABER SE ESTA LOGADO
		
		// SE FOR PÁGINA DE ERRO, LIBERA
		if (uri.startsWith("/error")) {
			return true;
		}
		
		// VERIFICAR SE handler é um HandlerMethod, o que indica que ele está chamando o método en algum controller
		if (handler instanceof HandlerMethod) {
			// CASTING DE OBJECT PARA HandlerMethod
			HandlerMethod metodo = (HandlerMethod) handler;
			
			if (uri.startsWith("/api")) {
				return true;
				
			}else {
			
				// VERIFICA SE O METODO É PUBLICO
				if(metodo.getMethodAnnotation(Publico.class) != null) {
					return true;
				}
				//	VERIFICA SE EXISTI USUARIO LOGADO
				if (session.getAttribute("usuarioLogado") != null) {
					return true;
				}
				
				// REDIRECIONA PARA A PAGINA INICIAL
				response.sendRedirect("/");
				return false;
			
			}
		}
		
		return true;
	}
	
}
