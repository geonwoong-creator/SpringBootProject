package kopo.poly.persistance.mapper;

import kopo.poly.dto.BnoDTO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface IBnoMapper {

    void InsertBno(BnoDTO pDTO) throws Exception;

    BnoDTO getBno(BnoDTO pDTO) throws Exception;
}
