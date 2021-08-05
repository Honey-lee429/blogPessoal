package com.blog.bloghanna.model;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
/**
 * Classe para fazer relação Many to Many
 */
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
public class UsuarioGrupo {
	@Id
	@GeneratedValue(strategy =  GenerationType.IDENTITY)
	private Long idGrupo;
	
	@NotNull (message = "Campo não pode ser nulo")
	private String nome;
	
	@NotNull (message = "Campo não pode ser nulo")
	private String descricao;	
	
	@ManyToOne
	@JoinColumn(name = "fk_usuario")
	@JsonIgnoreProperties({"gruposCriados", "tema", "texto", "maiorDeIdade"})
	private Usuario criador;
	
	@ManyToMany(mappedBy = "grupoInscrito",  cascade = CascadeType.ALL)
	private List<Usuario> usuariosInscritos = new ArrayList<>();

	//metodos especiais
	public Long getIdGrupo() {
		return idGrupo;
	}

	public void setIdGrupo(Long idGrupo) {
		this.idGrupo = idGrupo;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Usuario getCriador() {
		return criador;
	}

	public void setCriador(Usuario criador) {
		this.criador = criador;
	}

	public List<Usuario> getUsuariosInscritos() {
		return usuariosInscritos;
	}

	public void setUsuariosInscritos(List<Usuario> usuariosInscritos) {
		this.usuariosInscritos = usuariosInscritos;
	}
	
}
