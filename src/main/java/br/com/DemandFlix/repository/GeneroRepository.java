package br.com.DemandFlix.repository;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import br.com.DemandFlix.model.Genero;
@Repository
public interface GeneroRepository extends PagingAndSortingRepository<Genero, Long>{

	public List<Genero> findAllByOrderByNomeGeneroAsc();
}
