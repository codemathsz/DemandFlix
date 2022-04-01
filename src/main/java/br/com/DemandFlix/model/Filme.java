package br.com.DemandFlix.model;


import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import javax.persistence.OneToMany;

import lombok.Data;



@Entity
@Data
public class Filme {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idFilme;
	private String nomeFilme;
	private String diretor;
	private String anoGravacao;
	private String faixaIdade;
	private String elenco;
	private String palavraChave;
	@Column(columnDefinition = "text")
	private String descricao;
	@OneToMany // MUITOS PARA MUITOS, UM FILME PODE TER MAIS DE UM GENERO E UM GENERO PODE TER MAIS DE UM FILME
	private List<Genero> genero;
}
