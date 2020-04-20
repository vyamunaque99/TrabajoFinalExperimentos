package com.protech.matricula.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.protech.matricula.entity.Usuario;


public interface IUsuarioRepository extends JpaRepository<Usuario, Long>{
	
	public Usuario findByUsername(String username);

}
