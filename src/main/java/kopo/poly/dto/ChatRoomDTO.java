package kopo.poly.dto;

import lombok.Data;
import org.springframework.web.socket.WebSocketSession;

import java.util.HashSet;
import java.util.Set;

@Data
public class ChatRoomDTO {

    private String roomid;
    private String name;
    private Set<WebSocketSession> sessions = new HashSet<>();

    public static ChatRoomDTO create(String roomId, String name){
        ChatRoomDTO room = new ChatRoomDTO();

        room.roomid = roomId;
        room.name = name;
        return room;
    }

}
