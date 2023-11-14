package co.unicauca.segundoParcial.controller;

import co.unicauca.segundoParcial.access.SqlLiteRepository;
import co.unicauca.segundoParcial.model.AccionUsuario;
import lombok.NonNull;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/accionUsuario")
public class AccionUsuarioController {

    private SqlLiteRepository sqlLiteRepository = new SqlLiteRepository();

    @PostMapping
    public void createAccionUsuario(@RequestBody @NonNull AccionUsuario accionUsuario) {
        sqlLiteRepository.saveActionUser(accionUsuario);
    }

    @DeleteMapping("/{idUsuario}/{nombreAccion}")
    public void deleteAccionUsuario(@PathVariable int idUsuario, @PathVariable String nombreAccion) {
        sqlLiteRepository.deleteActionUser(idUsuario, nombreAccion);
    }
}
