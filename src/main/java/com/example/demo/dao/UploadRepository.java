package com.example.demo.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.UploadMetaData;

public interface UploadRepository extends JpaRepository<UploadMetaData, Long>   {

}
