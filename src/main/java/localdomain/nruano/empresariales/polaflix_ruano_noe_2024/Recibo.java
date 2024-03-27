package localdomain.nruano.empresariales.polaflix_ruano_noe_2024;

import java.time.LocalDateTime;
import java.util.Stack;

public class Recibo {
    
    private LocalDateTime fechaEmision;
    private Stack<Cargo> cargos;

    /**
     * Construye un recibo.
     */
    public Recibo() {
        this.fechaEmision = null;
        this.cargos = new Stack<Cargo>();
    }
    
	/**
	 * Anhade un cargo al recibo.
	 * @param c el cargo
	 */
    public void anhadeCargo(Cargo c) {
        cargos.push(c);
    }

	/**
	 * Calcula el importe total a pagar en base a los cargos del recibo.
	 * @return el importe a pagar
	 */
	public double calculaImporteTotal() {
		double total = 0;

		for (Cargo c: cargos) {
			total += c.getImporte();
		}

		return total;
	}

    /****** GETTERS ******/

    public LocalDateTime getFechaEmision() {
        return fechaEmision;
    }

    public Stack<Cargo> getCargos() {
        return cargos;
    }

    /****** SETTERS ******/

    public void setFechaEmision(LocalDateTime fechaEmision) {
        this.fechaEmision = fechaEmision;
    }

}
