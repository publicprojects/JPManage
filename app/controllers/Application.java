package controllers;

import play.*;
import play.cache.Cache;
import play.libs.Codec;
import play.libs.Crypto;
import play.libs.Images;
import play.libs.Time;
import play.mvc.*;

import java.text.SimpleDateFormat;
import java.util.*;

import models.*;
import utils.DateUtils;
import utils.IpMacAddress;
import utils.PropertiesUtils;

public class Application extends Controller {
    final static String LOGIN_RESPONSE = "login-response";
    final static String LOGIN_USER_ID = "login-user-id";
    final static String LOGIN_USER = "login-user";

    final static String COOKIE_USER="authUser";
    final static String COOKIE_PWD="authPwd";

    public final static int TYPE_MANAGERS=0;
    public final static int TYPE_ROLES=1;
    public final static int TYPE_PRIVILEGE=2;

    public static void index() {
        boolean iplimit =Boolean.parseBoolean(Play.configuration.getProperty("ip.limit"));
        boolean access = true;
        if (iplimit) {
            IpMacAddress ima = IpMacAddress.instance();
            String ip = ima.getIpAddress(request);
            access = IPLimit.volidIp(ip);
        }
        if (access) {
            String randomID = Codec.UUID();
            String account = Application.getCookVal(COOKIE_USER);
            String pwd = Application.getCookVal(COOKIE_PWD);
            String msg = session.get(LOGIN_RESPONSE);
            render(randomID, account, pwd, msg);
        } else {
            renderHtml("<style>.alert {font-family:'Microsoft Yahei';padding: 15px;display:block;  margin-bottom: 20px;  border: 1px solid transparent;  border-radius: 4px;}.alert-danger {color: #b94a48;  background-color: #f2dede;  border-color: #eed3d7;}</style><div style='padding:50px;text-align:center'><span class='alert alert-danger'><b>无权限！</b><p>你没有权限进入该系统。请自觉离开！</p></span></div>");
        }
    }

    private static String getCookVal(String cookname) {
        Http.Cookie remember = request.cookies.get(cookname);
        if (remember != null) {
            int firstIndex = remember.value.indexOf("-");
            int lastIndex = remember.value.lastIndexOf("-");
            if (lastIndex > firstIndex) {
                String sign = remember.value.substring(0, firstIndex);
                String restOfCookie = remember.value.substring(firstIndex + 1);
                String value = remember.value.substring(firstIndex + 1,
                        lastIndex);
                String time = remember.value.substring(lastIndex + 1);
                Date expirationDate = new Date(Long.parseLong(time));
                Date now = new Date();
                if (expirationDate.before(now)) {
                    response.removeCookie(cookname);
                    return null;
                }
                if (Crypto.sign(restOfCookie).equals(sign)) {
                    return value;
                }
            }
        }
        return null;
    }

    private static void setCook(boolean remember, String cookname,String cookvalue) {
        if (remember) {
            String duration = Play.configuration.getProperty(
                    "cookie.length", "30");
            Date expiration = DateUtils.getDaysNear(Integer.decode(duration));
            duration+="d";
            response.setCookie(cookname,
                    Crypto.sign(cookvalue + "-" + expiration.getTime()) + "-"
                            + cookvalue + "-" + expiration.getTime(), duration);
        } else {
            response.removeCookie(cookname);
        }
    }

    public static void signIn(String username, String password, int rememberme) {
//        String randomID, String code,
//        code = code.toUpperCase();
        session.clear();
        String msg = "";//"验证码错误!";
        Managers m = Managers.validateUser(username, password);
        if (m != null) {
            String account = Application.getCookVal(COOKIE_USER);
            String pwd = Application.getCookVal(COOKIE_PWD);
            if(account==null||pwd==null){
                setCook(true, COOKIE_USER, username);
                setCook((rememberme == 1), COOKIE_PWD, password);
            }
            m.lastLoginPcIp=IpMacAddress.instance().getIpAddress(request);
            session.put(LOGIN_USER_ID, m.userId);
            m.save();
            Manage.index();
        } else {
            msg = "用户名或密码错误!";
            session.put(LOGIN_RESPONSE, msg);
            index();
        }
//        }
//        else {
//            session.put(LOGIN_RESPONSE, msg);
//            index();
//        }
    }

    public static void signout() {
        session.clear();
        index();
    }

    public static void getVerifyCode(String id) {
        Images.Captcha captcha = Images.captcha();
        String code = captcha.getText("#ff3300");
        code = code.toUpperCase();
        Cache.set(id, code, "10mn");
        renderBinary(captcha);
    }

    protected static Managers getCacheUser() {
        Managers u = (Managers) Cache.get(LOGIN_USER);
        if (u == null) {
            String user_id = session.get(LOGIN_USER_ID);
            if (user_id == null) {
                return null;
            }
            u = Managers.findById(Long.parseLong(user_id));
            Cache.set(LOGIN_USER, u, "30mn");
        }
        return u;
    }

}