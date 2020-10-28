package com.kenon.kenonapp.Repository;

import com.kenon.kenonapp.Model.EmployeeModel;
import com.kenon.kenonapp.Model.TemperatureModel;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@Repository
public interface TemperatureRepository extends CrudRepository<TemperatureModel,Long> {
    @Query("SELECT max(lastUsed) from TemperatureModel  where userId=?1 ")
    Timestamp findLastUsedByUserID(String userId);

    @Query("from TemperatureModel where userId in (?1)")
    List<TemperatureModel> findAllByUserId(List<String> userId);

    @Query("select userId From TemperatureModel where Date(lastUsed)=?1")
    List EmailAddressOfCurrentDate(Date today);

}
