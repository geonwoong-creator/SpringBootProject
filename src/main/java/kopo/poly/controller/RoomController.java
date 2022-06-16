package kopo.poly.controller;

import kopo.poly.dto.BookDTO;
import kopo.poly.dto.ChatMesssageDTO;
import kopo.poly.persistance.redis.impl.ChatRoomRepository;
import kopo.poly.service.IBookService;
import kopo.poly.service.IChatRedisService;
import kopo.poly.util.CmmUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/chat")
@Slf4j
public class RoomController {

    private final ChatRoomRepository repository;

    @Resource(name = "BookService")
    private IBookService bookService;

    @Resource(name = "ChatRedisService")
    private IChatRedisService chatRedisService;

    //채팅방 목록 조회
    @GetMapping(value = "/rooms")
    public String rooms(ModelMap model, HttpSession session) throws Exception{

        log.info(this.getClass().getName() + ".getChatRoomList");
        log.info(this.getClass().getName() + ".getChatRoomList");
        String Userid = CmmUtil.nvl((String) session.getAttribute("SS_USER_ID"));



        model.addAttribute("list", repository.findAllRooms());


     //   model.addAttribute("list", repository.findAllRooms());
        //UserId 로 maria Book db seller  userid  return list<bookDTO>
        // list<ChatRoomDTO> 생성
        // for( ChatRoomDTO = repository.findById()
        // list add(ChatRoomDTO)
        //

        //model(key, list<ChatRoomDTO>)

        return "/chat/rooms";
    }

    //채팅방 개설
    @PostMapping(value = "/room")
    public String create(@RequestParam String name, HttpSession session, RedirectAttributes rttr) throws Exception {
        String Userid = CmmUtil.nvl((String) session.getAttribute("SS_USER_ID"));
        BookDTO pDTO = new BookDTO();
        pDTO.setUser_id(Userid);

        BookDTO book = bookService.getBookSeq(pDTO);

        log.info("Create chat room, name : " + name);
        rttr.addFlashAttribute("roomName", repository.createChatRoomDTO(name, book));
        return "redirect:/chat/rooms";
    }

    //채팅방 조회
    @GetMapping("/room")
    public String getRoom(HttpServletRequest request, Model model, HttpSession session) throws Exception {
        String Userid = CmmUtil.nvl((String) session.getAttribute("SS_USER_ID"));

        String roomId = request.getParameter("roomId");
        log.info("roomId :" + roomId);


        model.addAttribute("room", repository.findRoomById(roomId));


        return "/chat/room";
    }@GetMapping(value = "/chat/room")
    @ResponseBody
    public List<ChatMesssageDTO> getMsg(HttpSession session, HttpServletRequest request)
            throws Exception {

        log.info(this.getClass().getName() + ".getMsg Start!");

        String room_name = CmmUtil.nvl(request.getParameter("roomId"));

        log.info("room_name : " + room_name);

        ChatMesssageDTO cDTO = new ChatMesssageDTO();

//        cDTO.setRoomid("Chat_" + room_name);
        cDTO.setRoomid(room_name);

        List<ChatMesssageDTO> rList = chatRedisService.getChat(cDTO);

        if (rList == null) {
            rList = new ArrayList<>();

        }

        cDTO = null;

        log.info(this.getClass().getName() + ".getMsg End!");

        return rList;
    }

}
