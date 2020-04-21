package com.protech.matricula.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.protech.matricula.entity.Alumno;

public interface IAlumnoRepository extends JpaRepository<Alumno, Long>{

}
