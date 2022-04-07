package br.com.DemandFlix.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import br.com.DemandFlix.model.Filme;
import br.com.DemandFlix.model.Genero;
import br.com.DemandFlix.repository.GeneroRepository;

@Controller
public class GeneroController {

	@Autowired
	GeneroRepository repositoryGenero;
	
	@RequestMapping("cadastroGenero")
	public String cadastroGenero() {
		
		return "dashboard/filme/genero/formularioGenero";
	}
	
	
	@RequestMapping(value ="salvarGenero", method = RequestMethod.POST)
	public String salvarGenero(Genero genero) {
		
		repositoryGenero.save(genero);
		
		return "redirect:listaGeneros/1";
	}
	
	
	@RequestMapping("listaGeneros/{pagesGenero}")
	public String listarGeneros(Model model,@PathVariable("pagesGenero") int pagesGenero ) {
		
		
		// CRIA UM PAGEABLE INFORMANDO OS PARÂMETROS DA PÁGINA   | -1 O MENOS UM É POR QUE COMEÇA A CONTAR DO ZERO
				PageRequest pageable = PageRequest.of(pagesGenero-1, 15, Sort.by(Sort.Direction.ASC, "nomeGenero"));//	 (Sort.Direction.ASC, "nome") ORDENANDO PELO NOME
				
				// CRIAR UM APGE DE GENEROS, ATRAVES DOS PARÂMETROS PASSADOS AO REPOSITORY
				Page<Genero> pagina = repositoryGenero.findAll(pageable);// 	 ESSA LINHA JÁ GERA UM SELECT COM LIMITE
				
				// ADD A MODEL A LISTA COM OS GENEROS
				model.addAttribute("generos",pagina.getContent());// COLOCAMOS NA MODEL OS FILMES, SOMETE DA PÁGINA ATUAL, A PÁGINA QUE CHAMOU
				
				
				// VAMOS CRIAR UM VETOR DE INTEIROS E VAMOS POPULAR
				int totalPages = pagina.getTotalPages(); // VARIÁVEL PARA O TOTAL DE PÁGINAS
			
				// CRIA UM LIST  DE INTEIROS PARA ARMEZENA OS NºS DAS PÁGINAS
				List<Integer> numPaginas = new ArrayList<Integer>();
				
				// PREENCHER O LIST COM AS PÁGIAS
				for(int i = 1; i <= totalPages; i++) {
					
					numPaginas.add(i);
				}
				
				
				

						
				// ADD A PÁGINA AO LIST
				model.addAttribute("numeroPaginas",numPaginas);
				model.addAttribute("totalPaginas", totalPages);
				model.addAttribute("paginaAtual", pagesGenero);
		
		return"dashboard/filme/genero/listaGenero";
	}
	
	
	@RequestMapping("deletarGenero")
	public String excluirGenero(Long id) {
		
		repositoryGenero.deleteById(id);
		
		return"redirect:listaGeneros/1";
		
	}
	
	@RequestMapping("alterarGenero")
	public String alterarGenero(Model model, Long id) {
		
		Genero genero = repositoryGenero.findById(id).get();
		model.addAttribute("generos", genero);
		
		return"forward:cadastroGenero";
		
	}
	
	

}
