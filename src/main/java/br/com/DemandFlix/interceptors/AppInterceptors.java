package br.com.DemandFlix.interceptors;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;

import br.com.DemandFlix.annotation.Privado;
import br.com.DemandFlix.annotation.Publico;
import br.com.DemandFlix.rest.UsuarioRestController;


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
				// VARIÁVEL PARA TOKEN
				String token = null;
				//	VERIFICA SE É UM MÉTODO PRIVADO
				if (metodo.getMethodAnnotation(Privado.class) != null) {
					
					try {
						// SE O MÉTODO FOR PRIVADO RECUPERA O TOKEN
						token = request.getHeader("Authorization");
						Algorithm algoritmo = Algorithm.HMAC256(UsuarioRestController.SECRET);
						// obj para verificar token
						JWTVerifier verifier = JWT.require(algoritmo).withIssuer(UsuarioRestController.EMISSOR).build();
						// DECODIFICA O TOKEN
						DecodedJWT jwt = verifier.verify(token);// ESSA LINHA QUE VERIFICA O TOKEN
						// RECUPERAR OS DADOS DO PAYLOAD
						Map<String,Claim> claims = jwt.getClaims();
						System.out.println(claims.get("name"));
						
						return true;
						
					}catch (Exception e) {
						
						e.printStackTrace();
						// SE ESTIVER TENTANDO ACESSAR SEM O TOKEN
						if (token == null) {
							response.sendError(HttpStatus.UNAUTHORIZED.value(), e.getMessage());
						}else {
							response.sendError(HttpStatus.FORBIDDEN.value(), e.getMessage());
						}
						return false;
						
					}
					
				}
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
