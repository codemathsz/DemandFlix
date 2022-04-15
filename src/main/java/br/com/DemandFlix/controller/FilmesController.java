package br.com.DemandFlix.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
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
import br.com.DemandFlix.model.Filme;
import br.com.DemandFlix.repository.FilmeRepository;
import br.com.DemandFlix.repository.GeneroRepository;
import br.com.DemandFlix.util.FireBaseUtil;


@Controller
public class FilmesController {

	@Autowired
	private FilmeRepository repositoryFilme;
	
	@Autowired
	private GeneroRepository repositoryGenero;
	
	@Autowired
	private FireBaseUtil fireUtil;
	
	@RequestMapping("cadastroFilmes")
	public String cadastraFilme(Model model) {
		
		model.addAttribute("genero", repositoryGenero.findAllByOrderByNomeGeneroAsc());
		
		return "dashboard/filme/formulario";
	}
	
//	************************** FECHANDO O MÉTODO QUE LEVA PARA O FORMULÁRIO *******************************************
	
	
	
	
	 
	
	@RequestMapping(value = "salvar", method = RequestMethod.POST)
	public String salvarFilme(@Valid Filme filme, @RequestParam("fileFotos") MultipartFile[] fileFotos) {
		
		// STRING PARA ARMAZENAR AS URL's
		String fotos = filme.getFoto();
		
		// FOR PARA PASSAR POR CADA MultipartFile QUE EU TIVER
		for(MultipartFile arquivo : fileFotos) {
			
			// VERIFICANDO SE O ARQUIVO ESTÁ FAZIO, EXISTIR ELE SEMPRE VAI EXISTIR
			if(arquivo.getOriginalFilename().isEmpty()) {
				
				// COLOCAR UM CONTINUE PARA IR PARA O PROXIMO ARQUIVO
				continue;
			}
			
			try {
				
				// FAZ O UPLOAD E GUARDA A URL NA STRING FOTOS
				fotos += fireUtil.upload(arquivo)+";";
			} catch (IOException e) {
			
				e.printStackTrace();
				throw new RuntimeException(e);
			}
		}
		
		//		 FAZER OS VETORES DO GENERO
		System.out.println(fileFotos.length);
		
		// GUARDAR NA VARIAVEL RESTAURANTE AS FOTOS
		filme.setFoto(fotos);
		// SALVAR NO BD
		repositoryFilme.save(filme);
		
		return "redirect:cadastroFilmes";
	}
//	************************** FECHANDO O MÉTODO SALVA UM NOVO FILME  *******************************************	
	

	
	
	
	
	
	@RequestMapping("listaDeFilmes/{pagesFilme}")
	public String listarFilmes(Model model, @PathVariable("pagesFilme") int pagesFilme, Filme fotos) {
		
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
		
		Filme filme = repositoryFilme.findById(id).get();
		
		if(filme.getFoto().length() > 0) {
			for(String foto : filme.verFotos()) {
				fireUtil.deletarArq(foto);
			}
		}
		
		repositoryFilme.delete(filme);
		
		return "forward:listaDeFilmes/1";
	}
//	************************** FECHANDO O MÉTODO DELETA  FILME  *******************************************	
	
	
	@RequestMapping("excluirFotos")
	public String excluirFoto(Long idFilm, int numFoto, Model model) {
		
		// BUSCAR O FILME NO BANCO
		Filme film = repositoryFilme.findById(idFilm).get();
		
		// BUSCA A URL NO BD
		String urlFoto = film.verFotos()[numFoto];
		
		// DELETAR A FOTO
		fireUtil.deletarArq(urlFoto);
		
		// REMOVER A URL DO ATRIBUTO FOTO
		film.setFoto(film.getFoto().replace(urlFoto+";","" ));// 	replace(urlFoto+";","" ) ARRACANDO AS FOTOS
		
		
		repositoryFilme.save(film);
		
		model.addAttribute("filmes", film);
		
		return "forward:cadastroFilmes";
	}
	
	
	
}
