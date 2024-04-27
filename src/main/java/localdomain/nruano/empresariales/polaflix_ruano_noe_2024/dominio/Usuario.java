package localdomain.nruano.empresariales.polaflix_ruano_noe_2024.dominio;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.Stack;
import java.util.HashSet;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;

@Entity
public class Usuario {

	@Id
	private String nombre;

	private String contrasenha;
	private boolean cuotaFija;
	private String iban;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)	// FIXME: puede que el cascade sea REMOVE
	private List<Recibo> recibos;

	private Set<Long> capitulosVistos;

	@ManyToMany
	private Set<Serie> seriesTerminadas;
	@ManyToMany
	private Set<Serie> seriesPendientes;
	@ManyToMany
	private Set<Serie> seriesEmpezadas;
	
	/* Costes de las visualizaciones en funcion del tipo de serie o la suscripcion */
	private static final double CUOTA_FIJA = 20.0;
	private static final double PRECIO_ESTANDAR = 0.5;
	private static final double PRECIO_SILVER = 0.75;
	private static final double PRECIO_GOLD = 1.5;

	/**
	 * Constructor vacio.
	 */
	protected Usuario() { }

	/**
	 * Construye un nuevo usuario.
	 * @param nombre El nombre del usuario
	 * @param contrasenha La contrasenha del usuario
	 * @param cuotaFija El parametro que indica el tipo de suscripcion del usuario
	 * @param iban El IBAN del usuario
	 */
	public Usuario(String nombre, String contrasenha, boolean cuotaFija,
				   String iban) {
		this.nombre = nombre;
		this.contrasenha = contrasenha;
		this.cuotaFija = cuotaFija;
		this.iban = iban;

		this.recibos = new Stack<Recibo>();
		recibos.addLast(new Recibo());

		this.capitulosVistos = new HashSet<Long>();
		this.seriesEmpezadas = new HashSet<Serie>();
		this.seriesPendientes = new HashSet<Serie>();
		this.seriesTerminadas = new HashSet<Serie>();
	}

	/**
	 * Registra la visualización de un capítulo y anhade el cargo correspondiente
	 * al recibo del usuario.
	 */
	public void registraVisualizacion(Capitulo c) {
		Serie sTmp = c.getTemporada().getSerie();

		if (!seriesEmpezadas.contains(sTmp)) seriesEmpezadas.add(sTmp);

		CategoriaSerie categoria = sTmp.getCategoria();
		double importe = (cuotaFija) ? CUOTA_FIJA :
						((categoria == CategoriaSerie.ESTANDAR)	? PRECIO_ESTANDAR :
						((categoria == CategoriaSerie.GOLD)		? PRECIO_GOLD :
						((categoria == CategoriaSerie.SILVER)	? PRECIO_SILVER : -1)));

		// Se anhade la visualizacion del capitulo al ultimo recibo
		recibos.getLast().anhadeCargo(
				new Cargo(LocalDateTime.now(),
						  c.getId(),
						  (cuotaFija) ? 0 : importe));

		/* Se anhade la serie a la que pertenece el capítulo al listado de
		 * series vistas si el capítulo es el último de la serie */
		if (c.isUltimoCapituloSerie()) seriesTerminadas.add(c.getTemporada().getSerie());

		// Se registra la visualizacion del capitulo
		if(!capitulosVistos.contains(c.getId())) capitulosVistos.add(c.getId());
	}

	/**
	 * Dada una serie, retorna la ultima temporada (la de mayor indice) que se
	 * visualizo.
	 * @param s la serie
	 * @return la ultima temporada vista en caso de ser una serie empezada, la
	 * primera temporada en caso de ser una serie pendiente o terminada, o null
	 * en otro caso
	 */
	public Temporada getUltimaTemporadaSerie(Serie s) {
		if (seriesEmpezadas.contains(s)) {
			int mayorIndiceTemporada = 0;

			/* Busca entre todos los capitulos visualizados por el usuario, aquel
			 * que pertenezca a la temporada con mayor indice */
			for (Temporada t: s.getTemporadas()) {
				for (Long id: capitulosVistos) {
					if (t.getCapitulo(id) != null && t.getIndice() > mayorIndiceTemporada)
						mayorIndiceTemporada = t.getIndice();
				}
			}
		} else if (seriesPendientes.contains(s) || seriesTerminadas.contains(s)) {
			/* Si no es una serie empezada se retorna la primera temporada */
			return s.getTemporadas().getFirst();
		}

		return null;
	}

	/**
	 * Anhade una serie a las series pendientes del usuario
	 * @param s la nueva serie pendiente
	 */
	public void addSeriePendiente(Serie s) {
		if (!seriesPendientes.contains(s)) {
			seriesPendientes.add(s);
		}
	}

	@Override
	public int hashCode() {
		return nombre.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;

		Usuario other = (Usuario) obj;
		if (nombre == null && other.nombre != null)
			return false;
		else if (nombre != other.nombre)
			return false;
		
		return true;
	}

	/****** GETTERS ******/

	public String getNombre() {
		return nombre;
	}

	public String getContrasenha() {
		return contrasenha;
	}

	public boolean isCuotaFija() {
		return cuotaFija;
	}

	public String getIban() {
		return iban;
	}

	public Set<Long> getCapitulosVistos() {
		return capitulosVistos;
	}

	public Set<Serie> getSeriesTerminadas() {
		return seriesTerminadas;
	}

	public Set<Serie> getSeriesPendientes() {
		return seriesPendientes;
	}

	public Set<Serie> getSeriesEmpezadas() {
		return seriesEmpezadas;
	}

	public List<Recibo> getRecibos() {
		return recibos;
	}

	/****** SETTERS ******/

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public void setContrasenha(String contrasenha) {
		this.contrasenha = contrasenha;
	}

	public void setCuotaFija(boolean cuotaFija) {
		this.cuotaFija = cuotaFija;
	}

	public void setIban(String iban) {
		this.iban = iban;
	}

	public void setRecibos(List<Recibo> recibos) {
		this.recibos = recibos;
	}

	public void addRecibo() {
		recibos.getLast().setFechaEmision(LocalDateTime.now());
		if (cuotaFija) recibos.getLast().setImporte(CUOTA_FIJA);
		recibos.addLast(new Recibo());
	}

}
