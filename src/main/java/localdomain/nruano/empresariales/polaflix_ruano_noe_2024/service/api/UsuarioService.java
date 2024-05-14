package localdomain.nruano.empresariales.polaflix_ruano_noe_2024.service.api;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import localdomain.nruano.empresariales.polaflix_ruano_noe_2024.domain.Usuario;
import localdomain.nruano.empresariales.polaflix_ruano_noe_2024.repositories.UsuarioRepository;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository ur;

    public Optional<Usuario> getUsuarioByNombre(String nombre) {
        return ur.findById(nombre);
    }

}
