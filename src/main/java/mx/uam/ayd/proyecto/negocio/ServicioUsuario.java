package mx.uam.ayd.proyecto.negocio;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import mx.uam.ayd.proyecto.datos.GrupoRepository;
import mx.uam.ayd.proyecto.datos.UsuarioRepository;
import mx.uam.ayd.proyecto.dto.UsuarioDto;
import mx.uam.ayd.proyecto.negocio.modelo.Grupo;
import mx.uam.ayd.proyecto.negocio.modelo.Usuario;

@Slf4j
@Service
public class ServicioUsuario {
	
	@Autowired 
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private GrupoRepository grupoRepository;
	
//	/**
//	 * 
//	 * Permite agregar un usuario
//	 * 
//	 * @param nombre
//	 * @param apellido
//	 * @param grupo
//	 * @return
//	 */
//	public Usuario agregaUsuario(String nombre, String apellido, String nombreGrupo) {
//		
//		// Regla de negocio: No se permite agregar dos usuarios con el mismo nombre y apellido
//		
//		
//		Usuario usuario = usuarioRepository.findByNombreAndApellido(nombre, apellido);
//		
//		if(usuario != null) {
//			throw new IllegalArgumentException("Ese usuario ya existe");
//		}
//		
//		Grupo grupo = grupoRepository.findByNombre(nombreGrupo);
//		
//		if(grupo == null) {
//			throw new IllegalArgumentException("No se encontró el grupo");
//		}
//		
//		log.info("Agregando usuario nombre: "+nombre+" apellido:"+apellido);
//		
//		usuario = new Usuario();
//		usuario.setNombre(nombre);
//		usuario.setApellido(apellido);
//		
//		usuarioRepository.save(usuario);
//		
//		grupo.addUsuario(usuario);
//		
//		
//		grupoRepository.save(grupo);
//		
//		return usuario;
//		
//
//	}

	/**
	 * Recupera todos los usuarios existentes
	 * 
	 * @return Una lista con los usuarios (o lista vacía)
	 */
//	public List <Usuario> recuperaUsuarios() {
//		
//		List <Usuario> usuarios = new ArrayList<>();
//		
//		for(Usuario usuario:usuarioRepository.findAll()) {
//			usuarios.add(usuario);
//			System.out.println(usuario);
//		}
//				
//		return usuarios;
//	}
	
	/**
	 * Permite agregar un usuario
	 *  
	 * @param usuarioDto
	 * @return dto con el id del usuario
	 */
	public UsuarioDto agregaUsuario(UsuarioDto usuarioDto) {
		
		
		// Regla de negocio: No se permite agregar dos usuarios con el mismo nombre y apellido
		Usuario usuario = usuarioRepository.findByNombreAndApellido(usuarioDto.getNombre(), usuarioDto.getApellido());
		 
		if(usuario != null) {
			throw new IllegalArgumentException("Ese usuario ya existe");			
		}
		
		Optional <Grupo> optGrupo = grupoRepository.findById(usuarioDto.getGrupo());
		
		if(optGrupo.isEmpty()) {
			throw new IllegalArgumentException("No se encontró el grupo");
		}
				
		Grupo grupo = optGrupo.get();
		
		log.info("Agregando usuario nombre: "+usuarioDto.getNombre()+" apellido:"+usuarioDto.getApellido());
		
		usuario = new Usuario();
		usuario.setNombre(usuarioDto.getNombre());
		usuario.setApellido(usuarioDto.getApellido());
		usuario.setEdad(usuarioDto.getEdad());
		usuario.setGrupo(grupo);
		
	
		usuario = usuarioRepository.save(usuario);
		
		
		grupo.addUsuario(usuario);
		
		grupoRepository.save(grupo);
		

		return UsuarioDto.creaDto(usuario);

	}
	

	public List <UsuarioDto> recuperaUsuarios() {
		
		List <UsuarioDto> usuarios = new ArrayList<>();
	
		for (Usuario usuario:usuarioRepository.findAll()) {
			usuarios.add(UsuarioDto.creaDto(usuario));
		}
		
		return usuarios;
	}
	
	/**
	 * 
	 * @param id
	 * @return usuario con el id
	 */
	public UsuarioDto retrieve(Long id){
		log.info("Recuperando usuario con id: "+ id);
		Optional<Usuario> optUsuario = usuarioRepository.findById(id);
		return UsuarioDto.creaDto(optUsuario.get());
		
	}

	public UsuarioDto actualizar(Long id, UsuarioDto actualizadoUsuario) {
		// Primero veo que si esté en la BD
		Optional <Usuario> optUsuario = usuarioRepository.findById(id);
		Usuario usuario = optUsuario.get(); 
		
		Optional <Grupo> optGrupo = grupoRepository.findById(actualizadoUsuario.getGrupo());
		Grupo grupo = optGrupo.get();
			
		if(optGrupo.isEmpty()) {
			throw new IllegalArgumentException("No se encontró el grupo");
		}
		
		usuario.setNombre(actualizadoUsuario.getNombre());
		usuario.setApellido(actualizadoUsuario.getApellido());
		usuario.setEdad(actualizadoUsuario.getEdad());
		usuario.setGrupo(grupo);
			
		usuario = usuarioRepository.save(usuario);
				
		grupo.addUsuario(usuario);
			
		grupoRepository.save(grupo);

		log.info("Haciendo cambios del usuario: "+ usuario.getNombre());
				
		return UsuarioDto.creaDto(usuario);		
	}
	
	
	
	/**
	 * Elimina un usuario con un determinado id
	 * 
	 * @param id
	 * @return
	 */

	public boolean delete(Long id) {
		usuarioRepository.deleteById(id);
		Optional <Usuario> optUsuario = usuarioRepository.findById(id);
		return optUsuario.isPresent();
	}
	
	

}
