package com.blog.bloghanna.service;

import java.nio.charset.Charset;
import java.util.List;
import java.util.Optional;

import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.blog.bloghanna.model.Usuario;
import com.blog.bloghanna.model.UsuarioGrupo;
import com.blog.bloghanna.model.dtos.UserLoginDto;
import com.blog.bloghanna.model.dtos.UsuarioDtos;
import com.blog.bloghanna.repository.UsuarioRepository;
import com.blog.bloghanna.repository.GrupoRepository;

@Service
public class UsuarioService {
	private @Autowired UsuarioRepository repositoryU;
	private @Autowired GrupoRepository repositoryG;

	/**
	 * metodo para cadastrar usuario sem segurança
	 * 
	 * @param novoUsuario do tipo usuario
	 * @return optional com usuario ou optional empty
	 * @since 1.0
	 * @author hanely
	 * 
	 *         public Optional<?> cadastrarUsuario(Usuario novoUsuario) { return
	 *         repositoryU.findById(novoUsuario.getId()).map(usuarioExistente -> {
	 *         return Optional.empty(); }).orElseGet(() -> { return
	 *         Optional.ofNullable(repositoryU.save(novoUsuario)); }); }
	 * 
	 * 
	 *         public ResponseEntity<Object> cadastrarUsuario2(Usuario novoUsuario)
	 *         { return
	 *         repositoryU.findById(novoUsuario.getId()).map(usuarioExistente -> {
	 *         return ResponseEntity.notFound().build(); }).orElseGet(() -> { return
	 *         ResponseEntity.status(201).body(repositoryU.save(novoUsuario)); }); }
	 * 
	 *         public Optional<Object> cadastrarUsuario3(Usuario novoUsuario) {
	 *         Optional<Usuario> objetoOptional =
	 *         repositoryU.findById(novoUsuario.getId()); if
	 *         (objetoOptional.isPresent()) { return Optional.empty(); } else {
	 *         return Optional.ofNullable(repositoryU.save(novoUsuario)); } }
	 */

	public Usuario CadastrarUsuario(Usuario usuario) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		String senhaEncoder = encoder.encode(usuario.getSenha());
		usuario.setSenha(senhaEncoder);
		return repositoryU.save(usuario);
	}
	
	//forma alternativa de criar cadastrarUsuario
	public Optional<Object> cadastrarUsuario2(Usuario novoUsuario){
		return repositoryU.findByEmail(novoUsuario.getEmail()).map(usuarioExistente -> {
			return Optional.empty();
		}).orElseGet(() -> {
			BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
			String senhaCriptografada = encoder.encode(novoUsuario.getSenha());
			novoUsuario.setSenha(senhaCriptografada);
			return Optional.ofNullable(repositoryU.save(novoUsuario));
		});
	}
	
	/**
	 * método alternativo usando map para criar o token
	 * 
	 * public Optional<UserLoginDto> logar(Optional<UserLoginDto> login) {
			return repositoryU.findByEmail(login.get().getEmail()).map(usuarioExistente -> {
				BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
				if (encoder.matches(login.get().getSenha(), usuarioExistente.get().getSenha())) {
					String auth = login.get().getEmail() + ":" + login.get().getSenha();
					byte[] encodeAuth = Base64.encodeBase64(auth.getBytes(Charset.forName("US-ASCII")));
					String authHeader = "Basic " + new String(encodeAuth);
					
					//todos os atributos do DTO
					login.get().setToken(authHeader);
					login.get().setNome(usuarioExistente.get().getName());
	
					return Optional.ofNullable(login);
				} else {
					return Optional.empty();
			}).orElse(Optional.empty());
		}
	 * 
	 * 
	 */

	public Optional<UserLoginDto> logar(Optional<UserLoginDto> login) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		Optional<Usuario> usuarioExistente = repositoryU.findByEmail(login.get().getEmail());

		if (usuarioExistente.isPresent()) {
			if (encoder.matches(login.get().getSenha(), usuarioExistente.get().getSenha())) {
				String auth = login.get().getEmail() + ":" + login.get().getSenha();
				//Biblioteca Base64 não tem detro do pom. Precisamos inserir a ferramenta commons-codec.
				byte[] encodeAuth = Base64.encodeBase64(auth.getBytes(Charset.forName("US-ASCII")));
				String authHeader = "Basic " + new String(encodeAuth);

				login.get().setToken(authHeader);
				login.get().setNome(usuarioExistente.get().getName());
				login.get().setFoto(usuarioExistente.get().getFoto());
				login.get().setTipo(usuarioExistente.get().getTipo());
				login.get().setId(usuarioExistente.get().getId());

				return login;
			}
		}
		return null;
	}

	/**
	 * Serviço utilizado para atualizar um usuario
	 * 
	 * @param idUsuario,tipo        Integer para pesquisar o usuario no banco
	 * @param usuarioParaAtualizar, tipo UsuarioDto para transferencia de nome e
	 *                              sobrenome
	 * @return Optional com entidade de Usuario e Optional empty
	 * @since 1.0
	 * @author hanely
	 */
	public Optional<Usuario> atualizarUsuario(Integer idUsuario, UsuarioDtos usuarioParaAtualizar) {
		return repositoryU.findById(idUsuario).map(usuarioExistente -> {
			BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
			String senhaCriptografada = encoder.encode(usuarioParaAtualizar.getSenha());
			usuarioExistente.setSenha(senhaCriptografada);
			usuarioExistente.setMaiorDeIdade(usuarioParaAtualizar.isMaiorDeIdade());
			return Optional.ofNullable(repositoryU.save(usuarioExistente));
		}).orElseGet(() -> {
			return Optional.empty();
		});
	}

	/**
	 * serviço utilizado para criar grupo de usuario
	 * 
	 * @param id              da classe Usuario, tipo Integer para pesquisar o
	 *                        usuario no banco
	 * @param grupoParaCriar, tipo Grupo para criação
	 * @return entidade grupo criado
	 */

	public Optional<UsuarioGrupo> criarGrupo(Integer id, UsuarioGrupo grupoParaCriar) {
		return repositoryU.findById(id).map(usuarioExiste -> {
			grupoParaCriar.setCriador(usuarioExiste);
			return Optional.ofNullable(repositoryG.save(grupoParaCriar));
		}).orElseGet(() -> {
			return Optional.empty();
		});
	}

	/**
	 * metodo para inscrever o Usuario ao grupo
	 * 
	 * @param id,      para buscar usuario
	 * @param idGrupo, para buscar grupo
	 * @return Optional com usuario
	 */
	public Optional<Usuario> inscreverNoGrupo(Integer id, Long idGrupo) {
		Optional<UsuarioGrupo> grupoExistente = repositoryG.findById(idGrupo);
		if (grupoExistente.isPresent()) {
			return repositoryU.findById(id).map(usuarioExistente -> {
				usuarioExistente.getGrupoInscrito().add(grupoExistente.get());
				return Optional.ofNullable(repositoryU.save(usuarioExistente));
			}).orElseGet(() -> {
				return Optional.empty();
			});
		} else {
			return Optional.empty();
		}
	}

	public Optional<Usuario> sairDoGrupo(Integer id, Long idGrupo) {
		Optional<UsuarioGrupo> grupoExistente = repositoryG.findById(idGrupo);
		if (grupoExistente.isPresent()) {
			return repositoryU.findById(id).map(usuarioExistente -> {
				usuarioExistente.getGrupoInscrito().remove(grupoExistente.get());
				return Optional.ofNullable(repositoryU.save(usuarioExistente));
			}).orElseGet(() -> {
				return Optional.empty();
			});
		} else {
			return Optional.empty();
		}
	}
}
