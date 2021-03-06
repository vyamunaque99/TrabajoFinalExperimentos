package com.protech.matricula.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

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

import com.protech.matricula.entity.Alumno;
import com.protech.matricula.service.impl.AlumnoService;


@Controller
@RequestMapping("/alumno")
@SessionAttributes("alumno")
public class AlumnoController {
		
	@Autowired
	private AlumnoService alumnoService;
	
	@GetMapping
	public String alumno(Model model) {
		List<Alumno> alumnos= alumnoService.findAll();
		model.addAttribute("alumnos", alumnos);
		return "alumno/alumno";
	}
	
	@GetMapping(value = "/new")
	public String newAlumno(Model model) {
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
	public String saveAlumno(@Valid Alumno alumno,BindingResult result ,Model model,SessionStatus status,RedirectAttributes flashMessage){
		Date today = new Date();
		if(result.hasErrors()) {
			//Instanciamos objetos		
			model.addAttribute("title","Registrar alumno");
			return "alumno/form";
		}	
		if(alumnoService.existeAlumnoByCodigo(alumno.getCodigo()) && alumno.getId()==null) {
			model.addAttribute("message","El codigo de alumno ya existe");
			model.addAttribute("title","Registrar alumno");
			return "alumno/form";		
		}else if(alumno.getCodigo().length()!=10 || !StringUtils.isNumeric(alumno.getCodigo())) {
			model.addAttribute("message","El codigo de alumno es invalido");
			model.addAttribute("title","Registrar alumno");
			return "alumno/form";		
		}else if(alumno.getNombres().length()>30 || !StringUtils.isAlphaSpace(alumno.getNombres())) {
			model.addAttribute("message","Los nombres del alumno son inválidos");
			model.addAttribute("title","Registrar alumno");
			return "alumno/form";
		}else if(alumno.getApellidos().length()>30 || !StringUtils.isAlphaSpace(alumno.getApellidos())) {
			model.addAttribute("message","Los apellidos del alumno son inválidos");
			model.addAttribute("title","Registrar alumno");
			return "alumno/form";
		}else if(alumno.getDNI().length()!=8 || !StringUtils.isNumeric(alumno.getDNI())) {
			model.addAttribute("message","El DNI del alumno no es válido");
			model.addAttribute("title","Registrar alumno");
			return "alumno/form";
		}else if(today.before(alumno.getFechaNacimiento()) || TimeUnit.DAYS.convert(today.getTime()-alumno.getFechaNacimiento().getTime(),TimeUnit.MILLISECONDS)<18*365) {
			//System.out.println(TimeUnit.DAYS.convert(today.getTime()-alumno.getFechaNacimiento().getTime(),TimeUnit.MILLISECONDS));
			model.addAttribute("message","La fecha de nacimiento del alumno no es válida");
			model.addAttribute("title","Registrar alumno");
			return "alumno/form";
		} else if(alumno.getDireccion().length()>80 || StringUtils.containsAny(alumno.getDireccion(), "\b!#$%&/()='¡¿?´¨+*{}[];:_°|")) {
			model.addAttribute("message","La dirección del alumno no es válido");
			model.addAttribute("title","Registrar alumno");
			return "alumno/form";
		}
		/*
		//Si el alumno no tiene ningun rol
		System.out.println(alumno.getTelefono());
		if(alumno.getId() ==null) {
			List<Rol> listRoles= new ArrayList<>();
			Rol rol=new Rol();
			rol.setAuthority("ROLE_USER");
			listRoles.add(rol);
			Usuario usuario= alumno.getUsuario();
			usuario.setRoles(listRoles);
			alumno.setUsuario(usuario);
		}
		*/
		String mensaje;
		if(alumno.getId()!=null) {
			mensaje="Alumno editado exitosamente";
		}else {
			mensaje="Alumno registrado exitosamente";
		}
		flashMessage.addFlashAttribute("message", mensaje);
		alumnoService.saveOrUpdate(alumno);
		status.setComplete();
		return "redirect:/alumno";
	}

	@GetMapping(value = "/edit/{id}")
	public String editAlumno(@PathVariable(value = "id") Long id,Model model ,RedirectAttributes flash) {
		Optional<Alumno> alumno;
		if(id>0) {
			alumno=alumnoService.findById(id);
			if(!alumno.isPresent()) {
				flash.addFlashAttribute("title","El alumno no existe");
				return "redirect:/alumno";
			}
		}else {
			flash.addFlashAttribute("title", "El alumno no existe");
			return "redirect:/alumno";
		}
		model.addAttribute("title", "Editar alumno");
		model.addAttribute("alumno",alumno);
		return "alumno/form";
	}
	
	@GetMapping(value = "/delete/{id}")
	public String deleteAlumno(@PathVariable(value = "id") Long id,RedirectAttributes flashMessage) {
		Optional<Alumno> alumno;
		alumno=alumnoService.findById(id);
		if(!alumno.isPresent()) {
			return "redirect:/alumno";
		}else if(alumnoService.alumnoMatriculado(alumno.get().getCodigo())) {
			flashMessage.addFlashAttribute("messageError", "El alumno está matriculado en un curso y no puede eliminarse");
			return "redirect:/alumno";
		}
		alumnoService.deleteById(id);
		flashMessage.addFlashAttribute("message", "Se ha eliminado al alumno exitosamente");
		return "redirect:/alumno";
	}

}
