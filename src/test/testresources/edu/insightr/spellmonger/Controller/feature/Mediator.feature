Feature: Mediator
  The mediator is in charge of the player turn resolution. He is the one that decides what happen to the cards that
  were played on the previous turn.

  Scenario: Endgame
    Given a player named "Bob" with 5 health points
      And a player named "Alice" with 5 health points
    Given a playCard an "Eagle" with an attack of 3
    And a playCard an "Bear" with an attack of 5
    And a playCard an "Poison" with an attack of 3
    And a playCard an "Eagle" with an attack of 1
    Then "Bob" Should be dead