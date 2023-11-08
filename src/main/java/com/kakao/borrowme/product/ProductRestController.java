package com.kakao.borrowme.product;

import com.kakao.borrowme._core.utils.ApiUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequiredArgsConstructor // defalut 생성자 생성
@RestController
public class ProductRestController {

    private final ProductService productService;

    // 전체 제품 조회
    @GetMapping("/product")
    public ResponseEntity<?> findAll(
            @RequestParam(value = "lastProductId", required = false) Long lastProductId,
            @PageableDefault(page = 0, size = 5, sort = "name", direction = Sort.Direction.ASC) Pageable pageable) {
        Slice<ProductResponse.FindAllDTO> responseDTOs = productService.findAll(lastProductId, pageable);
        ApiUtils.ApiResult<?> apiResult = ApiUtils.success(responseDTOs);
        return ResponseEntity.ok(apiResult);
    }

    // 특정 제품 조회
    @GetMapping("/product/{productId}")
    public ResponseEntity<?> findById(@PathVariable Long productId) {
        ProductResponse.FindByIdDTO responseDTO = productService.findById(productId);
        ApiUtils.ApiResult<?> apiResult = ApiUtils.success(responseDTO);
        return ResponseEntity.ok(apiResult);
    }

    // 제품 검색
    @GetMapping("/product/search")
    public ResponseEntity<?> searchProducts(@RequestParam(value = "lastProductId", required = false) Long lastProductId,
                                            @RequestParam("keyword") String keyword,
                                            @PageableDefault(page = 0, size = 5, sort = "name", direction = Sort.Direction.ASC) Pageable pageable) {
        Slice<ProductResponse.FindAllDTO> responseDTOs = productService.searchProduct(lastProductId, keyword, pageable);
        ApiUtils.ApiResult<?> apiResult = ApiUtils.success(responseDTOs);
        return ResponseEntity.ok(apiResult);
    }

    // 대여하기
    @PostMapping("/product/{productId}/rent")
    public ResponseEntity<?> rentProduct(@PathVariable Long productId,
                                         @RequestBody ProductRequest.RentDTO requestDTO) {
        ProductResponse.RentDTO responseDTO = productService.rentProduct(productId, requestDTO);
        ApiUtils.ApiResult<?> apiResult = ApiUtils.success(responseDTO);
        return ResponseEntity.ok(apiResult);
    }
}
