package utils;

public enum EnvironmentsValues {
    PROD("https://www.nutanix.com/"),
    TEST_ENV("https://aemstage.nutanix.com/");
    public String urlValue;
    private EnvironmentsValues(String urlValue){
        this.urlValue = urlValue;
    }

    public static String getUrlValue() {
        String url;
        switch (LocalFileReader.getAppPropertiesValue("environment.name")) {
            case "PROD": {
                url = PROD.urlValue;
            }
            break;
            case "TEST_ENV": {
                url = TEST_ENV.urlValue;
                break;
            }
            default:
                throw new IllegalStateException("Unexpected value: " + LocalFileReader.getAppPropertiesValue("environment.name"));
        }
        return url;
    }

}
