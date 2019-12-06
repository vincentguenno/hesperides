package org.hesperides.test.bdd.platforms.scenarios;

import cucumber.api.DataTable;
import cucumber.api.java8.En;
import lombok.Value;
import org.apache.commons.lang3.StringUtils;
import org.hesperides.core.presentation.io.platforms.properties.DetailedPropertiesOutput;
import org.hesperides.core.presentation.io.platforms.properties.DetailedPropertiesOutput.GlobalDetailedPropertyOutput;
import org.hesperides.core.presentation.io.platforms.properties.DetailedPropertiesOutput.ModuleDetailedPropertyOutput;
import org.hesperides.test.bdd.commons.HesperidesScenario;
import org.hesperides.test.bdd.platforms.PlatformClient;
import org.hesperides.test.bdd.platforms.builders.DeployedModuleBuilder;
import org.hesperides.test.bdd.platforms.builders.PlatformBuilder;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.apache.commons.lang3.StringUtils.defaultIfEmpty;
import static org.junit.Assert.assertEquals;

public class GetDetailedProperties extends HesperidesScenario implements En {

    @Autowired
    private PlatformClient platformClient;
    @Autowired
    private PlatformBuilder platformBuilder;
    @Autowired
    private DeployedModuleBuilder deployedModuleBuilder;

    public GetDetailedProperties() {

        When("^I get the detailed properties of this module?$", () -> {
            platformClient.getDetailedProperties(platformBuilder.buildInput(), deployedModuleBuilder.buildPropertiesPath());
        });

        Then("^the detailed properties of this module are$", (DataTable data) -> {
            assertOK();
            List<ModuleDetailedProperty> moduleDetailedProperties = data.asList(ModuleDetailedProperty.class);
            List<ModuleDetailedPropertyOutput> expectedProperties = ModuleDetailedProperty.toModuleDetailedPropertyOutputs(moduleDetailedProperties);
            DetailedPropertiesOutput detailedPropertiesOutput = testContext.getResponseBody();
            List<ModuleDetailedPropertyOutput> actualProperties = detailedPropertiesOutput.getModules().get(0).getProperties();
            assertEquals(expectedProperties, actualProperties);
        });
    }

    @Value
    private static class ModuleDetailedProperty {
        String name;
        String storedValue;
        String finalValue;
        String defaultValue;
        boolean isRequired;
        boolean isPassword;
        String pattern;
        String comment;
        String referencedGlobalProperty;
        boolean isNotUsed;

        public static List<ModuleDetailedPropertyOutput> toModuleDetailedPropertyOutputs(List<ModuleDetailedProperty> moduleDetailedProperties) {
            return moduleDetailedProperties.stream()
                    .map(ModuleDetailedProperty::toModuleDetailedPropertyOutput)
                    .collect(Collectors.toList());
        }

        public ModuleDetailedPropertyOutput toModuleDetailedPropertyOutput() {
            List<GlobalDetailedPropertyOutput> globalDetailedPropertyOutputs = new ArrayList<>();
            if (StringUtils.isNotEmpty(referencedGlobalProperty)) {
                String[] ref = referencedGlobalProperty.split("=");
                globalDetailedPropertyOutputs.add(new GlobalDetailedPropertyOutput(ref[0], ref[1], ref[1]));
            }

            return new ModuleDetailedPropertyOutput(
                    name,
                    defaultIfEmpty(storedValue, null),
                    finalValue,
                    defaultIfEmpty(defaultValue, null),
                    isRequired,
                    isPassword,
                    defaultIfEmpty(pattern, null),
                    defaultIfEmpty(comment, null),
                    globalDetailedPropertyOutputs,
                    isNotUsed);
        }
    }
}
