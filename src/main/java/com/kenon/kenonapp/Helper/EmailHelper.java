package com.kenon.kenonapp.Helper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailHelper {
    @Autowired
    JavaMailSender javaMailSender;
    public void SentResetMail(String id,String email,String token,String name){
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setTo(email);
        simpleMailMessage.setSubject("パスワード再設定のメール");
        simpleMailMessage.setText(
                name + "さん" + "\n\n"
                        + "リンクをたどってパスワードをリセットしてください。\n http://localhost:8080/verify-link/"+id
                      +"/"+ token + "\nリンクをクリックすると、パスワードをリセットできます。"
        );
        javaMailSender.send(simpleMailMessage);
    }

}
