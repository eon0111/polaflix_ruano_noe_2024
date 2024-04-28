package localdomain.nruano.empresariales.polaflix_ruano_noe_2024.dominio;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.Stack;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

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
	private Map<Long, Serie> seriesTerminadas;
	@ManyToMany
	private Map<Long, Serie> seriesPendientes;
	@ManyToMany
	private Map<Long, Serie> seriesEmpezadas;
	
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
		this.seriesEmpezadas = new HashMap<Long, Serie>();
		this.seriesPendientes = new HashMap<Long, Serie>();
		this.seriesTerminadas = new HashMap<Long, Serie>();
	}

	/**
	 * Registra la visualización de un capítulo y anhade el cargo correspondiente
	 * al recibo del usuario.
	 */
	public void registraVisualizacion(Capitulo c) {
		Serie sTmp = c.getTemporada().getSerie();

		/* Se registra el comienzo de una serie si no se había visualizado aún
		 * ningún capítulo de esta */
		if (!seriesEmpezadas.containsKey(sTmp.getId()))
			seriesEmpezadas.put(sTmp.getId(), sTmp);

		/* Si es el último capítulo de la serie, esta se considera terminada */
		if (c.getIndice() == c.getTemporada().getCapitulos().size() &&
			c.getTemporada().getIndice() == c.getTemporada().getSerie().getNumTemporadas())
			seriesEmpezadas.remove(c.getTemporada().getSerie().getId());

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
		if (c.isUltimoCapituloSerie())
			seriesTerminadas.put(c.getTemporada().getSerie().getId(),
								 c.getTemporada().getSerie());

		// Se registra la visualizacion del capitulo
		if(!capitulosVistos.contains(c.getId())) capitulosVistos.add(c.getId());
	}

	/**
	 * Dada una serie, retorna la ultima temporada (la de mayor indice) que se
	 * visualizo.
	 * @param id el ID de la serie
	 * @return la ultima temporada vista en caso de ser una serie empezada, la
	 * primera temporada en caso de ser una serie pendiente o terminada, o null
	 * en otro caso
	 */
	public Temporada getUltimaTemporadaSerie(long id) {
		if (seriesEmpezadas.containsKey(id)) {
			int mayorIndiceTemporada = 0;

			/* Busca entre todos los capitulos visualizados por el usuario, aquel
			 * que pertenezca a la temporada con mayor indice */
			for (Temporada t: seriesEmpezadas.get(id).getTemporadas())
				for (Long idTmp: capitulosVistos)
					if (t.getCapitulo(idTmp) != null && t.getIndice() > mayorIndiceTemporada)
						mayorIndiceTemporada = t.getIndice();

			return seriesEmpezadas.get(id).getTemporadas().get(mayorIndiceTemporada - 1);
		}

		return ((seriesPendientes.containsKey(id)) ? seriesPendientes.get(id).getTemporadas().getFirst() : 
			   ((seriesTerminadas.containsKey(id)) ? seriesTerminadas.get(id).getTemporadas().getFirst() :
			   null));
	}

	/**
	 * Anhade una serie a las series pendientes del usuario.
	 * @param s la nueva serie pendiente
	 */
	public void addSeriePendiente(Serie s) {
		if (!seriesPendientes.containsKey(s.getId())) {
			seriesPendientes.put(s.getId(), s);
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

	public Map<Long, Serie> getSeriesTerminadas() {
		return seriesTerminadas;
	}

	public Map<Long, Serie> getSeriesPendientes() {
		return seriesPendientes;
	}

	public Map<Long, Serie> getSeriesEmpezadas() {
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
