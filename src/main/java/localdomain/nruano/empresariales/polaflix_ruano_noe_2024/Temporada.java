package localdomain.nruano.empresariales.polaflix_ruano_noe_2024;

import java.util.HashMap;

public class Temporada {
    
    private int indice;
    private Serie serie;
	private HashMap<String, Capitulo> capitulos;

    /**
     * Construye una temporada.
     * @param indice El ordinal de la temporada dentro de la serie
     * @param serie La serie a la que pertenece la temporada
     */
    public Temporada(int indice, Serie serie) {
        this.indice = indice;
        this.serie = serie;
		this.capitulos = new HashMap<String, Capitulo>();
    }


    @Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + indice;
		result = prime * result + ((serie == null) ? 0 : serie.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		else if (!(o instanceof Temporada) || o == null)
			return false;
		else if (((Temporada)o).getSerie().equals(this.serie) &&
				((Temporada) o).getIndice() == this.indice)
			return true;
		else return false;
	}

	/****** GETTERS ******/

    public int getIndice() {
        return indice;
    }

    public Serie getSerie() {
        return serie;
    }

	public HashMap<String, Capitulo> getCapitulos() {
		return capitulos;
	}

	public Capitulo getCapitulo(String id) {
		return capitulos.get(id);
	}

    /****** SETTERS ******/

    public void setIndice(int indice) {
        this.indice = indice;
    }

    public void setSerie(Serie serie) {
        this.serie = serie;
    }

	public void addCapitulo(Capitulo c) {
		this.capitulos.put(c.getId(), c);
	}

}
