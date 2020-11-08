package com.kenon.kenonapp.Helper;

import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
@Service
public class AuthHelper {

    public void isAdmin(HttpServletRequest req, HttpServletResponse rep) throws IOException {

        if (req.getSession().getAttribute("user") == null) {
            rep.sendRedirect("/404");
        } else if ((req.getSession().getAttribute("user").equals("user"))) {
            rep.sendRedirect("/user");
        } else if (!(req.getSession().getAttribute("user").equals("admin"))) {
            rep.sendRedirect("/404");
        }
    }

    public void isUser(HttpServletRequest req, HttpServletResponse rep) throws IOException {

        if (req.getSession().getAttribute("user") == null) {
            rep.sendRedirect("/404");
        }
        else if ((req.getSession().getAttribute("user").equals("admin"))) {
            rep.sendRedirect("/admin");
        }
        else if (!(req.getSession().getAttribute("user").equals("user"))) {
            rep.sendRedirect("/404");
        }

    }
}
