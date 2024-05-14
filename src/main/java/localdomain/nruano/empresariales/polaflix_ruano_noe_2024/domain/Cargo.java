package localdomain.nruano.empresariales.polaflix_ruano_noe_2024.domain;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonView;

import jakarta.persistence.Embeddable;
import jakarta.persistence.ManyToMany;
import localdomain.nruano.empresariales.polaflix_ruano_noe_2024.service.api.Views;

@Embeddable
public class Cargo {

	@JsonView(Views.DatosFacturas.class)
	private LocalDateTime fechaVisualizacion;

	@JsonView(Views.DatosFacturas.class)
	private double importe;

	@ManyToMany
	@JsonView(Views.DatosFacturas.class)
	private Serie serie;

	@JsonView(Views.DatosFacturas.class)
	private int indTemporada;

	@JsonView(Views.DatosFacturas.class)
	private int indCapitulo;

	/*
	 * Constructor Vacío
	 */
	protected Cargo() { }

	/**
	 * Construye el cargo de un recibo.
	 * @param fechaVisualizacion la fecha en que se visualizo el capitulo
	 * @param importe el importe a cobrar por la visualizacion
	 */
	public Cargo(LocalDateTime fechaVisualizacion, double importe,
				 Serie serie, int indTemporada, int indCapitulo) {
		this.fechaVisualizacion = fechaVisualizacion;
		this.importe = importe;
		this.serie = serie;
		this.indTemporada = indTemporada;
		this.indCapitulo = indCapitulo;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((fechaVisualizacion == null) ? 0 : fechaVisualizacion.hashCode());
		result = prime * result + ((serie == null) ? 0 : serie.hashCode());
		result = prime * result + indTemporada;
		result = prime * result + indCapitulo;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null || getClass() != obj.getClass())
			return false;

		Cargo other = (Cargo) obj;
		if (fechaVisualizacion == null && other.fechaVisualizacion != null || 
			!fechaVisualizacion.equals(other.fechaVisualizacion)) {
			return false;
		}
		if (serie == null && other.serie != null ||
			(!serie.equals(other.serie) || indTemporada != other.indTemporada ||
			indCapitulo != other.indCapitulo)) {
			return false;
		}
		return true;
	}

	/****** GETTERS ******/

	public LocalDateTime getFechaVisualizacion() {
		return fechaVisualizacion;
	}

	public double getImporte() {
		return importe;
	}

	public Serie getSerie() {
		return serie;
	}

	public int getIndTemporada() {
		return indTemporada;
	}

	public int getIndCapitulo() {
		return indCapitulo;
	}

	/****** SETTERS ******/
	
	public void setFechaVisualizacion(LocalDateTime fechaVisualizacion) {
		this.fechaVisualizacion = fechaVisualizacion;
	}

	public void setImporte(double importe) {
		this.importe = importe;
	}

	public void setSerie(Serie serie) {
		this.serie = serie;
	}

	public void setIndTemporada(int indTemporada) {
		this.indTemporada = indTemporada;
	}

	public void setIndCapitulo(int indCapitulo) {
		this.indCapitulo = indCapitulo;
	}

}
