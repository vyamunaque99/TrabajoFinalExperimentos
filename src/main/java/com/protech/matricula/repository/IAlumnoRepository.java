package com.protech.matricula.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.protech.matricula.entity.Alumno;

public interface IAlumnoRepository extends JpaRepository<Alumno, Long>{

	@Query(nativeQuery = true,value = "select count(*) from alumnos where codigo=?1")
	Integer existeAlumnobyCodigo(String codigo);
	
	@Query(nativeQuery = true,value = "select count(*) from alumnos where dni=?1")
	Integer existeAlumnobyDNI(String dni);
	
	@Query(nativeQuery = true,value = "select count(*) from alumnos a inner join matriculas m on a.id=m.alumno_id where a.codigo=?1")
	Integer numCursosMatriculados(String codigo);
	
}
