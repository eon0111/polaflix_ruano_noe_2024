package localdomain.nruano.empresariales.polaflix_ruano_noe_2024.domain.visualizaciones;

import com.fasterxml.jackson.annotation.JsonView;

import jakarta.persistence.Embeddable;
import localdomain.nruano.empresariales.polaflix_ruano_noe_2024.service.Views;

@Embeddable
public class VisualizacionCapitulo {

    @JsonView(Views.DatosVisualizacion.class)
    private int indice;

    protected VisualizacionCapitulo() { }

    public VisualizacionCapitulo(int indice) {
        this.indice = indice;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + indice;
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
        VisualizacionCapitulo other = (VisualizacionCapitulo) obj;
        if (indice != other.indice)
            return false;
        return true;
    }

    /****** GETTERS ******/

    public int getIndice() {
        return indice;
    }

    /****** SETTERS ******/

    public void setIndice(int indice) {
        this.indice = indice;
    }

}
