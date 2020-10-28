package com.kenon.kenonapp.Controller;

import com.kenon.kenonapp.Helper.EmailHelper;
import com.kenon.kenonapp.Helper.ExelHelper;
import com.kenon.kenonapp.Model.EmployeeModel;
import com.kenon.kenonapp.Model.PasswordModel;
import com.kenon.kenonapp.Model.TemperatureModel;
import com.kenon.kenonapp.Repository.EmployeeRepository;
import com.kenon.kenonapp.Repository.PasswordRepository;
import com.kenon.kenonapp.Repository.TemperatureRepository;
import com.kenon.kenonapp.Service.ExelService;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@Controller
public class AdminController {
    @Autowired
    PasswordRepository passwordRepository;
    @Autowired
    EmployeeRepository employeeRepository;
    @Autowired
    TemperatureRepository temperatureRepository;

    @GetMapping("/admin")
    public String index(ModelMap modelMap, HttpServletRequest req,HttpServletResponse rep) throws IOException {
        isAdmin(req,rep);
        if(req.getSession().getAttribute("userID")!=null){

        String id =  req.getSession().getAttribute("userID").toString();
        String name =  req.getSession().getAttribute("name").toString();
        String time="null";
            if (temperatureRepository.findLastUsedByUserID(id)!=null){
            time = temperatureRepository.findLastUsedByUserID(id).toString();
          }
        modelMap.addAttribute("id",id);
        modelMap.addAttribute("name",name);
        modelMap.addAttribute("time",time);

        }

        modelMap.addAttribute("nav","partials/Nav.jsp");
        modelMap.addAttribute("content","admin/index.jsp");
        return "jsp/layout";
    }

/// start unnecessary
/*
    @GetMapping("/admin/add-user")
    public String add_user(ModelMap modelMap,HttpServletRequest req, HttpServletResponse rep) throws IOException {
        isAdmin(req,rep);
        modelMap.addAttribute("nav","partials/Nav.jsp");
        modelMap.addAttribute("content","admin/Adduser.jsp");
        return "jsp/layout";
    }

    @PostMapping("/admin/user-saved")
    public RedirectView Usersaved(@ModelAttribute EmployeeModel employeeModel){

        employeeRepository.save(employeeModel);
        return new RedirectView("/admin/add-user");
    }
    @GetMapping("/admin/all-user")
    public String alluser(ModelMap modelMap,@ModelAttribute EmployeeModel employeeModel,HttpServletRequest req, HttpServletResponse rep) throws IOException {
        isAdmin(req,rep);
        modelMap.addAttribute("nav","partials/Nav.jsp");
        modelMap.addAttribute("content","admin/ShowUser.jsp");
        modelMap.addAttribute("datas",employeeRepository.findAll());
        return "jsp/layout";
    }
    @GetMapping("/admin/excel-upload")
    public String ExelUpload(ModelMap modelMap,HttpServletRequest req, HttpServletResponse rep) throws IOException {
        isAdmin(req,rep);
        modelMap.addAttribute("nav","partials/Nav.jsp");
        modelMap.addAttribute("content","admin/Excelform.jsp");
        return "jsp/layout";
    }
*/
    ///end unnecessary

    @Autowired
    ExelService exelService;
    @GetMapping("/admin/user-download")
    public ResponseEntity<Resource> getFile(HttpServletRequest req, HttpServletResponse rep) throws IOException {
        isAdmin(req,rep);
        String filename = "userinfo.xlsx";
        InputStreamResource file = new InputStreamResource(exelService.load());

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
                .contentType(MediaType.parseMediaType("application/vnd.ms-excel"))
                .body(file);
    }

    @PostMapping("/admin/user-upload")
    public RedirectView uploadFile(@RequestParam("file") MultipartFile file,RedirectAttributes attributes) {
        String message = "";
       exelService.savedata(file);
        if (ExelHelper.hasExcelFormat(file)) {
            try {
                exelService.savedata(file);
                message = "Uploaded the file successfully: " + file.getOriginalFilename();
                System.out.println(message);
                attributes.addFlashAttribute("error","ユーザーが正常に追加されました");
                return new RedirectView("/admin/user-import-export");
            } catch (Exception e) {
               message =" Failed to upload";
               System.out.println(message);
            }
        }

        attributes.addFlashAttribute("error","Excelのインポートが完了しました。");
        return new RedirectView("/admin/user-import-export");
    }
    @GetMapping("/admin/update-password")
    public String AdminUpdatePassword(ModelMap modelMap,HttpServletRequest request,HttpServletResponse response) throws IOException {
        isAdmin(request,response);
        modelMap.addAttribute("nav","partials/Nav.jsp");
        modelMap.addAttribute("content","admin/admin_updatepassword.jsp");
        return "jsp/layout";

    }

    @PostMapping("/admin/password-update")
    public RedirectView UpdatePassword(HttpServletRequest request, RedirectAttributes attributes) {

        String id = request.getSession().getAttribute("userID").toString();
        String oldpass = request.getParameter("old_password");
        String pass = request.getParameter("password");
        String repass = request.getParameter("re_password");

        PasswordModel passwordModel = passwordRepository.findById(id).get();
        if (passwordModel.getPassword().equals(DigestUtils.sha256Hex(oldpass))) {

            if (pass.equals(repass)) {

                if (pass.length()>=6){

                    passwordRepository.deleteById(id);
                    PasswordModel data = new PasswordModel();
                    data.setUserId(id);
                    String sha256hex = DigestUtils.sha256Hex(pass);
                    data.setPassword(sha256hex);
                    passwordRepository.save(data);
                    attributes.addFlashAttribute("error", "パスワードは正常に変更されました");
                    return new RedirectView("/admin/update-password");

                }
                else {
                    attributes.addFlashAttribute("error", "パスワードは5文字以上である必要があります");
                    return new RedirectView("/admin/update-password");
                }

            } else {
                attributes.addFlashAttribute("error", "パスワードが一致しません");
                return new RedirectView("/admin/update-password");

            }
        }
        else{
            attributes.addFlashAttribute("error", "現在のパスワードが間違っています");
            return new RedirectView("/admin/update-password");
        }

    }

    @PostMapping("/admin/save-temperature")
    public RedirectView SaveTemp(@ModelAttribute TemperatureModel temperatureModel,RedirectAttributes attributes){
        temperatureRepository.save(temperatureModel);
        attributes.addFlashAttribute("error","データが追加されます。");
        return new RedirectView("/admin");
    }

    @GetMapping("/admin/user-import-export")
    public String ExcelImportExport(ModelMap modelMap,HttpServletRequest request,HttpServletResponse response) throws IOException {
        this.isAdmin(request,response);
        modelMap.addAttribute("nav","partials/Nav.jsp");
        modelMap.addAttribute("content","admin/ExcelImportExport.jsp");
        return "jsp/layout";
    }

    public void isAdmin(HttpServletRequest req, HttpServletResponse rep) throws IOException {

        if (req.getSession().getAttribute("user") == null) {
            rep.sendRedirect("/");
        } else if ((req.getSession().getAttribute("user").equals("user"))) {
            rep.sendRedirect("/user");
        } else if (!(req.getSession().getAttribute("user").equals("admin"))) {
            rep.sendRedirect("/");
        }
    }
    @Autowired
    EmailHelper emailHelper;
    // sec // min // hour // day
    //0 0 10 ? * MON-FRI
    //0 0 12 ? * MON-FRI
    @Scheduled(cron="0 0 10 ? * MON-FRI")
    @Scheduled(cron="0 0 12 ? * MON-FRI")
    public void SentDailyMail(){
         // System.out.println("Corn");
          emailHelper.SentReminderMail();
    }







}
