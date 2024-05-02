package localdomain.nruano.empresariales.polaflix_ruano_noe_2024.service.api;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;

import localdomain.nruano.empresariales.polaflix_ruano_noe_2024.domain.Usuario;
import localdomain.nruano.empresariales.polaflix_ruano_noe_2024.repositories.UsuarioRepository;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    UsuarioRepository ur;

    /**
     * Busca un usuario en la plataforma.
     * @param idUsuario el ID (nombre) del usuario a buscar
     * @return la respuesta HTTP que corresponda al resultado de la búsqueda (200
     * si existe algún usuario en la plataforma con el nombre indicado, o 404 en
     * caso contrario)
     */
    @GetMapping(value="/{nombre}")
	@JsonView(Views.DatosUsuario.class)
	public ResponseEntity<Usuario> obtenerUsuario(@PathVariable("nombre") String nombreUsuario) {
		Optional<Usuario> u = ur.findById(nombreUsuario);
		ResponseEntity<Usuario> result;
		
		if (u.isPresent()) {
			result = ResponseEntity.ok(u.get());
		} else { 
			result = ResponseEntity.notFound().build();
		}

		return result; 	
	}

    /**
     * Registra un nuevo usuario en la plataforma.
     * @param u el nuevo usuario a registrar
     * @return la respuesta HTTP que corresponda al resultado del registro (201
     * si el registro fue correcto, 403 en caso contrario)
     */
    @PostMapping
	public ResponseEntity<Usuario> anhadirUsuario(@RequestBody @JsonView(Views.NuevoUsuario.class) Usuario u) {
		ResponseEntity<Usuario> result;
		
		if (ur.existsById(u.getNombre())) {
			result = ResponseEntity.status(HttpStatus.FORBIDDEN).build();
		} else {
			u = ur.save(u);
			URI resourceLink = null;

			try {
				resourceLink = new URI("/usuarios/" + u.getNombre());
			} catch (URISyntaxException e) { }

			result = ResponseEntity.created(resourceLink).build();
		}
		
		return result;
	}

}
