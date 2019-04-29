package org.hesperides.test.bdd.events;

import cucumber.api.java8.En;
import org.hesperides.core.presentation.io.events.EventOutput;
import org.hesperides.core.presentation.io.platforms.PlatformIO;
import org.hesperides.test.bdd.commons.HesperidesScenario;
import org.hesperides.test.bdd.platforms.PlatformBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;


public class GetPlatformEvents extends HesperidesScenario implements En {

    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private PlatformBuilder platformBuilder;

    public GetPlatformEvents() {
        When("^I( try to)? get the events of this platform", (String tryTo) -> {
            testContext.responseEntity = getPlatformEvents(platformBuilder.buildInput(), getResponseType(tryTo, EventOutput[].class));
            System.out.println();
        });
    }

    public ResponseEntity getPlatformEvents(PlatformIO platformInput, Class responseType) {
        return restTemplate.getForEntity("/events/platforms/{application_name}/{platform_name}",
                responseType,
                platformInput.getApplicationName(),
                platformInput.getPlatformName());
    }
}
