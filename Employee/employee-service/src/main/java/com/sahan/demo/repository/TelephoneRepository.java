package com.sahan.demo.repository;

import com.sahan.demo.modal.Telephone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TelephoneRepository extends JpaRepository<Telephone, Integer> {


//    @Query("DELETE from telephone WHERE employee_id = :employee_id")
//    void task(@Param("employee_id") Integer employee_id);


    void deleteByEmployeeId(Integer id);
}
