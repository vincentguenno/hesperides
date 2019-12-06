/*
 *
 * This file is part of the Hesperides distribution.
 * (https://github.com/voyages-sncf-technologies/hesperides)
 * Copyright (c) 2016 VSCT.
 *
 * Hesperides is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, version 3.
 *
 * Hesperides is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 *
 *
 */
package org.hesperides.core.domain.platforms.queries.views.properties;

import lombok.EqualsAndHashCode;
import lombok.Value;
import lombok.experimental.NonFinal;
import org.hesperides.core.domain.modules.entities.Module;
import org.hesperides.core.domain.templatecontainers.queries.PropertyView;

import java.util.Collections;
import java.util.List;

import static org.springframework.util.CollectionUtils.isEmpty;

@Value
public class DetailedPropertiesView {

    String applicationName;
    String platformName;
    List<DetailedPropertyView> globalProperties;
    List<ModuleDetailedPropertiesView> modules;

    @Value
    @NonFinal
    public static class DetailedPropertyView {
        String name;
        String storedValue;
        String finalValue;
    }

    @Value
    public static class ModuleDetailedPropertiesView {
        Module.Key moduleKey;
        String modulePath;
        String propertiesPath;
        List<ModuleDetailedPropertyView> properties;
    }

    @Value
    @EqualsAndHashCode(callSuper = true)
    public static class ModuleDetailedPropertyView extends DetailedPropertyView {

        String defaultValue;
        boolean isRequired;
        boolean isPassword;
        String pattern;
        String comment;
        List<DetailedPropertyView> referencedGlobalProperties;
        boolean isNotUsed;

        public ModuleDetailedPropertyView(
                String name,
                String storedValue,
                String finalValue,
                String defaultValue,
                List<PropertyView> propertyModels,
                List<ValuedPropertyView> globalProperties) {
            super(name, storedValue, finalValue);
            this.defaultValue = defaultValue;
            this.isRequired = !isEmpty(propertyModels) && propertyModels.get(0).isRequired();
            this.isPassword = !isEmpty(propertyModels) && propertyModels.get(0).isPassword();
            this.pattern = isEmpty(propertyModels) ? null : propertyModels.get(0).getPattern();
            this.comment = isEmpty(propertyModels) ? null : propertyModels.get(0).getComment();
            this.referencedGlobalProperties = Collections.emptyList();
            this.isNotUsed = false;
        }
    }
}