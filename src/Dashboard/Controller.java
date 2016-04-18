package Dashboard;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

public class Controller {

    public Controller() {
    }

    @FXML
    private void initialize(){
        updateNews();
        updateStocks(stockSymbols);
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
    private Accordion weatherAccordion;

    @FXML
    protected void updateWeather(){

        WeatherList = Weather.createWeatherObject();
        if(WeatherPane.getChildren().size() > 0){
            WeatherPane.getChildren().remove(0, WeatherList.size());
        }
        if(weatherAccordion.getPanes().size() > 0){
            weatherAccordion.getPanes().remove(0, WeatherList.size());
        }
        WeatherList.subList(0, 1).forEach(weather -> {
            VBox v = new VBox();
            Label current = new Label(weather.toString());
            Label text = new Label(weather.getText());

            v.getChildren().addAll(current);


            WeatherPane.getChildren().add(v);
            v.setPrefHeight(145);


            v.setStyle("-fx-background-image: url(" + weather.getImage() + ");" +
                    //"-fx-background-size: 100%;\n" +
                    "-fx-background-repeat: no-repeat;\n" +
                    "-fx-height: 100%;" +

                    "-fx-padding: 9;\n" +
                    "-fx-spacing: 8;");
            current.setStyle("-fx-text-fill: white;" +
                    "-fx-font-weight: bold;");
        });
        WeatherList.subList(2, 7).forEach(weather ->
        {
            AnchorPane anchorPane = new AnchorPane();
            TitledPane titledPane = new TitledPane(weather.getTitle(), anchorPane);
            TextArea textArea = new TextArea();

            textArea.setText(weather.getTemp() + weather.getText());
            textArea.setWrapText(true);
            textArea.setEditable(false);

            textArea.setMaxSize(480, 150);
            anchorPane.getChildren().addAll(textArea);

            weatherAccordion.getPanes().add(titledPane);

            /**hyperlink.setOnAction(e -> {
                try {
                    java.awt.Desktop.getDesktop().browse(new URI(hyperlink.getText()));
                } catch (IOException e1) {
                    e1.printStackTrace();
                } catch (URISyntaxException e1) {
                    e1.printStackTrace();
                }
            }); **/
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

                textArea.setMaxSize(480, 150);
                hyperlink.setMaxWidth(textArea.getMaxWidth());

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
        updateStocks(stockSymbols);
    }

    protected void updateStocks(List<String> stockSymbols){

        stockList = Stock.createStockObject(stockSymbols);
        if(stockPane.getChildren().size() > 0){
            stockPane.getChildren().clear();
        }

        stockList.stream().forEach(stock -> {
            VBox v = new VBox();

            Label symbl = new Label(stock.getSymbl().toUpperCase());
            Label lastTrade = new Label(stock.getLastTrade().toString());

            Button button = new Button();
            button.setText(stock.getPercentChange());
            button.setMinWidth(80);
            button.setMaxWidth(80);
            button.setAlignment(Pos.CENTER_RIGHT);

            if(stock.getPercentChange().startsWith("-")){
                button.setStyle("-fx-base: #FF0000;");
            }
            else{
                button.setStyle("-fx-base: #00FF00;");
            }


            HBox hBox = new HBox();
            Pane pane = new Pane();

            hBox.getChildren().addAll(symbl, pane, lastTrade, button);
            hBox.setMinWidth(240);
            hBox.setHgrow(pane, Priority.ALWAYS);
            hBox.setPadding(new Insets(3,3,3,3));
            hBox.setSpacing(5);
            hBox.setAlignment(Pos.CENTER);

            v.getChildren().add(hBox);
            stockPane.getChildren().addAll(v);
        });
    }

    List<String> stockSymbols = new ArrayList<String>();

    @FXML
    public void addQuote(ActionEvent actionEvent) {
        stockPane.getChildren().clear();
        Text t = new Text("Enter a quote");
        TextField t1 = new TextField();

        t1.setMinWidth(240);
        stockPane.getChildren().addAll(t, t1);

        t1.setOnAction(event -> {
            stockSymbols.add(t1.getText());
            updateStocks(stockSymbols);

        });
    }


}
