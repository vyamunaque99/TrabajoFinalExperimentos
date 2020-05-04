package com.protech.matricula.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.protech.matricula.entity.Matricula;

public interface IMatriculaRepository extends JpaRepository<Matricula, Long> {
	
	@Query(nativeQuery = true,value = "select count(*) from matriculas where curso_id=?1")
	Integer cantidadAlumnos(Long id);

}
