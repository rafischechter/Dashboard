package Dashboard;

import javafx.scene.image.Image;

import java.util.Date;

/**
 * Created by rafi on 3/29/2016.
 */
public class News {

    private Date date;
    private String section;
    private String title;
    private String abstr;
    private String url;
    private Image img;

    private News() {
    }

    private News(String section, String title, String abstr, String url) {
        setSection(section);
        setTitle(title);
        setAbstr(abstr);
        setUrl(url);
    }

    //Setter Methods
    public void setSection(String section) {
        this.section = section;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAbstr(String abstr) {
        this.abstr = abstr;
    }

    public void setUrl(String url) {
        this.url = url;
    }


    //Getter Methods
    public String getSection() {
        return section;
    }

    public String getTitle() {
        return title;
    }

    public String getAbstr() {
        return abstr;
    }

    public String getUrl() {
        return url;
    }


    public static News createNewsObject(){
        return new News();
    }

}
