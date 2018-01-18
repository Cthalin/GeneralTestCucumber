Feature: FeedImport

  Background:
    Given shop management is open

  @FeedImport
  Scenario Outline: Test Feed Import
    Then I click on import feed
    Then I select HTTP and put in the feed url "<FeedUrl>"
    Then I click on test feed
    Then the feed is imported successfully

    Examples:
      | FeedUrl |
      | http://www.daheim.de/channelpilot?password=cP4AMz2014 |