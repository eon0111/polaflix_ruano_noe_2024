package localdomain.nruano.empresariales.polaflix_ruano_noe_2024.domain.series;

import jakarta.persistence.Embeddable;

@Embeddable
public class PersonalSerie {
    
    private String nombre;
    private String apellido1;
    private String apellido2;
    
    /**
     * Construye el objeto que modela un creador o actor.
     * @param nombre el nombre del creador/actor
     * @param apellido1 el primer apellido del creador/actor
     * @param apellido2 el segundo apellido del creador/actor
     */
    public PersonalSerie(String nombre, String apellido1, String apellido2) {
        this.nombre = nombre;
        this.apellido1 = apellido1;
        this.apellido2 = apellido2;
    }

	/****** GETTERS ******/

	public String getNombre() {
        return nombre;
    }

	public String getApellido1() {
        return apellido1;
    }

	public String getApellido2() {
        return apellido2;
    }

	/****** SETTERS ******/

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setApellido1(String apellido1) {
        this.apellido1 = apellido1;
    }

    public void setApellido2(String apellido2) {
        this.apellido2 = apellido2;
    }

}
