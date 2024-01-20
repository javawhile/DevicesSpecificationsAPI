package org.devices.specifications.api.common.pojo;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode
public class BrandModelSpecifications {
    private String category;
    private String property;
    private List<String> values;
}
