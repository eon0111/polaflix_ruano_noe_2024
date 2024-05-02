package localdomain.nruano.empresariales.polaflix_ruano_noe_2024.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import localdomain.nruano.empresariales.polaflix_ruano_noe_2024.domain.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, String> {

    Usuario findByNombre(String nombre);

}
