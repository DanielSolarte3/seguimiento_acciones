package co.unicauca.segundoParcial.controller;

import co.unicauca.segundoParcial.access.SqlLiteRepository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {
    private SqlLiteRepository sqlLiteRepository = new SqlLiteRepository();
}
