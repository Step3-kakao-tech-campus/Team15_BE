package com.kakao.borrowme.product;

eimport com.kakao.borrowme.company.Company;
import com.kakao.borrowme.company.CompanyJPARepository;
import com.kakao.borrowme.product.image.ProductImage;
import com.kakao.borrowme.product.image.ProductImageJPARepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class ProductService {

    private final ProductJPARepository productRepository;
    private final ProductImageJPARepository productImageRepository;
    private final CompanyJPARepository companyRepository;

    public Page<ProductResponse.FindAllDTO> findAll(Pageable pageable, Long lastItemId) {
        Page<Product> productPage = productRepository.findNextPage(lastItemId, pageable);
        Page<ProductResponse.FindAllDTO> responseDTOPage = productPage.map(product -> {
            ProductImage productImage = productImageRepository.findByProductId(product.getId());
            return new ProductResponse.FindAllDTO(product, productImage);
        });

        return responseDTOPage;
    }


    public ProductResponse.FindByIdDTO findById(Long id) {
        Product product = productRepository.findById(id).orElseThrow(
                () -> new Exception404("해당 상품을 찾을 수 없습니다 : "+id)
        );
        ProductImage productImage = productImageRepository.findByProductId(id); // ProductImage 정보 조회

        Company company = companyRepository.findById(id)
                .orElseThrow(() -> new Exception404("해당 회사를 찾을 수 없습니다 : " + id)); // Company 정보 조회

        return new ProductResponse.FindByIdDTO(product, productImage, company);
    }


}