package br.com.DemandFlix.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Email;

import javax.validation.constraints.NotEmpty;

import lombok.Data;

@Data // GETTERS E SETTERS
@Entity // MAPEIA A ENTIDADE PARA O JPA
public class Administrador {

	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@NotEmpty  
	@Column(columnDefinition = "VARCHAR(55)")// VARCHAR COM TAMANHO 55
	private String nome;
	@Column(unique = true,columnDefinition = "VARCHAR(55)")// COLUNA UNICA, VARCHAR COM TAMANHO 55
	@Email
	private String email;
	@NotEmpty
	private String senha;
	
}
