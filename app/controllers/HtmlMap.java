package controllers;

/**
 * Created by chaoqing on 14-3-1.
 */
public class HtmlMap extends Application {
    public static void html(String name){
        render("/HtmlMap/"+name+".html");
    }
}
