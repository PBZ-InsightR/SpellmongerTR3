Feature: SpellMongerApp
  Scenario: Three Turns
    Given I have a Player named "Alice" with 5 health point
    And I have a Player named "Bob" with 5 health point
    When Alice plays a Beast
    And Bob plays a Wolf
    And the turn is ended
    Then Bob should lose 1 HP