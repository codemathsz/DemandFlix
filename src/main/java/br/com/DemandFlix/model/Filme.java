package br.com.DemandFlix.model;


import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import org.checkerframework.checker.units.qual.UnitsRelations;
import org.springframework.beans.factory.annotation.Autowired;

import br.com.DemandFlix.repository.FilmeRepository;
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
	@ManyToMany //	UM FILME TEM MUITOS GENEROS
	private List<Genero> genero;
	
	// METODO QUE RETORNA AS FOTOS NA FORMA DE UM VETOR STRING
	public String[] verFotos() {
		
		return foto.split(";");
	}
	
	
}
