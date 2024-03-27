package localdomain.nruano.empresariales.polaflix_ruano_noe_2024;

import java.util.ArrayList;
import java.util.Set;

public class Serie {

	private String id;
	private String titulo;
	private CategoriaSerie categoria;
	private String sinopsis;
	private ArrayList<Temporada> temporadas;
	private Set<PersonalSerie> personalSerie;

	/**
	 * Construye una serie.
	 * @param id el id de la serie
	 * @param titulo el titulo de la serie
	 * @param categoria la categoria de la serie
	 * @param sinopsis la sinopsis de la serie
	 */
	public Serie(String id, String titulo, CategoriaSerie categoria,
				 String sinopsis) {
		this.id = id;
		this.titulo = titulo;
		this.categoria = categoria;
		this.sinopsis = sinopsis;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		else if (!(o instanceof Serie) || o == null)
			return false;
		else if (((Serie)o).getId() == this.id)
			return true;
		else return false;
	}

	/****** GETTERS ******/

	public String getId() {
		return id;
	}

	public String getTitulo() {
		return titulo;
	}

	public CategoriaSerie getCategoria() {
		return categoria;
	}

	public String getSinopsis() {
		return sinopsis;
	}

	public ArrayList<Temporada> getTemporadas() {
		return temporadas;
	}

	public int getNumTemporadas() {
		return temporadas.size();
	}

	public Set<PersonalSerie> getPersonalSerie() {
		return personalSerie;
	}

	/****** SETTERS ******/

	public void setId(String id) {
		this.id = id;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public void setCategoria(CategoriaSerie categoria) {
		this.categoria = categoria;
	}

	public void setSinopsis(String sinopsis) {
		this.sinopsis = sinopsis;
	}

	public void addTemporada(Temporada temporada) {
		this.temporadas.add(temporada);
	}

	public void addPersonalSerie(PersonalSerie personalSerie) {
		this.personalSerie.add(personalSerie);
	}

}
