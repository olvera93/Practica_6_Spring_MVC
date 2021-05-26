package mx.uam.ayd.proyecto.negocio.modelo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import lombok.Data;

/**
 * Entidad de negocio Usuario
 * 
 * @author humbertocervantes
 *
 */
@Entity
@Data
public class Usuario {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long idUsuario;

	private String nombre;
	
	private String apellido;
	
	private int edad;
	
	@ManyToOne
	private Grupo grupo;

	public Usuario() {
		
	}
	
	public Usuario(String nombre, String apellido, int edad, Grupo grupo) {
		this.nombre = nombre;
		this.apellido = apellido;
		this.edad = edad;
		this.grupo = grupo;
	}
	
	
}
