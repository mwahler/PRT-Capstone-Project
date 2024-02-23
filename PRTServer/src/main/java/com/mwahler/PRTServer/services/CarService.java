package com.mwahler.PRTServer.services;

import com.mwahler.PRTServer.datatransferobjects.CarDTO;

import java.util.List;

public interface CarService {

    CarDTO save(CarDTO CarDTO);

    List<CarDTO> saveAll(List<CarDTO> carEntities);

    List<CarDTO> findAll();

    List<CarDTO> findAll(List<String> ids);

    CarDTO findOne(String id);

    CarDTO findOneByMac(String id);

    long count();

    long delete(String id);

    long delete(List<String> ids);

    long deleteAll();

    CarDTO update(CarDTO CarDTO);

    long update(List<CarDTO> carEntities);

}