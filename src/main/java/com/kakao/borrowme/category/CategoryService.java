package com.kakao.borrowme.category;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Slf4j
@Service
public class CategoryService {

    private final CategoryJPARepository categoryJPARepository;

    @Transactional(readOnly = true)
    public List<CategoryResponse.FindAllDTO> findAll() {

        // CategoryJPARepository를 사용하여 모든 카테고리 조회
        List<Category> categoryList = categoryJPARepository.findAll();

        // Category 엔티티를 DTO로 변환
        List<CategoryResponse.FindAllDTO> responseDTOs = categoryList.stream()
                .map(category -> new CategoryResponse.FindAllDTO(category.getId(), category.getName()))
                .collect(Collectors.toList());

        return responseDTOs;
    }
}
