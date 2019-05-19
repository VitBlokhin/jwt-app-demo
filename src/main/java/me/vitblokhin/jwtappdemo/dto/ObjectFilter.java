package me.vitblokhin.jwtappdemo.dto;

import lombok.Data;

import javax.validation.constraints.Min;

@Data
public class ObjectFilter {
    @Min(0)
    private int page = 0;
    @Min(1)
    private int size = 10;
} // class ObjectFilter
