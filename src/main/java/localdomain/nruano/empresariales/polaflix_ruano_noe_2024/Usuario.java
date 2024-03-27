// TODO: manejar casos de error por medio de excepciones (?)

package localdomain.nruano.empresariales.polaflix_ruano_noe_2024;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.Stack;

public class Usuario {

	/* Costes de las visualizaciones en funcion del tipo de serie o la suscripcion */
	private static final double CUOTA_FIJA = 20.0;
	private static final double PRECIO_ESTANDAR = 0.5;
	private static final double PRECIO_SILVER = 0.75;
	private static final double PRECIO_GOLD = 1.5;

	private String nombre;
	private String contrasenha;
	private boolean cuotaFija;
	private String iban;
	private Stack<Recibo> recibos;
	private Set<String> capitulosVistos;
	private Set<Serie> seriesTerminadas;
	private Set<Serie> seriesPendientes;
	private Set<Serie> seriesEmpezadas;
	
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
	}

	/**
	 * Registra la visualización de un capítulo y anhade el cargo correspondiente
	 * al recibo del usuario.
	 */
	public void registraVisualizacion(Capitulo c) {
		CategoriaSerie categoria = c.getTemporada().getSerie().getCategoria();
		double importe = (cuotaFija) ? CUOTA_FIJA :
						((categoria == CategoriaSerie.ESTANDAR)	? PRECIO_ESTANDAR :
						((categoria == CategoriaSerie.GOLD)		? PRECIO_GOLD :
						((categoria == CategoriaSerie.SILVER)	? PRECIO_SILVER : -1)));

		// Se anhade la visualizacion del capitulo al ultimo recibo
		recibos.lastElement().anhadeCargo(
				new Cargo(LocalDateTime.now(),
						(cuotaFija) ? CUOTA_FIJA : importe,
						c.getId()));

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
				for (String id: capitulosVistos) {
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

	public Set<String> getCapitulosVistos() {
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

	public void addRecibo() {
		recibos.lastElement().setFechaEmision(LocalDateTime.now());
		recibos.push(new Recibo());
	}

}
