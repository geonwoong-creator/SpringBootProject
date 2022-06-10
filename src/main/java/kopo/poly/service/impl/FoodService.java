package kopo.poly.service.impl;

import kopo.poly.dto.FoodDTO;
import kopo.poly.persistance.mapper.IFoodMapper;
import kopo.poly.service.IFoodService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service("FoodService")
public class FoodService implements IFoodService {

    private final IFoodMapper foodMapper;

    @Autowired
    public FoodService(IFoodMapper foodMapper) {this.foodMapper = foodMapper;}

    @Transactional
    @Override
    public void InsertFood(FoodDTO pDTO) throws Exception {

        log.info(this.getClass().getName() + ".InsertFood Start!");

        foodMapper.InsertFood(pDTO);
    }
}
