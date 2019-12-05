Feature: Get detailed properties

  Background:
    Given an authenticated user

  Scenario: get the properties details of a platform without properties
    Given an existing module
    And an existing platform with this module
    When I get the detailed properties of this module
    Then the properties details are successfully retrieved and they are empty
