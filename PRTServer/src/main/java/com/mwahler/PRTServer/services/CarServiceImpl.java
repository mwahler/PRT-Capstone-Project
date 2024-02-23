package com.mwahler.PRTServer.services;


import com.mwahler.PRTServer.datatransferobjects.CarDTO;
import com.mwahler.PRTServer.models.CarEntity;
import com.mwahler.PRTServer.repositories.CarRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarServiceImpl implements CarService {

    private final CarRepository carRepository;

    public CarServiceImpl(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    @Override
    public CarDTO save(CarDTO CarDTO) {
        return new CarDTO(carRepository.save(CarDTO.toCarEntity()));
    }

    @Override
    public List<CarDTO> saveAll(List<CarDTO> carEntities) {
        return carEntities.stream()
                .map(CarDTO::toCarEntity)
                .peek(carRepository::save)
                .map(CarDTO::new)
                .toList();
    }

    @Override
    public List<CarDTO> findAll() {
        return carRepository.findAll().stream().map(CarDTO::new).toList();
    }

    @Override
    public List<CarDTO> findAll(List<String> ids) {
        return carRepository.findAll(ids).stream().map(CarDTO::new).toList();
    }

    @Override
    public CarDTO findOne(String id) {
        CarEntity entity = carRepository.findOne(id);
        if(entity == null) {
            return null;
        }
        return new CarDTO(entity);
    }

    @Override
    public CarDTO findOneByMac(String macAddress) {
        CarEntity entity = carRepository.findOneByMac(macAddress);
        if(entity == null) {
            return null;
        }
        return new CarDTO(entity);
    }

    @Override
    public long count() {
        return carRepository.count();
    }

    @Override
    public long delete(String id) {
        return carRepository.delete(id);
    }

    @Override
    public long delete(List<String> ids) {
        return carRepository.delete(ids);
    }

    @Override
    public long deleteAll() {
        return carRepository.deleteAll();
    }

    @Override
    public CarDTO update(CarDTO CarDTO) {
        return new CarDTO(carRepository.update(CarDTO.toCarEntity()));
    }

    @Override
    public long update(List<CarDTO> carEntities) {
        return carRepository.update(carEntities.stream().map(CarDTO::toCarEntity).toList());
    }
}