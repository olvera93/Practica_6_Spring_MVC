package mx.uam.ayd.proyecto.dto;

import javax.persistence.Entity;

import lombok.Data;
import mx.uam.ayd.proyecto.negocio.modelo.Usuario;

/**
 * DTO de usuarios
 * 
 */
@Data
public class UsuarioDto {
	
	private long idUsuario;
	private String nombre;
	private String apellido;
	private int edad;
	private long grupo;
	
	/**
	 * Este método permite generar un DTO a partir de la entidad
	 * nota: es un método de clase y no se necesita un objeto
	 * para invocarlo. Se invoca como UsuarioDto.crea(param)
     * @param usuario la entidad
	 * @return dto obtenido a partir de la entidad
	 */
	
	public static UsuarioDto creaDto(Usuario usuario) {
		UsuarioDto dto = new UsuarioDto();
		
		dto.setIdUsuario(usuario.getIdUsuario());
		dto.setNombre(usuario.getNombre());
		dto.setApellido(usuario.getApellido());
		dto.setEdad(usuario.getEdad());
		dto.setGrupo(usuario.getGrupo().getIdGrupo());
		
		return dto;
	}

	
}
