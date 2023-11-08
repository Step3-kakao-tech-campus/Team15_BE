package com.kakao.borrowme.category;

import com.kakao.borrowme._core.utils.ApiUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/category")
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping("")
    public ResponseEntity<?> getCategory() {
        CategoryResponse.CategoryDTO responseDTOs = categoryService.getCategory();
        ApiUtils.ApiResult<?> apiResult = ApiUtils.success(responseDTOs);
        return ResponseEntity.ok(apiResult);
    }

    @GetMapping("/{categoryId}")
    public ResponseEntity<?> getProductByCategory(@PathVariable Long categoryId,
                                                  @RequestParam(value = "lastProductId", required = false) Long lastProductId,
                                                  @PageableDefault(page = 0, size = 5, sort = "name", direction = Sort.Direction.ASC) Pageable pageable) {
        Slice<CategoryResponse.ProductDTO> responseDTOs = categoryService.getProductByCategory(categoryId, lastProductId, pageable);
        ApiUtils.ApiResult<?> apiResult = ApiUtils.success(responseDTOs);
        return ResponseEntity.ok(apiResult);
    }
}