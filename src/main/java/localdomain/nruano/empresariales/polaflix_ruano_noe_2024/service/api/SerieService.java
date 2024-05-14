package localdomain.nruano.empresariales.polaflix_ruano_noe_2024.service.api;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import localdomain.nruano.empresariales.polaflix_ruano_noe_2024.domain.Serie;
import localdomain.nruano.empresariales.polaflix_ruano_noe_2024.repositories.SerieRepository;

@Service
public class SerieService {

    @Autowired
    SerieRepository sr;

    public ArrayList<Serie> getSeriesByInicial(char inicial) {
        return sr.findByTituloStartingWith(inicial);
    }

}
