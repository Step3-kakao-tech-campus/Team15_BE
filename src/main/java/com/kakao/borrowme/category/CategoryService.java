package com.kakao.borrowme.category;

import com.kakao.borrowme.product.Product;
import com.kakao.borrowme.product.ProductJPARepository;
import com.kakao.borrowme.product.image.ProductImage;
import com.kakao.borrowme.product.image.ProductImageJPARepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Slf4j
@Service
public class CategoryService {

    private final CategoryJPARepository categoryJPARepository;
    private final ProductJPARepository productJPARepository;
    private final ProductImageJPARepository productImageJPARepository;

    @Transactional(readOnly = true)
    public List<CategoryResponse.CategoryDTO> getCategory() {

        // 모든 카테고리 조회
        List<Category> categoryList = categoryJPARepository.findAll();

        // Category 엔티티를 DTO로 변환
        List<CategoryResponse.CategoryDTO> responseDTOs = categoryList.stream()
                .map(category -> new CategoryResponse.CategoryDTO(category.getId(), category.getName()))
                .collect(Collectors.toList());

        return responseDTOs;
    }

    @Transactional(readOnly = true)
    public List<CategoryResponse.ProductDTO> getCategoryProduct(Long categoryId) {

        // categoryId를 사용하여 해당 카테고리에 속한 상품을 조회
        List<Product> productList = productJPARepository.findByCategoryId(categoryId);

        // TODO : 존재하지 않는 카테고리 접근에 대한 에러처리
        if (productList.isEmpty()) {
            log.error("해당 카테고리가 존재하지 않습니다. categoryId: " + categoryId);
        }

        // Product 엔티티를 DTO로 변환
        List<CategoryResponse.ProductDTO> responseDTOs = new ArrayList<>();

        for (Product product : productList) {
            ProductImage productImage = productImageJPARepository.findByProductId(product.getId());

            String productImagePath = productImage.getProductImagePath();

            CategoryResponse.ProductDTO productDTO = new CategoryResponse.ProductDTO(
                    product.getId(),
                    product.getName(),
                    product.getRentalPrice(),
                    product.getRegularPrice(),
                    productImagePath);

            responseDTOs.add(productDTO);
        }

        return responseDTOs;
    }
}
