package com.kakao.borrowme.product;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RequiredArgsConstructor // defalut 생성자 생성
@RestController
public class ProductRestController {

    private final ProductService productService;

    // 전체 상품 목록 조회
    @GetMapping("/products")
    public ResponseEntity<Page<ProductResponse.FindAllDTO>> findAll(
            @PageableDefault(page = 0, size = 5, sort = "name", direction = Sort.Direction.ASC) Pageable pageable,
            @RequestParam(value = "lastItemId", required = false) Long lastItemId
    ) {
        Page<ProductResponse.FindAllDTO> responseDTOPage = productService.findAll(pageable, lastItemId);
        return ResponseEntity.ok(responseDTOPage);
    }

    // 개별 상품 상세 조회
    @GetMapping("/products/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        ProductResponse.FindByIdDTO responseDTO = productService.findById(id);
        ApiUtils.ApiResult<?> apiResult = ApiUtils.success(responseDTO);
        return ResponseEntity.ok(apiResult);
    }

}
