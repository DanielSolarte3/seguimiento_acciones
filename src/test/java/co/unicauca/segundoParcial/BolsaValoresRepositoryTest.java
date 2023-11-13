package co.unicauca.segundoParcial;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import co.unicauca.segundoParcial.access.BolsaValoresRepository;
import co.unicauca.segundoParcial.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

public class BolsaValoresRepositoryTest {

    private Accion testAction;

    private BolsaValoresRepository bolsaValoresRepository = new BolsaValoresRepository();

    @Test
    void testSaveEditAction(){
        testAction = new Accion();
        testAction.setNombreAccion("Facebook");
        testAction.setPrecioActual(10000L);
        testAction.setPrecioAnterior(5000L);
        assertTrue(bolsaValoresRepository.saveAction(testAction));
        assertTrue(bolsaValoresRepository.editAction("Facebook", 15000));
    }

    @Test
    void testFindFindAllActions(){
        List<Accion> actionList = new ArrayList<>();
        testAction = new Accion();
        testAction.setNombreAccion("Facebook");
        testAction.setPrecioActual(10000L);
        testAction.setPrecioAnterior(5000L);
        bolsaValoresRepository.saveAction(testAction);

        assertEquals( bolsaValoresRepository.findAction("Facebook").getNombreAccion(),
                testAction.getNombreAccion());

        actionList.add(testAction);
        testAction.setNombreAccion("Google");
        testAction.setPrecioActual(20000L);
        testAction.setPrecioAnterior(6000L);
        actionList.add(testAction);
        bolsaValoresRepository.saveAction(testAction);
    }

}
