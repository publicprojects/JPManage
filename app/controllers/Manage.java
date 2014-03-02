package controllers;

import com.google.gson.Gson;
import models.ManagerPrivilege;
import models.ManagerRole;
import models.Managers;
import utils.JSONBuilder;
import utils.JsonResponse;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chaoqing on 14-2-18.
 */
public class Manage extends  Application {
    public static void index(){
        String uid=session.get(LOGIN_USER_ID);
        if(uid==null){
            session.put(LOGIN_RESPONSE,"请登录");
            Application.index();
        }
        Managers manager=Managers.findById(Integer.decode(uid));
        List<ManagerRole> roles = manager.roles;
        List<ManagerPrivilege> privis = new ArrayList<ManagerPrivilege>();
        for (ManagerRole role : roles) {
            for (ManagerPrivilege privi : role.privileges) {
                if (!privis.contains(privi)) {
                    privis.add(privi);
                }
            }
        }
        render(manager,privis);
    }

    public static void assignRoles(Long id, String name) {
        Managers manager = Managers.findById(id);
        List<ManagerRole> has = manager.roles;
        List<ManagerRole> notHas = new ArrayList<ManagerRole>();
        List<ManagerRole> all = ManagerRole.findAll();
        for (ManagerRole ar : all) {
            if (!has.contains(ar)) {
                notHas.add(ar);
            }
        }
        render(has, notHas, name, id);
    }

    public static void assignRolesDo(String add, String remove, Long id) {
        Managers manager = Managers.findById(id);
        if (!"".equals(add)) {
            String[] adds = add.split(";");
            for (String id_ : adds) {
                Long _id = Long.parseLong(id_);
                manager.addRole((ManagerRole) ManagerRole.findById(_id));
            }
        }
        if (!"".equals(remove)) {
            String[] removes = remove.split(";");
            for (String id_ : removes) {
                Long _id = Long.parseLong(id_);
                manager.removeRole((ManagerRole) ManagerRole.findById(_id));
            }
        }
        try {
            manager.save();
            renderJSON(new JsonResponse(0, "[" + manager.userAccount
                    + "] 角色分配成功，重新登录生效。"));
        } catch (Exception e) {
            renderJSON(new JsonResponse(-1, "[" + manager.userAccount + "] 角色分配失败。"));
        }
    }

    public static void assignPrivileges(Long id, String name) {
        ManagerRole role = ManagerRole.findById(id);
        List<ManagerPrivilege> has = role.privileges;
        List<ManagerPrivilege> notHas = new ArrayList<ManagerPrivilege>();
        List<ManagerPrivilege> all = ManagerPrivilege.findAll();
        for (ManagerPrivilege allp : all) {
            if (!has.contains(allp)) {
                notHas.add(allp);
            }
        }
        render(has, notHas, name, id);
    }

    public static void assignPrivilegesDo(String add, String remove, Long id) {
        ManagerRole role = ManagerRole.findById(id);
        if (!"".equals(add)) {
            String[] adds = add.split(";");
            for (String id_ : adds) {
                Long _id = Long.parseLong(id_);
                role.addPrivilege((ManagerPrivilege) ManagerPrivilege
                        .findById(_id));
            }
        }
        if (!"".equals(remove)) {
            String[] removes = remove.split(";");
            for (String id_ : removes) {
                Long _id = Long.parseLong(id_);
                role.removePrivilege((ManagerPrivilege) ManagerPrivilege
                        .findById(_id));
            }
        }
        try {
            role.save();
            renderJSON(new JsonResponse(0, "[" + role.name + "] 权限分配成功，重新登录生效。"));
        } catch (Exception e) {
            renderJSON(new JsonResponse(-1, "[" + role.name + "] 权限分配失败。"));
        }
    }

    public static void queryData(int type){
        Gson json= JSONBuilder.build(List.class);
        switch (type){
            case MANAGERS:
                List<Managers> managers=ManageManagers.getManagers(0);
                renderJSON(json.toJson(managers));
                break;
        }
    }
}
