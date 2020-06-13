package com.protech.matricula.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.protech.matricula.entity.Curso;
import com.protech.matricula.entity.Profesor;
import com.protech.matricula.service.impl.CursoService;
import com.protech.matricula.service.impl.ProfesorService;

@Controller
@RequestMapping("/curso")
@SessionAttributes("curso")
public class CursoController {

	@Autowired
	private CursoService cursoService;
	
	@Autowired
	private ProfesorService profesorService;

	@GetMapping
	public String curso(Model model) {
		List<Curso> cursos = cursoService.findAll();
		model.addAttribute("cursos", cursos);
		return "curso/curso";
	}

	@GetMapping(value = "/new")
	public String newCurso(Model model) {
		Curso curso = new Curso();
		model.addAttribute("curso", curso);
		model.addAttribute("profesores", loadProfesores());
		model.addAttribute("title", "Registrar Curso");
		return "curso/form";
	}
	
	@PostMapping(value = "/save")
	public String saveCurso(@Valid Curso curso,BindingResult result,Model model,SessionStatus status,RedirectAttributes flashMessage) {
		if(result.hasErrors()) {
			model.addAttribute("title", "Registrar Curso");
			return "curso/form";
		}
		if(cursoService.existeCursoByCodigo(curso.getCodigo()) && curso.getId()==null) {
			model.addAttribute("message", "El código de curso ya existe");
			model.addAttribute("profesores", loadProfesores());
			model.addAttribute("title", "Registrar Curso");
			return "curso/form";
		}else if(curso.getCodigo().length()!=4 || !StringUtils.isNumeric(curso.getCodigo())) {
			model.addAttribute("message", "El código de curso es incorrecto");
			model.addAttribute("profesores", loadProfesores());
			model.addAttribute("title", "Registrar Curso");
			return "curso/form";
		}else if(curso.getNombre().length()>50 || !StringUtils.isAlphaSpace(curso.getNombre())) {
			model.addAttribute("message", "El nombre de curso es incorrecto");
			model.addAttribute("profesores", loadProfesores());
			model.addAttribute("title", "Registrar Curso");
			return "curso/form";
		}else if(curso.getDescripcion().length()>100 || StringUtils.containsAny(curso.getDescripcion(), "\b!#$%&/()='¡¿?´¨+*{}[];:_°|")) {
			model.addAttribute("message", "La descripción de curso es incorrecto");
			model.addAttribute("profesores", loadProfesores());
			model.addAttribute("title", "Registrar Curso");
			return "curso/form";
		}
		cursoService.saveOrUpdate(curso);
		flashMessage.addFlashAttribute("message", "Curso registrado exitosamente");
		status.setComplete();
		return "redirect:/curso";
	}
	
	@GetMapping(value = "/edit/{id}")
	public String editCurso(@PathVariable(value = "id") Long id,Model model ,RedirectAttributes flash) {
		Optional<Curso> curso;
		if(id>0) {
			curso=cursoService.findById(id);
			if(!curso.isPresent()) {
				flash.addFlashAttribute("title","El curso no existe");
				return "redirect:/curso";
			}
		}else {
			flash.addFlashAttribute("title","El curso no existe");
			return "redirect:/curso";
		}
		model.addAttribute("profesores", loadProfesores());
		model.addAttribute("title", "Editar Curso");
		model.addAttribute("curso", curso);
		return "curso/form";
	}
	
	@GetMapping(value = "/delete/{id}")
	public String deleteCurso(@PathVariable(value = "id") Long id,RedirectAttributes flashMessage) {
		Optional<Curso> curso;
		curso=cursoService.findById(id);
		if(!curso.isPresent()) {
			return "redirect:/curso";
		}else if(cursoService.existeAlumnos(id)) {
			flashMessage.addFlashAttribute("messageError", "El curso no puede eliminarse, ya que hay alumnos matriculados en el mismo");
			return "redirect:/curso";
		}
		cursoService.deleteById(id);
		flashMessage.addFlashAttribute("message", "Curso eliminado exitosamente");
		return "redirect:/curso";
	}
	
	public List<Profesor> loadProfesores(){
		return profesorService.findAll();
	}
}
