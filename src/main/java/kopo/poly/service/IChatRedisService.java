package kopo.poly.service;

import kopo.poly.dto.ChatMesssageDTO;

import java.util.List;

public interface IChatRedisService {

    List<ChatMesssageDTO> saveRedisStringJSON(ChatMesssageDTO cDTO) throws Exception;

    List<ChatMesssageDTO> getChat(ChatMesssageDTO cDTO) throws Exception;

}
