Feature: General

  Background:
    Given open the login page

    @General
    Scenario Outline: Test general function
      #Login
      When login with given data "<User>" and "<Password>"
      Then I see the dashboard
      #Create Shop
      Then I click on shop management
      Then shop management is open
      Then I click on shop selection
      Then I click on new shop
      Then I enter new shop name "<ShopName>", url "<ShopUrl>" and set active
      When I save the shop
      Then the shop is created successfully
      #Import Feed
      Then I click on import feed
      Then I select HTTP and put in the feed url "<FeedUrl>"
      When I click on test feed
      Then the feed is imported successfully


      Examples:
      | User |  Password  | ShopName  | ShopUrl | FeedUrl |
      | release_1080@channelpilot.com | Daheim123 | TestShop  | www.testshop.shop | http://www.daheim.de/channelpilot?password=cP4AMz2014 |