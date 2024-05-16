package localdomain.nruano.empresariales.polaflix_ruano_noe_2024.domain.series;

import com.fasterxml.jackson.annotation.JsonView;

import jakarta.persistence.Embeddable;
import localdomain.nruano.empresariales.polaflix_ruano_noe_2024.service.Views;

@Embeddable
public class PersonalSerie {
    
    @JsonView(Views.DatosSerie.class)
    private String nombre;

    @JsonView(Views.DatosSerie.class)
    private String apellido1;

    @JsonView(Views.DatosSerie.class)
    private String apellido2;

    /*
     * Constructor vacío.
     */
    protected PersonalSerie() { }
    
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
