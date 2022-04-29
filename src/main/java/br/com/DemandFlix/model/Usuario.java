package br.com.DemandFlix.model;

import javax.annotation.Generated;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import br.com.DemandFlix.util.HashUtil;
import lombok.Data;

@Entity
@Data
public class Usuario {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String nome;
	@Column(unique = true)
	private String email;
	@JsonProperty(access = Access.WRITE_ONLY)// Access.WRITE_ONLY , ACESSO SOMENTE ESCRITA,OU SEJA ACESSO SÓ PARA O setSenha e NÃO PARA o getSenha
	private String senha;
	
	public void setSenha(String senha) {
		
		this.senha = (HashUtil.hash(senha));
	}
}
