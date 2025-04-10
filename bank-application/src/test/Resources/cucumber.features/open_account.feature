Feature: Open a Bank Account
  Scenario: Successfully create a new bank account
    Given A user wants to open bank account
    When They send a valid account creation request with the following data
    |name | accountType | panNumber | aadhaarNumber | balance |
    | Kartik Patil | Saving   | JHFGD6758A | 674676879870 | 5600 |
    Then The account should be created successfully
    And They should receive a success response
