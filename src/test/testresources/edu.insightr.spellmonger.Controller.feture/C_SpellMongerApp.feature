Feature: SpellMongerApp
  Scenario: Three Turns
    Given I have a Player named "Alice"
    And I have a Player named "Bob"
    When Alice plays a Beast
    And Bob plays a Wolf
    And the turn is ended
    Then Bob should lose 1 HP