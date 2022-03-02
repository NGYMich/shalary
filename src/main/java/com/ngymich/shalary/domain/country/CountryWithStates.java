package com.ngymich.shalary.domain.country;

import com.ngymich.shalary.domain.state.State;
import lombok.Data;

import java.util.List;

@Data
public class CountryWithStates {
    private String name;
    private List<State> states;
}
