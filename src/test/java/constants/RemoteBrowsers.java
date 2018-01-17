package constants;

public enum RemoteBrowsers {

    FIREFOX,
    CHROME,
    SAFARI,
    IE;

    public static RemoteBrowsers browserForName(String browser) throws IllegalArgumentException{
        for(RemoteBrowsers b: values()){
            if(b.toString().equalsIgnoreCase(browser)){
                return b;
            }
        }
        throw browserNotFound(browser);
    }

    private static IllegalArgumentException browserNotFound(String outcome) {
        return new IllegalArgumentException(("Invalid browser [" + outcome + "]"));
    }
}
