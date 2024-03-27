package localdomain.nruano.empresariales.polaflix_ruano_noe_2024;

public class Capitulo {
    
    private int indice;
    private String titulo;
    private String enlace;
    private String descripcion;
    private Temporada temporada;

    /**
     * Construye un capitulo.
     * @param indice el indice del capitulo dentro de la temporada
     * @param titulo el titulo del capitulo
     * @param enlace la URL que lleva a la pagina de visualizacion del capitulo
     * @param descripcion la sinopsis del capitulo
     * @param temporada la temporada a la que pertenece el capitulo
     */
    public Capitulo(int indice, String titulo, String enlace, String descripcion,
                    Temporada temporada) {
        this.indice = indice;
        this.titulo = titulo;
        this.enlace = enlace;
        this.descripcion = descripcion;
        this.temporada = temporada;
    }

	/****** GETTERS ******/

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
    
}
