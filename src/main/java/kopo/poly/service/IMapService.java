package kopo.poly.service;

import kopo.poly.dto.MapDTO;

import java.util.List;

public interface IMapService {

    List<MapDTO> getMapInfo(MapDTO pDTO) throws Exception;
}
