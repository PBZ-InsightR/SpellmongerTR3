Feature: Players

  Scenario: Player Initialisation
      Given I have a Player
      When I check his health
      Then I should have 20 HP
      When I check their names
      Then I should have "Bob"
      When I check his cardNumber
      Then I should have 0
