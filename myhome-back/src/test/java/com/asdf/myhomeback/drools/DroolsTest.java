package com.asdf.myhomeback.drools;

import com.asdf.myhomeback.dto.IdDTO;
import org.junit.Test;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class DroolsTest {

    private static KieContainer kieContainer;

    @Test
    public void test() {

        kieContainer = KieServices.Factory.get().getKieClasspathContainer();
        KieSession kieSession = kieContainer.newKieSession("ExampleSession");

        kieSession.getAgenda().getAgendaGroup("test_agenda").setFocus();


        IdDTO idDTO = new IdDTO();
        idDTO.setId(5L);

        kieSession.insert(idDTO);
        kieSession.fireAllRules();

        assertEquals(6L, idDTO.getId());
    }
}
