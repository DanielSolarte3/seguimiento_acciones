package co.unicauca.segundoParcial.controller;

import co.unicauca.segundoParcial.access.BolsaValoresRepository;
import co.unicauca.segundoParcial.model.Accion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.sql.DataSource;
import java.util.List;

@RestController
@RequestMapping("/accion")
public class AccionController {
    @Autowired
    private DataSource dataSource;

    @Autowired
    private BolsaValoresRepository bolsaValoresRepository;

    @GetMapping("/list")
    public List<Accion> list() {
        return bolsaValoresRepository.findAllActions();
    }
}
