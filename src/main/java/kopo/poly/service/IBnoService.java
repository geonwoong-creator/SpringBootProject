package kopo.poly.service;

import kopo.poly.dto.BnoDTO;

public interface IBnoService {

    void InsertBno(BnoDTO pDTO) throws Exception;

    BnoDTO getBno(BnoDTO pDTO) throws Exception;
}
