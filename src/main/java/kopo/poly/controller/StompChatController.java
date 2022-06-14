package kopo.poly.controller;

import kopo.poly.dto.ChatMesssageDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Slf4j
@Controller
@RequiredArgsConstructor
public class StompChatController {

    private final SimpMessagingTemplate template; //특정 브로커로 메세지 전달

    //client가 send할 수 있는 경로
    //stompConfig에서 설정한 applicationDestinationPrefixes와 @MessageMapping 경로가 병합됨
    //"/pub/chat/enter"
    @MessageMapping(value = "/chat/enter")
    public void enter(ChatMesssageDTO messsage) {
        messsage.setMessage(messsage.getUser_id() + "님이 채팅방에 참여하였습니다.");
        template.convertAndSend("/sub/chat/room" + messsage.getRoomid(), messsage);
    }

    @MessageMapping(value = "/chat/message")
    public void message(ChatMesssageDTO messsage) {
        log.info("백엔드에 메세지 도착");
        template.convertAndSend("/sub/chat/room/" + messsage.getRoomid(), messsage);
    }
}
