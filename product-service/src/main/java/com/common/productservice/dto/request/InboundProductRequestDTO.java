package com.common.productservice.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class InboundProductRequestDTO {
    private long idocNo;
    List<BaseProductRequestDTO> products;
}
