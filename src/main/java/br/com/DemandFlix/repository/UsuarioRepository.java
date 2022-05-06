package br.com.DemandFlix.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import br.com.DemandFlix.model.Usuario;

@Repository
public interface UsuarioRepository  extends PagingAndSortingRepository<Usuario, Long>{

	// BUSCAR USUARIO NO BANCO
	public Usuario findByEmailAndSenha(String email, String senha);
}
