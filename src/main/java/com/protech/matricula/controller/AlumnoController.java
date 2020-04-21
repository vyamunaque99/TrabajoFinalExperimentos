package com.protech.matricula.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.support.SessionStatus;

import com.protech.matricula.entity.Alumno;
import com.protech.matricula.service.impl.AlumnoService;

@Controller
public class AlumnoController {
	
	@Autowired
	private AlumnoService alumnoService;
	
	@GetMapping("/alumno")
	public String alumno(Model model) {
		Alumno alumno= new Alumno();
		model.addAttribute("alumno",alumno);
		model.addAttribute("title", "Registrar alumno");
		return "alumno/form";
	}
	
	@PostMapping(value = "/save")
	public String saveAlumno(@Valid Alumno alumno,Model model,BindingResult result,SessionStatus status) {
		if(result.hasErrors()) {
			model.addAttribute("title","Nuevo alumno");
			return "alumno/form";
		}
		alumnoService.saveOrUpdate(alumno);
		status.setComplete();
		return "redirect:/";
	}

}
