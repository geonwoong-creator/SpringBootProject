package kopo.poly.dto;

import lombok.Data;

@Data
public class ProductDTO {

    private String product_seq; // 기본키, 순번
    private String product_name; // 제목
    private String filename; // 저장할 파일
    private String fileoriname; // 실제 파일
    private String fileurl; // 파일 위치
    private String addr; //주소
    private String mcoed;// 행정코드
    private String price;//가격
    private String pay_type;//결재방법
    private String contents; // 글 내용
    private String user_id; // 작성자
    private String read_cnt; // 조회수
    private String reg_id; // 등록자 아이디
    private String reg_dt; // 등록일
    private String chg_id; // 수정자 아이디
    private String chg_dt; // 수정일

}
