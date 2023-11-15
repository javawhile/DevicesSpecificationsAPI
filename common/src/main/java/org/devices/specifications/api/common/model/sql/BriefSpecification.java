package org.devices.specifications.api.common.model.sql;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@Data
@EqualsAndHashCode
@Entity
@Table(name = "briefspecification")
public class BriefSpecification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "specificationid")
    private Integer specificationId;

    @Column(name = "mapid")
    private Integer mapId;

    @Column(name = "devicename")
    private String deviceName;

    @Column(name = "dimensions")
    private String dimensions;

    @Column(name = "weight")
    private String weight;

    @Column(name = "soc")
    private String soc;

    @Column(name = "cpu")
    private String cpu;

    @Column(name = "gpu")
    private String gpu;

    @Column(name = "ram")
    private String ram;

    @Column(name = "storage")
    private String storage;

    @Column(name = "memorycards")
    private String memoryCards;

    @Column(name = "display")
    private String display;

    @Column(name = "battery")
    private String battery;

    @Column(name = "os")
    private String os;

    @Column(name = "camera")
    private String camera;

    @Column(name = "simcards")
    private String simCards;

    @Column(name = "wifi")
    private String wifi;

    @Column(name = "usb")
    private String usb;

    @Column(name = "bluetooth")
    private String bluetooth;

    @Column(name = "positioning")
    private String positioning;
}
