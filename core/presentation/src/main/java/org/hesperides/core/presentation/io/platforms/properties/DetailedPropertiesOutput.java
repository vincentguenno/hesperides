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

import lombok.Value;

import java.util.List;

@Value
public class DetailedPropertiesOutput {

    String applicationName;
    String platformName;
    List<GlobalDetailedPropertyOutput> properties;
    List<ModuleDetailedPropertiesOutput> modules;

    public static class GlobalDetailedPropertyOutput {
        String name;
        String storedValue;
        String finalValue;
    }

    public static class ModuleDetailedPropertiesOutput {
        String name;
        String version;
        String versionType;
        List<ModuleDetailedPropertyOutput> properties;

    }

    public static class ModuleDetailedPropertyOutput {
        String name;
        String storedValue;
        String finalValue;
        String defaultValue;
        List<String> mustacheContents;
        List<GlobalDetailedPropertyOutput> referencedGlobalProperties;
    }
}
