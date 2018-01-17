Feature: General

  Background:
    Given open the login page

    @General
    Scenario Outline: Test general function
      Then login with given data "<User>" and "<Password>"

      Examples:
      | User |  Password  |
      | release_1080@channelpilot.com | Daheim123 |