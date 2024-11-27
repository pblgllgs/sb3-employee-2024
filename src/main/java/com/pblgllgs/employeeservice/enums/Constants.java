package com.pblgllgs.employeeservice.enums;
/*
 *
 * @author pblgl
 * Created on 27-11-2024
 *
 */

import lombok.Getter;

@Getter
public enum Constants {
    RESOURCE_NOT_FOUND("Resource not found");
    final String value;
    Constants(String value) {
        this.value = value;
    }
}
