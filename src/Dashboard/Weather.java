package Dashboard;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.unbescape.html.HtmlEscape;

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
    private int daysHigh;
    private int daysLow;
    private String city;
    private String temp;
    private String text;
    private int realFeeel;
    private String Description; // Sunny, Cloudy, etc..
    private Image weatherIcon;
    private int humidity;
    private int windSpeed;
    private String windDirection;
    private Image image;

    private Weather(String city, String temp, String text, Image image){
        this.city = city;
        this.temp = temp;
        this.text = text;
        this.image = image;
    }

    public static List<Weather> createWeatherObject() {
        List<Weather> list = new ArrayList<>();

        String city;
        String temp;
        String text;
        String description;
        String image;
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

           // JSONObject imgURL = (JSONObject) channel.get("image");
           // image = new Image("http://www.animatedimages.org/data/media/606/animated-rain-image-0043.gif");
            Image image1 = new Image("http://www.animatedimages.org/data/media/606/animated-rain-image-0043.gif");

            // simple displays ImageView the image as is
            ImageView iv1 = new ImageView();
            iv1.setImage(image1);

            list.add(new Weather(city, temp, text, image1));

            System.out.println(city + " " + temp + "\n" + text);




        }catch (Exception e){
            e.printStackTrace();
        }


        return list;
    }


    //Setter Methods
    public void setDaysHigh(int daysHigh) {
        this.daysHigh = daysHigh;
    }

    public void setDaysLow(int daysLow) {
        this.daysLow = daysLow;
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

    public void setWeatherIcon(Image weatherIcon) {
        this.weatherIcon = weatherIcon;
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

    public int getDaysHigh() {
        return daysHigh;
    }

    public int getDaysLow() {
        return daysLow;
    }

    public String getTemp() {
        return temp;
    }
    public String getCity() {
        return city;
    }
    public String getText() {
        return text;
    }
    public Image getImage() {
        return image;
    }
    public int getRealFeeel() {
        return realFeeel;
    }

    public String getDescription() {
        return Description;
    }

    public Image getWeatherIcon() {
        return weatherIcon;
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
}



