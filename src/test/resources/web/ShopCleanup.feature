Feature: ShopCleanup

  Background:
    Given open the login page

    @Cleanup
    Scenario Outline: Delete all available TestShops
      #Login
      When login with given data "<User>" and "<Password>"
      Then I see the dashboard
      #Delete TestShop
      Then I click on shop management
      Then shop management is open
      Then I click on shop selection
      Then a TestShop is selected
      And I delete the shop
      Then I logout


      Examples:
        | User |  Password  |
        | release_1080@channelpilot.com | Daheim123 |