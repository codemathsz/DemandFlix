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

import br.com.DemandFlix.model.Filme;
import br.com.DemandFlix.repository.FilmeRepository;

@Controller
public class DashboardController {

	@Autowired
	private FilmeRepository repositoryFilme;
	
	@RequestMapping("dashboard")
	public String index(Model model) {
		
		// CRIA UM PAGEABLE INFORMANDO OS PARÂMETROS DA PÁGINA   | -1 O MENOS UM É POR QUE COMEÇA A CONTAR DO ZERO
		PageRequest pageable = PageRequest.ofSize(5);//	 (Sort.Direction.ASC, "nome") ORDENANDO PELO NOME
		
		// CRIAR UM APGE DE FILMES, ATRAVES DOS PARÂMETROS PASSADOS AO REPOSITORY
		Page<Filme> pagina = repositoryFilme.findAll(pageable);// 	 ESSA LINHA JÁ GERA UM SELECT COM LIMITE
		
		// ADD A MODEL A LISTA COM OS FILMES
		model.addAttribute("filmes",pagina.getContent());// COLOCAMOS NA MODEL OS FILMES, SOMETE DA PÁGINA ATUAL, A PÁGINA QUE CHAMOU
		
		
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
		
		return "dashboard/dashboard";
	}
	
	

}
