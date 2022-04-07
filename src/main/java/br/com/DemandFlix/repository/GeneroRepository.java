package br.com.DemandFlix.repository;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

import br.com.DemandFlix.model.Genero;

public interface GeneroRepository extends PagingAndSortingRepository<Genero, Long>{

	public List<Genero> findAllByOrderByNomeGeneroAsc();
}
