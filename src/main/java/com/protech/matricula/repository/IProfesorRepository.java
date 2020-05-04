package com.protech.matricula.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.protech.matricula.entity.Profesor;

public interface IProfesorRepository extends JpaRepository<Profesor, Long> {

}
