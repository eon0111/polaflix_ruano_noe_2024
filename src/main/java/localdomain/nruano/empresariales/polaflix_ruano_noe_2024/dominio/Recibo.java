package localdomain.nruano.empresariales.polaflix_ruano_noe_2024.dominio;

import java.time.LocalDateTime;
import java.util.Stack;
import java.util.UUID;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Recibo {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID id;
    
    private LocalDateTime fechaEmision;

	@ElementCollection(fetch = FetchType.LAZY)
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

		if (!cargos.isEmpty())
			for (Cargo c: cargos)
				total += c.getImporte();

		return total;
	}

    @Override
	public int hashCode() {
		return fechaEmision.hashCode();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		else if (!(o instanceof Recibo) || o == null)
			return false;
		else if (((Recibo)o).getFechaEmision().equals(this.fechaEmision))
			return true;
		else return false;
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
