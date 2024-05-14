package localdomain.nruano.empresariales.polaflix_ruano_noe_2024.domain.visualizaciones;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonView;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import localdomain.nruano.empresariales.polaflix_ruano_noe_2024.domain.Capitulo;
import localdomain.nruano.empresariales.polaflix_ruano_noe_2024.domain.Serie;
import localdomain.nruano.empresariales.polaflix_ruano_noe_2024.domain.Temporada;
import localdomain.nruano.empresariales.polaflix_ruano_noe_2024.service.api.Views;
import localdomain.nruano.empresariales.polaflix_ruano_noe_2024.service.api.Views.DatosVisualizaciones;

@Entity
public class VisualizacionSerie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @JsonView(Views.DatosVisualizaciones.class)
    @ManyToOne
    private Serie serie;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonView(DatosVisualizaciones.class)
    private Map<Integer, VisualizacionTemporada> visualizacionesTemporadas;

    protected VisualizacionSerie() { }

    public VisualizacionSerie(Serie serie) {
        this.serie = serie;
        this.visualizacionesTemporadas = new HashMap<Integer, VisualizacionTemporada>();
    }

    /**
     * Indica si un capítulo de una temporada de la serie ha sido visualizado por
     * el usuario o no.
     * @param indiceTemporada el índice de la temporada a la que pertenece el
     * capítulo
     * @param indiceCapitulo el índice del capítulo
     * @return true si el capítulo ha sido visualizado, false en caso contrario
     */
    public boolean capituloVisto(int indiceTemporada, int indiceCapitulo) {
        if (visualizacionesTemporadas.get(indiceTemporada).getCapitulosVistos().containsKey(indiceCapitulo))
            return true;
        else
            return false;
    }

    /**
     * Busca la temporada de mayor índice visualizada hasta el momento.
     * @return la temporada de mayor índice visualizada hasta el momento
     */
    public Temporada getUltimaTemporadaVista() {
        if (visualizacionesTemporadas.isEmpty()) return null;

        Temporada t = new Temporada(-1, null);

        for (VisualizacionTemporada vt: visualizacionesTemporadas.values())
            if (vt.getIndice() > t.getIndice())
                t = serie.getTemporadaByIndice(vt.getIndice());

        return (t.getIndice() == -1) ? null : t;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (int) (id ^ (id >>> 32));
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        VisualizacionSerie other = (VisualizacionSerie) obj;
        if (id != other.id)
            return false;
        return true;
    }

    /****** GETTERS ******/

    public Serie getSerie() {
        return serie;
    }

    public Map<Integer, VisualizacionTemporada> getVisualizacionesTemporadas() {
        return visualizacionesTemporadas;
    }

    public VisualizacionTemporada getVisualizacionesTemporada(int indiceTemporada) {
        return visualizacionesTemporadas.get(indiceTemporada);
    }

    /****** SETTERS ******/

    public void setSerie(Serie serie) {
        this.serie = serie;
    }

    public void setVisualizaciones(Map<Integer, VisualizacionTemporada> visualizaciones) {
        this.visualizacionesTemporadas = visualizaciones;
    }

    public void addVisualizacionTemporada(VisualizacionTemporada vt) {
        visualizacionesTemporadas.put(vt.getIndice(), vt);
    }

    public void addVisualizacionCapitulo(Capitulo c) {
        VisualizacionTemporada vt = visualizacionesTemporadas.get(c.getTemporada().getIndice());
        if (vt != null) vt.addVisualizacionCapitulo(new VisualizacionCapitulo(c.getIndice()));
    }

}
