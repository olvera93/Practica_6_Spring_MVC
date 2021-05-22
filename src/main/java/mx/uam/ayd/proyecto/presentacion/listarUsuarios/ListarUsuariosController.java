package mx.uam.ayd.proyecto.presentacion.listarUsuarios;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
import mx.uam.ayd.proyecto.negocio.ServicioUsuario;
import mx.uam.ayd.proyecto.negocio.modelo.Usuario;
import mx.uam.ayd.proyecto.presentacion.agregarUsuario.AgregarUsuarioController;

@Controller
@Slf4j
public class ListarUsuariosController {
	
	@Autowired
	private ServicioUsuario servicioUsuario;
	
	@RequestMapping(value = "/listarUsuarios", method = RequestMethod.GET)
	public String getListarUsuario(
			Model model) {
			
		
		try {
			//invocacion al servicio
			List<Usuario> usuario = servicioUsuario.recuperaUsuarios();
			//Agregamos el usuario al modelo que se le pasa a la vista
			model.addAttribute("usuario", usuario);
			
			//Redirigimos a la vista de éxito
			return "vistaListarUsuarios/ListarUsuarios";
			
		} catch (Exception e) {
			
			// Agregamos el mensaje de error al modelo
			model.addAttribute("error", e.getMessage());
			
			// Redirigimos a la vista de error
			return "vistaAgregarUsuario/AgregarUsuarioError";
		}
	}
	
	

}
