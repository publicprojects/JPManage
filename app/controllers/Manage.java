package controllers;

/**
 * Created by chaoqing on 14-2-18.
 */
public class Manage extends  Application {
    public static void index(){
        render();
    }

    public static void html(String html_name){
        render("/Manage/"+html_name+".html");
    }
}
