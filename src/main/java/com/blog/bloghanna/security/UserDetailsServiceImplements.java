package com.blog.bloghanna.security;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.blog.bloghanna.model.Usuario;
import com.blog.bloghanna.repository.UsuarioRepository;

@Service
public class UserDetailsServiceImplements implements UserDetailsService {
	
	@Autowired
	public UsuarioRepository repositoryU;
	
	@Override
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
		//Entra no respositoy, pesquisar o email e retornar esse objeto para o user
		Optional<Usuario> usuarioExistente = repositoryU.findByEmail(userName);
		usuarioExistente.orElseThrow(() -> new UsernameNotFoundException(userName + " not found."));
		
		return usuarioExistente.map(UserDetailImplements::new).get();
		
		//método alternativo Boaz
//		if(usuarioExistente.isPresent()) {
//			return new UserDetailImplements(usuarioExistente.get());
//		} else {
//			throw new UsernameNotFoundException(username + "não existe.");
//		}
		
	}

}
