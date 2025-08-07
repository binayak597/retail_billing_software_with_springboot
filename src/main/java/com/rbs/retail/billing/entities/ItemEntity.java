package com.rbs.retail.billing.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Entity
@Table(name = "tbl_items")
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String itemId;

    private String name;

    private BigDecimal price;

    private String description;

    private String imgUrl;

    @CreationTimestamp
    @Column(updatable = false)
    private Timestamp createdAt;

    @UpdateTimestamp
    private Timestamp updatedAt;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    @OnDelete(action = OnDeleteAction.RESTRICT)
    private CategoryEntity category;
}
