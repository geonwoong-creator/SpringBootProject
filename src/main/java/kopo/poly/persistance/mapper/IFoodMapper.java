package kopo.poly.persistance.mapper;

import kopo.poly.dto.FoodDTO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface IFoodMapper {

    void InsertFood(FoodDTO pDTO) throws Exception;

    FoodDTO getFoodInfo(FoodDTO pDTO) throws Exception;

}
