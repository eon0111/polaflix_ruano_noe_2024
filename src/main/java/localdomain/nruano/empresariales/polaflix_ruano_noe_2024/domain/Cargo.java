package localdomain.nruano.empresariales.polaflix_ruano_noe_2024.domain;

import java.time.LocalDateTime;

import jakarta.persistence.Embeddable;

@Embeddable
public class Cargo {

	private LocalDateTime fechaVisualizacion;
	private long idCapitulo;
	private double importe;

	/*
	 * Constructor Vacío
	 */
	protected Cargo() { }

	/**
	 * Construye el cargo de un recibo.
	 * @param fechaVisualizacion la fecha en que se visualizo el capitulo
	 * @param importe el importe a cobrar por la visualizacion
	 */
	public Cargo(LocalDateTime fechaVisualizacion, long idCapitulo,
				 double importe) {
		this.fechaVisualizacion = fechaVisualizacion;
		this.idCapitulo = idCapitulo;
		this.importe = importe;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((fechaVisualizacion == null) ? 0 : fechaVisualizacion.hashCode());
		result = prime * result + (int) (idCapitulo ^ (idCapitulo >>> 32));
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
		Cargo other = (Cargo) obj;
		if (fechaVisualizacion == null) {
			if (other.fechaVisualizacion != null)
				return false;
		} else if (!fechaVisualizacion.equals(other.fechaVisualizacion))
			return false;
		if (idCapitulo != other.idCapitulo)
			return false;
		return true;
	}

	/****** GETTERS ******/

	public long getIdCapitulo() {
		return idCapitulo;
	}

	public LocalDateTime getFechaVisualizacion() {
		return fechaVisualizacion;
	}

	public double getImporte() {
		return importe;
	}

	/****** SETTERS ******/
	
	public void setIdCapitulo(long idCapitulo) {
		this.idCapitulo = idCapitulo;
	}

	public void setFechaVisualizacion(LocalDateTime fechaVisualizacion) {
		this.fechaVisualizacion = fechaVisualizacion;
	}

	public void setImporte(double importe) {
		this.importe = importe;
	}

}
