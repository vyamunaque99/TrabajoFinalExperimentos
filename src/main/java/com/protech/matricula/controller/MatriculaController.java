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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
	
	@GetMapping
	public String matricula(Model model) {
		List<Matricula> matriculas=matriculaService.findAll();
		model.addAttribute("matriculas", matriculas);
		return "matricula/matricula";
	}
	
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
	public String saveMatricula(@Valid Matricula matricula,BindingResult result,Model model,SessionStatus status,RedirectAttributes flashMessage) {
		if(matriculaService.isCursoLleno(matricula.getCurso().getId())) {
			model.addAttribute("alumnos", loadAlumnos());
			model.addAttribute("cursos", loadCursos());	
			model.addAttribute("errorMessage","El curso se encuentra lleno");
			model.addAttribute("title","Matricularme en este curso");
			return "matricula/form";
		}else if (matriculaService.isAlumnoMatriculado(matricula.getCurso().getId(),matricula.getAlumno().getId())){
			model.addAttribute("alumnos", loadAlumnos());
			model.addAttribute("cursos", loadCursos());	
			model.addAttribute("title","Matricularme en este curso");
			model.addAttribute("errorMessage","El alumno ya está matriculado en el curso");
			return "matricula/form";
		}		
		matriculaService.saveOrUpdate(matricula);
		status.setComplete();
		model.addAttribute("alumnos", loadAlumnos());
		model.addAttribute("cursos", loadCursos());	
		model.addAttribute("title","Matricularme en este curso");
		model.addAttribute("message", "Alumno matriculado exitosamente");
		return "matricula/form";
	}
	
	@GetMapping(value = "/searchProfesor")
	public String listarPorProfesor(@RequestParam String codProfesor,Model model) {
		List<Matricula> matriculas=matriculaService.listarPorProfesor(codProfesor);
		model.addAttribute("matriculas", matriculas);
		System.out.println(codProfesor);
		return "matricula/matricula";	
	}
	
	@GetMapping(value = "/searchAlumno")
	public String listarPorAlumno(@RequestParam String codAlumno,Model model) {
		List<Matricula> matriculas=matriculaService.listarPorAlumno(codAlumno);
		model.addAttribute("matriculas", matriculas);
		System.out.println(codAlumno);
		return "matricula/matricula";	
	}
	
	@GetMapping(value = "/searchCurso")
	public String listarPorCurso(@RequestParam String codCurso,Model model) {
		List<Matricula> matriculas=matriculaService.listarPorCurso(codCurso);
		model.addAttribute("matriculas", matriculas);
		System.out.println(codCurso);
		return "matricula/matricula";	
	}
	
	public List<Curso>loadCursos(){
		return cursoService.findAll();
	}
	
	public List<Alumno>loadAlumnos(){
		return alumnoService.findAll();
	}

}
