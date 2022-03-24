package br.com.DemandFlix.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
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
			
			// REDIRECIONA UMA MENSGEM DE ERRO
			attr.addFlashAttribute("msgErro", "Campo do Nome está vazio, insira um nome");
			
			// REDIRECIONA PARA O FORMULÁRIO
			return "redirect:formularioAdmin";
		}else if (result.hasFieldErrors("email")) {
			
			// REDIRECIONA UMA MENSGEM DE ERRO
			attr.addFlashAttribute("msgErro", "Campo do Nome está vazio, insira um nome");
			
			// REDIRECIONA PARA O FORMULÁRIO
			return "redirect:formularioAdmin";
		}else if(result.hasFieldErrors("senha")) {
			
			// REDIRECIONA UMA MENSGEM DE ERRO
			attr.addFlashAttribute("msgErro", "Campo do Nome está vazio, insira um nome");
			
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
}



