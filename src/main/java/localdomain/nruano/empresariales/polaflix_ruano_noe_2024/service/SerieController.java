package localdomain.nruano.empresariales.polaflix_ruano_noe_2024.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;

import localdomain.nruano.empresariales.polaflix_ruano_noe_2024.domain.series.Serie;
import localdomain.nruano.empresariales.polaflix_ruano_noe_2024.repositories.SerieRepository;

@RestController
@RequestMapping("/series")
public class SerieController {

    @Autowired
    SerieRepository sr;

    @GetMapping(params = "titulo")
    @JsonView(Views.DatosSerie.class)
    public ResponseEntity<ArrayList<Serie>> obtenerSeriesPorTitulo(
            @RequestParam(required = true) String titulo) {

        ArrayList<Serie> s = sr.findByTitulo(titulo);

		return (!s.isEmpty()) ? ResponseEntity.ok(s) : ResponseEntity.notFound().build();
    }

    @GetMapping(params = "inicial")
    @JsonView(Views.DatosSerie.class)
    public ResponseEntity<ArrayList<Serie>> obtenerSeriesPorInicial(
            @RequestParam(required = true) char inicial) {

        ArrayList<Serie> s = sr.findByTituloStartingWith(
                (Character.isLowerCase(inicial)) ? Character.toUpperCase(inicial) : inicial);

		return (!s.isEmpty()) ? ResponseEntity.ok(s) : ResponseEntity.notFound().build();
    }

}
