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

public class BolsaValoresRepositoryTest {

    private Accion testAction;
    @Mock
    private BolsaValoresRepository bolsaValoresRepository;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }
    @Test
    void testSaveEditAction(){
        testAction = new Accion();
        testAction.setNombreAccion("Facebook");
        testAction.setPrecioActual(10000L);
        testAction.setPrecioAnterior(5000L);
        boolean ban = true;
        when(bolsaValoresRepository.saveAction(testAction)).thenReturn(ban);

        assertEquals(bolsaValoresRepository.saveAction(testAction), ban);
        //when(bolsaValoresRepository.editAction("Facebook", 15000L)).thenReturn(false);
    }

}
