package Dashboard;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.shape.Line;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class Controller {

    public Controller() {
    }

    @FXML
    private void initialize(){
        updateNews();
        updateStocks();
        updateWeather();
    }

    private ArrayList<News> newsList;
    private List<Stock> stockList;
    private List<Weather> WeatherList;

    @FXML
    private Accordion newsAccordion;

    @FXML
    private VBox stockPane;

    @FXML
    private VBox WeatherPane;

    @FXML
    protected void updateWeather(){

        WeatherList = Weather.createWeatherObject();
        if(WeatherPane.getChildren().size() > 0){
            WeatherPane.getChildren().remove(0, WeatherList.size());
        }

        WeatherList.stream().forEach(weather -> {
            VBox v = new VBox();
            Label temp = new Label(weather.getCity() + " " + weather.getTemp() + "° F");
            v.getChildren().addAll(temp);

            WeatherPane.getChildren().add(v);
        });
    }

    @FXML
    protected void updateNews(){

        if(newsAccordion.getPanes().size() > 0){
            newsAccordion.getPanes().remove(0, newsList.size());
        }

        newsList = News.createNewsObject();

        newsList.stream().forEach(news ->
            {
                AnchorPane anchorPane = new AnchorPane();
                TitledPane titledPane = new TitledPane(news.getTitle(), anchorPane);
                TextArea textArea = new TextArea();
                textArea.setText(news.getDescription());
                textArea.setWrapText(true);
                textArea.setEditable(false);
                Hyperlink hyperlink = new Hyperlink();
                hyperlink.setText(news.getLink().toString());
                textArea.setMaxWidth(480);

                anchorPane.getChildren().addAll(textArea, hyperlink);
                AnchorPane.setBottomAnchor(hyperlink, -10.0);
                newsAccordion.getPanes().add(titledPane);

                hyperlink.setOnAction(e -> {
                    try {
                        java.awt.Desktop.getDesktop().browse(new URI(hyperlink.getText()));
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    } catch (URISyntaxException e1) {
                        e1.printStackTrace();
                    }
                });

            });

    }


    @FXML
    protected void updateStocks(){

        stockList = Stock.createStockObject();
        if(stockPane.getChildren().size() > 0){
            stockPane.getChildren().remove(0, stockList.size());
        }

        stockList.stream().forEach(stock -> {
            VBox v = new VBox();
            v.setMinHeight(30);

            BorderPane b = new BorderPane();
            b.setPadding(new Insets(10));

            Label symbl = new Label(stock.getSymbl().toUpperCase());
            Label lastTrade = new Label(stock.getLastTrade().toString());

            b.setLeft(symbl);
            b.setRight(lastTrade);

            v.getChildren().add(b);
            stockPane.getChildren().addAll(v);
        });
    }




}
