package com.blog.bloghanna.model.dtos;

import javax.validation.constraints.NotBlank;

/**
 * Classe DTO utilizada para atualizar dados do usuario
 * 
 * @author hanely
 *
 */

public class UsuarioDtos {
	@NotBlank(message = "não pode ser branco ou nulo")
	private boolean maiorDeIdade;
	private String senha;

	public boolean isMaiorDeIdade() {
		return maiorDeIdade;
	}

	public void setMaiorDeIdade(boolean maiorDeIdade) {
		this.maiorDeIdade = maiorDeIdade;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

}
