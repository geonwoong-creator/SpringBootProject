package kopo.poly.dto;

import lombok.Data;

@Data
public class FoodDTO {

    private String mmcode ; //중분류 코드

    private String sscode ; //소분류 코드

    private String user_id;

    private String reg_dt;

}
