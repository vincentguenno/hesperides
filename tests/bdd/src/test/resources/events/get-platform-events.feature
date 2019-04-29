Feature: Get platform events

  Background:
    Given an authenticated user

  Scenario: get the events of a newly created platform
    Given an existing platform
    When I get the events of this platform
    Then 1 event is returned
    And event at index 0 is a PlatformCreatedEvent event type
