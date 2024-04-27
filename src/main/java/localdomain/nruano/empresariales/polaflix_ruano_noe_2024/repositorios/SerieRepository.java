package localdomain.nruano.empresariales.polaflix_ruano_noe_2024.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;

import localdomain.nruano.empresariales.polaflix_ruano_noe_2024.dominio.Serie;

public interface SerieRepository extends JpaRepository<Serie, Long> {

    public Serie findByTitulo(String titulo);

}
