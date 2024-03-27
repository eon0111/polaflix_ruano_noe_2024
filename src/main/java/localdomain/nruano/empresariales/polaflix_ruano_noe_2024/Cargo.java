package localdomain.nruano.empresariales.polaflix_ruano_noe_2024;

import java.time.LocalDateTime;

public class Cargo {

	private LocalDateTime fechaVisualizacion;
	private double importe;
	private String idCapitulo;

	/**
	 * Construye el cargo de un recibo.
	 * @param fechaVisualizacion la fecha en que se visualizo el capitulo
	 * @param importe el importe a cobrar por la visualizacion
	 */
	public Cargo(LocalDateTime fechaVisualizacion, double importe,
				 String idCapitulo) {
		this.fechaVisualizacion = fechaVisualizacion;
		this.importe = importe;
		this.idCapitulo = idCapitulo;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((fechaVisualizacion == null) ? 0 : fechaVisualizacion.hashCode());
		result = prime * result + ((idCapitulo == null) ? 0 : idCapitulo.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		else if (!(o instanceof Cargo) || o == null)
			return false;
		else if (((Cargo) o).getFechaVisualizacion().equals(this.fechaVisualizacion) &&
				((Cargo)o).getIdCapitulo() == this.idCapitulo)
			return true;
		else return false;
	}

	/****** GETTERS ******/

	public String getIdCapitulo() {
		return idCapitulo;
	}

	public LocalDateTime getFechaVisualizacion() {
		return fechaVisualizacion;
	}

	public double getImporte() {
		return importe;
	}

	/****** SETTERS ******/
	
	public void setIdCapitulo(String idCapitulo) {
		this.idCapitulo = idCapitulo;
	}

	public void setFechaVisualizacion(LocalDateTime fechaVisualizacion) {
		this.fechaVisualizacion = fechaVisualizacion;
	}

	public void setImporte(double importe) {
		this.importe = importe;
	}

}
