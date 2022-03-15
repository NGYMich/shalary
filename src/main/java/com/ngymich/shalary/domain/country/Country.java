package com.ngymich.shalary.domain.country;

import com.ngymich.shalary.domain.state.State;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class Country {
    private String name;
    private String flag;
    private List<String> states;
}
