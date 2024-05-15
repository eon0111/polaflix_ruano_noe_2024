package localdomain.nruano.empresariales.polaflix_ruano_noe_2024.service;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;

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

import jakarta.transaction.Transactional;
import localdomain.nruano.empresariales.polaflix_ruano_noe_2024.domain.Usuario;
import localdomain.nruano.empresariales.polaflix_ruano_noe_2024.domain.series.Serie;
import localdomain.nruano.empresariales.polaflix_ruano_noe_2024.repositories.SerieRepository;

@RestController
@RequestMapping("/series")
public class SerieController {

    @Autowired
    SerieRepository sr;

    @GetMapping(value = "/{titulo}")
    @JsonView(Views.DatosSerie.class)
    public ResponseEntity<ArrayList<Serie>> obtenerSeriesPorTitulo(
            @PathVariable("titulo") String titulo) {

        ArrayList<Serie> s = sr.findByTitulo(titulo);

		return (!s.isEmpty()) ? ResponseEntity.ok(s) : ResponseEntity.notFound().build();
    }

    @GetMapping(value = "/{inicial}")
    @JsonView(Views.DatosSerie.class)
    public ResponseEntity<ArrayList<Serie>> obtenerSeriesPorInicial(
            @PathVariable("inicial") char inicial) {

        ArrayList<Serie> s = sr.findByTituloStartingWith(inicial);

		return (!s.isEmpty()) ? ResponseEntity.ok(s) : ResponseEntity.notFound().build();
    }

    @Transactional
	@PostMapping
	public ResponseEntity<Serie> anhadirSerie(
			@RequestBody @JsonView(Views.NuevaSerie.class) Serie s) {

        // TODO: implementar (+ comprobar que ninguno de los atributos requeridos de la serie sea null)

        return null;
	}

    // TODO: anhadeSerie

    // TODO: eliminaSerie

}
