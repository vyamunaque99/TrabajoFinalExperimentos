package com.protech.matricula.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.protech.matricula.entity.Profesor;
import com.protech.matricula.service.impl.ProfesorService;

@Controller
@RequestMapping("/profesor")
@SessionAttributes("profesor")
public class ProfesorController {

	@Autowired
	private ProfesorService profesorService;

	@GetMapping
	public String profesor(Model model) {
		List<Profesor> profesores = profesorService.findAll();
		model.addAttribute("profesores", profesores);
		return "profesor/profesor";
	}

	@GetMapping(value = "/new")
	public String newProfesor(Model model) {
		Profesor profesor = new Profesor();
		model.addAttribute("profesor", profesor);
		model.addAttribute("title", "Registrar profesor");
		return "profesor/form";
	}
	
	@InitBinder     
	public void initBinder(WebDataBinder binder){
	     binder.registerCustomEditor(Date.class,     
	                         new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), true, 10));   
	}
	
	@PostMapping(value = "/save")
	public String saveProfesor(@Valid Profesor profesor,BindingResult result,Model model,SessionStatus status,RedirectAttributes flashMessage) {
		if(result.hasErrors()) {
			model.addAttribute("title", "Registrar profesor");
			return "profesor/form";
		}
		/*
		//Si el profesor no tiene ningun rol
		if(profesor.getId()==null) {
			List<Rol> listRoles = new ArrayList<>();
			Rol rol = new Rol();
			rol.setAuthority("ROLE_PROFESOR");
			listRoles.add(rol);
			Usuario usuario=profesor.getUsuario();
			usuario.setRoles(listRoles);
			profesor.setUsuario(usuario);
		}
		*/
		String mensaje;
		if(profesor.getId()!=null) {
			mensaje="Profesor editado exitosamente";
		}else {
			mensaje="Profesor registrado exitosamente";
		}
		flashMessage.addFlashAttribute("message", mensaje);
		profesorService.saveOrUpdate(profesor);
		status.setComplete();
		return "redirect:/profesor";
	}
	
	@GetMapping(value = "/edit/{id}")
	public String editProfesor(@PathVariable(value = "id") Long id,Model model ,RedirectAttributes flash) {
		Optional<Profesor> profesor;
		if(id>0) {
			profesor=profesorService.findById(id);
			if(!profesor.isPresent()) {
				flash.addFlashAttribute("title","El profesor no existe");
				return "redirect:/profesor";
			}
		}else {
			flash.addFlashAttribute("title", "El profesor no existe");
			return "redirect:/profesor";
		}
		model.addAttribute("title", "Editar profesor");
		model.addAttribute("profesor",profesor);
		return "profesor/form";
	}
	
	@GetMapping(value = "/delete/{id}")
	public String deleteProfesor(@PathVariable(value = "id") Long id,RedirectAttributes flashMessage) {
		profesorService.deleteById(id);
		flashMessage.addFlashAttribute("message", "Se ha eliminado al profesor exitosamente");
		return "redirect:/profesor";
	}
}
