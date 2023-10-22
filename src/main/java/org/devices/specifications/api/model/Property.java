package org.devices.specifications.api.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
public class Property {
    private String category;
    private String categoryDescription;
    private String propertyName;
    private String propertyDescription;
    private List<String> values;
}
