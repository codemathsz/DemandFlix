package br.com.DemandFlix.model;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

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
	private String foto;
	@Column(columnDefinition = "text")
	private String descricao;
	@ManyToOne //	UM FILME TEM MUITOS GENEROS
	private Genero genero;
	
	// METODO QUE RETORNA AS FOTOS NA FORMA DE UM VETOR STRING
	public String[] verFotos() {
		
		return foto.split(";");
	}
	
	
}
