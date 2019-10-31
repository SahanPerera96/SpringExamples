package com.sahan.demo.repository;

import com.sahan.demo.modal.DocumentTable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<DocumentTable, Integer> {

//    @Query("insert into DocumentTable (content, filename) values(:name, :blob)")
//    DocumentTable add(@Param("Name") String name, @Param("blob") Blob blob);
}
