package Dashboard;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.ImageViewBuilder;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import org.unbescape.html.HtmlEscape;

import javax.imageio.ImageIO;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.stream.Collectors;

/**
 * Created by rafi on 3/29/2016.
 */
public class News {

    private Date date;
    private String section;
    private String title;
    private String description;
    private String link;
    private String url;
    private String imgUrl;
    private Image image;

    private News() {
    }

    public News(String title, String description, String link) {
        setTitle(title);
        setDescription(description);
        setLink(link);
    }

    public News(String title, String description, String link, Image image) {
        setTitle(title);
        setDescription(description);
        setLink(link);
        setImage(image);
    }


    //Setter Methods
    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setLink(String link){
        this.link = link;
    }

    public void setImage(Image image){
        this.image = image;
    }


    //Getter Methods
    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getLink(){
        return link;
    }

    public Image getImage(){
        return image;
    }


    public static ArrayList<News> createNewsObject(){

        ArrayList<News> list = new ArrayList<News>();

        String title;
        String description;
        String link;
        String imageUrl;
        Image image;

        String yql = "select * from feed where url='http://rss.news.yahoo.com/rss/topstories' limit 25";

        try{
            String http = "http://query.yahooapis.com/v1/public/yql?q=" + URLEncoder.encode(yql, "UTF-8") + "&format=json&diagnostics=true&env=http%3A%2F%2Fdatatables.org%2Falltables.env";

            URL url = new URL(http);
            URLConnection con = url.openConnection();
            InputStream in = con.getInputStream();
            String result = new BufferedReader(new InputStreamReader(in)).lines().collect(Collectors.joining("\n"));

            JSONParser parser = new JSONParser();

            JSONObject jsonObject = (JSONObject) parser.parse(result);
            JSONObject query = (JSONObject) jsonObject.get("query");
            JSONObject results = (JSONObject) query.get("results");

            JSONArray items = (JSONArray) results.get("item");

            Iterator iterator = items.iterator();
            while (iterator.hasNext()) {
                JSONObject item = (JSONObject) iterator.next();
                title = (String)item.get("title");
                description = (String)item.get("description");

                if (description.contains("title=")) {

                   int end = description.length() - 21;
                   description = description.substring(description.indexOf("</a>") + 4 , end);
                    description = HtmlEscape.unescapeHtml(description);
                }

                link = (String)item.get("link");

                if(item.containsKey("content")){
                    JSONObject content = (JSONObject) item.get("content");
                    imageUrl = (String) content.get("url");
                    image = new Image(imageUrl);

                    list.add(new News(title, description, link, image));
                }
                else{
                    image = new Image("/assets/blankImage.png");
                    list.add(new News(title, description, link, image));
                }

            }

        }catch (Exception e){
            e.printStackTrace();
        }


        return list;
    }

}
