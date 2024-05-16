package localdomain.nruano.empresariales.polaflix_ruano_noe_2024.domain.facturacion;

import java.time.LocalDateTime;
import java.util.Stack;

import com.fasterxml.jackson.annotation.JsonView;

import java.util.List;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import localdomain.nruano.empresariales.polaflix_ruano_noe_2024.service.Views;

@Entity
public class Factura {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@JsonView(Views.DatosFacturas.class)
	private double importe;

	@JsonView(Views.DatosFacturas.class)
	private LocalDateTime fechaCreacion;

	@JsonView(Views.DatosFacturas.class)
    private LocalDateTime fechaEmision;

	@ElementCollection(fetch = FetchType.LAZY)
	@JsonView(Views.DatosFacturas.class)
    private List<Cargo> cargos;

    /**
     * Construye un recibo.
     */
    public Factura() {
		this.fechaCreacion = LocalDateTime.now();
        this.fechaEmision = null;
        this.cargos = new Stack<Cargo>();
		this.importe = 0;
    }
    
	/**
	 * Anhade un cargo al recibo.
	 * @param c el cargo
	 */
    public void anhadeCargo(Cargo c) {
        cargos.addLast(c);
		importe += c.getImporte();
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
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
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
		Factura other = (Factura) obj;
		if (id != other.id)
			return false;
		return true;
	}

	/****** GETTERS ******/

	public long getId() {
		return id;
	}

	public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

	public LocalDateTime getFechaEmision() {
        return fechaEmision;
    }

    public List<Cargo> getCargos() {
        return cargos;
    }

	public double getImporte() {
		return this.importe;
	}

    /****** SETTERS ******/

	public void setId(long id) {
		this.id = id;
	}

	public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public void setFechaEmision(LocalDateTime fechaEmision) {
        this.fechaEmision = fechaEmision;
    }

	public void setCargos(Stack<Cargo> cargos) {
		this.cargos = cargos;
	}

	public void setImporte(double importe) {
		this.importe = importe;
	}

}
