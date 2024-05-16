package localdomain.nruano.empresariales.polaflix_ruano_noe_2024.domain.series;

import com.fasterxml.jackson.annotation.JsonView;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import localdomain.nruano.empresariales.polaflix_ruano_noe_2024.service.Views;

@Entity
public class Capitulo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @JsonView(Views.DatosTemporada.class)
    private int indice;

    @ManyToOne
    private Temporada temporada;
    
    @JsonView(Views.DatosTemporada.class)
    private String titulo;

    @JsonView(Views.DatosTemporada.class)
    private String enlace;
    
    private String descripcion;

    /**
	 * Constructor vacio.
	 */
    public Capitulo() { }
    
    /**
     * Construye un capitulo.
	 * @param id el identificador del capitulo
     * @param indice el indice del capitulo dentro de la temporada
     * @param titulo el titulo del capitulo
     * @param enlace la URL que lleva a la pagina de visualizacion del capitulo
     * @param descripcion la sinopsis del capitulo
     * @param temporada la temporada a la que pertenece el capitulo
     */
    public Capitulo(String titulo, String enlace, String descripcion,
                    Temporada temporada, int indice) {
        this.titulo = titulo;
        this.enlace = enlace;
        this.descripcion = descripcion;
        this.temporada = temporada;
        this.indice = indice;
    }

    /**
     * Retorna un booleano en función de si el capítulo es el último de la
     * temporada o no.
     * @return true si la temporada a la que pertenece el capítulo es la última
     * de la serie y si el capítulo es el último de la temporada a la que este
     * pertenece, o false en caso contrario
     */
    public boolean isUltimoCapituloSerie() {
        return ((temporada.getIndice() == (temporada.getSerie().getNumTemporadas()) &&
                indice == temporada.getNumCapitulos()) ? true : false);
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
        Capitulo other = (Capitulo) obj;
        if (id != other.id)
            return false;
        return true;
    }

    /****** GETTERS ******/

    public long getId() {
        return id;
    }

	public int getIndice() {
		return indice;
	}

	public String getTitulo() {
        return titulo;
    }

	public String getEnlace() {
        return enlace;
    }

	public String getDescripcion() {
        return descripcion;
    }

	public Temporada getTemporada() {
        return temporada;
    }

	/****** SETTERS ******/

    public void setIndice(int indice) {
        this.indice = indice;
    }
    
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
    
    public void setEnlace(String enlace) {
        this.enlace = enlace;
    }
    
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    
    public void setTemporada(Temporada temporada) {
        this.temporada = temporada;
    }

    public void setId(long id) {
        this.id = id;
    }
    
}
