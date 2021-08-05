package com.blog.bloghanna.controller;

import java.util.List;

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
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.blog.bloghanna.model.Tema;
import com.blog.bloghanna.repository.TemaRepository;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/tema")
public class TemaController {

	@Autowired
	private TemaRepository apelido;

	@GetMapping
	public ResponseEntity<List<Tema>> getAll() {
		return ResponseEntity.ok(apelido.findAll());
	}

	@GetMapping("/{id_tema}")
	public ResponseEntity<Tema> getById(@PathVariable long id_tema) {
		return apelido.findById(id_tema).map(resposta -> ResponseEntity.ok(resposta))
				.orElse(ResponseEntity.notFound().build());
	}

	@GetMapping("/nome/{nome}")
	public ResponseEntity<List<Tema>> getByName(@PathVariable String nome) {
		return ResponseEntity.ok(apelido.findAllByDescricaoContainingIgnoreCase(nome));
	}

	@PostMapping
	public ResponseEntity<Tema> post(@Valid @RequestBody Tema tema) {
		return ResponseEntity.status(HttpStatus.CREATED).body(apelido.save(tema));
	}

	@PutMapping
	@ResponseStatus(HttpStatus.OK)
	public Tema change(@Valid @RequestBody Tema tema) {
		return apelido.save(tema);
	}

	@DeleteMapping("/{id_tema}")
	public void delete(@PathVariable long id_tema) {
		apelido.deleteById(id_tema);
	}
}
