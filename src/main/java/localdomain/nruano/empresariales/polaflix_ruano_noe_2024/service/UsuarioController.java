package localdomain.nruano.empresariales.polaflix_ruano_noe_2024.service;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;

import jakarta.transaction.Transactional;
import localdomain.nruano.empresariales.polaflix_ruano_noe_2024.domain.Usuario;
import localdomain.nruano.empresariales.polaflix_ruano_noe_2024.domain.facturacion.Factura;
import localdomain.nruano.empresariales.polaflix_ruano_noe_2024.domain.series.Capitulo;
import localdomain.nruano.empresariales.polaflix_ruano_noe_2024.domain.series.Serie;
import localdomain.nruano.empresariales.polaflix_ruano_noe_2024.domain.series.Temporada;
import localdomain.nruano.empresariales.polaflix_ruano_noe_2024.domain.visualizaciones.VisualizacionCapitulo;
import localdomain.nruano.empresariales.polaflix_ruano_noe_2024.domain.visualizaciones.VisualizacionSerie;
import localdomain.nruano.empresariales.polaflix_ruano_noe_2024.repositories.SerieRepository;
import localdomain.nruano.empresariales.polaflix_ruano_noe_2024.repositories.UsuarioRepository;


@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

	@Autowired
	UsuarioRepository ur;

	@Autowired
	SerieRepository sr;

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

	@Transactional
	@PostMapping
	public ResponseEntity<Usuario> anhadirUsuario(
			@RequestBody @JsonView(Views.NuevoUsuario.class) Usuario u) {

		ResponseEntity<Usuario> result;

		// TODO: comprobar que ninguno de los atributos requeridos del usuario sea null y elaborar la respuesta HTTP que corresponda en caso de serlo
		
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

	@Transactional
	@DeleteMapping(value = "/{nombre}")
	public ResponseEntity<Usuario> eliminarUsuario(@PathVariable("nombre") String nombre) {

		ResponseEntity<Usuario> result;
		
		if (!ur.existsById(nombre)) {
			result = ResponseEntity.status(HttpStatus.FORBIDDEN).build();
		} else {
			ur.deleteById(nombre);
			result = ResponseEntity.ok(null);
		}
		
		return result;
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
	@JsonView(Views.DatosVisualizacion.class)
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

	@Transactional
	@PutMapping(value = "/{nombre}/visualizaciones/{idSerie}/{indTemp}/{indCap}")
	@JsonView(Views.DatosVisualizacion.class)
	public ResponseEntity<VisualizacionCapitulo> registrarVisualizacion(
			@PathVariable("nombre") String nombreUsuario,
			@PathVariable("idSerie") Long idSerie,
			@PathVariable("indTemp") int indTemp,
			@PathVariable("indCap") int indCap) {

		if (nombreUsuario == null || idSerie == null)
			return ResponseEntity.badRequest().build();

		Usuario u = ur.findByNombre(nombreUsuario);
		if (u == null) return ResponseEntity.notFound().build();

		Serie s = sr.findById(idSerie).get();
		if (s == null) return ResponseEntity.notFound().build();

		Temporada t = s.getTemporada(indTemp);
		if (t == null) return ResponseEntity.notFound().build();

		Capitulo c = t.getCapitulo(indCap);
		if (c == null) return ResponseEntity.notFound().build();

		/* Una vez realizadas todas las comprobaciones se registra la visualización */
		VisualizacionCapitulo vc = u.registraVisualizacion(c);

		return (vc != null) ? ResponseEntity.ok(vc) : ResponseEntity.notFound().build();
	}

	@Transactional
	@PutMapping(value = "/{nombre}/series-pendientes/{idSerie}")
	@JsonView(Views.DatosSerie.class)
	public ResponseEntity<Serie> anhadirSeriePendiente(
			@PathVariable("nombre") String nombreUsuario,
			@PathVariable("idSerie") Long idSerie) {

		if (nombreUsuario == null || idSerie == null)
			return ResponseEntity.badRequest().build();

		Usuario u = ur.findByNombre(nombreUsuario);
		if (u == null) return ResponseEntity.notFound().build();

		Serie s = sr.findById(idSerie).get();
		if (s == null) return ResponseEntity.notFound().build();

		if (u.getSeriesPendientes().containsKey(idSerie) ||
				u.getSeriesEmpezadas().containsKey(idSerie) ||
				u.getSeriesTerminadas().containsKey(idSerie)) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
		}

		/* Una vez realizadas todas las comprobaciones se registra la serie como
		 * pendiente */
		u.addSeriePendiente(s);

		return ResponseEntity.ok(s);
	}

}
