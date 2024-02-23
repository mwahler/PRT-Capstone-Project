package com.mwahler.PRTServer.repositories;

import com.mwahler.PRTServer.models.DataPointEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DataPointRepository {

    DataPointEntity save(DataPointEntity dataPointEntity);

    List<DataPointEntity> saveAll(List<DataPointEntity> dataPointEntities);

    List<DataPointEntity> findAll();

    List<DataPointEntity> findAllByCar(String id);

    List<DataPointEntity> findAll(List<String> ids);

    DataPointEntity findOne(String id);

    long count();

    long delete(String id);

    long delete(List<String> ids);

    long deleteAll();

    DataPointEntity update(DataPointEntity dataPointEntity);

    long update(List<DataPointEntity> dataPointEntities);

}