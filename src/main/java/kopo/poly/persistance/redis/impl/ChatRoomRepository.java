package kopo.poly.persistance.redis.impl;

import kopo.poly.dto.BookDTO;
import kopo.poly.dto.ChatRoomDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.*;

@Repository
public class ChatRoomRepository {

    private Map<String, ChatRoomDTO> chatRoomDTOMap;
    private final RedisTemplate<String, Object> redisDB;

    @Autowired
    public ChatRoomRepository(RedisTemplate<String, Object> redisDB) {
        this.redisDB = redisDB;
    }


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

    public ChatRoomDTO createChatRoomDTO(BookDTO book) {

        String roomId = book.getUser_id() + "-" + book.getBook_seq();
        ChatRoomDTO room = ChatRoomDTO.create(roomId);
        chatRoomDTOMap.put(roomId, room);

        return room;
    }

    public Set<String> findAllByUserId(String userId) throws Exception {
        Set<String> roomSet = new HashSet<>();
        String key = userId + "*";
        roomSet = (Set<String>) redisDB.keys(key);
        return roomSet;
    }
}
