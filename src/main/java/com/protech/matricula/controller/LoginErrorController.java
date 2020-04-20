package com.protech.matricula.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.WebAttributes;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginErrorController {

	@GetMapping("/login-error")
	public String login(HttpServletRequest request, Model model) {
		HttpSession session = request.getSession(false);	
		String errorMessage = null;
        if (session != null) {
            AuthenticationException ex = (AuthenticationException) session
                    .getAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
            if (ex != null) {
                errorMessage = ex.getMessage();
            }
        }
        
        switch (errorMessage) {
		case "Bad credentials":
			model.addAttribute("error", "Credenciales incorrectas");
			break;
		case "User account is locked":
			model.addAttribute("error", "Usuario bloqueado, por intentos fallidos");
			break;
		default:
			break;
		}
        
		//System.out.println(errorMessage);
		return "login/login";
	}
	
}
