package com.jerseystore.jersey_backend.dto.response;

import lombok.*;
import java.util.List;

@Data @AllArgsConstructor @NoArgsConstructor @Builder
public class WishlistResponse {
    private Long id;
    private List<WishlistItemResponse> items;
}