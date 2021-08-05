package com.blog.bloghanna.controller;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.blog.bloghanna.model.Usuario;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UsuarioControllerTest {

	@Autowired
	private TestRestTemplate testRestTemplate;

	private Usuario usuario;
	private Usuario usuarioAlterar;

	@BeforeAll
	public void star() {
		usuario = new Usuario("Mari", "menezes", "mari@email.com", "123");
		usuarioAlterar = new Usuario("Joana", "Ishikawa", "joana@email.com", "123");
	}
	
	@Disabled
	@Test
	void deveRealizarPostUsuario() {
		/*
		 * Criando um objeto do tipo HttpEntity para enviar como terceiro parâmentro do
		 * método exchange. (Enviando um objto contato via body)
		 * 
		 */
		HttpEntity<Usuario> request = new HttpEntity<Usuario>(usuario);

		ResponseEntity<Usuario> resposta = testRestTemplate.exchange("/ususario/cadastrar", HttpMethod.POST, request,
				Usuario.class);
		assertEquals(HttpStatus.CREATED, resposta.getStatusCode());
	}

	@Disabled
	@Test
	void deveMostrarTodosUsuarios() {
		ResponseEntity<String> resposta = testRestTemplate.exchange("/usuario", HttpMethod.GET, null, String.class);
		assertEquals(HttpStatus.OK, resposta.getStatusCode());
	}

	@Disabled
	@Test
	void deveRealizarPutContatos() {

		HttpEntity<Usuario> request = new HttpEntity<Usuario>(usuarioAlterar);

		ResponseEntity<Usuario> resposta = testRestTemplate.exchange("/usuario/logar", HttpMethod.PUT, request,
				Usuario.class);
		assertEquals(HttpStatus.OK, resposta.getStatusCode());

	}

	@Disabled
	@Test
	void deveRealizarDeleteUsuarios() {

		/*
		 * O Contato com Id 1 será apagado somente ele existir no Banco. Caso contrário,
		 * o teste irá falhar!
		 * 
		 */
		ResponseEntity<String> resposta = testRestTemplate.exchange("/usuario/1", HttpMethod.DELETE, null,
				String.class);
		assertEquals(HttpStatus.OK, resposta.getStatusCode());

	}

}
