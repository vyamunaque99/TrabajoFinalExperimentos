package com.protech.matricula.controller;

import java.security.Principal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {

	@GetMapping("/login")
	public String login(Model model,Principal principal,
			@RequestParam (value = "error", required = false) String error) {
		
		if(principal!=null) {
			return "redirect:/";
		}
		return "login/login";
	}
	
}
