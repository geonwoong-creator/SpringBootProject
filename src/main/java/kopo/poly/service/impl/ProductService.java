package kopo.poly.service.impl;

import kopo.poly.dto.ProductDTO;
import kopo.poly.persistance.mapper.IProductMapper;
import kopo.poly.service.IProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service("ProductService")
public class ProductService implements IProductService {

    private final IProductMapper productMapper;

    @Autowired
    public ProductService(IProductMapper productMapper) {
        this.productMapper = productMapper;
    }

    @Override
    public List<ProductDTO> getProductList() throws Exception {

        log.info(this.getClass().getName() + ".getProductList start!");

        return productMapper.getProductList();

    }



    @Transactional
    @Override
    public void InsertProduct(ProductDTO pDTO) throws Exception {

        log.info(this.getClass().getName() + ".InsertProduct start!");

        productMapper.insertProduct(pDTO);
    }

    @Transactional
    @Override
    public ProductDTO getProduct(ProductDTO pDTO) throws Exception {

        log.info(this.getClass().getName() + ".getProduct start!");

        // 상세보기 할때마다, 조회수 증가하기
        log.info("Update ReadCNT");
        productMapper.updateProductReadCnt(pDTO);

        return productMapper.getProduct(pDTO);

    }

    @Transactional
    @Override
    public void updateProduct(ProductDTO pDTO) throws Exception {

        log.info(this.getClass().getName() + ".updateProduct start!");

        productMapper.updateProduct(pDTO);

    }

    @Transactional
    @Override
    public void deleteProduct(ProductDTO pDTO) throws Exception {

        log.info(this.getClass().getName() + ".deleteProduct start!");

        productMapper.deleteProduct(pDTO);

    }

    @Transactional
    @Override
    public int fileInsert(ProductDTO pDTO) throws Exception {
        log.info(this.getClass().getName() + ".fileinsert");


        return productMapper.fileInsert(pDTO);
    }
}
