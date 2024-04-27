package localdomain.nruano.empresariales.polaflix_ruano_noe_2024.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import localdomain.nruano.empresariales.polaflix_ruano_noe_2024.dominio.Temporada;

public interface TemporadaRepository extends JpaRepository<Temporada, Long> {
    
    @Query("SELECT t FROM Temporada t WHERE t.serie.id = ?1 AND t.indice = ?2")
    public Temporada findByIdSerieAndIndice(long idSerie, int indice);

    @Query("SELECT t FROM Temporada t WHERE t.serie.titulo = ?1 AND t.indice = ?2")
    public Temporada findByTituloSerieAndIndice(String tituloSerie, int indice);

}
