package com.mwahler.PRTServer.services;

import com.mwahler.PRTServer.datatransferobjects.DataPointDTO;
import com.mwahler.PRTServer.models.DataPointEntity;
import com.mwahler.PRTServer.repositories.DataPointRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DataPointServiceImpl implements DataPointService {

    private final DataPointRepository dataPointRepository;

    public DataPointServiceImpl(DataPointRepository dataPointRepository) {
        this.dataPointRepository = dataPointRepository;
    }

    @Override
    public DataPointDTO save(DataPointDTO DataPointDTO) {
        return new DataPointDTO(dataPointRepository.save(DataPointDTO.toDataPointEntity()));
    }

    @Override
    public List<DataPointDTO> saveAll(List<DataPointDTO> dataPointEntities) {
        return dataPointEntities.stream()
                .map(DataPointDTO::toDataPointEntity)
                .peek(dataPointRepository::save)
                .map(DataPointDTO::new)
                .toList();
    }

    @Override
    public List<DataPointDTO> findAll() {
        return dataPointRepository.findAll().stream().map(DataPointDTO::new).toList();
    }

    @Override
    public List<DataPointDTO> findAllByCar(String id) {
        return dataPointRepository.findAllByCar(id).stream().map(DataPointDTO::new).toList();
    }

    @Override
    public List<DataPointDTO> findAll(List<String> ids) {
        return dataPointRepository.findAll(ids).stream().map(DataPointDTO::new).toList();
    }

    @Override
    public DataPointDTO findOne(String id) {
        DataPointEntity entity = dataPointRepository.findOne(id);
        if(entity == null) {
            return null;
        }
        return new DataPointDTO(entity);
    }

    @Override
    public long count() {
        return dataPointRepository.count();
    }

    @Override
    public long delete(String id) {
        return dataPointRepository.delete(id);
    }

    @Override
    public long delete(List<String> ids) {
        return dataPointRepository.delete(ids);
    }

    @Override
    public long deleteAll() {
        return dataPointRepository.deleteAll();
    }

    @Override
    public DataPointDTO update(DataPointDTO DataPointDTO) {
        return new DataPointDTO(dataPointRepository.update(DataPointDTO.toDataPointEntity()));
    }

    @Override
    public long update(List<DataPointDTO> dataPointEntities) {
        return dataPointRepository.update(dataPointEntities.stream().map(DataPointDTO::toDataPointEntity).toList());
    }
}