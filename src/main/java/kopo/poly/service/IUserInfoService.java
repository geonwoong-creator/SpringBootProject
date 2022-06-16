package kopo.poly.service;

import kopo.poly.dto.UserInfoDTO;

public interface IUserInfoService {

    // 회원 가입하기(회원정보 등록하기)
    int insertUserInfo(UserInfoDTO pDTO) throws Exception;

    // 로그인을 위해 아이디와 비밀번호가 일치하는지 확인하기
    int getUserLoginCheck(UserInfoDTO pDTO) throws Exception;

    //비밀번호 찾기를 위해 아이디와 이메일 일치하는지 확인하기
     int forgetPassword(UserInfoDTO pDTO) throws Exception;

     //비밀번호 변경
    void updatePassword(UserInfoDTO pDTO) throws Exception;

    int idCheck(String user_id) throws Exception;
}