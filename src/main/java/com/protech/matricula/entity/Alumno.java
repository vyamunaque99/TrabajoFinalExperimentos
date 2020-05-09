package com.protech.matricula.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "alumnos")
public class Alumno implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotEmpty(message="Debe ingresar codigo")
	@Column(name="codigo", nullable = false , length=30)
	private String codigo;
	
	@NotEmpty(message="Debe ingresar nombre")
	@Column(name="nombres", nullable = false , length=30)
	private String nombres;

	@NotEmpty(message="Debe ingresar apellidos")
	@Column(name="apellidos", nullable=false, length=30)
	private String apellidos;

	@NotEmpty(message ="Debe ingresar DNI")
	@Size(min=8,max = 8)
	@Column(unique = true)
	private String DNI;

	@NotNull(message = "Debe ingresar una Fecha")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Temporal(TemporalType.DATE)
	private Date fechaNacimiento;

	@NotEmpty(message="Debe ingresar Direccion")
	@Column(name="direccion", nullable=false,length = 100)
	private String direccion;

	@NotEmpty(message="Debe ingresar Telefono")
	@Column(name="telefono",nullable=false,length = 9,unique = true)
	private String telefono;

	/*
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "usuario_id", referencedColumnName = "id")
	private Usuario usuario;
	*/
	
	@OneToOne(mappedBy = "alumno")
	private Matricula matricula;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombres() {
		return nombres;
	}

	public void setNombres(String nombres) {
		this.nombres = nombres;
	}

	public String getApellidos() {
		return apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public String getDNI() {
		return DNI;
	}

	public void setDNI(String dNI) {
		DNI = dNI;
	}

	public Date getFechaNacimiento() {
		return fechaNacimiento;
	}

	public void setFechaNacimiento(Date fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	
	/*
	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	*/

}
