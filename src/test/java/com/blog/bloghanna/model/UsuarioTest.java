package com.blog.bloghanna.model;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;


@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class UsuarioTest {
	public Usuario usuario;

	/*
	 * Injeta um Objeto da Classe Validator, responsável pela Validação dos
	 * Atributos da Model
	 */
	
	//private Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
	@Autowired
	private ValidatorFactory validatorf = Validation.buildDefaultValidatorFactory();
	Validator validator = validatorf.getValidator();

	@BeforeEach
	public void star() {
		usuario = new Usuario("nome", "sobrenome", "email@email.com", "senha"); // atributos que são notNull
	}

	@Test
	void testValidacaoAtributos() {
		// Armazena a lista de Mensagens de Erros de Validação da Model
		Set<ConstraintViolation<Usuario>> violacao = validator.validate(usuario);
		// O Teste só passará se a Lista de Erros estiver vazia!
		assertTrue(violacao.isEmpty());
		// Exibe as Mensagens de Erro se existirem
		System.out.println(violacao.toString());
	}
}
