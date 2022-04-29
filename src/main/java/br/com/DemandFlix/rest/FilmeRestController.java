package br.com.DemandFlix.rest;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.DemandFlix.annotation.Publico;
import br.com.DemandFlix.model.Filme;
import br.com.DemandFlix.model.Genero;
import br.com.DemandFlix.repository.FilmeRepository;

// CONTROLLER PARA RESPONDER REQUISIÇÕES REST
@RestController
@RequestMapping("/api/filme")
public class FilmeRestController {

	@Autowired
	private FilmeRepository repository;
	
	
	// LISTAR OS FILMES
	@Publico
	@RequestMapping(value = "", method = RequestMethod.GET)
	public Iterable<Filme> getFilmes(){
		
		return repository.findAll();
	}
	
	@Publico
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<Filme> getFilme(@PathVariable("id") Long idFilme){//ResponseEntity<Filme> PARA PERSONALIZAR O RETORNO 
		
		// TENTA BUSCAR O FILME NO RESTAURANTE
		Optional<Filme> optional = repository.findById(idFilme);
		
		// SE O RESTAURANTE EXISTIR
		if (optional.isPresent()) {
			
			// EXISTI E RETORNA O FILME
			return ResponseEntity.ok(optional.get());
		}else {
			
			// NÃO EXISTI E RETONA O ERRO 404
			return ResponseEntity.notFound().build();
		}
	} 
	
	
	// listar os restaurantes por tipo
	@Publico
	@RequestMapping(value = "generos/{idGenero}", method = RequestMethod.GET)
	public Iterable<Filme> getGeneroFilme(@PathVariable("idGenero") Long idGenero){

		Iterable<Filme> lista = repository.findByGeneroIdGenero(idGenero); 
		return lista;
		 
		
	}
}
