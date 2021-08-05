package com.blog.bloghanna.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "blog")
public class Usuario {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@NotNull(message = "Nome é obrigatório")
	private String name;
	
	@NotNull(message = "Sobrenoma é obrigatório")
	private String sobrenome;
	
	@NotBlank
	@Email
	private String email;
	
	@NotBlank
	private String senha;
	
	private String foto; 
	
	private String tipo;
	

	private boolean maiorDeIdade;
	
	@Size(max = 140)
	private String texto;
	
	/**
	 * Tabela Usuario recebeu a FK de tb_tema
	 */
	@ManyToOne 
	@JsonIgnoreProperties("usuario") //usuario que está entro da classe Tema
	private Tema tema;
	
	/**
	 * Tabela usuario_grupo recebeu a FK de Usuario
	 */
	@OneToMany(mappedBy = "criador",  cascade = CascadeType.REMOVE)
	@JsonIgnoreProperties({"criador", "usuariosInscritos"})
	private List<UsuarioGrupo> gruposCriados = new ArrayList<>();
	
	/**
	 * 
	 */
	@ManyToMany(cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
	@JoinTable(
			name = "tb_manytomany",
			joinColumns = @JoinColumn(name = "fk_usuario"),
			inverseJoinColumns = @JoinColumn(name= "fk_grupo"))
	@JsonIgnoreProperties({"usuariosInscritos", "grupoInscrito", "criador"})
	private List<UsuarioGrupo> grupoInscrito = new ArrayList<>();
	
	
	
	public Usuario(@NotNull(message = "Nome é obrigatório") String name, @NotNull(message = "Sobrenome é obrigatório")
	String sobrenome, @NotBlank @Email String email,
			@NotBlank String senha) {
		super();
		this.name = name;
		this.sobrenome = sobrenome;
		this.email = email;
		this.senha = senha;
	}
	
	

	public Usuario() {
		super();
	}



	//special methods 
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSobrenome() {
		return sobrenome;
	}

	public void setSobrenome(String sobrenome) {
		this.sobrenome = sobrenome;
	}

	public boolean isMaiorDeIdade() {
		return maiorDeIdade;
	}

	public void setMaiorDeIdade(boolean maiorDeIdade) {
		this.maiorDeIdade = maiorDeIdade;
	}

	public String getTexto() {
		return texto;
	}

	public void setTexto(String texto) {
		this.texto = texto;
	}

	public Tema getTema() {
		return tema;
	}

	public void setTema(Tema tema) {
		this.tema = tema;
	}

	public List<UsuarioGrupo> getGruposCriados() {
		return gruposCriados;
	}

	public void setGruposCriados(List<UsuarioGrupo> gruposCriados) {
		this.gruposCriados = gruposCriados;
	}

	public List<UsuarioGrupo> getGrupoInscrito() {
		return grupoInscrito;
	}

	public void setGrupoInscrito(List<UsuarioGrupo> grupoInscrito) {
		this.grupoInscrito = grupoInscrito;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}
	
	public String getFoto() {
		return foto;
	}



	public void setFoto(String foto) {
		this.foto = foto;
	}



	public String getTipo() {
		return tipo;
	}


	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
}
