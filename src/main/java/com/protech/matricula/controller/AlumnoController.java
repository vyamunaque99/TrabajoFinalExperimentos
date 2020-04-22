package com.protech.matricula.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.support.SessionStatus;

import com.protech.matricula.entity.Alumno;
import com.protech.matricula.entity.Rol;
import com.protech.matricula.entity.Usuario;
import com.protech.matricula.service.impl.AlumnoService;

@Controller
@RequestMapping("/alumno")
public class AlumnoController {
	
	@Autowired
	private AlumnoService alumnoService;
	
	@GetMapping
	public String alumno(Model model) {
		Alumno alumno= new Alumno();
		model.addAttribute("alumno",alumno);
		model.addAttribute("title", "Registrar alumno");
		return "alumno/form";
	}
	
	@InitBinder     
	public void initBinder(WebDataBinder binder){
	     binder.registerCustomEditor(Date.class,     
	                         new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), true, 10));   
	}
	
	@PostMapping(value = "/save")
	public String saveAlumno(@Valid Alumno alumno,Model model,BindingResult result,SessionStatus status) {
		if(result.hasErrors()) {
			//Instanciamos objetos		
			model.addAttribute("title","Nuevo alumno");
			return "alumno/form";
		}
		List<Rol> listRoles= new ArrayList<>();
		Rol rol=new Rol();
		rol.setAuthority("ROLE_USER");
		listRoles.add(rol);
		Usuario usuario= alumno.getUsuario();
		usuario.setRoles(listRoles);
		alumno.setUsuario(usuario);
		alumnoService.saveOrUpdate(alumno);
		status.setComplete();
		return "redirect:/";
	}

}
