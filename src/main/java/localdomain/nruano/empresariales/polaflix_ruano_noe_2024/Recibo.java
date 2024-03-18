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

    public void anhadeCargo(Capitulo capitulo) {
        cargos.push(new Cargo());
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
