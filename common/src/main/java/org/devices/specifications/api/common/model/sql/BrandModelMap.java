package org.devices.specifications.api.common.model.sql;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@Data
@EqualsAndHashCode
@Entity
@Table(name = "brandmodelmap")
public class BrandModelMap {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "mapid")
    private Integer mapId;

    @Column(name = "brandname")
    private String brandName;

    @Column(name = "brandurl")
    private String brandUrl;

    @Column(name = "modelname")
    private String modelName;

    @Column(name = "modelurl")
    private String modelUrl;

}
