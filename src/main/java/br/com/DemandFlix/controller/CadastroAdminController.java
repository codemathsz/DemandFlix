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
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.DemandFlix.model.Administrador;
import br.com.DemandFlix.repository.AdministradorRepository;


// ****** BindingResult ******** 
//BindingResult contém o resultado de uma validação e associação e contém erros que podem ter ocorrido. O BindingResult deve vir logo após o objeto de modelo que é validado ou o Spring falha ao validar o objeto e lança uma exceção.


@Controller
public class CadastroAdminController {

	@Autowired
	AdministradorRepository repository;
	
	@RequestMapping("formularioAdmin")
    public String formAdmin() {
        
		return "cadastroAdm/cadastroAdmin";	
    }
	
	
	@RequestMapping(value = "salvarAdmin", method = RequestMethod.POST) //	 REQUEST MAPING PARA SALVAR O ADMINISTRADOR, DO TIPO POST
	public String salvarAdministrador(@Valid Administrador administrador, BindingResult result, RedirectAttributes attr) { // 	@Valid VALIDAR A VARIAVEL, NOT NUL...
		
		// VERIFICA SE HOUVERAM ERROS NA VALIDAÇÃO
		// result.hasErrors(), SE HOUVE UM ERRO
		//	hasFieldErrors USANDO PARA MANDAR UMA MENSAGEM EXATA
		if(result.hasFieldErrors("nome")) {
			
			// ADD UMA MENSGEM DE ERRO
			attr.addFlashAttribute("msgErro", "Campo do Nome está vazio, insira um nome");
			
			// ADD UMA MENSGEM DE ERRO
			return "redirect:formularioAdmin";
		}else if (result.hasFieldErrors("email")) {
			
			// ADD UMA MENSGEM DE ERRO
			attr.addFlashAttribute("msgErro", "Campo do Email está vazio, insira um email");
			
			// REDIRECIONA PARA O FORMULÁRIO
			return "redirect:formularioAdmin";
		}else if(result.hasFieldErrors("senha")) {
			
			// ADD UMA MENSGEM DE ERRO
			attr.addFlashAttribute("msgErro", "Campo da Senha está vazio, insira uma senha");
			
			// REDIRECIONA PARA O FORMULÁRIO
			return "redirect:formularioAdmin";
		}
		
		try {
			
			// SALVA NO BD A ENTIDADE
			repository.save(administrador);
			
			// ADD UM MENSAGEM DE SUCESSO
			attr.addFlashAttribute("msgSucesso","Administrador cadastrado com sucesso!! ID: "+administrador.getId());
			
		}catch (Exception e) {
			// ADD UMA MENSAGEM DE ERRO
			attr.addFlashAttribute("msgErro","Ocorreu um erro ao cadastrar: "+e.getMessage());
		}
		
		
		
		
		return "redirect:formularioAdmin";
	}
	
	
	
						// /{page} PAGINAÇÃO, NA REQUEST , TEM QUE FALAR O NUMERO DA PAGINA QUE QUEREMOS ACESAR
	@RequestMapping("listaAdmin/{page}")
	public String listaAdmin(Model model,@PathVariable("page") int page) {//	 int page, NÃO VINCULA DIRETO COM O PAGE DO REQUEST, COLOCANDO O @PathVariable ELE VINCULAR E O NOME DO INT PODE SER QLQR UM	
		
		// OBTER O REGISTRO DE ACORDO CO O NUMERO DE PAGINAS
		
		// CRIA UM PAGEABLE INFORMANDO OS PARÂMETROS DA PÁGINA   | -1 O MENOS UM É POR QUE COMEÇA A CONTAR DO ZERO
		PageRequest pageable = PageRequest.of(page-1, 6, Sort.by(Sort.Direction.ASC, "nome"));//	 (Sort.Direction.ASC, "nome") ORDENANDO PELO NOME
		
		// CRIAR UM APGE DE ADM, ATRA´VES DOS PARÂMETROS PASSADOS AO REPOSITORY
		Page<Administrador> pagina = repository.findAll(pageable);// 	 ESSA LINHA JÁ GERA UM SELECT COM LIMITE
		
		// ADD A MODEL A LISTA COM OS ADMS
		model.addAttribute("admins",pagina.getContent());// COLOCAMOS NA MODEL OS ADM, SOMETE DA PÁGINA ATUAL, A PÁGINA QUE CHAMOU
		
		// VAMOS CRIAR UM VETOR DE INTEIROS E VAMOS POPULAR
		
		int totalPages = pagina.getTotalPages(); // VARIÁVEL PARA O TOTAL DE PÁGINAS
		
		// CRIA UM LIST  DE INTEIROS PARA ARMEZENA OS NºS DAS PÁGINAS
		List<Integer> numPaginas = new ArrayList<Integer>();
		
		// PREENCHER O LIST COM AS PÁGIAS
		for(int i = 1; i <= totalPages; i++) {
			numPaginas.add(i);
		}
		
		// ADD A PÁGINA AO LIST
					model.addAttribute("numPaginas",numPaginas);
					model.addAttribute("totalPaginas", totalPages);
					model.addAttribute("paginaAtual", page);
		
		// RETORNA PARA O HTML DA LISTA
		return "listaAdm/lista";
	}
	
	
	@RequestMapping("excluirAdministrador")
	public String excluirAdm(Long id) {
		
		repository.deleteById(id);;
		
		return "redirect:listaAdmin/1";
		
	}
	
	
	@RequestMapping("alterarAdm")
	public String alterarAdm(Long id, Model model) {
		
		Administrador adm = repository.findById(id).get();
		model.addAttribute("admins", adm);
		
		return "forward:formularioAdmin";
		
	}
}



