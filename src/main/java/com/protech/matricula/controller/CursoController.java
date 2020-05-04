package com.protech.matricula.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

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
	public String saveCurso(@Valid Curso curso,BindingResult result,Model model,SessionStatus status) {
		if(result.hasErrors()) {
			model.addAttribute("title", "Registrar Curso");
			return "curso/form";
		}
		cursoService.saveOrUpdate(curso);
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
	public String deleteCurso(@PathVariable(value = "id") Long id) {
		cursoService.deleteById(id);
		return "redirect:/curso";
	}
	
	public List<Profesor> loadProfesores(){
		return profesorService.findAll();
	}
}
