package Dashboard;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextFlow;

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

        stockList = Stock.createStockObject();
        if(stockPane.getChildren().size() > 0){
            stockPane.getChildren().remove(0, stockList.size());
        }

        stockList.stream().forEach(stock -> {
            VBox v = new VBox();

            //BorderPane b = new BorderPane();
            //b.setPadding(new Insets(10));

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

            //hBox.setAlignment(Pos.CENTER);
            //hBox.setMargin(lastTrade, new Insets(5));
            //hBox.getChildren().addAll(lastTrade, button);

            //HBox hBox1 = new HBox();
            //hBox1.setAlignment(Pos.CENTER);
            //hBox1.getChildren().add(symbl);

            //b.setLeft(hBox1);
            //b.setRight(hBox);

            //v.getChildren().add(b);

            v.getChildren().add(hBox);
            stockPane.getChildren().addAll(v);
        });
    }

    @FXML
    public void addQuote(ActionEvent actionEvent) {

    }


}
