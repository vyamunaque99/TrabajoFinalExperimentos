package com.protech.matricula.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.protech.matricula.entity.Usuario;
import com.protech.matricula.repository.IUsuarioRepository;
import com.protech.matricula.service.IUsuarioService;

@Service
public class UsuarioService implements IUsuarioService{

	@Autowired
	private IUsuarioRepository usuarioRepository;
	
	@Override
	public Usuario saveOrUpdate(Usuario entity) {
		// TODO Auto-generated method stub
		return usuarioRepository.save(entity);
	}

	@Override
	public List<Usuario> findAll() {
		// TODO Auto-generated method stub
		return usuarioRepository.findAll();
	}

	@Override
	public Optional<Usuario> findById(Long id) {
		// TODO Auto-generated method stub
		return usuarioRepository.findById(id);
	}
	
	public Usuario findByUsername(String username) {
		return usuarioRepository.findByUsername(username);
	}

}
