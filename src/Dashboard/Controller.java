package Dashboard;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.StrokeType;
import javafx.scene.text.*;
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

    private List<String> stockSymbols = new ArrayList<String>();

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

        if(WeatherPane.getChildren().size() > 0){
            WeatherPane.getChildren().remove(0, 1);
        }
        if(weatherAccordion.getPanes().size() > 0){
            weatherAccordion.getPanes().clear();
        }

            VBox v = new VBox();
            Text current = new Text(WeatherCurrent.toString());


            v.getChildren().addAll(current);


            WeatherPane.getChildren().add(v);
            v.setPrefHeight(151);

            //current.setWrapText(true);
            v.setStyle("-fx-background-image: url(" + WeatherCurrent.getImage() + ");" +
                    "-fx-background-position: center center;" +
                    "-fx-background-size: 100%;" +
                    //"-fx-background-size: 100%;\n" +
                    "-fx-background-repeat: no-repeat;\n" +
                    "-fx-height: 151;" +

                    "-fx-padding: 11;\n" +
                    "-fx-spacing: 8;");
            current.setStyle("-fx-text-fill: white;" +
                    "-fx-font-size: 14;" +
                    "-fx-font-weight: 900;" +
                    "-fx-stroke: black;" +
                    "-fx-stroke-width: .4;");

        current.setFill(Color.WHITE);
        //current.set;
        //current.setStrokeType(StrokeType.OUTSIDE);



        WeatherList.subList(1, 6).forEach(weather ->
        {
            HBox test = new HBox();
            Pane spacer = new Pane();
            AnchorPane anchorPane = new AnchorPane();
            anchorPane.setMinHeight(130);

            VBox fV = new VBox();

            Text forecast = new Text(weather.toString());
            fV.getChildren().addAll(forecast);
            WeatherPane.getChildren().addAll(fV);
            //forecast.setWrapText(true);
            /**textArea.setEditable(false);
            textArea.setOpacity(2);
**/
            ImageView icon = new ImageView(weather.getIcon());
            icon.setFitHeight(30);
            icon.setFitWidth(30);

            Label text = new Label(weather.getTitle());
            test.getChildren().addAll(text, spacer, icon);
            test.setMinWidth(205);
            test.setHgrow(spacer, Priority.ALWAYS);
            test.setSpacing(5);
            test.setAlignment(Pos.CENTER);

            TitledPane titledPane = new TitledPane("", anchorPane);
            titledPane.setGraphic(test);
            titledPane.setContentDisplay(ContentDisplay.RIGHT);
            titledPane.setGraphicTextGap(165);
            titledPane.setStyle(
                    "-fx-font-weight: bold;\n");
            fV.setPrefWidth(255);
            fV.setPrefHeight(130);
            fV.setStyle("-fx-background-image: url(" + weather.getImage() + ");" +
                    "-fx-background-position: center center;" +
                    "-fx-background-size: 100%;" +
                    //"-fx-background-size: 100%;\n" +
                    "-fx-background-repeat: no-repeat;\n" +
                    "-fx-height: 130;" +
                    "-fx-padding: 11;\n" +
                    "-fx-spacing: 8;");
            forecast.setStyle("-fx-text-fill: white;" +
                    "-fx-font-size: 14;" +
                    "-fx-font-weight: 900;" +
                    "-fx-stroke: black;" +
                    "-fx-stroke-width: .4;");
            forecast.setFill(Color.WHITE);
            anchorPane.getChildren().addAll(fV);
            weatherAccordion.getPanes().add(titledPane);

            //test.setContent(weatherAccordion);
            //test.getContent(ScrollBar);

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

                ImageView imageView = new ImageView(news.getImage());
                imageView.setFitHeight(30);
                imageView.setPreserveRatio(true);

                titledPane.setGraphic(imageView);

                textArea.setText(news.getDescription());
                textArea.setWrapText(true);
                textArea.setEditable(false);
                Hyperlink hyperlink = new Hyperlink();
                hyperlink.setText(news.getLink().toString());

                textArea.setMaxSize(480, 150);
                textArea.setMinSize(480, 150);
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
            Label lastTrade = new Label(stock.getLastTrade());

            Button button = new Button();
            HBox buttonFormat = new HBox();
            buttonFormat.setMinHeight(22);
            Text sign = new Text(stock.getPercentChange().charAt(0) + "");
            sign.setFill(Color.WHITE);
            Pane spacer = new Pane();
            Text percentChange = new Text(stock.getPercentChange().substring(1,5) + "%");
            //percentChange.setFont(Font.font("Verdana",));
            percentChange.setFill(Color.WHITE);

            buttonFormat.getChildren().addAll(sign, spacer, percentChange);
            buttonFormat.setHgrow(spacer, Priority.ALWAYS);
            buttonFormat.setAlignment(Pos.CENTER_RIGHT);
            
            button.setGraphic(buttonFormat);
            button.setMinWidth(70);
            button.setMaxWidth(70);
            button.setAlignment(Pos.CENTER_RIGHT);


            if(stock.getPercentChange().startsWith("-")){
                button.setStyle("-fx-base: #FF0000;");
            }
            else{
                button.setStyle("-fx-base: #00E500;");
            }

            HBox hBox = new HBox();
            Pane pane = new Pane();
            AnchorPane anchorPane = new AnchorPane();
            TitledPane titledPane = new TitledPane("", anchorPane);
            anchorPane.setMinWidth(255);

            hBox.getChildren().addAll(symbl, pane, lastTrade, button);
            hBox.setMinWidth(205);
            hBox.setHgrow(pane, Priority.ALWAYS);
            hBox.setSpacing(5);
            hBox.setAlignment(Pos.CENTER);


            HBox hBox1 = new HBox();
            hBox1.setPadding(new Insets(5));

            VBox left = new VBox();
            VBox right = new VBox();

            right.setPadding(new Insets(0,0,0,20));


            VBox vBox1 = new VBox();
            vBox1.setPadding(new Insets(5));
            Text openD = new Text("$" + stock.getOpen());
            Text open = new Text("Open");
            open.setFill(Color.GREY);
            vBox1.getChildren().addAll(openD, open);

            VBox vBox2 = new VBox();
            vBox2.setPadding(new Insets(5));
            Text todaysHighD = new Text("$" + stock.getDaysHigh());
            Text todaysHigh = new Text("Todays High");
            todaysHigh.setFill(Color.GREY);
            vBox2.getChildren().addAll(todaysHighD, todaysHigh);

            VBox vBox3 = new VBox();
            vBox3.setPadding(new Insets(5));
            Text todaysLowD = new Text("$" + stock.getDaysLow());
            Text todaysLow = new Text("Todays Low");
            todaysLow.setFill(Color.GREY);
            vBox3.getChildren().addAll(todaysLowD, todaysLow);

            VBox vBox4 = new VBox();
            vBox4.setPadding(new Insets(5));
            Text volumeD = new Text(stock.getVolume());
            Text volume = new Text("Volume");
            volume.setFill(Color.GREY);
            vBox4.getChildren().addAll(volumeD, volume);

            VBox vBox5 = new VBox();
            vBox5.setPadding(new Insets(5));
            Text avgVolumeD = new Text(stock.getAvgVolume());
            Text avgVolume = new Text("Average Volume");
            avgVolume.setFill(Color.GREY);
            vBox5.getChildren().addAll(avgVolumeD, avgVolume);

            VBox vBox6 = new VBox();
            vBox6.setPadding(new Insets(5));
            Text marketCapD = new Text("$" + stock.getMarketCap());
            Text marketCap = new Text("Market Cap");
            marketCap.setFill(Color.GREY);
            vBox6.getChildren().addAll(marketCapD, marketCap);


            left.getChildren().addAll(vBox1, vBox2, vBox3);
            right.getChildren().addAll(vBox4, vBox5, vBox6);

            hBox1.getChildren().addAll(left, right);

            anchorPane.getChildren().addAll(hBox1);

            titledPane.setGraphic(hBox);

            stocksAccordion.getPanes().add(titledPane);
        });
    }



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
