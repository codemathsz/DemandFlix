package br.com.DemandFlix.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.DemandFlix.model.Filme;
import br.com.DemandFlix.model.Genero;
import br.com.DemandFlix.repository.FilmeRepository;
import br.com.DemandFlix.repository.GeneroRepository;

@Controller
public class FilmesController {

	@Autowired
	private FilmeRepository repositoryFilme;
	
	@Autowired
	private GeneroRepository repositoryGenero;
	
	@RequestMapping("cadastroFilmes")
	public String cadastraFilme(Model model) {
		
		List<Genero> genero = (List<Genero>) repositoryGenero.findAll();
		model.addAttribute("genero", genero);
		
		return "filmes/cadastroFilmes/formulario";
	}
	
	
	@RequestMapping(value = "salvar", method = RequestMethod.POST)
	public String salvarFilme(@Valid Filme filme, Model model) {
		
		
		
		repositoryFilme.save(filme);
		
		return "redirect:cadastroFilmes";
	}
	
	@RequestMapping("listaDeFilmes/{pagesFilme}")
	public String listarFilmes(Model model, @PathVariable("pagesFilme") int pagesFilme) {
		
		// CRIA UM PAGEABLE INFORMANDO OS PARÂMETROS DA PÁGINA   | -1 O MENOS UM É POR QUE COMEÇA A CONTAR DO ZERO
		PageRequest pageable = PageRequest.of(pagesFilme-1, 15, Sort.by(Sort.Direction.ASC, "nomeFilme"));//	 (Sort.Direction.ASC, "nome") ORDENANDO PELO NOME
		
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
		model.addAttribute("paginaAtual", pagesFilme);
		
		
		return "filmes/listaFilmes/lista";
	}
	
	@RequestMapping("alterarFilme")
	public String alterarFilme(Long id, Model model) {
		
		Filme filme = repositoryFilme.findById(id).get();
		model.addAttribute("filmes", filme);
		
		return "forward:cadastroFilmes";
	}
	
	
	@RequestMapping("deletarFilme")
	public String deletarFilme(Long id) {
		
		repositoryFilme.deleteById(id);
		
		return "forward:listaDeFilmes/1";
	}
	
	
	
	@RequestMapping("filtar")
	private String filtar(String nome, String diretor, Model model, RedirectAttributes attr) {
		
		List<Filme> filme = repositoryFilme.filtrarFilme(nome,diretor);
		
		System.out.println("Nome filme:"+nome+"\nDiretor: "+diretor);
		
		model.addAttribute("filmes", filme);
		
		return "listaDeFilmes/1";
		
	}
}
