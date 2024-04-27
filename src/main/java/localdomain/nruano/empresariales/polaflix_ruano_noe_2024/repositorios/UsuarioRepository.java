package localdomain.nruano.empresariales.polaflix_ruano_noe_2024.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;

import localdomain.nruano.empresariales.polaflix_ruano_noe_2024.dominio.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, String> {

    Usuario findByNombre(String nombre);

}
