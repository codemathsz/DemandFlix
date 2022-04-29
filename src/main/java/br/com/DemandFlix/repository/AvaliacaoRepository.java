package br.com.DemandFlix.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import br.com.DemandFlix.model.Avaliacao;

@Repository
public interface AvaliacaoRepository extends PagingAndSortingRepository<Avaliacao, Long	>{

}
