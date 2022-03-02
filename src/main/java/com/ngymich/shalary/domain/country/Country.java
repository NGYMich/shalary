package com.ngymich.shalary.domain.country;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Country {
    private String name;
    private String flag;
}
