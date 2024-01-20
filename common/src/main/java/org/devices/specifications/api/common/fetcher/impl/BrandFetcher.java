package org.devices.specifications.api.common.fetcher.impl;

import org.devices.specifications.api.common.fetcher.BaseFetcher;
import org.devices.specifications.api.common.fetcher.constants.Constants;
import org.devices.specifications.api.common.model.Brand;
import org.devices.specifications.api.common.model.ConnectionConfig;
import org.jsoup.nodes.Attributes;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class BrandFetcher extends BaseFetcher<Set<Brand>> implements Constants {

    private static final Logger logger = LoggerFactory.getLogger(BrandFetcher.class);

    @Override
    public Set<Brand> fetchForUrl(final String url, final ConnectionConfig connectionConfig) {
        logger.debug("getAllBrands: started for url={}", url);

        DocumentFetcher documentFetcher = new DocumentFetcher();
        Document document = documentFetcher.fetchForUrl(url, connectionConfig);

        if (document == null) {
            String errorMessage = String.format("Unknown Error: fetched document cannot be null for url=%s", url);
            logger.error(errorMessage);
            throw new RuntimeException(errorMessage);
        }

        Elements allBrandListElements = document.getElementsByClass(BRAND_LIST_BLOCK);

        if (allBrandListElements.isEmpty()) {
            String errorMessage = String.format("Unknown Error: allBrandListElements cannot be empty for url=%s", url);
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

        logger.debug("getAllBrands: ended for url={}", url);

        return brands;
    }
}
