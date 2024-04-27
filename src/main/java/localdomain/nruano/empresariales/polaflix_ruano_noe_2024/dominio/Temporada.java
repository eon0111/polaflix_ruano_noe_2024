package localdomain.nruano.empresariales.polaflix_ruano_noe_2024.dominio;

import java.util.HashMap;
import java.util.Map;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
public class Temporada {
    
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)	// FIXME: igual hay que poner IDENTITY en todas las clases
	private long id;
    private int indice;

	@ManyToOne
    private Serie serie;

	@OneToMany(mappedBy = "temporada", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Map<Long, Capitulo> capitulos;

	/**
	 * Constructor vacio.
	 */
	protected Temporada() { }

    /**
     * Construye una temporada.
     * @param indice El ordinal de la temporada dentro de la serie
     * @param serie La serie a la que pertenece la temporada
     */
    public Temporada(int indice, Serie serie) {
        this.indice = indice;
        this.serie = serie;
		this.capitulos = new HashMap<Long, Capitulo>();
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

	public Map<Long, Capitulo> getCapitulos() {
		return capitulos;
	}

	public Capitulo getCapitulo(long id) {
		return capitulos.get(id);
	}

    /****** SETTERS ******/

    public void setIndice(int indice) {
        this.indice = indice;
    }

    public void setSerie(Serie serie) {
        this.serie = serie;
    }

	public void setCapitulos(Map<Long, Capitulo> capitulos) {
		this.capitulos = capitulos;
	}

	public void addCapitulo(Capitulo c) {
		this.capitulos.put(c.getId(), c);
	}

}
