package kopo.poly.service.impl;

import kopo.poly.dto.UserInfoDTO;
import kopo.poly.persistance.mapper.IMainMapper;
import kopo.poly.service.IMainService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service("MainService")
public class MainService implements IMainService {

    private final IMainMapper mainMapper;

    @Autowired
    public MainService(IMainMapper mainMapper) {this.mainMapper = mainMapper;}

    @Override
    public UserInfoDTO getUserInfo(UserInfoDTO pDTO) throws Exception {
        log.info(this.getClass().getName() + ".getUserInfo Start!");

        return mainMapper.getUserInfo(pDTO);
    }



}
