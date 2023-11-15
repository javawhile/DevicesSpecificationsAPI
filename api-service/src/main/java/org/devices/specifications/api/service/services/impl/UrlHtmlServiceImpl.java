package org.devices.specifications.api.service.services.impl;

import org.devices.specifications.api.common.model.sql.UrlHtml;
import org.devices.specifications.api.service.repository.UrlHtmlRepository;
import org.devices.specifications.api.service.services.UrlHtmlService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UrlHtmlServiceImpl implements UrlHtmlService {

    private final Logger logger = LoggerFactory.getLogger(UrlHtmlServiceImpl.class);

    @Autowired(required = false)
    private UrlHtmlRepository urlHtmlRepository;

    public void saveWebpageAsHtml(String url, String html) {
        int savedId = saveUrlHtml(url, html);
        if (savedId != -1) {
            logger.info("HTML persist successful savedId: {}", savedId);
        }
    }

    private int saveUrlHtml(String url, String html) {
        UrlHtml urlHtml = getUrlHtml(url, html);
        int alreadySavedId = getIdByUrlHtml(url);
        if(alreadySavedId == -1) {
            UrlHtml urlHtmlSaved = urlHtmlRepository.save(urlHtml);
            return urlHtmlSaved.getId();
        }
        return alreadySavedId;
    }

    private int getIdByUrlHtml(String url) {
        Example<UrlHtml> example = Example.of(getUrlHtml(url, null), ExampleMatcher.matchingAll().withIgnoreCase());
        Optional<UrlHtml> urlHtmlOptional = urlHtmlRepository.findOne(example);
        if(urlHtmlOptional.isPresent()) {
            return urlHtmlOptional.get().getId();
        }
        return -1;
    }

    private UrlHtml getUrlHtml(String url, String html) {
        UrlHtml urlHtml = new UrlHtml();
        urlHtml.setUrl(url);
        urlHtml.setHtml(html);
        return urlHtml;
    }
}
