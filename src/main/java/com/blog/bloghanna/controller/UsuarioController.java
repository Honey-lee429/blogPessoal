package com.blog.bloghanna.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.blog.bloghanna.model.Usuario;
import com.blog.bloghanna.model.UsuarioGrupo;
import com.blog.bloghanna.model.dtos.UserLoginDto;
import com.blog.bloghanna.model.dtos.UsuarioDtos;
import com.blog.bloghanna.repository.UsuarioRepository;
import com.blog.bloghanna.service.UsuarioService;

@RestController
@RequestMapping("/usuario")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class UsuarioController {
	@Autowired
	private UsuarioRepository repository;

	@Autowired
	private UsuarioService service;
	
//	@Autowired
//	private UsuarioService usuarioService;

	@GetMapping
	public ResponseEntity<List<Usuario>> listar() {
		// return repository.findAll();
		List<Usuario> listaDeUsuarios = repository.findAll();

		if (listaDeUsuarios.isEmpty()) {
			return ResponseEntity.status(204).build();
		} else {
			return ResponseEntity.status(200).body(listaDeUsuarios);
		}
	}

	@GetMapping("/{id}") // Quando quermos passar um valor pela URI (URL), recepcionamos o valor através
							// da anotação @PathVariable
	public ResponseEntity<Usuario> getId(@PathVariable Integer id) {
		return repository.findById(id).map(resposta -> ResponseEntity.ok(resposta))
				.orElse(ResponseEntity.notFound().build()); // caso o usuario insira um valor que não existe no bco.
															// dados retorna status 404 not found
	}

	@GetMapping("/nome/{name}") // não pode deixar somente {nome} pq o PathVariable não entende que são
								// atributos diferentes, desta forma daria duplicidade.
	public ResponseEntity<List<Usuario>> getNome(@PathVariable String name) {
		return ResponseEntity.ok(repository.findAllByNameContainingIgnoreCase(name));
	}

	@GetMapping("/teste")
	public String escreva() {
		return "olá, mundo!";
	}

//	@PostMapping //método direto
//	@ResponseStatus(HttpStatus.CREATED) // método post recebe status 201
//	public Usuario adicionar(@Valid @RequestBody Usuario usuario) { // @ResquestBody informa que a tabela que inserimos
//																	// no Postman será transformado em objeto usuario
//																	// pelo Java
//		return repository.save(usuario);
//	}
	/**
	 * 
	 * Post do usuario sem segurança
	 * 
	 * @PostMapping // método do UsuarioService public ResponseEntity<Object>
	 *              adicionar(@Valid @RequestBody Usuario usuario) { return
	 *              service.cadastrarUsuario2(usuario); }
	 */

	@PostMapping("/logar")
	public ResponseEntity<UserLoginDto> authentication(@Valid @RequestBody Optional<UserLoginDto> user){
		return service.logar(user).map(resp -> ResponseEntity.ok(resp))
				.orElse(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
	}
	
	@PostMapping("/cadastrar")
	public ResponseEntity<Usuario> cadastro(@RequestBody Usuario usuario) {
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(service.CadastrarUsuario(usuario));
	}
	
//	public Optional<?> adicionar2(Usuario novoUsuario) {
//		return repository.findById(novoUsuario.getId()).map(produtoExistente -> {
//			return Optional.empty();
//		}).orElseGet(() -> {
//			return Optional.ofNullable(repository.save(novoUsuario));
//		});
//	}

//	@PutMapping
//	public ResponseEntity<@Valid Usuario> alterar(@Valid @RequestBody Usuario usuario) { //@ResquestBody informa que a tabela que inserimos no Postman será transformado em objeto usuario pelo Java
//		return ResponseEntity.status(HttpStatus.OK).body(repository.save(usuario)); //Sempre quando formos realizar um Post/Put enviaremos os dados via body
//	}

	@PutMapping("/{id}/atualizar")
	public ResponseEntity<Usuario> alterar(@Valid @PathVariable(value = "id") Integer id,
			@RequestBody UsuarioDtos usuarioParaAtualizar) {
		return service.atualizarUsuario(id, usuarioParaAtualizar)
				.map(usuarioAtualizado -> ResponseEntity.status(201).body(usuarioAtualizado))
				.orElse(ResponseEntity.status(400).build());
	}

	@DeleteMapping("/{id}")
	public void delete(@PathVariable Integer id) {
		repository.deleteById(id); // no momento que puxar o delte no postman ele não retornará nada
	}

	/**
	 * 
	 * @param nome, para encontrar nomes que contenha um ou mais caracteres
	 *              específico @return, retorna os nomes que contem o caracter de
	 *              pesquisa
	 */
	@GetMapping("/pesquisa")
	public ResponseEntity<List<Usuario>> searchName(@RequestParam String nome) {
		return ResponseEntity.status(200).body(repository.findAllByNameContainingIgnoreCase(nome));
	}

	@PostMapping("/{id}/criar/grupo")
	public ResponseEntity<UsuarioGrupo> criarGrupo(@PathVariable(value = "id") Integer id,
			@Valid @RequestBody UsuarioGrupo CriandoGrupo) {
		return service.criarGrupo(id, CriandoGrupo)
				.map(grupoCriado -> ResponseEntity.status(HttpStatus.CREATED).body(grupoCriado))
				.orElse(ResponseEntity.badRequest().build());
	}

	@PutMapping("/inscrever/usuario/{id}/grupo/{id_grupo}")
	public ResponseEntity<Usuario> adicionarUsuarioNoGrupo(@PathVariable(value = "id") Integer id,
			@PathVariable(value = "id_grupo") Long id_grupo) {
		return service.inscreverNoGrupo(id, id_grupo)
				.map(usuarioInscrito -> ResponseEntity.status(201).body(usuarioInscrito))
				.orElse(ResponseEntity.badRequest().build());
	}

	@DeleteMapping("/sair/usuario/{id}/grupo/{id_grupo}")
	public ResponseEntity<Usuario> excluirUsuarioDoGrupo(@PathVariable(value = "id") Integer id,
			@PathVariable(value = "id_grupo") Long id_grupo) {
		return service.sairDoGrupo(id, id_grupo)
				.map(usuarioExcluido -> ResponseEntity.status(201).body(usuarioExcluido))
				.orElse(ResponseEntity.badRequest().build());
	}
}
