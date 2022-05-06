package br.com.DemandFlix.model;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import lombok.Data;


@Data
@Entity
public class Avaliacao {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private double nota;
	@Column(columnDefinition = "TEXT")
	private String comentario;
	
	@JsonFormat(pattern = "dd-MM-yyyy")
	private Calendar dataVisita;
	@JsonProperty(access = Access.WRITE_ONLY)// só vai usar a propriedade filme para fazer set, fizemos isso por que no Json ficou um loop por que o filme tem um avaliação e a avaliação tem um filme
	@ManyToOne
	private Filme filme;
	@ManyToOne
	private Usuario usuario;
	
	
}
	