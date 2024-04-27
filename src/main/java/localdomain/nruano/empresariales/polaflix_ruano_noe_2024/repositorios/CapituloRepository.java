package localdomain.nruano.empresariales.polaflix_ruano_noe_2024.repositorios;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import localdomain.nruano.empresariales.polaflix_ruano_noe_2024.dominio.Capitulo;

public interface CapituloRepository extends JpaRepository<Capitulo, Long> {

    @Query("SELECT c FROM Capitulo c WHERE c.temporada.serie.id = ?1 AND c.temporada.indice = ?2")
    public ArrayList<Capitulo> findByIdSerieAndIndTemp(long idSerie, int indiceTemporada);

    @Query("SELECT c FROM Capitulo c WHERE c.temporada.serie.titulo = ?1 AND c.temporada.indice = ?2")
    public ArrayList<Capitulo> findByTituloSerieAndIndTemp(String tituloSerie, int indiceTemporada);

    @Query("SELECT c FROM Capitulo c WHERE c.temporada.serie.titulo = ?1 AND c.temporada.indice = ?2 AND c.indice = ?3")
    public Capitulo findByTituloSerieAndIndTempAndIndCap(String titSerie, int indTemp, int indCap);

}
