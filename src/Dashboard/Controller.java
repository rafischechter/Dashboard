package Dashboard;

import javafx.fxml.FXML;
import javafx.scene.control.Accordion;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

import java.util.ArrayList;

public class Controller {

    @FXML
    private void initialize(){
        updateNews();
    }

    private ArrayList<News> newsList;

    @FXML
    private Accordion newsAccordion;

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
                textArea.setMaxWidth(480);

                anchorPane.getChildren().add(textArea);
                newsAccordion.getPanes().add(titledPane);

            });

    }


}
