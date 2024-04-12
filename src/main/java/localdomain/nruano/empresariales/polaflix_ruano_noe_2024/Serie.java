package localdomain.nruano.empresariales.polaflix_ruano_noe_2024;

import java.util.ArrayList;
import java.util.Set;
import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;

@Entity
public class Serie {

	@Id
	@GeneratedValue (strategy = GenerationType.AUTO)
	private UUID id;

	private String titulo;
	private CategoriaSerie categoria;
	private String sinopsis;

	@OneToMany
	private ArrayList<Temporada> temporadas;

	@ManyToMany
	private Set<PersonalSerie> creadores;

	@ManyToMany
	private Set<PersonalSerie> actores;

	/**
	 * Construye una serie.
	 * @param id el id de la serie
	 * @param titulo el titulo de la serie
	 * @param categoria la categoria de la serie
	 * @param sinopsis la sinopsis de la serie
	 */
	public Serie(String titulo, CategoriaSerie categoria, String sinopsis) {
		this.titulo = titulo;
		this.categoria = categoria;
		this.sinopsis = sinopsis;
	}

	@Override
	public int hashCode() {
		return id.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;

		Serie other = (Serie) obj;
		if (id == null && (other.id != null))
			return false;
		else if (!id.equals(other.id))
			return false;
		
		return true;
	}

	/****** GETTERS ******/

	public UUID getId() {
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

	public Set<PersonalSerie> getCreadores() {
		return creadores;
	}

	public Set<PersonalSerie> getActores() {
		return actores;
	}

	/****** SETTERS ******/

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

	public void addCreador(PersonalSerie c) {
		this.creadores.add(c);
	}

	public void addActor(PersonalSerie a) {
		this.actores.add(a);
	}

}
