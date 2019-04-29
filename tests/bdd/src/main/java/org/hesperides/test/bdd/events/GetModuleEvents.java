package org.hesperides.test.bdd.events;

import cucumber.api.java8.En;
import org.hesperides.core.presentation.io.ModuleIO;
import org.hesperides.core.presentation.io.events.EventOutput;
import org.hesperides.test.bdd.commons.HesperidesScenario;
import org.hesperides.test.bdd.modules.ModuleBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;


public class GetModuleEvents extends HesperidesScenario implements En {

    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private ModuleBuilder moduleBuilder;

    public GetModuleEvents() {
        When("^I( try to)? get the events of this module$", (String tryTo) -> {
            testContext.responseEntity = getModuleEvents(moduleBuilder.build(), getResponseType(tryTo, EventOutput[].class));
        });
    }

    public ResponseEntity getModuleEvents(ModuleIO moduleInput, Class responseType) {
        return restTemplate.getForEntity("/events/modules/{name}/{version}/{type}",
                responseType,
                moduleInput.getName(),
                moduleInput.getVersion(),
                moduleInput.getIsWorkingCopy() ? "workingcopy" : "release");
    }
}
