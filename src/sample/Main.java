package sample;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
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

    //String siteURL = "https://www.ebay.co.uk/b/Nintendo-Switch-Video-Games/139973/bn_75532845";
    StringBuilder siteURL = new StringBuilder("https://en-gb.facebook.com/marketplace/category/search/?query=");

    //FACEBOOK - "https://en-gb.facebook.com/marketplace/item/" + ID + "/" - this gives the product url
    @Override
    public void start(Stage stage) throws Exception {
        //
        String searchProduct = "3ds games";
        searchProduct = searchProduct.replaceAll(" ", "+");//facebook has a space in its query. A "+" can be used instead.
        siteURL.append(searchProduct);
        //
        //
        System.out.println("Site: " + siteURL);
        URL websiteUrl = new URL(siteURL.toString());
        HttpURLConnection urlConn = (HttpURLConnection) websiteUrl.openConnection();
        String line;
        StringBuilder sb = new StringBuilder();
        BufferedReader in = new BufferedReader(new InputStreamReader(urlConn.getInputStream()));
        while ((line = in.readLine()) != null) {
            sb.append(line);
        }
        Document doc = Jsoup.parse(sb.toString());

        Elements elements = doc.getAllElements();

        Element targetSyntax = elements.get(elements.size()-6);

        String unformatJSON=targetSyntax.toString().substring(targetSyntax.toString().indexOf("{\"__bbox"),targetSyntax.toString().length()-21);

        JsonObject wholeProductData = (JsonObject) new JsonParser().parse(unformatJSON);

        //gets the array of products
        JsonArray productData = wholeProductData.get("__bbox").getAsJsonObject().get("result").getAsJsonObject()
                .get("data").getAsJsonObject().get("marketplace_search").getAsJsonObject().get("feed_units")
                .getAsJsonObject().get("edges").getAsJsonArray();

        for (int i = 0; i <productData.size() ; i++) {
            String title=productData.get(i).getAsJsonObject().get("node").getAsJsonObject().get("listing").getAsJsonObject().get("marketplace_listing_title").getAsString();
            String price = productData.get(i).getAsJsonObject().get("node").getAsJsonObject().get("listing").getAsJsonObject().get("listing_price").getAsJsonObject()
                    .get("formatted_amount").getAsString();
            String strikePrice = null;
            try{//looks for a strike price
                strikePrice = productData.get(i).getAsJsonObject().get("node").getAsJsonObject().get("listing").getAsJsonObject().get("strikethrough_price").getAsJsonObject()
                        .get("formatted_amount").getAsString();

            }catch (Exception e){
            }
            String id = productData.get(i).getAsJsonObject().get("node").getAsJsonObject().get("listing").getAsJsonObject().get("id").getAsString();

            products.add(new Product(title,price,strikePrice,id));
            //
            products.get(products.size()-1).output();
            System.out.println("-------------------------------------");
        }

        //System.out.println(productData);



        stage.setScene(scene);
        //stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
