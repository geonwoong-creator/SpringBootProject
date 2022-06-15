package kopo.poly.service.impl;

import kopo.poly.dto.MapDTO;
import kopo.poly.persistance.mapper.IMapMapper;
import kopo.poly.service.IMapService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service("MapService")
public class MapService implements IMapService {

    private final IMapMapper mapMapper;

    @Autowired
    public MapService(IMapMapper mapMapper) {this.mapMapper = mapMapper;}

    @Override
    public List<MapDTO> getMapInfo(MapDTO pDTO) throws Exception {

        log.info(this.getClass().getName() + ".getMapInfo start!");

        return mapMapper.getMapInfo(pDTO);
    }
}
