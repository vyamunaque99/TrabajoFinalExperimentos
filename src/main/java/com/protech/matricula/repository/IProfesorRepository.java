package com.protech.matricula.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.protech.matricula.entity.Profesor;

public interface IProfesorRepository extends JpaRepository<Profesor, Long> {

	@Query(nativeQuery = true,value = "select count(*) from profesores where codigo=?1")
	Integer existeProfesorbyCodigo(String codigo);

	@Query(nativeQuery = true,value = "select count(*) from profesor where dni=?1")
	Integer existeProfesorbyDNI(String dni);
	
	@Query(nativeQuery = true,value = "select count(*) from profesores p "
			+ "inner join cursos c on p.id=c.profesor_id where p.codigo=?1")
	Integer numCursosInscritos(String codigo);
}
