Feature: Get stream events

  Background:
    Given an authenticated user

  Scenario: get the stream events of a newly created platform
    Given an existing platform named "TOTO" with application name "APP"
    When I get the events of "platform-APP-TOTO"
    Then 1 event is returned
    And event at index 0 is a PlatformCreatedEvent event type
