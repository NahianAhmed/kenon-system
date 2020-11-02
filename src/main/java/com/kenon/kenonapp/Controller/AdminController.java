package com.kenon.kenonapp.Controller;

import com.kenon.kenonapp.Helper.EmailHelper;
import com.kenon.kenonapp.Helper.ExelHelper;
import com.kenon.kenonapp.Model.*;
import com.kenon.kenonapp.Repository.EmployeeRepository;
import com.kenon.kenonapp.Repository.PasswordRepository;
import com.kenon.kenonapp.Repository.TempCaptureIRepo;
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
import javax.xml.crypto.Data;
import java.io.IOException;
import java.sql.Array;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;


@Controller
public class AdminController {
    @Autowired
    PasswordRepository passwordRepository;
    @Autowired
    EmployeeRepository employeeRepository;
    @Autowired
    TemperatureRepository temperatureRepository;

    @GetMapping("/admin")
    public String index(ModelMap modelMap, HttpServletRequest req,HttpServletResponse rep,RedirectAttributes attributes) throws IOException {
        isAdmin(req,rep);
        if(req.getSession().getAttribute("userID")!=null){

        String id =  req.getSession().getAttribute("userID").toString();
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
          //  time = temperatureRepository.findLastUsedByUserID(id).toString();
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
        if (temperatureModel.getTemperature()>=30 && temperatureModel.getTemperature()<=45){
            temperatureRepository.save(temperatureModel);
            attributes.addFlashAttribute("error","データが追加されます。");
           // attributes.addFlashAttribute("sweetAlart","show");
        }
        else {
            attributes.addFlashAttribute("error","体温は30度から45度の間でなければなりません。");

        }

        return new RedirectView("/admin");
    }

    @GetMapping("/admin/user-import-export")
    public String ExcelImportExport(ModelMap modelMap,HttpServletRequest request,HttpServletResponse response) throws IOException {
        this.isAdmin(request,response);
        modelMap.addAttribute("nav","partials/Nav.jsp");
        modelMap.addAttribute("content","admin/ExcelImportExport.jsp");
        return "jsp/layout";
    }


    @GetMapping("/admin/temperature-export")
    public String TemperatureExport(ModelMap modelMap,HttpServletRequest request,HttpServletResponse response) throws IOException {
        isAdmin(request,response);
        List department =  employeeRepository.AllDepartment();

        String option="";
        for (Object dep : department) {

            option+="<option value=\"";
            option+=dep;
            option+="\"> ";
            option+=dep;
            option+="</option>" ;

           // System.out.println(dep);
        }
        //System.out.println(option);
        modelMap.addAttribute("department",option);
        modelMap.addAttribute("nav","partials/Nav.jsp");
        modelMap.addAttribute("content","admin/TemperatureExport.jsp");
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
   @Autowired
    TempCaptureIRepo tempCaptureIRepo;

    @PostMapping("/admin/user-temperature")
    public String CollectTemperature(HttpServletRequest request)throws Exception{
        String date1 = request.getParameter("date");
        String date2 =  LocalDate.parse(date1).minusDays(5).toString();
        String department = request.getParameter("department");

        SimpleDateFormat formatter1=new SimpleDateFormat("yyyy-MM-dd");
        Date StartData = formatter1.parse(date2);
        Date EndDate = formatter1.parse(date1);
        List<EmployeeModel> emp = employeeRepository.findAll();
        List<TempCaptureModel> tempCaptureModellist = new ArrayList<TempCaptureModel>();

        for (EmployeeModel obj: emp) {
            TempCaptureModel tempCaptureModel = new TempCaptureModel();
            tempCaptureModel.setUserid(obj.getUserId());
            tempCaptureModel.setName(obj.getFullName());
            tempCaptureModel.setKananame(obj.getFullNameInKata());
            tempCaptureModel.setDept(obj.getDepartment());

            //System.out.print(" ID: "+obj.getUserId());
           // System.out.println(" Name: "+obj.getFullName());
           // System.out.print(" Name: "+obj.getFullNameInKata());
           // System.out.print(" Dept.: "+obj.getDepartment());
            List<TemperatureModel> data = temperatureRepository.TemperatureByDate(obj.getUserId(),StartData,EndDate);
            int i = 0;
            for (TemperatureModel temp: data) {

//            System.out.print(" Temp.: "+temp.getTemperature());
//            System.out.print(" Symptoms: "+temp.isSymtoms());
                if(i==0){ tempCaptureModel.setDay1(temp.getLastUsed().toString()+","+temp.getTemperature()+","+temp.isSymtoms()); }
                else if(i==1){tempCaptureModel.setDay2(temp.getLastUsed().toString()+","+temp.getTemperature()+","+temp.isSymtoms()); }
                else if(i==2){tempCaptureModel.setDay3(temp.getLastUsed().toString()+","+temp.getTemperature()+","+temp.isSymtoms()); }
                else if(i==3){tempCaptureModel.setDay4(temp.getLastUsed().toString()+","+temp.getTemperature()+","+temp.isSymtoms()); }
                else if(i==4){ tempCaptureModel.setDay5(temp.getLastUsed().toString()+","+temp.getTemperature()+","+temp.isSymtoms());}
                i++;
        }
           tempCaptureModellist.add(tempCaptureModel);
           // tempCaptureIRepo.save(tempCaptureModel);

        }

      tempCaptureIRepo.saveAll(tempCaptureModellist);

        return "redirect:/admin/user-temperature";

    }

    @GetMapping("/admin/user-temperature")
    public ResponseEntity<Resource> UserTemp(HttpServletRequest req, HttpServletResponse rep) throws IOException {
        isAdmin(req,rep);
        String filename = "user_temperature.xlsx";
        InputStreamResource file = new InputStreamResource(exelService.loadTemp());

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
                .contentType(MediaType.parseMediaType("application/vnd.ms-excel"))
                .body(file);
    }







}
