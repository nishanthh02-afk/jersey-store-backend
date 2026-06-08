package com.jerseystore.jersey_backend.entity;

import com.jerseystore.jersey_backend.enums.KitType;
import com.jerseystore.jersey_backend.enums.Size;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "productvariants")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductVariant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @Enumerated(EnumType.STRING)
    private KitType kitType;

    @Enumerated(EnumType.STRING)
    private Size size;

    private Integer stock;
    private Double price;
}
