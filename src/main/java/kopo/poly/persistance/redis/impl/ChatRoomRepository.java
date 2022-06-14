package kopo.poly.persistance.redis.impl;

import kopo.poly.dto.BookDTO;
import kopo.poly.dto.ChatRoomDTO;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.*;

@Repository
public class ChatRoomRepository {

    private Map<String, ChatRoomDTO> chatRoomDTOMap;

    private BookDTO bookDTO;

    @PostConstruct
    private void init() {
        chatRoomDTOMap = new LinkedHashMap<>();
    }

    public List<ChatRoomDTO> findAllRooms(){
        //채팅방 생성 순서 최근 순으로 반환
        List<ChatRoomDTO> result = new ArrayList<>(chatRoomDTOMap.values());
        Collections.reverse(result);

        return result;
    }

    public ChatRoomDTO findRoomById(String id) {
        return chatRoomDTOMap.get(id);
    }

    public ChatRoomDTO createChatRoomDTO(String name, BookDTO book) {

        String roomId = book.getBook_seq();
        ChatRoomDTO room = ChatRoomDTO.create(roomId, name);
        chatRoomDTOMap.put(roomId, room);

        return room;
    }
}
