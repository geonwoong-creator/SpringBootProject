package kopo.poly.persistance.redis.impl;

import kopo.poly.dto.ChatMesssageDTO;

import java.util.List;

public interface IChatRedisMapper {

    int saveRedisStringJSON(ChatMesssageDTO cDTO) throws Exception;

    boolean setTimeOutMinute(String roomKey, int minutes) throws Exception;

    List<ChatMesssageDTO> getChat(ChatMesssageDTO cDTO) throws Exception;
}
