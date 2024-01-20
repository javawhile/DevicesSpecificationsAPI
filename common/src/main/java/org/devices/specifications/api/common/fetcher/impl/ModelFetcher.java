package org.devices.specifications.api.common.fetcher.impl;

import org.devices.specifications.api.common.fetcher.BaseFetcher;
import org.devices.specifications.api.common.fetcher.constants.Constants;
import org.devices.specifications.api.common.model.ConnectionConfig;
import org.devices.specifications.api.common.model.Model;
import org.jsoup.nodes.Attributes;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class ModelFetcher extends BaseFetcher<Set<Model>> implements Constants {

    private static final Logger logger = LoggerFactory.getLogger(ModelFetcher.class);

    @Override
    public Set<Model> fetchForUrl(final String url, final ConnectionConfig connectionConfig) {
        logger.debug("getAllModels: started for url={}", url);

        //REMOVE HTML CONSUMER IN CASE MODELS ARE REQUESTED AS PAGE IS NOT FULL LOAD
        connectionConfig.setUrlWebpageHtmlConsumer(null);

        DocumentFetcher documentFetcher = new DocumentFetcher();
        Document document = documentFetcher.fetchForUrl(url, connectionConfig);

        if (document == null) {
            String errorMessage = String.format("Unknown Error: fetched document cannot be null for url=%s", url);
            logger.error(errorMessage);
            throw new RuntimeException(errorMessage);
        }

        Elements allModelListElements = document.getElementsByClass(MODEL_LIST_BLOCK);

        if (allModelListElements.isEmpty()) {
            String errorMessage = String.format("Unknown Error: allModelListElements cannot be empty for url=%s", url);
            logger.error(errorMessage);
            return Collections.emptySet();
        }

        final Set<Model> models = new HashSet<>();

        for (Element modelElement : allModelListElements) {
            //GET ALL LINK TAGS
            Elements linkTags = modelElement.getElementsByTag(A);

            if (linkTags.isEmpty()) {
                continue;
            }

            for (Element linkTag : linkTags) {
                if (linkTag == null) {
                    continue;
                }
                Attributes linkTagAttributes = linkTag.attributes();
                if (linkTagAttributes.isEmpty()) {
                    continue;
                }

                String hrefUrl = linkTagAttributes.get(HREF);
                String contentInTag = linkTag.text();

                if(hrefUrl.trim().isEmpty() || contentInTag.trim().isEmpty()) {
                    continue;
                }

                Model model = new Model();
                model.setModelName(contentInTag);
                model.setModelUrl(hrefUrl);

                models.add(model);
            }
        }

        logger.debug("getAllModels: ended for url={}", url);

        return models;
    }

}
