package com.kenon.kenonapp.Repository;

import com.kenon.kenonapp.Model.PasswordModel;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PasswordRepository extends CrudRepository <PasswordModel,String> {

}
