package Dashboard;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.unbescape.html.HtmlEscape;
import java.io.File;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by rafi on 3/29/2016.
 */
public class Weather {

    private Date date;
    private String high;
    private String low;
    private String title;
    private String temp;
    private String text;
    private int realFeeel;
    private String Description; // Sunny, Cloudy, etc..
    private Image weatherIcon;
    private int humidity;
    private int windSpeed;
    private String windDirection;
    private String image;
    private Image icon;

    private Weather(String title, String temp, String high, String low, String text, String image){
        this.title = title;
        this.temp = temp;
        this.text = text;
        this.image = image;
        this.high = high;
        this.low = low;
    }

    private Weather(String title, String temp, String high, String low, String text, String image, Image icon){
        this.title = title;
        this.temp = temp;
        this.text = text;
        this.image = image;
        this.high = high;
        this.low = low;
        this.icon = icon;
    }

    public static Weather WeatherCurrentObject() {

        String image = null;
        String city = null;
        String temp = null;
        String text = null;
        String high = null;
        String low = null;

        String yql = "select * from weather.forecast where woeid in (select woeid from geo.places(1) where text=\"brooklyn, ny\")";

        try {

            String http = "https://query.yahooapis.com/v1/public/yql?q=" + URLEncoder.encode(yql, "UTF-8") + "&format=json&env=store%3A%2F%2Fdatatables.org%2Falltableswithkeys";

            URL url = new URL(http);
            URLConnection con = url.openConnection();
            InputStream in = con.getInputStream();
            String result = new BufferedReader(new InputStreamReader(in)).lines().collect(Collectors.joining("\n"));

            JSONParser parser = new JSONParser();

            JSONObject jsonObject = (JSONObject) parser.parse(result);
            JSONObject query = (JSONObject) jsonObject.get("query");
            JSONObject results = (JSONObject) query.get("results");
            JSONObject channel = (JSONObject) results.get("channel");

            JSONObject location = (JSONObject) channel.get("location");
            city = location.get("city") + "," + location.get("region") + " " + location.get("country");

            JSONObject item = (JSONObject) channel.get("item");
            JSONObject condition = (JSONObject) item.get("condition");

            temp = (String)condition.get("temp");
            text = (String)condition.get("text");

            String conCode = (String)condition.get("code");
            String file = "./src/assets/" + conCode + ".gif";
            System.out.println(conCode);

            try {
                File f = new File(file);
                File ff = new File(f.getAbsolutePath());
                System.out.println(f.getAbsolutePath());
                if (ff.exists())
                    image = "/assets/" + conCode + ".gif";  //imageSwitch(text);
                else
                    image = "/assets/error.gif";
            }
            catch(Exception e){
                e.printStackTrace();
            }

            JSONArray forecast = (JSONArray) item.get("forecast");
            JSONObject today = (JSONObject) forecast.get(0);
            high = (String)today.get("high");
            low = (String)today.get("low");



            System.out.println(city + " " + temp + "\n" + text);

        }catch (Exception e){
            e.printStackTrace();
        }

        Weather current = new Weather(city, temp, high, low, text, image);

        return current;
    }

    public static List<Weather> WeatherForecastObject() {

        List<Weather> list = new ArrayList<>();

        String file;
        String image="";
        String temp;
        String text;
        String title;
        String conditionCode;
        String high;
        String low;

        String yql = "select * from weather.forecast where woeid in (select woeid from geo.places(1) where text=\"brooklyn, ny\")";

        try {

            String http = "https://query.yahooapis.com/v1/public/yql?q=" + URLEncoder.encode(yql, "UTF-8") + "&format=json&env=store%3A%2F%2Fdatatables.org%2Falltableswithkeys";

            URL url = new URL(http);
            URLConnection con = url.openConnection();
            InputStream in = con.getInputStream();
            String result = new BufferedReader(new InputStreamReader(in)).lines().collect(Collectors.joining("\n"));

            JSONParser parser = new JSONParser();

            JSONObject jsonObject = (JSONObject) parser.parse(result);
            JSONObject query = (JSONObject) jsonObject.get("query");
            JSONObject results = (JSONObject) query.get("results");
            JSONObject channel = (JSONObject) results.get("channel");

            JSONObject item = (JSONObject) channel.get("item");

            JSONArray forecast = (JSONArray) item.get("forecast");

            Iterator iterator = forecast.iterator();
            while (iterator.hasNext()) {
                JSONObject day = (JSONObject) iterator.next();
                title = (String) day.get("day");
                conditionCode = (String) day.get("code");
                high = (String) day.get("high");
                low = (String) day.get("low");
                temp = getAverage(high, low);
                text = (String) day.get("text");

                file = "./src/assets/" + conditionCode + ".gif";
                System.out.println(file);

                try {
                    File imageFile = new File(file);
                    File imagePath = new File(imageFile.getAbsolutePath());
                    System.out.println(imageFile.getAbsolutePath());
                    if (imagePath.exists())
                        image = "/assets/" + conditionCode + ".gif";  //imageSwitch(text);
                    else
                        image = "/assets/error.gif";
                }
                catch(Exception e){
                    e.printStackTrace();
                }
                Image icon = new Image("http://l.yimg.com/a/i/us/we/52/" + conditionCode + ".gif");
                list.add(new Weather(title, temp, high, low, text, image, icon));
            }

        }catch (Exception e){
            e.printStackTrace();
        }


        return list;
    }

    //Setter Methods
    public void setHigh(String high) {
        this.high = high;
    }

    public void setLow(String low) {
        this.low = low;
    }

    public void setTemp(int currentTemperture) {
        this.temp = temp;
    }

    public void setRealFeeel(int realFeeel) {
        this.realFeeel = realFeeel;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setHumidity(int humidity) {
        this.humidity = humidity;
    }

    public void setWindSpeed(int windSpeed) {
        this.windSpeed = windSpeed;
    }

    public void setWindDirection(String windDirection) {
        this.windDirection = windDirection;
    }

    //Getter Methods

    public String getHigh() {
        high = "High: " + high + "° F";
        return high;
    }

    public String getLow() {
        low = "Low:  " + low + "° F";
        return low;
    }

    public static String getAverage(String high, String low){
        int highS = Integer.parseInt(high);
        int lowS = Integer.parseInt(low);
        int average = (highS + lowS) / 2;
        return Integer.toString(average);
    }

    public String getTemp() {
        return temp;
    }
    public String getTitle() {
        return title;
    }
    public String getText() {
        return text;
    }
    public String getImage() {
        return image;
    }
    public int getRealFeeel() {
        return realFeeel;
    }

    public String getDescription() {
        return Description;
    }

    public Image getIcon() {
        return icon;
    }

    public int getHumidity() {
        return humidity;
    }

    public int getWindSpeed() {
        return windSpeed;
    }

    public String getWindDirection() {
        return windDirection;
    }

    @Override
    public String toString() {
        String current = String.format("%1$s %2$13s %3$s\n%4$61s\n%5$61s\n%6$s", getTitle(), getTemp(), "° F", getHigh(), getLow(), getText());
        return current;
    }
}



