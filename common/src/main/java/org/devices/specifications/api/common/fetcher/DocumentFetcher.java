package org.devices.specifications.api.common.fetcher;

import org.devices.specifications.api.common.model.ConnectionConfig;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.net.ssl.TrustManager;
import java.io.IOException;
import java.util.Map;

@Component
public class DocumentFetcher {

    private static final Logger logger = LoggerFactory.getLogger(DocumentFetcher.class);

    public Document getPageAsDocument(final String url, final ConnectionConfig connectionConfig) {
        try {
            logger.debug("getPageAsDocument: started for url={}", url);

            Connection connect = Jsoup.connect(url);

            //CONNECTION PROPERTIES
            if(isValidProperty(connectionConfig.getAgent())) {
                connect.userAgent(connectionConfig.getAgent());
            }
            if(isValidProperty(connectionConfig.getTimeout())) {
                connect.timeout(connectionConfig.getTimeout());
            }
            if(isValidProperty(connectionConfig.isAllowRedirects())) {
                connect.followRedirects(connectionConfig.isAllowRedirects());
            }
            if(isValidProperty(connectionConfig.getHeaders())) {
                connect.headers(connectionConfig.getHeaders());
            }
            if(isValidProperty(connectionConfig.getProxyHost()) && isValidProperty(connectionConfig.getProxyPort())) {
                connect.proxy(connectionConfig.getProxyHost(), connectionConfig.getProxyPort());
            }

            Document document = connect.get();

            logger.debug("getPageAsDocument: ended for url={}", url);

            return document;
        } catch (IOException e) {
            String errorMessage = String.format("Exception while fetching document page for url=%s, message=%s",
                    url, e.getMessage());
            logger.error(errorMessage);
            throw new RuntimeException(errorMessage);
        }
    }

    public Document getPageAsDocument(String url) {
        return getPageAsDocument(url, new ConnectionConfig());
    }

    private boolean isValidProperty(Object object) {
        if(object == null) {
            return false;
        }
        if(object instanceof String) {
            String propertyInString = (String) object;
            return !propertyInString.trim().isEmpty();
        } else if(object instanceof Map) {
            Map<?, ?> propertyInMap = (Map<?, ?>) object;
            return !propertyInMap.keySet().isEmpty();
        }
        return false;
    }

}