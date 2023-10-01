package org.devices.specifications.api.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
public class Specifications {
    private String deviceName;
    private String dimensions;
    private String weight;
    private String soc;
    private String cpu;
    private String gpu;
    private String ram;
    private String storage;
    private String memoryCards;
    private String display;
    private String battery;
    private String os;
    private String camera;
    private String simCards;
    private String wifi;
    private String usb;
    private String bluetooth;
    private String positioning;
}
