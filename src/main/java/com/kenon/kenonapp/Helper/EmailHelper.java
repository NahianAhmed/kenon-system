package com.kenon.kenonapp.Helper;

import com.kenon.kenonapp.Model.EmployeeModel;
import com.kenon.kenonapp.Model.TemperatureModel;
import com.kenon.kenonapp.Repository.EmployeeRepository;
import com.kenon.kenonapp.Repository.TemperatureRepository;
import org.apache.xmlbeans.impl.xb.xsdschema.All;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

@Service
public class EmailHelper {
    @Autowired
    EmployeeRepository employeeRepository;
    EmployeeModel employeeModel;
    @Autowired
    TemperatureRepository temperatureRepository;
    TemperatureModel temperatureModel;
    @Autowired
    JavaMailSender javaMailSender;
    public void SentResetMail(String id,String email,String token,String name){
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setTo(email);
        simpleMailMessage.setSubject("パスワード再設定のメール");
        simpleMailMessage.setText(
                name + "様" + "\n\n"
                        + "パスワードをリセットするには次のリンクをクリックしてください。\n http://localhost:8080/verify-link/"+id
                      +"/"+ token + "\n(このURLは1回の変更のみ有効です。)"+"\n\n"+
                        "このメールに心当たりがない場合、ほかの方がパスワードをリセットする際に誤ってメールアドレスを入力した可能性があります。\n"+
                        "リクエストした覚えがない場合は、このメールは破棄してください。" + "\n\n"+
                        "================================================\n"+
                        "本メールは送信専用メールです。返信はできません。"+"\n"+
                        "問い合わせは下記にお願いします。"+"\n\n"+
                        " -本件問い合わせ先-：\n "+
                        "tel    :             026-228-6644  \n"+
                        "E-mail :        miyazawagkeiken.local \n"+
                        "================================================"
        );
        javaMailSender.send(simpleMailMessage);
    }


    public void SentReminderMail(){
        List AllMail = employeeRepository.AllMailAddress();
       // System.out.println(AllMail);
        Date today = new Date();
        List IdOfTodayData=temperatureRepository.EmailAddressOfCurrentDate(today);

        List SubmittedMail = employeeRepository.DataSubmittedEmail(IdOfTodayData);
        //System.out.println("Those id submitted data"+ IdOfTodayData +"And their email address "+SubmittedMail);
        AllMail.removeAll(SubmittedMail);
        if (AllMail!=null){
            System.out.println("Remind those "+AllMail);
            Iterator<String> itr = AllMail.iterator();
            while (itr.hasNext()) {

               // System.out.println(itr.next());
                SentRemindEmail(itr.next());

            }

        }


    }


    public void SentRemindEmail(String email){
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setTo(email);
        simpleMailMessage.setSubject("リマインダーメール");
        simpleMailMessage.setText(
                         "\n\n"
                        + "本日の体温及び風邪の症状等が未入力ですので、入力してください。 \n http://localhost:8080/"
                        + "\n                                   ケイケンシステム管理部 \n"+
                         "============================================================\n"+
                                 "本メールは送信専用メールです。返信はできません。\n"+
                                 "問い合わせは下記にお願いします。\n\n"+
                                 "tel    :             026-228-6644  \n"+
                                 "E-mail :        miyazawagkeiken.local \n"+
                         "=============================================================\n"

        );
        javaMailSender.send(simpleMailMessage);
    }





}
