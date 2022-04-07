package br.com.DemandFlix.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import br.com.DemandFlix.model.Administrador;


												// PARA PAGINAÇÃO
public interface AdministradorRepository extends PagingAndSortingRepository<Administrador, Long>{

	@Query("SELECT a FROM Administrador a WHERE a.nome LIKE %:n%")// LINGUAGEM JPQL, LINGUAGEM DE CONSULTA DO JAVA, c variavel
	public List<Administrador> buscarPorNome(@Param("n") String nome);
}
