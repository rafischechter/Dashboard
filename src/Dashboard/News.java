package Dashboard;

import javafx.scene.image.Image;

import java.util.Date;

/**
 * Created by rafi on 3/29/2016.
 */
public class News {

    private Date date;
    private String headline;
    private String story;
    private Image image;


    //Setter Methods
    public void setHeadline(String headline) {
        this.headline = headline;
    }

    public void setStory(String story) {
        this.story = story;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    //Getter Methods

    public String getHeadline() {
        return headline;
    }

    public String getStory() {
        return story;
    }

    public Image getImage() {
        return image;
    }
}
