package localdomain.nruano.empresariales.polaflix_ruano_noe_2024;

import java.time.LocalDateTime;

public class Cargo {

	private Capitulo capitulo;
	private LocalDateTime fechaVisualizacion;
	private double importe;

	/**
	 * Construye el cargo de un recibo.
	 * @param fechaVisualizacion la fecha en que se visualizo el capitulo
	 * @param importe el importe a cobrar por la visualizacion
	 * @param capitulo el capitulo visualizado
	 */
	public Cargo(LocalDateTime fechaVisualizacion, double importe,
				 Capitulo capitulo) {
		this.fechaVisualizacion = fechaVisualizacion;
		this.importe = importe;
		this.capitulo = capitulo;
	}

	/****** GETTERS ******/

	public Capitulo getCapitulo() {
		return capitulo;
	}

	public LocalDateTime getFechaVisualizacion() {
		return fechaVisualizacion;
	}

	public double getImporte() {
		return importe;
	}

	/****** SETTERS ******/
	
	public void setCapitulo(Capitulo capitulo) {
		this.capitulo = capitulo;
	}

	public void setFechaVisualizacion(LocalDateTime fechaVisualizacion) {
		this.fechaVisualizacion = fechaVisualizacion;
	}

	public void setImporte(double importe) {
		this.importe = importe;
	}
}
