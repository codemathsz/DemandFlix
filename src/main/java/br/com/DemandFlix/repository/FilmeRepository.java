package br.com.DemandFlix.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import br.com.DemandFlix.model.Filme;

public interface FilmeRepository extends PagingAndSortingRepository<Filme, Long>{
	
	@Query("SELECT f FROM Filme f WHERE f.nomeFilme LIKE %:nome%  OR f.diretor LIKE %:diretor%")
	public List<Filme> filtrarFilme(@Param("nome") String nome, @Param("diretor") String diretor);

}
 