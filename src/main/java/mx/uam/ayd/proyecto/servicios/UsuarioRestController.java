package mx.uam.ayd.proyecto.servicios;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import mx.uam.ayd.proyecto.dto.UsuarioDto;
import mx.uam.ayd.proyecto.negocio.ServicioUsuario;

@RestController
@RequestMapping("/v1") // Versionamiento
@Slf4j 
public class UsuarioRestController {

	@Autowired
	private ServicioUsuario servicioUsuario;
	
	/**
	 * Permite recuperar todos los usuarios
	 * 
	 * @return
	 */
	@ApiOperation(value = "Recupera usuarios", notes = "Recupera a todos los usuarios")
	@ApiResponses(value = {
		@ApiResponse(code = 200, message = "Usuarios recuperados exitosamente"),
		@ApiResponse(code = 500, message = "Error a recuperar usuarios", response = List.class)})
	@GetMapping(path = "/usuarios", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity <List<UsuarioDto>> retrieveAll() {
		List <UsuarioDto> usuarios = servicioUsuario.recuperaUsuarios();
		
		return ResponseEntity.status(HttpStatus.OK).body(usuarios);
	}
	
	/**
	 * Método que permite agregar un usuario
	 * 
	 * @param nuevoUsuario
	 * @return
	 */
	@ApiOperation(value = "Agrega usuario", notes = "Se agrega a un usuario a través del DTO")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Usuario agregado exitosamente"),
			@ApiResponse(code = 500, message = "Error al agregar usuario")})
	@PostMapping(path = "/usuarios", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<UsuarioDto> creat(@RequestBody @Valid UsuarioDto nuevoUsuario) {
		try {
			UsuarioDto usuarioDto = servicioUsuario.agregaUsuario(nuevoUsuario);
			return ResponseEntity.status(HttpStatus.CREATED).body(usuarioDto);
		} catch (Exception e) {
			HttpStatus status;
			
			if(e instanceof IllegalArgumentException) {
				status = HttpStatus.BAD_REQUEST;
			} else {
				status = HttpStatus.INTERNAL_SERVER_ERROR;
			}
			
			throw new ResponseStatusException(status, e.getMessage());
		}
	}
	
	/**
	 * Permite recuperar un usuario a partir de su ID
	 * 
	 * @param id
	 * @return
	 */
	@ApiOperation(value = "Recupera usuario", notes = "Recupera a un usuario mediante su ID")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Usuario encontrado exitosamente"),
			@ApiResponse(code = 404, message = "No se encontro el usuario"),
			@ApiResponse(code = 500, message = "Error al encontrar usuario")})
	@GetMapping(path = "/usuarios/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity <?> retrieve(@PathVariable("id")  @Valid Long id) {
	
		// HACER
		log.info("Buscando al usuario con id " + id);
		try {
			UsuarioDto usuario = servicioUsuario.retrieve(id);
			
			if(usuario != null) {
				return ResponseEntity.status(HttpStatus.OK).body(usuario);
			} else {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontro usuario");
			}
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontro usuario");
		}
		
	}
	
	
	/**
	 * Método que permite actualizar a un usuario
	 * 
	 * @param usuarioActualizado
	 * @return
	 */
	@ApiOperation(value = "Modifica usuario", notes = "Modifica a un usuario mediante su ID")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Usuario modificado exitosamente"),
			@ApiResponse(code = 404, message = "No se encontro el usuario"),
			@ApiResponse(code = 500, message = "Error al modificar usuario")})
	@PutMapping(path = "/usuarios/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity <?> update(@PathVariable("id") @Valid Long id,@RequestBody UsuarioDto usuarioActualizado) {
		
		
		try {
			UsuarioDto usuario = servicioUsuario.actualizar(id, usuarioActualizado);
			if(usuario != null) {
				return ResponseEntity.status(HttpStatus.OK).body(usuario);			
			} else {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se actualizo el usuario");
			}
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontro usuario");
		}
		
	}
	
	
	/**
	 * Método que permite eliminar a un usuario
	 * Mediante el id
	 * 
	 * @param id
	 * @return
	 */
	@ApiOperation(value = "Elimina usuario", notes = "Elimina a un usuario mediante su ID")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Usuario eliminado exitosamente"),
			@ApiResponse(code = 404, message = "No se encontro el usuario"),
			@ApiResponse(code = 500, message = "Error al eliminar al usuario")})
	@DeleteMapping(path = "/usuarios/{id}")
	public ResponseEntity <?> delete(@PathVariable("id") @Valid Long id) {
		
		log.info("Buscando al usuario con id para eliminarlo: " + id);
		
		try {
			UsuarioDto usuario = servicioUsuario.retrieve(id);
			if (usuario != null) {
				servicioUsuario.delete(id);
				return ResponseEntity.status(HttpStatus.OK).body("Usuario eliminado correctamente");

			} else {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
			}
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontro usuario");
		}
		
	}


	
	
}
