package com.mwahler.PRTServer.controllers;

import com.mwahler.PRTServer.datatransferobjects.DataPointDTO;
import com.mwahler.PRTServer.services.DataPointService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class DataPointController {

    private final static Logger LOGGER = LoggerFactory.getLogger(DataPointController.class);
    private final DataPointService dataPointService;

    public DataPointController(DataPointService dataPointService) {
        this.dataPointService = dataPointService;
    }

    @PostMapping("dataPoint")
    @ResponseStatus(HttpStatus.CREATED)
    public DataPointDTO postDataPoint(@RequestBody DataPointDTO DataPointDTO) {
        return dataPointService.save(DataPointDTO);
    }

    @PostMapping("dataPoints")
    @ResponseStatus(HttpStatus.CREATED)
    public List<DataPointDTO> postDataPoints(@RequestBody List<DataPointDTO> dataPointEntities) {
        return dataPointService.saveAll(dataPointEntities);
    }

    @GetMapping("dataPoints")
    public List<DataPointDTO> getDataPoints() {
        return dataPointService.findAll();
    }

    @GetMapping("dataPoints/car/{id}")
    public List<DataPointDTO> getDataPointsByCar(@PathVariable String id) {
        return dataPointService.findAllByCar(id);
    }

    @GetMapping("dataPoint/{id}")
    public ResponseEntity<DataPointDTO> getDataPoint(@PathVariable String id) {
        DataPointDTO DataPointDTO = dataPointService.findOne(id);
        if (DataPointDTO == null) return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        return ResponseEntity.ok(DataPointDTO);
    }

    @GetMapping("dataPoints/{ids}")
    public List<DataPointDTO> getDataPoints(@PathVariable String ids) {
        List<String> listIds = List.of(ids.split(","));
        return dataPointService.findAll(listIds);
    }

    @GetMapping("dataPoints/count")
    public Long getCount() {
        return dataPointService.count();
    }

    @DeleteMapping("dataPoint/{id}")
    public Long deleteDataPoint(@PathVariable String id) {
        return dataPointService.delete(id);
    }

    @DeleteMapping("dataPoints/{ids}")
    public Long deleteDataPoints(@PathVariable String ids) {
        List<String> listIds = List.of(ids.split(","));
        return dataPointService.delete(listIds);
    }

    @DeleteMapping("dataPoints")
    public Long deleteDataPoints() {
        return dataPointService.deleteAll();
    }

    @PutMapping("dataPoint")
    public DataPointDTO putDataPoint(@RequestBody DataPointDTO DataPointDTO) {
        return dataPointService.update(DataPointDTO);
    }

    @PutMapping("dataPoints")
    public Long putDataPoint(@RequestBody List<DataPointDTO> dataPointEntities) {
        return dataPointService.update(dataPointEntities);
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public final Exception handleAllExceptions(RuntimeException e) {
        LOGGER.error("Internal server error.", e);
        return e;
    }}