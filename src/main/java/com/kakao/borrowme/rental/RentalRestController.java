package com.kakao.borrowme.rental;

import com.kakao.borrowme._core.utils.ApiUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class RentalRestController {

    private final RentalService rentalService;

    @GetMapping("/rental")
    public ResponseEntity<?> FindAllDTO() {
        List<RentalResponse.FindAllDTO> responseDTOs = rentalService.findAll();
        ApiUtils.ApiResult<?> apiResult = ApiUtils.success(responseDTOs);
        return ResponseEntity.ok(apiResult);
    }
}
