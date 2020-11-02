package com.kenon.kenonapp.Repository;

import com.kenon.kenonapp.Model.TempCaptureModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TempCaptureIRepo extends JpaRepository<TempCaptureModel,Long> {
}
