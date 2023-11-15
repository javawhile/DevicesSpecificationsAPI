package org.devices.specifications.api.common.fetcher;

import org.devices.specifications.api.common.fetcher.constants.Constants;
import org.devices.specifications.api.common.model.Brand;
import org.devices.specifications.api.common.model.ConnectionConfig;
import org.jsoup.nodes.Attributes;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class BrandFetcher implements Constants {

    @Value("${scrap.web.base.url}")
    private String BASE_URL;

    private static final Logger logger = LoggerFactory.getLogger(BrandFetcher.class);

    public Set<Brand> getAllBrands(final ConnectionConfig connectionConfig) {
        logger.debug("getAllBrands: started for url={}", BASE_URL);

        DocumentFetcher documentFetcher = new DocumentFetcher();
        Document document = documentFetcher.getPageAsDocument(BASE_URL, connectionConfig);

        if (document == null) {
            String errorMessage = String.format("Unknown Error: fetched document cannot be null for url=%s", BASE_URL);
            logger.error(errorMessage);
            throw new RuntimeException(errorMessage);
        }

        if(connectionConfig.getUrlWebpageHtmlConsumer() != null) {
            connectionConfig.getUrlWebpageHtmlConsumer().accept(BASE_URL, document.html());
        }

        Elements allBrandListElements = document.getElementsByClass(BRAND_LIST_BLOCK);

        if (allBrandListElements.isEmpty()) {
            String errorMessage = String.format("Unknown Error: allBrandListElements cannot be empty for url=%s", BASE_URL);
            logger.error(errorMessage);
            return Collections.emptySet();
        }

        final Set<Brand> brands = new HashSet<>();

        for (Element brandElement : allBrandListElements) {
            //GET ALL LINK TAGS
            Elements linkTags = brandElement.getElementsByTag(A);

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

                Brand brand = new Brand();
                brand.setBrandName(contentInTag.trim());
                brand.setBrandUrl(hrefUrl.trim());
                brands.add(brand);
            }
        }

        logger.debug("getAllBrands: ended for url={}", BASE_URL);

        return brands;
    }

    public Set<Brand> getAllBrands() {
        return getAllBrands(new ConnectionConfig());
    }
}
