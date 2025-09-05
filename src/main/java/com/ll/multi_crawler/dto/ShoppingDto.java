package com.ll.multi_crawler.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShoppingDto {
    private int no;
    private String title;
    private String mall;
    private String price;
    private String imageUrl;
}
