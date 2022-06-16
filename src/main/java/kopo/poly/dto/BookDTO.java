package kopo.poly.dto;

import lombok.Data;

@Data
public class BookDTO {

    private String book_seq;
    private String product_name;
    private String product_addr;
    private String product_mcoed;
    private String seller;
    private String user_id;
    private String reg_dt;


    private String exists_yn;
}
