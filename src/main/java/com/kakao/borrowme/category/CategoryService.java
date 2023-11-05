package com.kakao.borrowme.category;

import com.kakao.borrowme._core.errors.exception.Exception404;
import com.kakao.borrowme.product.Product;
import com.kakao.borrowme.product.ProductJPARepository;
import com.kakao.borrowme.product.image.ProductImage;
import com.kakao.borrowme.product.image.ProductImageJPARepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CategoryService {

    private final CategoryJPARepository categoryJPARepository;
    private final ProductJPARepository productJPARepository;
    private final ProductImageJPARepository productImageJPARepository;

    @Transactional(readOnly = true)
    public CategoryResponse.CategoryDTO getCategory() {
        // 모든 카테고리 조회
        List<Category> categoryList = categoryJPARepository.findAll();
        return new CategoryResponse.CategoryDTO(categoryList);
    }

    @Transactional(readOnly = true)
    public Slice<CategoryResponse.ProductDTO> getProductByCategory(Long categoryId, Long lastProductId, Pageable pageable) {
        // categoryId를 사용하여 해당 카테고리에 속한 상품을 페이징하여 조회
        Slice<Product> productSlice = productJPARepository.findNextPageByCategoryId(categoryId, lastProductId, pageable);

        if (!productSlice.hasContent()) {
            throw new Exception404("해당 카테고리가 존재하지 않습니다. : " + categoryId, "category_not_existed");
        }

        Slice<CategoryResponse.ProductDTO> responseDTOSlice = productSlice.map(product -> {
            ProductImage productImage = productImageJPARepository.findByCategoryId(product.getCategory().getId());
            return new CategoryResponse.ProductDTO(product, productImage);
        });
        return responseDTOSlice;
    }
}
