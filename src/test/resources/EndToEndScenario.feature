Feature: End To End Scenario for Books Stores

  
  Scenario: End To End Scenario for Books Store
    Given I am Authorized User
    When I assign a book to myself
    Then I remove the book 
    And I confirm the book is removed

