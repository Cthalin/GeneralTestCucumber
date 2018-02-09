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
      Then I logout

      Examples:
        | User |  Password  | ShopName  | ShopUrl |
        | release_1080@channelpilot.com | Daheim123 | TestShop  | www.testshop.shop |

    @General
    Scenario Outline: Import Feed
      #Login
      When login with given data "<User>" and "<Password>"
      Then I see the dashboard
      #Open TestShop
      Then I click on shop management
      Then shop management is open
      Then I click on shop selection
      Then a TestShop is selected
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
      Then I logout

      Examples:
        | User |  Password  | FeedUrl |
        | release_1080@channelpilot.com | Daheim123 | http://www.daheim.de/channelpilot?password=cP4AMz2014 |

    @General
    Scenario Outline: Add Export Channel
      #Login
      When login with given data "<User>" and "<Password>"
      Then I see the dashboard
      #Create Export Channel
      When I click on PSM
      Then the PSM is opened
      Then I select the test shop named "<ShopName>"
      When I click on add channel
      Then Channel Selection is opened
      When I click on channel "<ChannelRef>"
      Then given channel "<ChannelLogo>" is selected
      When I click on add
      Then the channel "<ChannelTitle>" is added
      Then I open the export channel "<ChannelTitle>"
      Then I set the channel to active
      Then I check the export feed "<ChannelTitle>" on "<ChannelFile>"
      Then I logout

      Examples:
        | User |  Password  | ShopName  | ChannelRef  | ChannelLogo | ChannelTitle  |    ChannelFile |
        | release_1080@channelpilot.com | Daheim123 | TestShop  | #idealo.de  | https://cdn-frontend-channelpilotsolu.netdna-ssl.com/images/channels/medium/idealo.de.png | idealo (DE) |    idealode  |

    @General
    Scenario Outline: Create Filter
      #Login
      When login with given data "<User>" and "<Password>"
      Then I see the dashboard
      #Open TestShop
      Then I click on shop management
      Then shop management is open
      Then I click on shop selection
      Then a TestShop is selected
      #Add Filter
      Then I click on set up filter
      Then I click on add filter
      Then I put in a filter name "<FilterName>" and save it
      Then I setup a filter for price
      When I click on PSM
      Then the PSM is opened
      Then I select the test shop named "<ShopName>"
      Then I open the export channel "<ChannelTitle>"
      Then I select the created testfilter
      Then I check the export feed "<ChannelTitle>" on "<ChannelFile>"
      Then I logout

      Examples:
        | User |  Password  | ShopName  |      ChannelTitle  |    ChannelFile |  FilterName  |
        | release_1080@channelpilot.com | Daheim123 | TestShop  |      idealo (DE) |    idealode  |  TestFilter  |

    @General
    Scenario Outline: Delete TestShop
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