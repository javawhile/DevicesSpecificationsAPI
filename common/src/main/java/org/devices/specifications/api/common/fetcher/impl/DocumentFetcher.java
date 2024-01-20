package org.devices.specifications.api.common.fetcher.impl;

import org.devices.specifications.api.common.fetcher.BaseFetcher;
import org.devices.specifications.api.common.model.ConnectionConfig;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.parser.Parser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

@Component
public class DocumentFetcher extends BaseFetcher<Document> {

    private static final Logger logger = LoggerFactory.getLogger(DocumentFetcher.class);

    @Override
    public Document fetchForUrl(final String url, final ConnectionConfig connectionConfig) {
        try {
            logger.debug("getPageAsDocument: started for url={}", url);

            if (connectionConfig.getUrlWebpageHtmlProducer() != null) {
                String html = connectionConfig.getUrlWebpageHtmlProducer().apply(url);
                if (isValidProperty(html)) {
                    try {
                        Document parsedDocument = Jsoup.parse(html, Parser.xmlParser());
                        if (connectionConfig.getUrlWebpageHtmlConsumer() != null) {
                            connectionConfig.getUrlWebpageHtmlConsumer().accept(url, parsedDocument.html());
                        }
                        logger.debug("getPageAsDocument: [Producer] ended for url={}", url);
                        return parsedDocument;
                    } catch (final Exception ignored) {
                        connectionConfig.setUrlWebpageHtmlProducer(null);
                        return fetchForUrl(url, connectionConfig);
                    }
                }
            }

            Connection connect = Jsoup.connect(url);

            //CONNECTION PROPERTIES
            if (isValidProperty(connectionConfig.getAgent())) {
                connect.userAgent(connectionConfig.getAgent());
            }
            if (isValidProperty(connectionConfig.getTimeout())) {
                connect.timeout(connectionConfig.getTimeout());
            }
            if (isValidProperty(connectionConfig.isAllowRedirects())) {
                connect.followRedirects(connectionConfig.isAllowRedirects());
            }
            if (isValidProperty(connectionConfig.getHeaders())) {
                connect.headers(connectionConfig.getHeaders());
            }
            if (isValidProperty(connectionConfig.getProxyHost()) && isValidProperty(connectionConfig.getProxyPort())) {
                connect.proxy(connectionConfig.getProxyHost(), connectionConfig.getProxyPort());
            }

            Document document = connect.get();

            if (connectionConfig.getUrlWebpageHtmlConsumer() != null) {
                connectionConfig.getUrlWebpageHtmlConsumer().accept(url, document.html());
            }

            logger.debug("getPageAsDocument: ended for url={}", url);

            return document;
        } catch (IOException e) {
            String errorMessage = String.format("Exception while fetching document page for url=%s, message=%s",
                    url, e.getMessage());
            logger.error(errorMessage);
            throw new RuntimeException(errorMessage);
        }
    }

    private boolean isValidProperty(Object object) {
        if (object == null) {
            return false;
        }
        if (object instanceof String) {
            String propertyInString = (String) object;
            return !propertyInString.trim().isEmpty();
        } else if (object instanceof Map) {
            Map<?, ?> propertyInMap = (Map<?, ?>) object;
            return !propertyInMap.keySet().isEmpty();
        }
        return false;
    }

}