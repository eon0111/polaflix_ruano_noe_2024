package localdomain.nruano.empresariales.polaflix_ruano_noe_2024.service.api;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;

import localdomain.nruano.empresariales.polaflix_ruano_noe_2024.domain.Factura;
import localdomain.nruano.empresariales.polaflix_ruano_noe_2024.domain.Serie;
import localdomain.nruano.empresariales.polaflix_ruano_noe_2024.domain.Usuario;
import localdomain.nruano.empresariales.polaflix_ruano_noe_2024.domain.visualizaciones.VisualizacionSerie;
import localdomain.nruano.empresariales.polaflix_ruano_noe_2024.repositories.UsuarioRepository;
import org.springframework.web.bind.annotation.RequestParam;


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
		if (nombreUsuario == null) return ResponseEntity.badRequest().build();
		
		Usuario u = ur.getReferenceById(nombreUsuario);

		return (u != null) ? ResponseEntity.ok(u) : ResponseEntity.notFound().build();
	}

	@GetMapping(value = "/{nombre}/facturas")
	@JsonView(Views.DatosTemporada.class)
	public ResponseEntity<ArrayList<Factura>> obtenerFacturas(
			@PathVariable("nombre") String nombreUsuario) {
		if (nombreUsuario == null) return ResponseEntity.badRequest().build();

		ArrayList<Factura> f = (ArrayList<Factura>)ur.getReferenceById(nombreUsuario).getFacturas();
		
		return (f != null) ? ResponseEntity.ok(f) : ResponseEntity.notFound().build();
	}

	@GetMapping("/{nombre}/visualizaciones")
	public ResponseEntity<Map<Long, VisualizacionSerie>> obtenerVisualizaciones(
			@RequestParam("nombre") String nombreUsuario) {
		if (nombreUsuario == null)
		return ResponseEntity.badRequest().build();

		HashMap<Long, VisualizacionSerie> v = (HashMap<Long, VisualizacionSerie>) ur.getReferenceById(nombreUsuario)
				.getVisualizacionesSeries();

		return (v != null) ? ResponseEntity.ok(v) : ResponseEntity.notFound().build();
	}

	// TODO: obtenerUltimaTemporadaSerie(long idSerie) -> /{nombre}/seriesEmpezadas/{id}/ultimaTemporada
	// TODO: hacer uno de estos por cada tipo de serie (+ pendientes, + terminadas)

}
