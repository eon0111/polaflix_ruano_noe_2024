package localdomain.nruano.empresariales.polaflix_ruano_noe_2024.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;

import localdomain.nruano.empresariales.polaflix_ruano_noe_2024.domain.Factura;
import localdomain.nruano.empresariales.polaflix_ruano_noe_2024.domain.Temporada;
import localdomain.nruano.empresariales.polaflix_ruano_noe_2024.domain.Usuario;
import localdomain.nruano.empresariales.polaflix_ruano_noe_2024.domain.visualizaciones.VisualizacionSerie;
import localdomain.nruano.empresariales.polaflix_ruano_noe_2024.repositories.UsuarioRepository;


@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

	@Autowired
	UsuarioRepository ur;

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
		if (nombreUsuario == null)
			return ResponseEntity.badRequest().build();
		
		Usuario u = ur.getReferenceById(nombreUsuario);

		return (u != null) ? ResponseEntity.ok(u) : ResponseEntity.notFound().build();
	}

	@GetMapping(value = "/{nombre}/facturas")
	@JsonView(Views.DatosFacturas.class)
	public ResponseEntity<List<Factura>> obtenerFacturas(
			@PathVariable("nombre") String nombreUsuario) {
		if (nombreUsuario == null)
			return ResponseEntity.badRequest().build();

		List<Factura> f = ur.getReferenceById(nombreUsuario).getFacturas();
		
		return (f != null) ? ResponseEntity.ok(f) : ResponseEntity.notFound().build();
	}

	@GetMapping(value = "/{nombre}/visualizaciones")
	@JsonView(Views.DatosVisualizaciones.class)
	public ResponseEntity<Map<Long, VisualizacionSerie>> obtenerVisualizaciones(
			@PathVariable("nombre") String nombreUsuario) {
		if (nombreUsuario == null)
			return ResponseEntity.badRequest().build();

		Map<Long, VisualizacionSerie> v = ur.getReferenceById(nombreUsuario)
				.getVisualizacionesSeries();

		return (v != null) ? ResponseEntity.ok(v) : ResponseEntity.notFound().build();
	}

	@GetMapping(value = "/{nombre}/series/{id}")
	@JsonView(Views.DatosTemporada.class)
	public ResponseEntity<Temporada> obtenerUltimaTemporadaSerie(
			@PathVariable("nombre") String nombreUsuario,
			@PathVariable("id") Long idSerie) {
		if (nombreUsuario == null || idSerie == null)
			return ResponseEntity.badRequest().build();

		Temporada t = ur.getReferenceById(nombreUsuario).getUltimaTemporadaSerie(idSerie);

		return (t != null) ? ResponseEntity.ok(t) : ResponseEntity.notFound().build();
	}

	// TODO: registraVisualizacion

	// TODO: anhadeSeriePendiente

}
