package com.kakao.borrowme.product;


import com.kakao.borrowme._core.errors.exception.Exception404;
import com.kakao.borrowme.company.Company;
import com.kakao.borrowme.company.CompanyJPARepository;
import com.kakao.borrowme.product.image.ProductImage;
import com.kakao.borrowme.product.image.ProductImageJPARepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class ProductService {

    private final ProductJPARepository productRepository;
    private final ProductImageJPARepository productImageRepository;
    private final CompanyJPARepository companyRepository;

    public Slice<ProductResponse.FindAllDTO> findAll(Long lastProductId, Pageable pageable) {
        Slice<Product> productSlice = productRepository.findNextPage(lastProductId, pageable);
        Slice<ProductResponse.FindAllDTO> responseDTOSlice = productSlice.map(product -> {
            ProductImage productImage = productImageRepository.findByProductId(product.getId());
            return new ProductResponse.FindAllDTO(product, productImage);
        });
        return responseDTOSlice;
    }

    public ProductResponse.FindByIdDTO findById(Long id) {
        Product product = productRepository.findById(id).orElseThrow(
                () -> new Exception404("존재하지 않는 제품입니다. : "+id,"product_not_existed"));

        ProductImage productImage = productImageRepository.findByProductId(id); // ProductImage 정보 조회

        Company company = companyRepository.findById(product.getCompany().getId()).orElseThrow(
                () -> new Exception404("존재하지 않는 회사입니다. : "+id,"company_not_existed")); // Company 정보 조회
        return new ProductResponse.FindByIdDTO(product, productImage, company);
    }
}
