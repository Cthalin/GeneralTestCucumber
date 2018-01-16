public class TestData {
    String ref;
    String logo;
    String title;

    public TestData(String chan){

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
    }
}
