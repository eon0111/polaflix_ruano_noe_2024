package localdomain.nruano.empresariales.polaflix_ruano_noe_2024;

import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.Embeddable;

@Embeddable
public class Cargo {

	private LocalDateTime fechaVisualizacion;
	private UUID idCapitulo;
	private double importe;

	/**
	 * Construye el cargo de un recibo.
	 * @param fechaVisualizacion la fecha en que se visualizo el capitulo
	 * @param importe el importe a cobrar por la visualizacion
	 */
	public Cargo(LocalDateTime fechaVisualizacion, UUID idCapitulo,
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

	public UUID getIdCapitulo() {
		return idCapitulo;
	}

	public LocalDateTime getFechaVisualizacion() {
		return fechaVisualizacion;
	}

	public double getImporte() {
		return importe;
	}

	/****** SETTERS ******/
	
	public void setIdCapitulo(UUID idCapitulo) {
		this.idCapitulo = idCapitulo;
	}

	public void setFechaVisualizacion(LocalDateTime fechaVisualizacion) {
		this.fechaVisualizacion = fechaVisualizacion;
	}

	public void setImporte(double importe) {
		this.importe = importe;
	}

}
