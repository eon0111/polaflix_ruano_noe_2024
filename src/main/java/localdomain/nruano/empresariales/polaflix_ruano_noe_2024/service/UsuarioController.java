package localdomain.nruano.empresariales.polaflix_ruano_noe_2024.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;

import jakarta.transaction.Transactional;
import localdomain.nruano.empresariales.polaflix_ruano_noe_2024.domain.Capitulo;
import localdomain.nruano.empresariales.polaflix_ruano_noe_2024.domain.Factura;
import localdomain.nruano.empresariales.polaflix_ruano_noe_2024.domain.Serie;
import localdomain.nruano.empresariales.polaflix_ruano_noe_2024.domain.Temporada;
import localdomain.nruano.empresariales.polaflix_ruano_noe_2024.domain.Usuario;
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

	@PutMapping(value = "/{nombre}/visualizaciones/{idSerie}/{indTemp}/{indCap}")
	@JsonView(Views.DatosVisualizacion.class)
	@Transactional
	public ResponseEntity<VisualizacionCapitulo> registraVisualizacion(
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

	// TODO: anhadeUsuario

	// TODO: anhadeSeriePendiente

}
