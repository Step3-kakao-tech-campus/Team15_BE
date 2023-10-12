package com.kakao.borrowme.category;

import lombok.Getter;
import lombok.Setter;

public class CategoryResponse {

    @Getter @Setter
    public static class FindAllDTO {

        private Long id;
        private String name;


        public FindAllDTO(Long id, String name) {
            this.id = id;
            this.name = name;
        }

    }
}
