package mx.uam.ayd.proyecto.presentacion.agregarUsuario;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.extern.slf4j.Slf4j;
import mx.uam.ayd.proyecto.dto.UsuarioDto;
import mx.uam.ayd.proyecto.negocio.ServicioGrupo;
import mx.uam.ayd.proyecto.negocio.ServicioUsuario;
import mx.uam.ayd.proyecto.negocio.modelo.Grupo;
import mx.uam.ayd.proyecto.negocio.modelo.Usuario;

@Controller
@Slf4j
public class AgregarUsuarioController {

	@Autowired
	private ServicioUsuario servicioUsuario;
	
	@Autowired
	private ServicioGrupo servicioGrupo;
	
	/**
	 * 
	 * Método invocado cuando se hace una petición GET a la ruta
	 * 
	 * @param model
	 * @return
	 */
	
	@RequestMapping(value="/agregarUsuario", method = RequestMethod.GET)
	public String getAgregarUsuario(Model model) {
		log.info("Iniciando Historia de usuario: Agrega Usuario");
		try {
			//invocacion al servicio
			List<Grupo> grupo = servicioGrupo.recuperaGrupos();
			//Agregamos el usuario al modelo que se le pasa a la vista
			model.addAttribute("grupo", grupo);
			log.info("Recuperando grupos: ", grupo);
			
			//Redirigimos a la vista de éxito
			return "vistaAgregarUsuario/FormaAgregarUsuario";	
			
		} catch (Exception e) {
			
			// Agregamos el mensaje de error al modelo
			model.addAttribute("error", e.getMessage());
			
			// Redirigimos a la vista de error
			return "vistaAgregarUsuario/AgregarUsuarioError";
		}
		
	}
	
	
	/**
	 * 
	 * Método invocado cuando se hace una petición POST a la ruta
	 * 
	 * @param nombre
	 * @param apellido
	 * @param grupo
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/agregarUsuario", method = RequestMethod.POST)
	public String postAgregarUsuario(
			@RequestParam(value = "nombre", required = true) String nombre,
			@RequestParam(value = "apellido", required = true) String apellido,
			@RequestParam(value = "edad", required = true) int edad,
			@RequestParam(value = "grupo", required = true) String grupo,
			Model model) {
			
		log.info("Agregando Usuario: " + nombre + " " + apellido + " " + edad + " " + grupo);
		
		


		try {
			Grupo grupo2 = servicioGrupo.recuperaGrupo(grupo);
			//invocacion al servicio
			UsuarioDto usuario = servicioUsuario.agregaUsuario(UsuarioDto.creaDto(new Usuario(nombre, apellido, edad, grupo2)));
			//Agregamos el usuario al modelo que se le pasa a la vista
			model.addAttribute("usuario", usuario);
			
			//Redirigimos a la vista de éxito
			return "vistaAgregarUsuario/AgregarUsuarioExito";
			
		} catch (Exception e) {
			
			// Agregamos el mensaje de error al modelo
			model.addAttribute("error", e.getMessage());
			
			// Redirigimos a la vista de error
			return "vistaAgregarUsuario/AgregarUsuarioError";
		}
	}

}
