package localdomain.nruano.empresariales.polaflix_ruano_noe_2024.domain.visualizaciones;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonView;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import localdomain.nruano.empresariales.polaflix_ruano_noe_2024.service.api.Views;

@Entity
public class VisualizacionTemporada {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @JsonView(Views.DatosVisualizaciones.class)
    private int indice;

    @ElementCollection
    @JsonView(Views.DatosVisualizaciones.class)
    private Map<Integer, VisualizacionCapitulo> capitulosVistos;

    public VisualizacionTemporada() { }

    public VisualizacionTemporada(int indice) {
        this.indice = indice;
        this.capitulosVistos = new HashMap<Integer, VisualizacionCapitulo>();
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
        VisualizacionTemporada other = (VisualizacionTemporada) obj;
        if (id != other.id)
            return false;
        return true;
    }

    /****** GETTERS ******/

    public int getIndice() {
        return indice;
    }

    public Map<Integer, VisualizacionCapitulo> getCapitulosVistos() {
        return capitulosVistos;
    }

    /****** SETTERS ******/

    public void setIndice(int indice) {
        this.indice = indice;
    }

    public void setCapitulosVistos(Map<Integer, VisualizacionCapitulo> capitulosVistos) {
        this.capitulosVistos = capitulosVistos;
    }

    public void addVisualizacionCapitulo(VisualizacionCapitulo vc) {
        capitulosVistos.put(vc.getIndice(), vc);
    }

}
