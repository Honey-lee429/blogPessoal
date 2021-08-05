package com.blog.bloghanna.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.blog.bloghanna.model.UsuarioGrupo;

@Repository
public interface GrupoRepository extends JpaRepository<UsuarioGrupo, Long>{
	
}
