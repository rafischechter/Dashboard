package Dashboard;

import javafx.fxml.FXML;
import javafx.scene.control.Accordion;
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.AnchorPane;

import java.util.ArrayList;

public class Controller {

    private ArrayList<News> newsList;

    @FXML
    private Accordion newsAccordion;


    public void initialize(){
        updateNews();
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
                Label label = new Label(news.getDescription());
                anchorPane.getChildren().add(label);
                newsAccordion.getPanes().add(titledPane);
            });

    }



}
