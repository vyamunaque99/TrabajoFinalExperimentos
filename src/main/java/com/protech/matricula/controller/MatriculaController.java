package com.protech.matricula.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import com.protech.matricula.entity.Alumno;
import com.protech.matricula.entity.Curso;
import com.protech.matricula.entity.Matricula;
import com.protech.matricula.service.impl.AlumnoService;
import com.protech.matricula.service.impl.CursoService;
import com.protech.matricula.service.impl.MatriculaService;

@Controller
@RequestMapping("/matricula")
@SessionAttributes("matricula")
public class MatriculaController {
	
	@Autowired
	private MatriculaService matriculaService;
	
	@Autowired
	private CursoService cursoService;
	
	@Autowired
	private AlumnoService alumnoService;
	
	@GetMapping(value = "/new")
	public String newMatricula(Model model) {
		Matricula matricula = new Matricula();
		model.addAttribute("matricula", matricula);	
		model.addAttribute("alumnos", loadAlumnos());
		model.addAttribute("cursos", loadCursos());	
		model.addAttribute("title", "Matricularme en este curso");
		return "matricula/form";
	}
	
	@PostMapping(value = "/save")
	public String saveMatricula(@Valid Matricula matricula,BindingResult result,Model model,SessionStatus status) {
		if(matriculaService.isCursoLleno(matricula.getCurso().getId())) {
			model.addAttribute("title","Nuevo modelo");
			return "matricula/form";
		}
		matriculaService.saveOrUpdate(matricula);
		status.setComplete();
		return "redirect:/matricula/new";
	}
	
	public List<Curso>loadCursos(){
		return cursoService.findAll();
	}
	
	public List<Alumno>loadAlumnos(){
		return alumnoService.findAll();
	}

}
