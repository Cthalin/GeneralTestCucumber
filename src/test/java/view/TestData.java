package view;

public class TestData {
    String ref;
    String logo;
    String title;
    String url;
    String user;
    String passwd;
    String feedUrl;
    String shopTitle;
    String shopUrl;

    TestData(String chan){

        switch (chan){
            case "google":
                this.ref = "#googleShopping";
                this.logo = "https://cdn-frontend-channelpilotsolu.netdna-ssl.com/images/channels/medium/googleShopping.png";
                this.title = "googleShopping (DE)";
                break;
            case "idealo":
                this.ref = "#idealo.de";
                this.logo = "https://cdn-frontend-channelpilotsolu.netdna-ssl.com/images/channels/medium/idealo.de.png";
                this.title = "idealo (DE)";
                break;
            default:
                System.out.println("No predefined channel");
                break;
        }

        //Release
        this.url = "https://release.go.channelpilot.com";
        this.user = "release_1080@channelpilot.com";
        this.passwd = "Daheim123";
        //Würfel
//        this.url = "http://erikswuerfel";
//        this.user = "erik.slowikowski@channelpilot.com";
//        this.passwd = "*uUQL;k!6(9p&Em&";

        this.feedUrl = "http://www.daheim.de/channelpilot?password=cP4AMz2014";
        this.shopTitle = "TestShop";
        this.shopUrl = "www.testshop.shop";
    }
}
