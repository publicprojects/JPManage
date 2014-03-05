package controllers;

import com.google.gson.Gson;
import models.ManagerPrivilege;
import models.ManagerRole;
import models.Managers;
import play.db.jpa.GenericModel;
import play.db.jpa.Model;
import utils.DateUtils;
import utils.JSONBuilder;
import utils.JsonResponse;
import utils.Pagination;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by chaoqing on 14-2-18.
 */
public class Manage extends Application {
    public static void index() {
        String uid = session.get(LOGIN_USER_ID);
        if (uid == null) {
            session.put(LOGIN_RESPONSE, "请登录");
            Application.index();
        }
        Managers manager = Managers.findById(Long.decode(uid));
        List<ManagerRole> roles = manager.roles;
        List<ManagerPrivilege> privis = new ArrayList<ManagerPrivilege>();
        for (ManagerRole role : roles) {
            for (ManagerPrivilege privi : role.privileges) {
                if (!privis.contains(privi)) {
                    privis.add(privi);
                }
            }
        }
        render(manager, privis);
    }

    public static void assignRoles(Long id) {
        Managers manager = Managers.findById(id);
        String account=manager.userAccount;
        List<ManagerRole> has = manager.roles;
        List<ManagerRole> notHas = new ArrayList<ManagerRole>();
        List<ManagerRole> all = ManagerRole.findAll();
        for (ManagerRole ar : all) {
            if (!has.contains(ar)) {
                notHas.add(ar);
            }
        }
        render(has, notHas,account, id);
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

    public static void queryData(int type, int current, String[] key, String[] val) {
        Pagination page = Pagination.getInstance();
        switch (type) {
            case TYPE_MANAGERS:
                Map<Object, Object> managers = ManageManagers.getManagers(page, current, key, val);
                renderJSON(managers);
                break;
            case TYPE_ROLES:
                Map<Object, Object> roles = ManageRoles.getRoles(page, current, key, val);
                renderJSON(roles);
                break;
        }
    }

    public static void createData(int type) {
        switch (type) {
            case TYPE_MANAGERS:
                Managers data = params.get("data", Managers.class);
                Managers temp = Managers.find("userAccount=?", data.userAccount).first();
                if (temp != null) {
                    renderJSON(new JsonResponse(-1, "管理员账号[" + data.userAccount + "]已经存在，请重新设置"));
                    return;
                }
                data.generatorPass(data.userPass);
                data.save();
                renderJSON(new JsonResponse(0, "管理员[" + data.userAccount + "/" + data.userName + "]创建成功"));
                break;
            case TYPE_ROLES:
                ManagerRole role=params.get("data",ManagerRole.class);
                ManagerRole temp_r=ManagerRole.find("name=?",role.name).first();
                if(temp_r!=null){
                    renderJSON(new JsonResponse(-1, "角色名称[" + role.name + "]已经存在，请重新设置"));
                    return;
                }
                role.createAt= DateUtils.getDaysNear(0);
                role.save();
                renderJSON(new JsonResponse(0, "角色[" + role.name + "]创建成功"));
                break;
        }
    }

    public static void delData(int type, Long id) {
        switch (type) {
            case TYPE_MANAGERS:
                Managers manager = Managers.findById(id);
                manager.delete();
                renderJSON(new JsonResponse(0, "管理员[" + manager.userAccount + "/" + manager.userName + "]已成功删除"));
                break;
            case TYPE_ROLES:
                ManagerRole role=ManagerRole.findById(id);
                role.delete();
                renderJSON(new JsonResponse(0, "角色[" + role.name + "]已成功删除"));
                break;
        }
    }

    public static void delDataConfirm(int type, Long id) {
        GenericModel data = null;
        switch (type) {
            case TYPE_MANAGERS:
                data = Managers.findById(id);
                break;
            case TYPE_ROLES:
                data=ManagerRole.findById(id);
                break;
        }
        render("HtmlMap/delDataConfirm.html", type, id, data);
    }

    public static void getPerDataById(int type,Long id){
        GenericModel data;
        switch (type){
            case TYPE_MANAGERS:
                data=Managers.findById(id);
                render("/HtmlMap/addManager.html",data);
                break;
            case TYPE_ROLES:
                data=ManagerRole.findById(id);
                render("/HtmlMap/addManagerRole.html",data);
                break;
        }
    }
    public static void updatePerData(int type){
        switch (type){
            case TYPE_MANAGERS:
                Managers manager=params.get("data",Managers.class);
                Managers temp_m=Managers.find("userId!=? and userAccount=?",manager.userId,manager.userAccount).first();
                if(temp_m!=null){
                    renderJSON(new JsonResponse(-1,"管理员登录账户[<b>"+manager.userAccount+"</b>]已经存在。"));
                    return;
                }
                manager.save();
                renderJSON(new JsonResponse(0,"管理员修改成功。"));
                break;
            case TYPE_ROLES:
                ManagerRole role=params.get("data",ManagerRole.class);
                ManagerRole temp_r=ManagerRole.find("id!=? and name=?",role.id,role.name).first();
                if(temp_r!=null){
                    renderJSON(new JsonResponse(-1,"角色[<b>"+role.name+"</b>]已经存在。"));
                    return;
                }
                role.save();
                renderJSON(new JsonResponse(0,"角色修改成功。"));
                break;
        }
    }
}
