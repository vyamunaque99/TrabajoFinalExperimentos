package com.protech.matricula.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
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
		Date today = new Date();
		if(result.hasErrors()) {
			model.addAttribute("title", "Registrar profesor");
			return "profesor/form";
		}
		if(profesorService.existeProfesorByCodigo(profesor.getCodigo()) && profesor.getId()==null) {
			model.addAttribute("message","El codigo de profesor ya existe");
			model.addAttribute("title", "Registrar profesor");
			return "profesor/form";
		}else if(profesor.getCodigo().length()!=10 || !StringUtils.isNumeric(profesor.getCodigo())) {
			model.addAttribute("message","El codigo de profesor es invalido");
			model.addAttribute("title", "Registrar profesor");
			return "profesor/form";
		}else if(profesor.getNombres().length()>30 || !StringUtils.isAlphaSpace(profesor.getNombres())) {
			model.addAttribute("message","Los nombres del profesor son inválidos");
			model.addAttribute("title", "Registrar profesor");
			return "profesor/form";
		}else if(profesor.getApellidos().length()>30 || !StringUtils.isAlphaSpace(profesor.getApellidos())) {
			model.addAttribute("message","Los apellidos del profesor son inválidos");
			model.addAttribute("title","Registrar profesor");
			return "profesor/form";
		}else if(profesor.getDNI().length()!=8 || !StringUtils.isNumeric(profesor.getDNI())) {
			model.addAttribute("message","El DNI del profesor no es válido");
			model.addAttribute("title","Registrar profesor");
			return "profesor/form";
		}else if(today.before(profesor.getFechaIngreso())) {
			model.addAttribute("message","La fecha de nacimiento del alumno no es válida");
			model.addAttribute("title","Registrar alumno");
			return "alumno/form";
		}else if(profesor.getTelefono().length()!=9 || !StringUtils.isNumeric(profesor.getTelefono())) {
			model.addAttribute("message","El número de teléfono celular del profesor no es válido");
			model.addAttribute("title","Registrar profesor");
			return "profesor/form";
		}else if(profesor.getDireccion().length()>80 || StringUtils.containsAny(profesor.getDireccion(), "\b!#$%&/()='¡¿?´¨+*{}[];:_°|")) {
			model.addAttribute("message","La dirección del profesor no es válido");
			model.addAttribute("title","Registrar profesor");
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
		Optional<Profesor> profesor;
		profesor=profesorService.findById(id);
		if(!profesor.isPresent()) {
			return "redirect:/profesor";
		}else if(profesorService.profesorInscrito(profesor.get().getCodigo())) {
			flashMessage.addFlashAttribute("messageError", "El profesor está afiliado a un curso y no puede eliminarse");
			return "redirect:/profesor";
		}
		profesorService.deleteById(id);
		flashMessage.addFlashAttribute("message", "Se ha eliminado al profesor exitosamente");
		return "redirect:/profesor";
	}
}
