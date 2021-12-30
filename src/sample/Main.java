package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class Main extends Application {

    Group group = new Group();
    Scene scene = new Scene(group, 500, 500);

    ArrayList<Product> products = new ArrayList<>();

    String siteURL = "https://www.ebay.co.uk/b/Nintendo-Switch-Video-Games/139973/bn_75532845";

    //NINTENDO SWITCH GAMES - https://www.ebay.co.uk/b/Nintendo-Switch-Video-Games/139973/bn_75532845

    //ELEMENT title - s-item__title s-item__title--has-tags
    //BASE TITLE ELEMENT - s-item__title
//"https://www.ebay.co.uk/b/Nintendo-Switch-Video-Games/139973/bn_75532845"
    @Override
    public void start(Stage stage) throws Exception {

        System.out.println("Site: " + siteURL);
        URL websiteUrl = new URL(siteURL);
        HttpURLConnection urlConn = (HttpURLConnection) websiteUrl.openConnection();
        String line;
        StringBuilder sb = new StringBuilder();
        BufferedReader in = new BufferedReader(new InputStreamReader(urlConn.getInputStream()));
        while ((line = in.readLine()) != null) {
            sb.append(line);
        }
        Document doc = Jsoup.parse(sb.toString());

        //Elements test = doc.getElementsByClass("s-item__info clearfix");

        Elements items = doc.getElementsByClass("s-item__info clearfix");
        for (Element product : items) {
            if (!product.getElementsByClass("s-item__title-tag").toString().contains("SPONSORED")) {
                String title = product.getElementsByClass("s-item__title").toString();
                title = title.replaceAll("\\<.*?\\>", "");
                title=title.replaceAll("amp;","");
                //
                String price = product.getElementsByClass("s-item__price").toString();
                price = price.replaceAll("\\<.*?\\>", "");
                //
                String url = product.getElementsByClass("s-item__link").toString();
                url = url.substring(url.indexOf("href=") + 6, url.indexOf("><h3") - 1);

                products.add(new Product(product, title, price, url));
                products.get(products.size() - 1).output();

                //System.out.println(product);
                System.out.println("-------------------------------------------------------------");
            }
        }

        stage.setScene(scene);
        //stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
