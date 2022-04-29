package br.com.DemandFlix.rest;

import java.net.URI;
import java.util.Optional;

import javax.management.RuntimeErrorException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.DemandFlix.annotation.Privado;
import br.com.DemandFlix.annotation.Publico;
import br.com.DemandFlix.model.Erro;
import br.com.DemandFlix.model.Usuario;
import br.com.DemandFlix.repository.UsuarioRepository;

@RestController
@RequestMapping("/api/usuario")
public class UsuarioRestController {

	
	@Autowired
	private UsuarioRepository repository;

	@Publico
	@RequestMapping(value = "", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)// consumes = MediaType.APPLICATION_JSON_VALUE, SÓ ACEITA CONTENT TYPE JSON, produces INDICAR O QUE ELE PRODUZ, MAIS NÃO DECLARAMOS POIS O SPRIG BOOT JÁ FAZ ISSO POR QUE JÁ É PREPARADO PRA TRABALHAR CO  REST
	public ResponseEntity<Object> criarUsuario(@RequestBody Usuario usuario) {// ResponseEntity --> MANIPULAR A RESPOSTA, CEFECCIONAR o response, @RequestBody USUARIO VEM DO CORPO DA APLICAÇÃO
		
		try {
			// INSERI O USUARIO NO BANCO
			repository.save(usuario);
			
			//RETORNA UM CÓDIGO HTTP 201, INFORMA COMO ACESSAR O RECURSO INSERÇÃO
			// E ACRESENTANDO NO CORPO DA RESPOSTA O OBJETO INSERIDO
			return ResponseEntity.created(URI.create("/api/usuario/"+usuario.getId())).body(usuario);// body(usuario) colocar no body a resposta gerada
		}catch (DataIntegrityViolationException e) {// REGISTRO DUPLICADO
			
			e.printStackTrace();
			Erro erro = new Erro(HttpStatus.INTERNAL_SERVER_ERROR, "Registro duplicado", e.getClass().getName());
			return new ResponseEntity<Object>(erro, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@Privado
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<Usuario> getUsuario(@PathVariable("id") Long idUsuario){
		
	
		Optional<Usuario> optional = repository.findById(idUsuario); 
		
		
		if (optional.isPresent()) {
			
			return ResponseEntity.ok(optional.get());
		}else {
			return ResponseEntity.notFound().build();
		}
	}
	
	@Privado
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Void> atualizarUsuario(@RequestBody Usuario usuario,@PathVariable("id") Long id ){
		
		// VALIDAÇÃO ID, SE EXISTI OU NÃO, POR QUE SE NÃO EXISTIR ELE VAI CRIAR EM VEZ DE ATUALIZAR
		if (usuario.getId() != id) {
			
			throw new RuntimeException("ID Inválido");
		}
		
		repository.save(usuario);
		
		
		return ResponseEntity.ok().build();
	}
			
	@Privado
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> excluirUsuario(@PathVariable("id") Long idUsuario){
		
		repository.deleteById(idUsuario);
		return ResponseEntity.noContent().build();
	}
}
