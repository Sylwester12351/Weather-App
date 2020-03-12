package office;

import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

import static library.Constans.*;

public class ApiJSONConverter {

    private String temperature = null;
    private String temperatureMin = null;
    private String temperatureMax = null;


    public void convert() throws IOException { // todo try catch exception
        URL url = new URL(SERVICE + City + "," + Country + "&" + "units=" + Type + "&" + "APPID=" + ApiID);
        BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
        String line = reader.readLine();
        // Pobieranie JSON

        // Wyciąganie informacji z JSON
        //*****************************************************************
        //Temp
        if (!StringUtils.isBlank(line)) {
            int startIndex = line.indexOf("{\"temp\"") + 8;
            int endIndex = line.indexOf(",\"feels_like\"");
            temperature = line.substring(startIndex, endIndex);
            // System.out.println(temperature);
        }
        //Min temp
        if (!StringUtils.isBlank(line)) {
            int startIndex = line.indexOf(",\"temp_min\"") + 12;
            int endIndex = line.indexOf(",\"temp_max\"");
            temperatureMin = line.substring(startIndex, endIndex);
            // System.out.println(temperatureMin);
        }
        //Max temp
        if (!StringUtils.isBlank(line)) {
            int startIndex = line.indexOf("\"temp_max\":") + 11;
            int endIndex = line.indexOf(",\"pressure\"");
            temperatureMax = line.substring(startIndex, endIndex);
            //System.out.println(temperatureMax);
        }//todo dodaj więcej informacji takich jak cisnienie i takie tam
        //*****************************************************************
    }

    public String getTemperatureMax() {
        return temperatureMax;
    }

    public String getTemperatureMin() {
        return temperatureMin;
    }

    public String getTemperature() {
        return temperature;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }

    public String getCountry() {
        return Country;
    }

    public void setCountry(String country) {
        Country = country;
    }

    public String getCelcius() {
        return Type;
    }

    public void setCelcius(String celcius) {
        Type = celcius;
    }

    public String getApiID() {
        return ApiID;
    }

    public void setApiID(String apiID) {
        ApiID = apiID;
    }
}
