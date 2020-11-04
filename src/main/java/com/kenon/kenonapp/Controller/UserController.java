package com.kenon.kenonapp.Controller;

import com.kenon.kenonapp.Model.PasswordModel;
import com.kenon.kenonapp.Model.TemperatureModel;
import com.kenon.kenonapp.Repository.EmployeeRepository;
import com.kenon.kenonapp.Repository.PasswordRepository;
import com.kenon.kenonapp.Repository.TemperatureRepository;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Timestamp;


@Controller
public class UserController {

    @Autowired
     PasswordRepository passwordRepository;
     @Autowired
    EmployeeRepository employeeRepository;
     @Autowired
    TemperatureRepository temperatureRepository;


    @GetMapping("/user")
    public String index(ModelMap modelMap, HttpServletRequest req,HttpServletResponse rep) throws IOException {
        isUser(req,rep);
        if(req.getSession().getAttribute("userID")!=null){
            String id=  req.getSession().getAttribute("userID").toString();
            String name =  req.getSession().getAttribute("name").toString();
            String time="null";
           if (temperatureRepository.findLastUsedByUserID(id)!=null){
               Timestamp date = temperatureRepository.findLastUsedByUserID(id);
               int year = date.getYear();
               int month = date.getMonth();
               int dates = date.getDate();
               int hour = date.getHours();
               int min = date.getMinutes();
               int sec = date.getSeconds();
               time = ((1900+year)+"年"+(month+1)+"月"+dates+"日"+hour+"時"+min+"分"+sec+" 秒");
           // time = temperatureRepository.findLastUsedByUserID(id).toString();
        }

            modelMap.addAttribute("id",id);
            modelMap.addAttribute("name",name);
            modelMap.addAttribute("time",time);
        }

        modelMap.addAttribute("nav","partials/Nav.jsp");
        modelMap.addAttribute("content","user/index.jsp");
        return "jsp/layout";


    }

    @GetMapping("/user/update-password")
    public String AdminUpdatePassword(ModelMap modelMap,HttpServletRequest request,HttpServletResponse response) throws IOException {
        isUser(request,response);
        modelMap.addAttribute("nav","partials/Nav.jsp");
        modelMap.addAttribute("content","user/updatepassword.jsp");
        return "jsp/layout";
    }
    @PostMapping("/user/password-update")
    public RedirectView UpdatePassword(HttpServletRequest request, RedirectAttributes attributes) {

        String id = request.getSession().getAttribute("userID").toString();
        String oldpass = request.getParameter("old_password");
        String pass = request.getParameter("password");
        String repass = request.getParameter("re_password");

        PasswordModel passwordModel = passwordRepository.findById(id).get();
        if (passwordModel.getPassword().equals(DigestUtils.sha256Hex(oldpass))) {

            if (pass.equals(repass)) {

                if(pass.length()>=6){

                    passwordRepository.deleteById(id);
                    PasswordModel data = new PasswordModel();
                    data.setUserId(id);
                    String sha256hex = DigestUtils.sha256Hex(pass);
                    data.setPassword(sha256hex);
                    passwordRepository.save(data);
                    attributes.addFlashAttribute("error", "パスワードは正常に変更されました");
                    return new RedirectView("/user/update-password");

                }
                else {
                    attributes.addFlashAttribute("error", "パスワードは5文字以上である必要があります");
                    return new RedirectView("/user/update-password");
                }

            } else {
                attributes.addFlashAttribute("error", "パスワードが一致しません");
                return new RedirectView("/user/update-password");

            }
        }
        else{
                attributes.addFlashAttribute("error", "現在のパスワードが間違っています");
                return new RedirectView("/user/update-password");
            }

        }

        @PostMapping("/user/save-temperature")
        public RedirectView SaveTemp(@ModelAttribute TemperatureModel temperatureModel,RedirectAttributes attributes,HttpServletRequest req){
            if (temperatureModel.getTemperature()>=30 && temperatureModel.getTemperature()<=45){
                temperatureRepository.save(temperatureModel);
                attributes.addFlashAttribute("error","データが追加されます。");
                req.getSession().setAttribute("alert","show");
            }
            else {
                attributes.addFlashAttribute("error","体温は30度から45度の間でなければなりません。");
            }
        return new RedirectView("/user");
        }


    public void isUser(HttpServletRequest req, HttpServletResponse rep) throws IOException {

        if (req.getSession().getAttribute("user") == null) {
            rep.sendRedirect("/");
        }
        else if ((req.getSession().getAttribute("user").equals("admin"))) {
            rep.sendRedirect("/admin");
        }
        else if (!(req.getSession().getAttribute("user").equals("user"))) {
            rep.sendRedirect("/");
        }

    }



}
