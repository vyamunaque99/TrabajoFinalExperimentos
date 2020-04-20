package com.protech.matricula.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.protech.matricula.entity.Rol;
import com.protech.matricula.entity.Usuario;
import com.protech.matricula.repository.IUsuarioRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {
	
	@Autowired
	private IUsuarioRepository usuarioRepository;
	
	private Logger logger= LoggerFactory.getLogger(CustomUserDetailsService.class);

	@Override
	@Transactional(readOnly = true)
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		Usuario usuario=usuarioRepository.findByUsername(username);
		
		if(usuario==null) {
			logger.error("Error, no existe '"+username+"'");
			throw new UsernameNotFoundException("Username "+username+" no existe");
		}
		
		//	System.out.println(usuario.getPassword());
		
		List<GrantedAuthority> authorities=new ArrayList<GrantedAuthority>();
		
		for(Rol rol: usuario.getRoles()) {
			authorities.add(new SimpleGrantedAuthority(rol.getAuthority()));
		}
		
		return new User(usuario.getUsername(), usuario.getPassword(), true, true, true, !usuario.isLocked(),authorities);
	}

}
