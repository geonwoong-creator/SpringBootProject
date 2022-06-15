package kopo.poly.persistance.mapper;

import kopo.poly.dto.MapDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface IMapMapper {

    List<MapDTO> getMapInfo(MapDTO pDTO) throws Exception;
}
