package br.com.DemandFlix.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import br.com.DemandFlix.model.Administrador;

												// PARA PAGINAÇÃO
public interface AdministradorRepository extends PagingAndSortingRepository<Administrador, Long>{

	
}
