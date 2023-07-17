package hongshop.hongshop.global.util;

import org.springframework.web.util.WebUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CookieUtil {

    public static String getCookie(HttpServletRequest req, String name){
        Cookie cookie = WebUtils.getCookie(req, name);
        return cookie != null ? cookie.getValue() : null;
    }

    public static void createCookie(HttpServletResponse res, String name, String value, Integer maxAge){
        Cookie cookie = new Cookie(name, value);
        cookie.setHttpOnly(true);
        cookie.setMaxAge(maxAge);
        cookie.setPath("/");
        res.addCookie(cookie);
    }
}
