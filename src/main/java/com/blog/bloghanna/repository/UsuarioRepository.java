package com.blog.bloghanna.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.blog.bloghanna.model.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
	public List<Usuario> findAllByNameContainingIgnoreCase(String name);

	public Optional<Usuario> findByEmail(String email);
	public Optional<Usuario> findByNameContaining(String name);

	//public Usuario findByNameContaining(String name);
}
