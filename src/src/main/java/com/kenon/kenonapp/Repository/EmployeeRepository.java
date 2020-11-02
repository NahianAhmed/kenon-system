package com.kenon.kenonapp.Repository;

import com.kenon.kenonapp.Model.EmployeeModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.sql.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<EmployeeModel,String> {

  @Query("from EmployeeModel  where email=?1")
  EmployeeModel findUserByEmail(String email);

  @Query("SELECT email from EmployeeModel")
  List AllMailAddress();

  @Query("SELECT email from EmployeeModel where userId=(?1)")
  List DataSubmittedEmail(List<String> userId);

  @Query("SELECT DISTINCT department from EmployeeModel")
   List AllDepartment();






}
