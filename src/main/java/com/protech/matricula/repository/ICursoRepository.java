package com.protech.matricula.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.protech.matricula.entity.Curso;

public interface ICursoRepository extends JpaRepository<Curso, Long>{

}
