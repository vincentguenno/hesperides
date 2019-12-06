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
package org.hesperides.core.presentation.io.platforms.properties;

import lombok.AllArgsConstructor;
import lombok.Value;
import org.hesperides.core.domain.platforms.queries.views.properties.DetailedPropertiesView;
import org.hesperides.core.domain.platforms.queries.views.properties.DetailedPropertiesView.DetailedPropertyView;
import org.hesperides.core.domain.platforms.queries.views.properties.DetailedPropertiesView.ModuleDetailedPropertiesView;
import org.hesperides.core.domain.platforms.queries.views.properties.DetailedPropertiesView.ModuleDetailedPropertyView;

import java.util.List;
import java.util.stream.Collectors;

import static org.apache.commons.lang3.StringUtils.defaultIfEmpty;

@Value
public class DetailedPropertiesOutput {

    String applicationName;
    String platformName;
    List<GlobalDetailedPropertyOutput> properties;
    List<ModuleDetailedPropertiesOutput> modules;

    public DetailedPropertiesOutput(DetailedPropertiesView detailedPropertiesView) {
        applicationName = detailedPropertiesView.getApplicationName();
        platformName = detailedPropertiesView.getPlatformName();
        properties = GlobalDetailedPropertyOutput.fromViews(detailedPropertiesView.getGlobalProperties());
        modules = ModuleDetailedPropertiesOutput.fromViews(detailedPropertiesView.getModules());
    }

    @Value
    @AllArgsConstructor
    public static class GlobalDetailedPropertyOutput {
        String name;
        String storedValue;
        String finalValue;

        public GlobalDetailedPropertyOutput(DetailedPropertyView detailedPropertyView) {
            name = detailedPropertyView.getName();
            storedValue = detailedPropertyView.getStoredValue();
            finalValue = detailedPropertyView.getFinalValue();
        }

        public static List<GlobalDetailedPropertyOutput> fromViews(List<DetailedPropertyView> referencedGlobalPropertyViews) {
            return referencedGlobalPropertyViews.stream()
                    .map(GlobalDetailedPropertyOutput::new)
                    .collect(Collectors.toList());
        }
    }

    @Value
    public static class ModuleDetailedPropertiesOutput {
        String name;
        String version;
        String versionType;
        String modulePath;
        String propertiesPath;
        List<ModuleDetailedPropertyOutput> properties;

        public ModuleDetailedPropertiesOutput(ModuleDetailedPropertiesView view) {
            name = view.getModuleKey().getName();
            version = view.getModuleKey().getVersion();
            versionType = view.getModuleKey().getVersionType().toString();
            modulePath = view.getModulePath();
            propertiesPath = view.getModulePath();
            properties = ModuleDetailedPropertyOutput.fromViews(view.getProperties());
        }

        public static List<ModuleDetailedPropertiesOutput> fromViews(List<ModuleDetailedPropertiesView> moduleDetailedPropertiesViews) {
            return moduleDetailedPropertiesViews.stream()
                    .map(ModuleDetailedPropertiesOutput::new)
                    .collect(Collectors.toList());
        }
    }

    @Value
    @AllArgsConstructor
    public static class ModuleDetailedPropertyOutput {
        String name;
        String storedValue;
        String finalValue;
        String defaultValue;
        boolean isRequired;
        boolean isPassword;
        String pattern;
        String comment;
        List<GlobalDetailedPropertyOutput> referencedGlobalProperties;
        boolean isNotUsed;

        public ModuleDetailedPropertyOutput(ModuleDetailedPropertyView view) {
            name = view.getName();
            storedValue = view.getStoredValue();
            finalValue = view.getFinalValue();
            defaultValue = defaultIfEmpty(view.getDefaultValue(), null);
            isRequired = view.isRequired();
            isPassword = view.isPassword();
            pattern = defaultIfEmpty(view.getPattern(), null);
            comment = defaultIfEmpty(view.getComment(), null);
            referencedGlobalProperties = GlobalDetailedPropertyOutput.fromViews(view.getReferencedGlobalProperties());
            isNotUsed = view.isNotUsed();
        }

        public static List<ModuleDetailedPropertyOutput> fromViews(List<ModuleDetailedPropertyView> moduleDetailedPropertyViews) {
            return moduleDetailedPropertyViews.stream()
                    .map(ModuleDetailedPropertyOutput::new)
                    .collect(Collectors.toList());
        }
    }
}
