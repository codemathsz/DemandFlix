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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
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
		
		model.addAttribute("genero", repositoryGenero.findAllByOrderByNomeGeneroAsc());
		
		return "dashboard/filme/formulario";
	}
	
//	************************** FECHANDO O MÉTODO QUE LEVA PARA O FORMULÁRIO *******************************************
	
	
	
	
	
	
	@RequestMapping(value = "salvar", method = RequestMethod.POST)
	public String salvarFilme(@Valid Filme filme, @RequestParam("fileFotos") MultipartFile[] fileFotos) {
		
		//		 FAZER OS VETORES DO GENERO
		System.out.println(fileFotos.length);
		
		repositoryFilme.save(filme);
		
		return "redirect:cadastroFilmes";
	}
//	************************** FECHANDO O MÉTODO SALVA UM NOVO FILME  *******************************************	
	

	
	
	
	
	
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
		
		
		return "dashboard/filme/listaFilme";
	}
//	************************** FECHANDO O MÉTODO LISTA UM FILME  *******************************************	
	
	
	
	
	
	
	
	@RequestMapping("alterarFilme")
	public String alterarFilme(Long id, Model model) {
		
		Filme filme = repositoryFilme.findById(id).get();
		model.addAttribute("filmes", filme);
		
		return "forward:cadastroFilmes";
	}
//	************************** FECHANDO O MÉTODO QUE ALTERA UM FILME  *******************************************	
	
	
	
	
	
	
	@RequestMapping("deletarFilme")
	public String deletarFilme(Long id) {
		
		repositoryFilme.deleteById(id);
		
		return "forward:listaDeFilmes/1";
	}
//	************************** FECHANDO O MÉTODO DELETA  FILME  *******************************************	
	
	
	
	
	
	
}
