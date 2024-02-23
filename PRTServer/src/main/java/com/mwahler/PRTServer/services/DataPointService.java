package com.mwahler.PRTServer.services;


import com.mwahler.PRTServer.datatransferobjects.DataPointDTO;

import java.util.List;

public interface DataPointService {

    DataPointDTO save(DataPointDTO DataPointDTO);

    List<DataPointDTO> saveAll(List<DataPointDTO> dataPointEntities);

    List<DataPointDTO> findAll();
    List<DataPointDTO> findAllByCar(String id);

    List<DataPointDTO> findAll(List<String> ids);

    DataPointDTO findOne(String id);

    long count();

    long delete(String id);

    long delete(List<String> ids);

    long deleteAll();

    DataPointDTO update(DataPointDTO DataPointDTO);

    long update(List<DataPointDTO> dataPointEntities);

}