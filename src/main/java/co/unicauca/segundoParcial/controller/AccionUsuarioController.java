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

    @DeleteMapping("/{idUsuario}/{nombreUsuario}")
    public void deleteAccionUsuario(@PathVariable int idUsuario, @PathVariable String nombreUsuario) {
        sqlLiteRepository.deleteActionUser(idUsuario, nombreUsuario);
    }




}
