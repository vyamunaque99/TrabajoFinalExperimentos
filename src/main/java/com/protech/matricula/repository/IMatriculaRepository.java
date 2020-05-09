package com.protech.matricula.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.protech.matricula.entity.Matricula;

public interface IMatriculaRepository extends JpaRepository<Matricula, Long> {
	
	@Query(nativeQuery = true,value = "select count(*) from matriculas where curso_id=?1")
	Integer cantidadAlumnos(Long id);
	
	@Query(nativeQuery = true,value = "select count(*) from matriculas where curso_id=?1 and alumno_id=?2")
	Integer existeAlumno(Long idCurso,Long idAlumno);
	
	@Query(nativeQuery = true,value = "select m.* from matriculas m inner join alumnos a on m.alumno_id=a.id where a.codigo=?1")
	List<Matricula> listarPorAlumno(String codigo);
	
	@Query(nativeQuery = true,value = "select m.* from matriculas m inner join cursos c on m.curso_id=c.id where c.codigo=?1")
	List<Matricula> listarPorCurso(String codigo);
	
	@Query(nativeQuery = true,value = "select m.* from matriculas m "
			+ "inner join cursos c on m.curso_id=c.id "
			+ "inner join profesores p on c.profesor_id=p.id where p.codigo=?1")
	List<Matricula> listarPorProfesor(String codigo);
}
