package kopo.poly.service;

import kopo.poly.dto.UserInfoDTO;

public interface IMainService {

    UserInfoDTO getUserInfo(UserInfoDTO pDTO) throws Exception;
}
