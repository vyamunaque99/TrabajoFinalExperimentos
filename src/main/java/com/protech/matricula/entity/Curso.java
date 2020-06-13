package com.protech.matricula.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name="cursos")
public class Curso implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotEmpty(message="Debe ingresar un codigo")
	@Column(name="codigo", nullable = false , length=4)
	//@Size(min=4,max = 4)
	private String codigo;
	
	@NotEmpty(message="Debe ingresar un nombre de curso")
	@Column(name="nombre", nullable = false , length=50)
	private String nombre;
	
	@NotEmpty(message="Debe ingresar un codigo")
	@Column(name="descripcion", nullable = false , length=100)
	private String descripcion;

	@NotEmpty(message="No existen profesores registrados, Por favor registre uno antes de procede")
	@ManyToOne
	@JoinColumn(name = "profesor_id")
	private Profesor profesor;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Profesor getProfesor() {
		return profesor;
	}

	public void setProfesor(Profesor profesor) {
		this.profesor = profesor;
	}
}
