package kopo.poly.dto;

import lombok.Data;
import org.springframework.web.socket.WebSocketSession;

import java.util.HashSet;
import java.util.Set;

@Data
public class ChatRoomDTO {

    private String roomid;
    private Set<WebSocketSession> sessions = new HashSet<>();

    public static ChatRoomDTO create(String roomId){
        ChatRoomDTO room = new ChatRoomDTO();

        room.roomid = roomId;
        return room;
    }

}
