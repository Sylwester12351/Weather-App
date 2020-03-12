import office.ApiJSONConverter;
import office.TimeUpdate;
import org.apache.commons.lang3.StringUtils;

import java.io.*;

public class ReadConfig {

    private ApiJSONConverter converter = new ApiJSONConverter();
    private TimeUpdate timeUpdate = new TimeUpdate();
    private String api;
    private String city;
    private String country;
    String text;


    public ReadConfig readConf() throws IOException { // todo try catch
        BufferedReader bufferedReader = new BufferedReader(new FileReader("conf"));
        text = bufferedReader.readLine();
        System.out.println(text);
        if (!StringUtils.isBlank(text)) {
            int startIndex = text.indexOf("API={") + 5;
            int endIndex = text.indexOf("};CITY");
            api = text.substring(startIndex, endIndex);
            converter.setApiID(api);
            System.out.println(api);
        }
        if (!StringUtils.isBlank(text)) {
            int startIndex = text.indexOf(";CITY={") + 7;
            int endIndex = text.indexOf("};COUNTRY");
            city = text.substring(startIndex, endIndex);
            converter.setCity(city);
            System.out.println(city);
        }
        if (!StringUtils.isBlank(text)) {
            int startIndex = text.indexOf("COUNTRY={") + 9;
            int endIndex = text.indexOf("};TIME");
            country = text.substring(startIndex, endIndex);
            converter.setCountry(country);
            System.out.println(country);
        }
        return null;
    }
}