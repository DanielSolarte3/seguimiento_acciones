package co.unicauca.segundoParcial.controller;

import co.unicauca.segundoParcial.access.BolsaValoresRepository;
import co.unicauca.segundoParcial.model.Accion;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.sql.DataSource;
import java.util.List;

@RestController
@RequestMapping("/accion")
public class AccionController {

    private BolsaValoresRepository bolsaValoresRepository = new BolsaValoresRepository();

    @PostMapping
    public void createAccion(@RequestBody @NonNull Accion action){
        bolsaValoresRepository.saveAction(action);
    }

    @PutMapping("/{nombreAccion}-{precioActual}")
    public void editAccion(@PathVariable String nombreAccion, @PathVariable long precioActual){
        bolsaValoresRepository.editAction(nombreAccion, precioActual);
    }

    @GetMapping("/{nombreAccion}")
    public Accion findAccion(@PathVariable String nombreAccion){
        return bolsaValoresRepository.findAction(nombreAccion);
    }

    @GetMapping
    public List<Accion> list() {
        return bolsaValoresRepository.findAllActions();
    }
}
