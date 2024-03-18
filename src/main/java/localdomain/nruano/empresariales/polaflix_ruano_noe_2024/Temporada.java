package localdomain.nruano.empresariales.polaflix_ruano_noe_2024;

public class Temporada {
    
    private int indice;
    private Serie serie;

    /**
     * Construye una temporada.
     * @param indice El ordinal de la temporada dentro de la serie
     * @param serie La serie a la que pertenece la temporada
     */
    public Temporada(int indice, Serie serie) {
        this.indice = indice;
        this.serie = serie;
    }

    /****** GETTERS ******/

    public int getIndice() {
        return indice;
    }

    public Serie getSerie() {
        return serie;
    }

    /****** SETTERS ******/

    public void setIndice(int indice) {
        this.indice = indice;
    }

    public void setSerie(Serie serie) {
        this.serie = serie;
    }

}
