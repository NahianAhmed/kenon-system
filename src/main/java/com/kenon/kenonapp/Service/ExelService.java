package com.kenon.kenonapp.Service;

import com.kenon.kenonapp.Helper.ExelHelper;
import com.kenon.kenonapp.Helper.TempChaptureHelper;
import com.kenon.kenonapp.Model.EmployeeModel;
import com.kenon.kenonapp.Model.PasswordModel;
import com.kenon.kenonapp.Model.TempCaptureModel;
import com.kenon.kenonapp.Repository.EmployeeRepository;
import com.kenon.kenonapp.Repository.PasswordRepository;
import com.kenon.kenonapp.Repository.TempCaptureIRepo;
import com.kenon.kenonapp.Repository.TemperatureRepository;
import org.springframework.stereotype.Service;
import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ExelService {
    @Autowired
    EmployeeRepository employeeRepository;
    @Autowired
    PasswordRepository passwordRepository;
    @Autowired
    TemperatureRepository temperatureRepository;
    @Autowired
    TempCaptureIRepo tempCaptureIRepo;
    public ByteArrayInputStream load() {
        ByteArrayInputStream in = ExelHelper.ToExcel((List<EmployeeModel>) employeeRepository.findAll());
        return in;
    }

    public ByteArrayInputStream loadTemp() {
        ByteArrayInputStream in = TempChaptureHelper.ToExcel( (List<TempCaptureModel>) tempCaptureIRepo.findAll());
       tempCaptureIRepo.deleteAll();
        return in;
    }

    public void savedata(MultipartFile file) {

      try {

          List<EmployeeModel> allemp = (List<EmployeeModel>) employeeRepository.findAll();
          List <String> listA = ExelHelper.AllID(file.getInputStream());
          List <String> listB = new ArrayList<>();
          for (EmployeeModel ED :allemp) {
                listB.add(ED.getUserId());
          }
         // System.out.println(listA);
          //System.out.println(listB);
          listB.removeAll(listA);

         // System.out.println("All I need " +listB);
         // employeeRepository.findAllById(listB);
             employeeRepository.deleteAll( employeeRepository.findAllById(listB));
             temperatureRepository.deleteAll(temperatureRepository.findAllByUserId(listB));

            List<EmployeeModel> employeeModelList = ExelHelper.ToDBuser(file.getInputStream());
            employeeRepository.saveAll(employeeModelList);

            passwordRepository.deleteAll( passwordRepository.findAllById(listB));
            List<PasswordModel> passwordModelList = ExelHelper.ToDBpassword(file.getInputStream());
            passwordRepository.saveAll(passwordModelList);

        } catch (IOException e) {
            throw new RuntimeException("fail to store excel data: " + e.getMessage());
        }
    }


}
