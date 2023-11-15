package org.devices.specifications.api.common.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;

@Data
@EqualsAndHashCode
public class ConnectionConfig {
    private String agent = "Mozilla/5.0";
    private Integer timeout = 10000;
    private boolean allowRedirects = false;
    private Map<String, String> headers = new HashMap<>();
    private String proxyHost = null;
    private Integer proxyPort = null;
    private BiConsumer<String, String> urlWebpageHtmlConsumer;
}
