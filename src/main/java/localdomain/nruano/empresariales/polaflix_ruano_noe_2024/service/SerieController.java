package localdomain.nruano.empresariales.polaflix_ruano_noe_2024.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;

import localdomain.nruano.empresariales.polaflix_ruano_noe_2024.domain.Serie;
import localdomain.nruano.empresariales.polaflix_ruano_noe_2024.repositories.SerieRepository;

@RestController
@RequestMapping("/series")
public class SerieController {

    @Autowired
    SerieRepository sr;

    @GetMapping(value = "/{inicial}")
    @JsonView(Views.DatosSerie.class)
    public ResponseEntity<ArrayList<Serie>> obtenerSeriesPorInicial(@PathVariable("inicial") char inicial) {
        ArrayList<Serie> s = sr.findByTituloStartingWith(inicial);
        ResponseEntity<ArrayList<Serie>> result;

		if (!s.isEmpty()) {
			result = ResponseEntity.ok(s);
		} else { 
			result = ResponseEntity.notFound().build();
		}

		return result; 	
    }

    // TODO: anhadeSerie

}
