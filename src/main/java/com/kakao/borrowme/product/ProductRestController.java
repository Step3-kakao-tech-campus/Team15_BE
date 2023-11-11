package com.kakao.borrowme.product;

import com.kakao.borrowme._core.utils.ApiUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/product")
public class ProductRestController {
    private final ProductService productService;

    // 전체 제품 조회
    @GetMapping("")
    public ResponseEntity<?> findAll(
            @RequestParam(value = "lastProductId", required = false) Long lastProductId,
            @PageableDefault(page = 0, size = 5, sort = "name", direction = Sort.Direction.ASC) Pageable pageable) {
        Slice<ProductResponse.FindAllDTO> responseDTOs = productService.findAll(lastProductId, pageable);
        return ResponseEntity.ok(ApiUtils.success(responseDTOs));
    }

    // 특정 제품 조회
    @GetMapping("/{productId}")
    public ResponseEntity<?> findById(@PathVariable Long productId) {
        ProductResponse.FindByIdDTO responseDTO = productService.findById(productId);
        return ResponseEntity.ok(ApiUtils.success(responseDTO));
    }

    // 제품 검색
    @GetMapping("/search")
    public ResponseEntity<?> searchProduct(@RequestParam(value = "lastProductId", required = false) Long lastProductId,
                                            @RequestParam("keyword") String keyword,
                                            @PageableDefault(page = 0, size = 5, sort = "name", direction = Sort.Direction.ASC) Pageable pageable) {
        Slice<ProductResponse.FindAllDTO> responseDTOs = productService.searchProduct(lastProductId, keyword, pageable);
        return ResponseEntity.ok(ApiUtils.success(responseDTOs));
    }

    // 대여하기
    @PostMapping("/{productId}/rent")
    public ResponseEntity<?> rentProduct(@PathVariable Long productId,
                                         @RequestBody ProductRequest.RentDTO requestDTO) {
        ProductResponse.RentDTO responseDTO = productService.rentProduct(productId, requestDTO);
        return ResponseEntity.ok(ApiUtils.success(responseDTO));
    }
}
