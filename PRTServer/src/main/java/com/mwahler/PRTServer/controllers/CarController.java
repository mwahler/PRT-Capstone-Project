package com.mwahler.PRTServer.controllers;

import com.mwahler.PRTServer.datatransferobjects.CarDTO;
import com.mwahler.PRTServer.services.CarService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/api")
public class CarController {

    private final static Logger LOGGER = LoggerFactory.getLogger(CarController.class);
    private final CarService carService;

    public CarController(CarService carService) {
        this.carService = carService;
    }

    @PostMapping("car")
    @ResponseStatus(HttpStatus.CREATED)
    public CarDTO postCar(@RequestBody CarDTO CarDTO) {
        return carService.save(CarDTO);
    }

    @PostMapping("cars")
    @ResponseStatus(HttpStatus.CREATED)
    public List<CarDTO> postCars(@RequestBody List<CarDTO> carEntities) {
        return carService.saveAll(carEntities);
    }

    @GetMapping("cars")
    public List<CarDTO> getCars() {
        return carService.findAll();
    }

    @GetMapping("car/{id}")
    public ResponseEntity<CarDTO> getCar(@PathVariable String id) {
        CarDTO CarDTO = carService.findOne(id);
        if (CarDTO == null) return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        return ResponseEntity.ok(CarDTO);
    }

    @GetMapping("cars/{ids}")
    public List<CarDTO> getCars(@PathVariable String ids) {
        List<String> listIds = List.of(ids.split(","));
        return carService.findAll(listIds);
    }

    @GetMapping("car/serial/{serialNumber}")
    public ResponseEntity<CarDTO> getCarBySerial(@PathVariable String serialNumber) {
        CarDTO CarDTO = carService.findOneBySerial(serialNumber);
        if (CarDTO == null) return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        return ResponseEntity.ok(CarDTO);
    }

    @GetMapping("cars/count")
    public Long getCount() {
        return carService.count();
    }

    @DeleteMapping("car/{id}")
    public Long deleteCar(@PathVariable String id) {
        return carService.delete(id);
    }

    @DeleteMapping("cars/{ids}")
    public Long deleteCars(@PathVariable String ids) {
        List<String> listIds = List.of(ids.split(","));
        return carService.delete(listIds);
    }

    @DeleteMapping("cars")
    public Long deleteCars() {
        return carService.deleteAll();
    }

    @PutMapping("car")
    public CarDTO putCar(@RequestBody CarDTO CarDTO) {
        return carService.update(CarDTO);
    }

    @PutMapping("cars")
    public Long putCar(@RequestBody List<CarDTO> carEntities) {
        return carService.update(carEntities);
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public final Exception handleAllExceptions(RuntimeException e) {
        LOGGER.error("Internal server error.", e);
        return e;
    }
}