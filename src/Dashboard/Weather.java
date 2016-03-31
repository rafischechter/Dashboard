package Dashboard;

import javafx.scene.image.Image;

import java.util.Date;

/**
 * Created by rafi on 3/29/2016.
 */
public class Weather {

    private Date date;
    private int daysHigh;
    private int daysLow;
    private int currentTemperture;
    private int realFeeel;
    private String Description; // Sunny, Cloudy, etc..
    private Image weatherIcon;
    private int humidity;
    private int windSpeed;
    private String windDirection;


    private Weather(){}


    //Setter Methods
    public void setDaysHigh(int daysHigh) {
        this.daysHigh = daysHigh;
    }

    public void setDaysLow(int daysLow) {
        this.daysLow = daysLow;
    }

    public void setCurrentTemperture(int currentTemperture) {
        this.currentTemperture = currentTemperture;
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

    public int getCurrentTemperture() {
        return currentTemperture;
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


    public static Weather createWeatherObject(){
        return new Weather();
    }
}
