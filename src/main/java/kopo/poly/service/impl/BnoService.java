package kopo.poly.service.impl;

import kopo.poly.dto.BnoDTO;
import kopo.poly.persistance.mapper.IBnoMapper;
import kopo.poly.service.IBnoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service("BnoService")
public class BnoService implements IBnoService {

    private final IBnoMapper bnoMapper;

    @Autowired
    public BnoService(IBnoMapper bnoMapper) {this.bnoMapper = bnoMapper;}

    @Transactional
    @Override
    public void InsertBno(BnoDTO pDTO) throws Exception {

        log.info(this.getClass().getName() + ".InsertBno Start!");

        bnoMapper.InsertBno(pDTO);
    }

    @Override
    public BnoDTO getBno(BnoDTO pDTO) throws Exception {
        log.info(this.getClass().getName() + ".getBno Start!");

        return bnoMapper.getBno(pDTO);
    }


}
