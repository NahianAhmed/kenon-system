package com.kenon.kenonapp.Controller;

import com.kenon.kenonapp.Helper.EmailHelper;
import com.kenon.kenonapp.Model.EmployeeModel;
import com.kenon.kenonapp.Model.PasswordModel;
import com.kenon.kenonapp.Repository.EmployeeRepository;
import com.kenon.kenonapp.Repository.PasswordRepository;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;
import javax.servlet.http.HttpServletRequest;



@Controller
public class LoginController {
    @GetMapping("/")
    public String Login(ModelMap modelMap){
        modelMap.addAttribute("content","login_pages/login.jsp");
        return "jsp/layout";
    }
    @Autowired
    EmployeeRepository employeeRepository;
    @Autowired
    PasswordRepository passwordRepository;
    @PostMapping("/check-login")
    public RedirectView CheckLogin(HttpServletRequest req, ModelMap modelMap, RedirectAttributes redirectAttributes){
        String id = req.getParameter("id");
        String password = req.getParameter("password");

        String sha256hex = DigestUtils.sha256Hex(password);

       // System.out.println(sha256hex);


        if (employeeRepository.existsById(id)){
          //  System.out.println(id +" is exists ");
         PasswordModel pass = passwordRepository.findById(id).get();
         EmployeeModel emp =  employeeRepository.findById(id).get();
         if (id.equals(pass.getUserId()) && sha256hex.equals(pass.getPassword())){
             //System.out.println(" password match ");
             if (emp.isAdmin()){
                 // session
                 req.getSession().setAttribute("user","admin");
                 req.getSession().setAttribute("userID",id);
                 req.getSession().setAttribute("name",emp.getFullName());
                 return new RedirectView("/admin");

             }
             else {
                 //session
                 req.getSession().setAttribute("user","user");
                 req.getSession().setAttribute("userID",id);
                 req.getSession().setAttribute("name",emp.getFullName());
                 return new RedirectView("/user");
             }

         }
         else {

             //System.out.println(" password does not  match  ");
             redirectAttributes.addFlashAttribute("error","間違ったIDまたはパスワード ");

         }


        }
        else {
            redirectAttributes.addFlashAttribute("error","  ユーザーではありません " );
        }


       // System.out.println(id +" - "+ password);
        return new RedirectView("/");
    }

    @GetMapping("/logout")
    public RedirectView logout(HttpServletRequest req){
        req.getSession().removeAttribute("user");
        req.getSession().removeAttribute("userID");
        req.getSession().removeAttribute("name");
        return new RedirectView("/");
    }

    @GetMapping("/forget-password")
    public String ResetPassword(ModelMap modelMap) {
       // modelMap.addAttribute("nav","partials/navbar.jsp");
        modelMap.addAttribute("content","login_pages/forgetpassword.jsp");
        return "jsp/layout";
    }
    @Autowired
    EmailHelper emailHelper;
    @PostMapping("/sent-token")
    public RedirectView SentTokentoEmail(HttpServletRequest request,RedirectAttributes redirectAttributes){
        String email = request.getParameter("email");
       // System.out.println(email);
        try {
            EmployeeModel employeeModelList = employeeRepository.findUserByEmail(email);
            if(employeeModelList!=null){
                PasswordModel passwordModel = new PasswordModel();
                passwordModel.setUserId(employeeModelList.getUserId());
                String generatedString = RandomStringUtils.random(32, true, true);
                passwordModel.setToken(generatedString);
                passwordRepository.save(passwordModel);
                emailHelper.SentResetMail(employeeModelList.getUserId(),employeeModelList.getEmail(),generatedString,employeeModelList.getFullName());
            }
            else{
                redirectAttributes.addFlashAttribute("error","メールアドレスが無効です.");
                return new RedirectView("/forget-password");
            }



        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("error","メールアドレスが無効です.");
            return new RedirectView("/forget-password");
        }


        redirectAttributes.addFlashAttribute("error","メールを確認してください");
        return new RedirectView("/");
    }

    @GetMapping("/verify-link/{id}/{token}")
   public String TokenVerify(ModelMap modelMap, @PathVariable String id,@PathVariable String token,RedirectAttributes redirectAttributes,HttpServletRequest request){
        if(passwordRepository.existsById(id)){
            PasswordModel passwordModel = passwordRepository.findById(id).get();
            if (passwordModel.getToken().equals(token)){
                modelMap.addAttribute("tempid",id);
                modelMap.addAttribute("temptoken",token);
                modelMap.addAttribute("content","login_pages/updateforgetpassword.jsp");
                return "jsp/layout";
            }
        }
        redirectAttributes.addFlashAttribute("error","エラーリンク");
        return "redirect:/";
   }
   @PostMapping("/update-forget-password")
   public RedirectView updatePassword(HttpServletRequest request,RedirectAttributes redirectAttributes){
        String tempid = request.getParameter("tempid");
        String temptoken = request.getParameter("temptoken");
        String password = request.getParameter("password");

        if (request.getParameter("password").equals(request.getParameter("re_password"))){
            if(request.getParameter("password").length()>=6){
               // System.out.println("okkk");
                passwordRepository.deleteById(tempid);
                PasswordModel passwordModel = new PasswordModel();
                passwordModel.setUserId(tempid);
                String sha256hex = DigestUtils.sha256Hex(password);
                passwordModel.setPassword(sha256hex);
                passwordRepository.save(passwordModel);
                redirectAttributes.addFlashAttribute("error","パスワードが変更されましたログインしてください");
                return new RedirectView("/");

            }
            else {
                redirectAttributes.addFlashAttribute("error","パスワードは5文字以上である必要があります");
                return new RedirectView("/verify-link/"+tempid+"/"+temptoken);
            }


        }
        redirectAttributes.addFlashAttribute("error","パスワードが一致しません");
        return new RedirectView("/verify-link/"+tempid+"/"+temptoken);
   }

}
