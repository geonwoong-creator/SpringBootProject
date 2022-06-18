package kopo.poly.service;

import kopo.poly.dto.FoodDTO;

public interface IFoodService {

    void InsertFood(FoodDTO pDTO) throws Exception;

    FoodDTO getFoodInfo(FoodDTO pDTO) throws Exception;
}
