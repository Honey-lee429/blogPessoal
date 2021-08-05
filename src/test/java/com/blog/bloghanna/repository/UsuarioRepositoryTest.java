package com.blog.bloghanna.repository;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

import com.blog.bloghanna.model.Usuario;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UsuarioRepositoryTest {

	@Autowired
	private UsuarioRepository repositoryU;

	/**
	 * O método start(), anotado com a anotação @BeforeAll, inicializa 3 objetos do
	 * tipo contato e executa em todos o método método findFirstByNome() para
	 * verificar se existe o contato antes de salvar no banco de dados
	 */

	@BeforeAll
	void star() {
		Usuario usuario = new Usuario("noem", "sobrenome", "neom@email.com", "senha");
		if (repositoryU.findByEmail(usuario.getEmail()) != null) {
			repositoryU.save(usuario);
		}

		usuario = new Usuario("novo noem", "novo sobrenome", "email@email.com", "nova senha");
		if (repositoryU.findByEmail(usuario.getEmail()) != null) {
			repositoryU.save(usuario);
		}

		usuario = new Usuario("noem mais antigo", "sobrenome mais antigo", "joe@email.com", "senha antiga");
		if (repositoryU.findByEmail(usuario.getEmail()) != null) {
			repositoryU.save(usuario);
		}
	}

	/**
	 * o teste verifica se existe algum contato cujo nome seja “chefe”, através da
	 * assertion AssertTrue(). Se existir, passa no teste.
	 * 
	 */
	@Test
	public void findByEmail() throws Exception {
		Usuario usuario = repositoryU.findByEmail("neom@email.com").get();
		assertTrue(usuario.getEmail().equals("neom@email.com"));
	}

	/**
	 * o teste verifica se existe algum contato cujo nome seja “chefe”, através da
	 * assertion AssertTrue(). Se existir, passa no teste.
	 * 
	 */
	@Test
	public void findAllByNomeIgnoreCaseRetornaTresContato() {

		List<Usuario> usuario = repositoryU.findAllByNameContainingIgnoreCase("noem");

		assertEquals(3, usuario.size());
	}

	@AfterAll
	public void end() {
		repositoryU.deleteAll();
	}

}
