package localdomain.nruano.empresariales.polaflix_ruano_noe_2024.service;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

    @Transactional
	@PostMapping
	public ResponseEntity<Serie> anhadirSerie(
			@RequestBody @JsonView(Views.NuevaSerie.class) Serie s) {

        ResponseEntity<Serie> result;

        if (s == null) {
            result = ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        } else {
            s = sr.save(s);
            
            URI resourceLink = null;
            try {
                resourceLink = new URI("/series/" + s.getId());
            } catch (URISyntaxException e) { }
            
            result = ResponseEntity.created(resourceLink).build();
        }
        
        return result;
	}

    @Transactional
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Serie> eliminarSerie(@PathVariable("id") Long id) {

		ResponseEntity<Serie> result;
		
		if (!sr.existsById(id)) {
			result = ResponseEntity.status(HttpStatus.FORBIDDEN).build();
		} else {
			sr.deleteById(id);
			result = ResponseEntity.ok(null);
		}
		
		return result;
	}

}
