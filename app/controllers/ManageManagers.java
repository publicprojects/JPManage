package controllers;

import models.Managers;

import java.util.List;

/**
 * Created by chaoqing on 14-3-2.
 */
public class ManageManagers extends Application {
    public static List<Managers> getManagers(int current){
        List<Managers> list=Managers.findAll();
        return list;
    }
}
