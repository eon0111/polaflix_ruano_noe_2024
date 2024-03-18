package localdomain.nruano.empresariales.polaflix_ruano_noe_2024;

import java.util.SortedSet;

public class Usuario {

    private String nombre;
    private String contrasenha;
    private boolean cuotaFija;
    private String iban;
    private SortedSet<Recibo> recibos;
    
    /**
     * Construye un nuevo usuario.
     * @param nombre El nombre del usuario
     * @param contrasenha La contrasenha del usuario
     * @param cuotaFija El parametro que indica el tipo de suscripcion del usuario
     * @param iban El IBAN del usuario
     */
    public Usuario(String nombre, String contrasenha, boolean cuotaFija,
                   String iban) {
        this.nombre = nombre;
        this.contrasenha = contrasenha;
        this.cuotaFija = cuotaFija;
        this.iban = iban;
        this.recibos = new SortedSet<Recibo>;
    }

    /****** GETTERS ******/

    public String getNombre() {
        return nombre;
    }

    public String getContrasenha() {
        return contrasenha;
    }

    public boolean isCuotaFija() {
        return cuotaFija;
    }

    public String getIban() {
        return iban;
    }

    /****** SETTERS ******/

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setContrasenha(String contrasenha) {
        this.contrasenha = contrasenha;
    }

    public void setCuotaFija(boolean cuotaFija) {
        this.cuotaFija = cuotaFija;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }

}
