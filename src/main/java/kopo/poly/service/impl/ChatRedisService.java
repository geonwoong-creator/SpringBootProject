package kopo.poly.service.impl;

import kopo.poly.dto.ChatMesssageDTO;
import kopo.poly.persistance.redis.impl.IChatRedisMapper;
import kopo.poly.service.IChatRedisService;
import kopo.poly.util.CmmUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Slf4j
@Service("ChatRedisService")
public class ChatRedisService implements IChatRedisService {

    @Resource(name = "ChatRedisMapper")
    private IChatRedisMapper chatRedisMapper;

    @Override
    public List<ChatMesssageDTO> saveRedisStringJSON(ChatMesssageDTO cDTO) throws Exception {
        log.info(this.getClass().getName() + ".saveRedisStringJSON start!");


        if (chatRedisMapper.saveRedisStringJSON(cDTO) == 1) {
            log.info("chatMapper.insert start");
            String roomName = cDTO.getUser_id() + cDTO.getRoomid();
            chatRedisMapper.setTimeOutMinute(CmmUtil.nvl(roomName), 5);
        } else {
            log.info("fail");
        }

        return getChat(cDTO);



    }

    @Override
    public List<ChatMesssageDTO> getChat(ChatMesssageDTO cDTO) throws Exception {

        log.info(this.getClass().getName() + ".getChat Start!");

        return chatRedisMapper.getChat(cDTO);
    }

}
