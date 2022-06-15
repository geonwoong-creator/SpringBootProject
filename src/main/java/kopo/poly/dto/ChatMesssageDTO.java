package kopo.poly.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class ChatMesssageDTO {

    private String roomid = "";
    private String user_id = "";
    private String message = "";
}
