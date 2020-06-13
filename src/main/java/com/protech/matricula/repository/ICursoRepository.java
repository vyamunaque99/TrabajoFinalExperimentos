package com.protech.matricula.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.protech.matricula.entity.Curso;

public interface ICursoRepository extends JpaRepository<Curso, Long>{

	@Query(nativeQuery = true,value = "select count(*) from cursos where codigo=?1")
	Integer existeCursobyCodigo(String codigo);
	
}
