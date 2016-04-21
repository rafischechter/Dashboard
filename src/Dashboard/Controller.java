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
    private Weather WeatherCurrent;
    private List<Weather> WeatherList;

    @FXML
    private Accordion newsAccordion;


    @FXML
    private Accordion stocksAccordion;

    @FXML
    private AnchorPane stocksPane;

    @FXML
    private VBox WeatherPane;

    @FXML
    private Accordion weatherAccordion;

    @FXML
    protected void updateWeather(){

        WeatherCurrent = Weather.WeatherCurrentObject();

        WeatherList = Weather.WeatherForecastObject();

        if(weatherAccordion.getPanes().size() > 0){
            weatherAccordion.getPanes().remove(0, WeatherList.size());
        }


            VBox v = new VBox();
            Label current = new Label(WeatherCurrent.toString());
            Label text = new Label(WeatherCurrent.getText());

            v.getChildren().addAll(current);


            WeatherPane.getChildren().add(v);
            v.setPrefHeight(145);

            current.setWrapText(true);
            v.setStyle("-fx-background-image: url(" + WeatherCurrent.getImage() + ");" +
                    "-fx-background-size: 100%;\n" +
                    "-fx-background-repeat: no-repeat;\n" +
                    //"-fx-height: 100%;" +

                    "-fx-padding: 9;\n" +
                    "-fx-spacing: 8;");
            current.setStyle("-fx-text-fill: white;" +
                    "-fx-font-weight: bold;");


        WeatherList.subList(1, 6).forEach(weather ->
        {
            AnchorPane anchorPane = new AnchorPane();
            TitledPane titledPane = new TitledPane(weather.getTitle(), anchorPane);
            VBox fV = new VBox();

            Label forecast = new Label(weather.toString());
            fV.getChildren().addAll(forecast);
            forecast.setWrapText(true);
            /**textArea.setEditable(false);
            textArea.setOpacity(2);
**/
            ImageView dd = new ImageView(weather.getIcon());
            titledPane.setGraphic(dd);
            titledPane.setContentDisplay(ContentDisplay.RIGHT);
            titledPane.setGraphicTextGap(135);
            titledPane.setStyle("-fx-font-weight: bold;\n" +
            "-fx-color: #e6e6ff;");
            fV.setPrefSize(270, 150);
            //fV.setPrefHeight(130);
            fV.setStyle("-fx-background-image: url(" + weather.getImage() + ");" +
                    "-fx-background-size: 100%;\n" +
                    "-fx-background-repeat: no-repeat;\n" +
                    //"-fx-height: 100%;" +
                    "-fx-padding: 9;\n" +
                    "-fx-spacing: 8;");
            forecast.setStyle("-fx-text-fill: white;" +
                    "-fx-font-weight: bold;");
            anchorPane.getChildren().addAll(fV);
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
        if(stocksAccordion.getPanes().size() > 0){
            stocksAccordion.getPanes().clear();
        }

        stockList.stream().forEach(stock -> {

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
            AnchorPane anchorPane = new AnchorPane();
            TitledPane titledPane = new TitledPane("", anchorPane);
            anchorPane.setMinWidth(240);
            anchorPane.setMinHeight(100);


            hBox.getChildren().addAll(symbl, pane, lastTrade, button);
            hBox.setMinWidth(205);
            hBox.setHgrow(pane, Priority.ALWAYS);
            //hBox.setPadding(new Insets(3,3,3,3));
            hBox.setSpacing(5);
            hBox.setAlignment(Pos.CENTER);



            titledPane.setGraphic(hBox);

            stocksAccordion.getPanes().add(titledPane);
        });
    }

    List<String> stockSymbols = new ArrayList<String>();

    @FXML
    public void addQuote(ActionEvent actionEvent) {
        stocksAccordion.getPanes().clear();
        Text text = new Text("Enter a quote");
        TextField textField = new TextField();

        textField.setMinWidth(240);
        VBox vBox = new VBox();
        vBox.getChildren().addAll(text, textField);
        stocksPane.getChildren().addAll(vBox);
        textField.requestFocus();

        textField.setOnAction(event -> {
            stockSymbols.add(textField.getText());
            stocksPane.getChildren().remove(vBox);
            updateStocks(stockSymbols);

        });
    }


}
