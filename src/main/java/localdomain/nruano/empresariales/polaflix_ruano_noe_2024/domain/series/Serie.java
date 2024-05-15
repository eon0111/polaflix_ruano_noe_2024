package localdomain.nruano.empresariales.polaflix_ruano_noe_2024.domain.series;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonView;

import jakarta.persistence.CascadeType;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import localdomain.nruano.empresariales.polaflix_ruano_noe_2024.service.Views;

@Entity
public class Serie {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@JsonView({ Views.DatosSerie.class,
				Views.DatosUsuario.class,
				Views.DatosTemporada.class,
				Views.DatosVisualizacion.class })
	private String titulo;

	@Enumerated(EnumType.STRING)
	private CategoriaSerie categoria;

	@JsonView(Views.DatosSerie.class)
	private String sinopsis;

	@OneToMany(mappedBy = "serie", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<Temporada> temporadas;

	@ElementCollection(fetch = FetchType.LAZY)
	private Set<PersonalSerie> creadores;

	@ElementCollection(fetch = FetchType.LAZY)
	private Set<PersonalSerie> actores;

	/**
	 * Constructor vacio.
	 */
	protected Serie() { }

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
		this.temporadas = new ArrayList<Temporada>();
		this.creadores = new HashSet<PersonalSerie>();
		this.actores = new HashSet<PersonalSerie>();
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
		Serie other = (Serie) obj;
		if (id != other.id)
			return false;
		return true;
	}

	/****** GETTERS ******/

	public long getId() {
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

	public List<Temporada> getTemporadas() {
		return temporadas;
	}

	public Temporada getTemporada(int indice) {
		return (indice < 1 || indice > getNumTemporadas()) ? null : temporadas.get(indice - 1);
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

	public void setId(long id) {
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

	public void setTemporadas(List<Temporada> temporadas) {
		this.temporadas = temporadas;
	}

	public void addTemporada(Temporada temporada) {
		this.temporadas.add(temporada);
	}

	public void setCreadores(Set<PersonalSerie> creadores) {
		this.creadores = creadores;
	}

	public void addCreador(PersonalSerie c) {
		this.creadores.add(c);
	}

	public void setActores(Set<PersonalSerie> actores) {
		this.actores = actores;
	}

	public void addActor(PersonalSerie a) {
		this.actores.add(a);
	}

}
