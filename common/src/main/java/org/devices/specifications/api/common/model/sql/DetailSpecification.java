package org.devices.specifications.api.common.model.sql;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.List;

@Data
@EqualsAndHashCode
@Entity
@Table(name = "detailspecification")
public class DetailSpecification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "specificationid")
    private Integer specificationId;

    @Column(name = "mapid")
    private Integer mapId;

    @Column(name = "category")
    private String category;

    @Column(name = "categorydescription")
    private String categoryDescription;

    @Column(name = "propertyname")
    private String propertyName;

    @Column(name = "propertydescription")
    private String propertyDescription;

    @Column(name = "valuesarray")
    private String values;

}
