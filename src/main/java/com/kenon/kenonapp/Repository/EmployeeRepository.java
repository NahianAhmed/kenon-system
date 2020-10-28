package com.kenon.kenonapp.Repository;

import com.kenon.kenonapp.Model.EmployeeModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<EmployeeModel,String> {

  @Query("from EmployeeModel  where email=?1")
  EmployeeModel findUserByEmail(String email);


}
