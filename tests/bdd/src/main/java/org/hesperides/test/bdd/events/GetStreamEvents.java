package org.hesperides.test.bdd.events;

import cucumber.api.java8.En;
import org.hesperides.core.presentation.io.events.EventOutput;
import org.hesperides.test.bdd.commons.HesperidesScenario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import static org.hamcrest.Matchers.endsWith;
import static org.hamcrest.Matchers.hasProperty;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;


public class GetStreamEvents extends HesperidesScenario implements En {

    @Autowired
    private RestTemplate restTemplate;

    public GetStreamEvents() {

        When("^I( try to)? get the events of \"([^\"]*)\"$", (String tryTo, String streamName) -> {
            testContext.responseEntity = getStreamEvents(streamName, getResponseType(tryTo, EventOutput[].class));
        });

        Then("^(\\d+) event(?: is|s are) returned$", (Integer nbEvents) -> {
            assertOK();
            EventOutput[] events = (EventOutput[]) testContext.getResponseBody();
            assertEquals(nbEvents.intValue(), events.length);
        });

        Then("^event at index (\\d+) is a (.*) event type$", (Integer index, String eventType) -> {
            EventOutput[] events = (EventOutput[]) testContext.getResponseBody();
            assertThat(events[index], hasProperty("type", endsWith(eventType)));
        });
    }

    public ResponseEntity getStreamEvents(String streamName, Class responseType) {
        return restTemplate.getForEntity("/events/{stream_name}", responseType, streamName);
    }
}
