Feature: Get detailed properties

  Background:
    Given an authenticated user

  Scenario: get the detail of a simple property
    Given an existing module with this template content
    """
    {{ simple-property }}
    """
    And an existing platform with this module
    And the platform has these valued properties
      | name            | value        |
      | simple-property | simple-value |
    When I get the detailed properties of this module
    Then the detailed properties of this module are
      | name            | storedValue  | finalValue   | defaultValue | isRequired | isPassword | pattern | comment | referencedGlobalProperty | isNotUsed |
      | simple-property | simple-value | simple-value |              | false      | false      |         |         |                          | false     |

  Scenario: get the detail of a property with default value
    Given an existing module with this template content
    """
    {{ default-property | @default "toto" }}
    {{ overridden-default-property | @default "toto" }}
    """
    And an existing platform with this module
    And the platform has these valued properties
      | name                        | value            |
      | overridden-default-property | overriding-value |
    When I get the detailed properties of this module
    Then the detailed properties of this module are
      | name                        | storedValue      | finalValue       | defaultValue | isRequired | isPassword | pattern | comment | referencedGlobalProperty | isNotUsed |
      | overridden-default-property | overriding-value | overriding-value | toto         | false      | false      |         |         |                          | false     |
      | default-property            |                  | toto             | toto         | false      | false      |         |         |                          | false     |

  Scenario: get the detail of a required property
    Given an existing module with this template content
    """
    {{ required-property | @required }}
    """
    And an existing platform with this module
    And the platform has these valued properties
      | name              | value          |
      | required-property | required-value |
    When I get the detailed properties of this module
    Then the detailed properties of this module are
      | name              | storedValue    | finalValue     | defaultValue | isRequired | isPassword | pattern | comment | referencedGlobalProperty | isNotUsed |
      | required-property | required-value | required-value |              | true       | false      |         |         |                          | false     |

  Scenario: get the detail of a password property
    Given an existing module with this template content
    """
    {{ password-property | @password }}
    """
    And an existing platform with this module
    And the platform has these valued properties
      | name              | value    |
      | password-property | P4$$word |
    When I get the detailed properties of this module
    Then the detailed properties of this module are
      | name              | storedValue | finalValue | defaultValue | isRequired | isPassword | pattern | comment | referencedGlobalProperty | isNotUsed |
      | password-property | P4$$word    | P4$$word   |              | false      | true       |         |         |                          | false     |

  Scenario: get the detail of a property with pattern
    Given an existing module with this template content
    """
    {{ pattern-property | @pattern "[0-9]" }}
    """
    And an existing platform with this module
    And the platform has these valued properties
      | name             | value |
      | pattern-property | 0     |
    When I get the detailed properties of this module
    Then the detailed properties of this module are
      | name             | storedValue | finalValue | defaultValue | isRequired | isPassword | pattern | comment | referencedGlobalProperty | isNotUsed |
      | pattern-property | 0           | 0          |              | false      | false      | [0-9]   |         |                          | false     |

  Scenario: get the detail of a commented property
    Given an existing module with this template content
    """
    {{ commented-property | @comment "This is a comment" }}
    """
    And an existing platform with this module
    And the platform has these valued properties
      | name               | value |
      | commented-property | foo   |
    When I get the detailed properties of this module
    Then the detailed properties of this module are
      | name               | storedValue | finalValue | defaultValue | isRequired | isPassword | pattern | comment           | referencedGlobalProperty | isNotUsed |
      | commented-property | foo         | foo        |              | false      | false      |         | This is a comment |                          | false     |

  Scenario: get the detail of a property referencing another property
    Given an existing module with this template content
    """
    {{ ref-property }}
    """
    And an existing platform with this module
    And the platform has these valued properties
      | name         | value              |
      | ref-property | {{ property-ref }} |
      | property-ref | ref-value          |
    When I get the detailed properties of this module
    Then the detailed properties of this module are
      | name         | storedValue        | finalValue | defaultValue | isRequired | isPassword | pattern | comment | referencedGlobalProperty | isNotUsed |
      | ref-property | {{ property-ref }} | ref-value  |              | false      | false      |         |         |                          | false     |
      | property-ref | ref-value          | ref-value  |              | false      | false      |         |         |                          | false     |

  Scenario: get the detail of properties referencing global properties
    Given an existing module with this template content
    """
    {{ global-property }}
    {{ ref-global-property }}
    """
    And an existing platform with this module
    And the platform has these valued properties
      | name                | value                 |
      | ref-global-property | {{ global-property }} |
    And the platform has these global properties
      | name            | value        |
      | global-property | global-value |
    When I get the detailed properties of this module
    Then the detailed properties of this module are
      | name                | storedValue           | finalValue   | defaultValue | isRequired | isPassword | pattern | comment | referencedGlobalProperty     | isNotUsed |
      | ref-global-property | {{ global-property }} | global-value |              | false      | false      |         |         | global-property=global-value | false     |
      | global-property     |                       | global-value |              | false      | false      |         |         | global-property=global-value | false     |

  Scenario: get the detail of a property without value
    Given an existing module with this template content
    """
    {{ property-without-value }}
    """
    And an existing platform with this module
    When I get the detailed properties of this module
    Then the detailed properties of this module are
      | name                   | storedValue | finalValue | defaultValue | isRequired | isPassword | pattern | comment | referencedGlobalProperty | isNotUsed |
      | property-without-value |             |            |              | false      | false      |         |         |                          | false     |

  Scenario: get the detail of a property that is not referenced in any template
    Given an existing module
    Given an existing platform with this module
    And the platform has these valued properties
      | name              | value          |
      | not-used-property | not-used-value |
    When I get the detailed properties of this module
    Then the detailed properties of this module are
      | name              | storedValue    | finalValue     | defaultValue | isRequired | isPassword | pattern | comment | referencedGlobalProperty | isNotUsed |
      | not-used-property | not-used-value | not-used-value |              | false      | false      |         |         |                          | true      |

  Scenario: get the detail of property with multiple models

  Scenario: with a global property referencing another global property

  Scenario: multiple modules

  Scenario: nested properties
