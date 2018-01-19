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
      #Check Feed Format
      When I click on check format
      Then the format is successfully tested
      #Save Feed
      When I save the feed settings
      Then the settings are saved
      #Create Export Channel
      When I click on PSM
      Then the PSM is opened

      Then I logout

      Examples:
      | User |  Password  | ShopName  | ShopUrl | FeedUrl |
      | release_1080@channelpilot.com | Daheim123 | TestShop  | www.testshop.shop | http://www.daheim.de/channelpilot?password=cP4AMz2014 |