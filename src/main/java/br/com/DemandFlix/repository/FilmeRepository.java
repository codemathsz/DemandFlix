package br.com.DemandFlix.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.DemandFlix.model.Filme;
import br.com.DemandFlix.model.Genero;
@Repository
public interface FilmeRepository extends PagingAndSortingRepository<Filme, Long>{
	
	@Query("SELECT f FROM Filme f WHERE f.nomeFilme LIKE %:nome% ")
	public List<Filme> filtrarFilme(@Param("nome") String nome);

	@Query("SELECT f FROM Filme f WHERE f.genero = :idGenero")
	public Iterable<Filme> findByGenero(@Param("idGenero") Long idGenero);
	
}
 