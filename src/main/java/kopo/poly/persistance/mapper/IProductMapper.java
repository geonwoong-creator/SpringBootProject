package kopo.poly.persistance.mapper;

import kopo.poly.dto.ProductDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface IProductMapper {

    //상품 리스트
    List<ProductDTO> getProductList() throws Exception;

    //상품 글 등록
    void insertProduct(ProductDTO pDTO) throws Exception;

    //상품 상세보기
    ProductDTO getProduct(ProductDTO pDTO) throws Exception;

    //상품 조회수 업데이트
    void updateProductReadCnt(ProductDTO pDTO) throws Exception;

    //상품 글 수정
    void updateProduct(ProductDTO pDTO) throws Exception;

    //상품 글 삭제
    void deleteProduct(ProductDTO pDTO) throws Exception;

    //파일 업로드
    int fileInsert(ProductDTO pDTO) throws Exception;

}
