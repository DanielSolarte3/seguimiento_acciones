package co.unicauca.segundoParcial.controller;

import co.unicauca.segundoParcial.access.SqlLiteRepository;
import co.unicauca.segundoParcial.model.Usuario;
import io.micrometer.common.lang.NonNull;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {
    private SqlLiteRepository sqlLiteRepository = new SqlLiteRepository();

    @PostMapping
    public void createUsuario(@RequestBody @NonNull Usuario user){
        sqlLiteRepository.saveUser(user);
    }
}
