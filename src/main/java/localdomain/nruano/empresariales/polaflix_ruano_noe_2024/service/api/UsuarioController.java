package localdomain.nruano.empresariales.polaflix_ruano_noe_2024.service.api;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;

import localdomain.nruano.empresariales.polaflix_ruano_noe_2024.domain.Usuario;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    UsuarioService us;

    /**
     * Busca un usuario en la plataforma.
     * @param nombreUsuario el ID (nombre) del usuario a buscar
     * @return la respuesta HTTP que corresponda al resultado de la búsqueda (200
     * si existe algún usuario en la plataforma con el nombre indicado, o 404 en
     * caso contrario)
     */
    @GetMapping(value = "/{nombre}")
	@JsonView(Views.DatosUsuario.class)
	public ResponseEntity<Usuario> obtenerUsuario(@PathVariable("nombre") String nombreUsuario) {
		Optional<Usuario> u = us.getUsuarioByNombre(nombreUsuario);
		ResponseEntity<Usuario> result;
		
		if (u.isPresent()) {
			result = ResponseEntity.ok(u.get());
		} else { 
			result = ResponseEntity.notFound().build();
		}

		return result; 	
	}
/*
	@GetMapping(value = "/{nombre}/seriesEmpezadas/{id}")
	@JsonView(Views.DatosTemporada.class)
	public ResponseEntity<Temporada> obtenerTemporadaSerieEmpezada(
			@PathVariable("nombre") String nombreUsuario,
			@PathVariable("id") long idSerie) {
		ResponseEntity<Usuario> result;
		Optional<Usuario> u = us.getUsuarioByNombre(nombreUsuario);

		if (u.isPresent()) {
			
		}




		return null;
	}
*/
}
