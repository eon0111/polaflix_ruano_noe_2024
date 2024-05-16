package localdomain.nruano.empresariales.polaflix_ruano_noe_2024.service;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
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
	public ResponseEntity<Optional<Usuario>> obtenerUsuario(@PathVariable("nombre") String nombreUsuario) {

		if (nombreUsuario == null)
			return ResponseEntity.badRequest().build();
		
		Optional<Usuario> u = ur.findById(nombreUsuario);

		return (u.isPresent()) ? ResponseEntity.ok(u) : ResponseEntity.notFound().build();
	}

	@GetMapping(value = "/{nombre}/facturas")
	@JsonView(Views.DatosFacturas.class)
	public ResponseEntity<List<Factura>> obtenerFacturas(
			@PathVariable("nombre") String nombreUsuario) {

		if (nombreUsuario == null)
			return ResponseEntity.badRequest().build();

		Optional<Usuario> u = ur.findById(nombreUsuario);
		if (!u.isPresent()) return ResponseEntity.notFound().build();

		List<Factura> f = ur.getReferenceById(nombreUsuario).getFacturas();
		return ResponseEntity.ok(f);
	}

	@GetMapping(value = "/{nombre}/visualizaciones")
	@JsonView(Views.DatosVisualizacion.class)
	public ResponseEntity<Map<Long, VisualizacionSerie>> obtenerVisualizaciones(
			@PathVariable("nombre") String nombreUsuario) {

		if (nombreUsuario == null)
			return ResponseEntity.badRequest().build();

		Optional<Usuario> u = ur.findById(nombreUsuario);
		if (!u.isPresent()) return ResponseEntity.notFound().build();

		Map<Long, VisualizacionSerie> v = ur.getReferenceById(nombreUsuario)
											.getVisualizacionesSeries();
		return ResponseEntity.ok(v);
	}

	@GetMapping(value = "/{nombre}/series/{id}")
	@JsonView(Views.DatosTemporada.class)
	public ResponseEntity<Temporada> obtenerUltimaTemporadaSerie(
			@PathVariable("nombre") String nombreUsuario,
			@PathVariable("id") Long idSerie) {

		if (nombreUsuario == null || idSerie == null)
			return ResponseEntity.badRequest().build();

		Optional<Usuario> u = ur.findById(nombreUsuario);
		if (!u.isPresent()) return ResponseEntity.notFound().build();

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

		Optional<Serie> s = sr.findById(idSerie);
		if (!s.isPresent()) return ResponseEntity.notFound().build();

		Temporada t = s.get().getTemporada(indTemp);
		if (t == null) return ResponseEntity.notFound().build();

		Capitulo c = t.getCapitulo(indCap);
		if (c == null) return ResponseEntity.notFound().build();

		Optional<Usuario> u = ur.findById(nombreUsuario);
		if (!u.isPresent()) return ResponseEntity.notFound().build();

		/* Una vez realizadas todas las comprobaciones se registra la visualización */
		u.get().registraVisualizacion(c);
		return ResponseEntity.ok(null);
	}

	@Transactional
	@PutMapping(value = "/{nombre}/series-pendientes/{idSerie}")
	@JsonView(Views.DatosSerie.class)
	public ResponseEntity<Serie> anhadirSeriePendiente(
			@PathVariable("nombre") String nombreUsuario,
			@PathVariable("idSerie") Long idSerie) {

		if (nombreUsuario == null || idSerie == null)
			return ResponseEntity.badRequest().build();

		Optional<Usuario> u = ur.findById(nombreUsuario);
		if (!u.isPresent()) return ResponseEntity.notFound().build();

		Optional<Serie> s = sr.findById(idSerie);
		if (!s.isPresent()) return ResponseEntity.notFound().build();

		if (u.get().getSeriesPendientes().containsKey(idSerie)) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
		}

		/* Una vez realizadas todas las comprobaciones se registra la serie como
		 * pendiente */
		u.get().addSeriePendiente(s.get());

		return ResponseEntity.ok(s.get());
	}

}
