package com.protech.matricula.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotEmpty;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Entity
@Table(name="usuarios")
public class Usuario implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotEmpty(message = "Debe ingresar usuario")
	@Column(unique = true,length = 9)
	private String username;
	
	@NotEmpty(message = "Debe ingresar contraseña")
	private String password;
	
	//private boolean enabled;
	
	private boolean locked;
	
	private int failedAttemps;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date lastTimeLocked;
	
	/*
	@OneToOne(mappedBy = "usuario")
	private Profesor profesor;
	
	@OneToOne(mappedBy = "usuario")
	private Alumno alumno;
	*/
	
	
	@OneToMany(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
	@JoinColumn(name="user_id")
	private List<Rol> roles;

	@PrePersist
	public void prePersist() {
		this.password=new BCryptPasswordEncoder().encode(password);
	}
	
	@PreUpdate
	public void preUpdate()
	{
		this.password=new BCryptPasswordEncoder().encode(password);
	}
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}


	public List<Rol> getRoles() {
		return roles;
	}

	public void setRoles(List<Rol> roles) {
		this.roles = roles;
	}

	public int getFailedAttemps() {
		return failedAttemps;
	}

	public void setFailedAttemps(int failedAttemps) {
		this.failedAttemps = failedAttemps;
	}
	
	Usuario(){
		this.failedAttemps=0;
		this.locked=false;
	}

	public boolean isLocked() {
		return locked;
	}

	public void setLocked(boolean locked) {
		this.locked = locked;
	}

	public Date getLastTimeLocked() {
		return lastTimeLocked;
	}

	public void setLastTimeLocked(Date lastTimeLocked) {
		this.lastTimeLocked = lastTimeLocked;
	}
	

}
