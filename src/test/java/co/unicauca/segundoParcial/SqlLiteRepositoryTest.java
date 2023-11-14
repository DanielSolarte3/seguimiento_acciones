package co.unicauca.segundoParcial;

import co.unicauca.segundoParcial.access.SqlLiteRepository;
import co.unicauca.segundoParcial.model.Accion;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class SqlLiteRepositoryTest {
    private SqlLiteRepository sqlLiteRepository = new SqlLiteRepository();

    @Test
    void testSaveAction(){
        Accion test = new Accion();
        test.setNombreAccion("Facebook");
        test.setPrecioActual(10000L);
        test.setPrecioAnterior(5000L);
        assertTrue(sqlLiteRepository.getBolsaValoresRepository().saveAction(test));
    }
}
