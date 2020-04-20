package com.protech.matricula.config;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.security.authentication.event.AuthenticationFailureLockedEvent;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.stereotype.Component;

import com.protech.matricula.entity.Usuario;
import com.protech.matricula.service.impl.UsuarioService;

@Component
public class EventListener {
	
	@Autowired
	private UsuarioService usuarioService;
	
    @org.springframework.context.event.EventListener
	public void AuthenticationSuccess(AuthenticationSuccessEvent event) {
    	System.out.println("login success");
    	String username = event.getAuthentication().getName();
    	Usuario usuario=usuarioService.findByUsername(username);
    	usuario.setFailedAttemps(0);
    	usuarioService.saveOrUpdate(usuario);
	}

    @org.springframework.context.event.EventListener
    public void authenticationFailed(AuthenticationFailureBadCredentialsEvent event) {

        String username = (String) event.getAuthentication().getPrincipal();
        Boolean isAuthenticated=event.getAuthentication().isAuthenticated();
        
        Usuario usuario=usuarioService.findByUsername(username);
        
        //Incrementar la cantidad de intentos fallidos
        if(usuario!=null && !isAuthenticated ) {
        	if(!usuario.isLocked()) {
        		if(usuario.getFailedAttemps()<3) {
        			//Incremento de intento fallidos
            		usuario.setFailedAttemps(usuario.getFailedAttemps()+1);      		       		
            		usuarioService.saveOrUpdate(usuario);
            	}
            	if(usuario.getFailedAttemps()==3) {
            		//Bloqueo de cuenta
            		//Obtencion de fecha de bloqueo (Timestamp)
            		Date today = new Date();
            		usuario.setLocked(true);
            		usuario.setLastTimeLocked(today);
            		usuarioService.saveOrUpdate(usuario);
            	}
        	}
        }
        // update the failed login count for the user
        // ...
    }
    
    @org.springframework.context.event.EventListener
    public void unlockUser(AuthenticationFailureLockedEvent event) {
    	Date today = new Date();
    	String username = (String) event.getAuthentication().getPrincipal();
    	Usuario usuario=usuarioService.findByUsername(username);
    	System.out.println(TimeUnit.MINUTES.convert(today.getTime()-usuario.getLastTimeLocked().getTime(), TimeUnit.MILLISECONDS));
    	//Revisa si ha pasado los 30 minutos
    	if(TimeUnit.MINUTES.convert(today.getTime()-usuario.getLastTimeLocked().getTime(), TimeUnit.MILLISECONDS)>30) {
    		usuario.setLocked(false);
    		usuario.setFailedAttemps(0);
    		usuarioService.saveOrUpdate(usuario);
    	}
    }

}