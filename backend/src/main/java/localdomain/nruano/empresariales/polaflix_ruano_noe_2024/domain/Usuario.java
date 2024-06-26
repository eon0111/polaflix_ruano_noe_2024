package localdomain.nruano.empresariales.polaflix_ruano_noe_2024.domain;

import com.fasterxml.jackson.annotation.JsonView;

import java.time.LocalDateTime;
import java.util.Stack;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import localdomain.nruano.empresariales.polaflix_ruano_noe_2024.domain.facturacion.Cargo;
import localdomain.nruano.empresariales.polaflix_ruano_noe_2024.domain.facturacion.Factura;
import localdomain.nruano.empresariales.polaflix_ruano_noe_2024.domain.facturacion.PrecioVisualizacion;
import localdomain.nruano.empresariales.polaflix_ruano_noe_2024.domain.series.Capitulo;
import localdomain.nruano.empresariales.polaflix_ruano_noe_2024.domain.series.CategoriaSerie;
import localdomain.nruano.empresariales.polaflix_ruano_noe_2024.domain.series.Serie;
import localdomain.nruano.empresariales.polaflix_ruano_noe_2024.domain.series.Temporada;
import localdomain.nruano.empresariales.polaflix_ruano_noe_2024.domain.visualizaciones.VisualizacionCapitulo;
import localdomain.nruano.empresariales.polaflix_ruano_noe_2024.domain.visualizaciones.VisualizacionSerie;
import localdomain.nruano.empresariales.polaflix_ruano_noe_2024.domain.visualizaciones.VisualizacionTemporada;
import localdomain.nruano.empresariales.polaflix_ruano_noe_2024.service.Views;

@Entity
public class Usuario {

	@Id
	@JsonView({ Views.DatosUsuario.class,
				Views.NuevoUsuario.class })
	private String nombre;

	@JsonView(Views.NuevoUsuario.class)
	private String contrasenha;

	@JsonView(Views.NuevoUsuario.class)
	private boolean cuotaFija;

	@JsonView(Views.NuevoUsuario.class)
	private String iban;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JsonView(Views.DatosFacturas.class)
	private List<Factura> facturas;

	/* Un mapa que, por cada serie visualizada y por cada temporada de esas
	 * series, alberga las visualizaciones de cada capítulo */
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JsonView(Views.DatosVisualizacion.class)
	private Map<Long, VisualizacionSerie> visualizacionesSeries;

	@ManyToMany
	@JsonView(Views.DatosUsuario.class)
	private Map<Long, Serie> seriesTerminadas;

	@ManyToMany
	@JsonView(Views.DatosUsuario.class)
	private Map<Long, Serie> seriesPendientes;
	
	@ManyToMany
	@JsonView(Views.DatosUsuario.class)
	private Map<Long, Serie> seriesEmpezadas;

	// TODO: atributo foto de perfil + @JsonView({ Views.DatosUsuario.class, Views.NuevoUsuario.class })
	
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

		this.facturas = new Stack<Factura>();
		facturas.addLast(new Factura());

		this.visualizacionesSeries = new HashMap<Long, VisualizacionSerie>();
		this.seriesEmpezadas = new HashMap<Long, Serie>();
		this.seriesPendientes = new HashMap<Long, Serie>();
		this.seriesTerminadas = new HashMap<Long, Serie>();
	}

	/**
	 * Registra la visualización de un capítulo y anhade el cargo correspondiente
	 * a la factura del usuario.
	 */
	public VisualizacionCapitulo registraVisualizacion(Capitulo c) {
		Temporada tTmp = c.getTemporada();
		Serie sTmp = tTmp.getSerie();

		/* Se registra el comienzo de una serie si no se había visualizado aún
		 * ningún capítulo de esta */
		if (!seriesEmpezadas.containsKey(sTmp.getId()))
			seriesEmpezadas.put(sTmp.getId(), sTmp);

		/* Se computa el importe a cobrar por la visualización en función del tipo
		 * de serie o la suscripción del usuario */
		CategoriaSerie categoria = sTmp.getCategoria();
		double importe = (cuotaFija) ? PrecioVisualizacion.CUOTA_FIJA :
						((categoria == CategoriaSerie.ESTANDAR)	? PrecioVisualizacion.PRECIO_ESTANDAR :
						((categoria == CategoriaSerie.GOLD)		? PrecioVisualizacion.PRECIO_GOLD :
						((categoria == CategoriaSerie.SILVER)	? PrecioVisualizacion.PRECIO_SILVER : -1)));

		// Se anhade la visualizacion del capitulo a la última factura
		facturas.getLast().anhadeCargo(new Cargo(LocalDateTime.now(),
												 importe,
												 sTmp.getTitulo(),
												 tTmp.getIndice(),
												 c.getIndice()));

		/* Se anhade la serie a la que pertenece el capítulo al listado de
		 * series vistas si el capítulo es el último de la serie */
		if (c.isUltimoCapituloSerie()) {
			seriesEmpezadas.remove(sTmp.getId());
			seriesTerminadas.put(sTmp.getId(), sTmp);
		}

		// Se registra la visualizacion del capitulo
		long idSerie = sTmp.getId();
		int indiceTemprada = tTmp.getIndice();

		VisualizacionSerie vsTmp = visualizacionesSeries.get(idSerie);
		VisualizacionCapitulo vc = null;

		if (vsTmp == null) {
			/* Si no se ha visualizado ningún capítulo de la serie a la que
			 * pertenece el capítulo, se registra la visualización de la serie */
			VisualizacionSerie vs = new VisualizacionSerie(sTmp);
			VisualizacionTemporada vt = new VisualizacionTemporada(tTmp.getIndice());
			vc = new VisualizacionCapitulo(c.getIndice());

			vt.addVisualizacionCapitulo(vc);
			vs.addVisualizacionTemporada(vt);

			visualizacionesSeries.put(idSerie, vs);
		} else if (vsTmp.getVisualizacionesCapitulos(indiceTemprada) == null) {
			/* Si no se ha visualizado ningún capítulo de la temporada a la que
			 * pertenece el capítulo, se registra la visualización de la temporada */
			VisualizacionTemporada vt = new VisualizacionTemporada(tTmp.getIndice());
			vc = new VisualizacionCapitulo(c.getIndice());

			vt.addVisualizacionCapitulo(vc);

			vsTmp.addVisualizacionTemporada(vt);
		} else {
			/* Se registra la visualización del capítulo una vez comprobado que
			 * se han visualizado capítulos de la misma temporada */
			vsTmp.addVisualizacionCapitulo(c);

			vc = vsTmp.getVisualizacionCapitulo(c.getTemporada().getIndice(), c.getIndice());
		}

		return vc;
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
		return (seriesEmpezadas.containsKey(id)) ? visualizacionesSeries.get(id).getUltimaTemporadaVista() :
			  ((seriesPendientes.containsKey(id)) ? seriesPendientes.get(id).getTemporadas().getFirst() : 
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
		if (this != obj || getClass() != obj.getClass() || obj == null)
			return false;

		Usuario other = (Usuario) obj;
		if (nombre == null && other.nombre != null || nombre != other.nombre)
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

	public Map<Long, VisualizacionSerie> getVisualizacionesSeries() {
		return visualizacionesSeries;
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

	public List<Factura> getFacturas() {
		return facturas;
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

	public void setfacturas(List<Factura> facturas) {
		this.facturas = facturas;
	}

	public void addFactura() {
		facturas.getLast().setFechaEmision(LocalDateTime.now());
		if (cuotaFija) facturas.getLast().setImporte(PrecioVisualizacion.CUOTA_FIJA);
		facturas.addLast(new Factura());
	}

	public void setVisualizaciones(Map<Long, VisualizacionSerie> visualizacionesSeries) {
		this.visualizacionesSeries = visualizacionesSeries;
	}

	public void setSeriesEmpezadas(Map<Long, Serie> seriesEmpezadas) {
		this.seriesEmpezadas = seriesEmpezadas;
	}
	
	public void setSeriesPendientes(Map<Long, Serie> seriesPendientes) {
		this.seriesPendientes = seriesPendientes;
	}

	public void setSeriesTerminadas(Map<Long, Serie> seriesTerminadas) {
		this.seriesTerminadas = seriesTerminadas;
	}

}
