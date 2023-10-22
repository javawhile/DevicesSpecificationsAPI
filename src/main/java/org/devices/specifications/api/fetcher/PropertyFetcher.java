package org.devices.specifications.api.fetcher;

import org.devices.specifications.api.fetcher.constants.Constants;
import org.devices.specifications.api.model.ConnectionConfig;
import org.devices.specifications.api.model.Property;
import org.devices.specifications.api.utils.Utils;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class PropertyFetcher implements Constants {

    @Autowired
    private Utils utils;

    public Set<Property> fetchForUrl(String url, ConnectionConfig connectionConfig) {
        DocumentFetcher documentFetcher = new DocumentFetcher();
        Document document = documentFetcher.getPageAsDocument(url, connectionConfig);

        Element targetDiv = getTargetDivElement(document);

        String categoryName = null;
        String categoryDescription = null;
        String propertyName = null;
        String propertyDescription = null;
        List<String> propertyValues = null;

        Set<Property> allProperties = new HashSet<>();

        for(Element child: targetDiv.children()) {
            if(child.tagName().equalsIgnoreCase(HEADER)) {
                Elements h2 = child.getElementsByTag(H2);
                Elements h3 = child.getElementsByTag(H3);
                if(!h2.isEmpty() && !h3.isEmpty()) {
                    categoryName = h2.get(0).text();
                    categoryDescription = h3.get(0).text();
                }
            } else if (child.tagName().equalsIgnoreCase(TABLE)) {
                Elements tbody = child.getElementsByTag(TBODY);

                if(!tbody.isEmpty()) {
                    for(Element tableRow: tbody.get(0).children()) {
                        Elements tds = tableRow.children();
                        if(tds.size() >= 2) {
                            //GET PROPERTY NAME AND DESCRIPTION FROM FIRST TABLE DATA TAG
                            propertyDescription = getPropertyDescriptionAndRemove(tds.get(0));
                            propertyName = getPropertyName(tds.get(0));

                            //GET PROPERTY VALUES FROM SECOND TABLE DATA TAG
                            propertyValues = getPropertyValues(tds.get(1));

                            //SAVE PROPERTY
                            allProperties.add(new Property(categoryName, categoryDescription, propertyName, propertyDescription, propertyValues));
                        }
                    }
                }

                categoryName = null;
                categoryDescription = null;
            } else {

                categoryName = null;
                categoryDescription = null;
            }
        }

        return allProperties;
    }

    public Set<Property> fetchForUrl(String url) {
        return fetchForUrl(url, new ConnectionConfig());
    }

    private List<String> getPropertyValues(Element elementTableData) {
        if(elementTableData == null) {
            return null;
        }

        //REMOVE SPAN TAGS
        Elements children = elementTableData.children();
        if(!children.isEmpty()) {
            for(Element child: children) {
                if(child.tagName().equalsIgnoreCase(SPAN)) {
                    child.remove();
                }
            }
        }

        //GET AND PARSE PROPERTY VALUES
        String valuesString = elementTableData.toString();
        valuesString = utils.removeTags(valuesString, TAG_TD_START, TAG_TD_END, SPACE_LINES, LINES);

        String[] splitByBrTag = valuesString.split(TAG_BR);
        return utils.preprocessStringArray(splitByBrTag);
    }

    private String getPropertyName(Element elementTableData) {
        if(elementTableData == null) {
            return null;
        }
        return elementTableData.text();
    }

    private String getPropertyDescriptionAndRemove(Element elementTableData) {
        if(elementTableData == null) {
            return null;
        }
        Elements propertyDescriptionInParaTag = elementTableData.getElementsByTag(P);
        if(!propertyDescriptionInParaTag.isEmpty()) {
            String propertyDescription = propertyDescriptionInParaTag.get(0).text();
            propertyDescriptionInParaTag.get(0).remove();
            return propertyDescription;
        }
        return null;

    }

    private Element getTargetDivElement(Document document) {
        Element divWithMainId = document.getElementById(ID_MAIN);
        Element targetDiv = null;
        if (divWithMainId != null) {
            for (Element child : divWithMainId.children()) {
                if (isTargetDivElementValid(child)) {
                    targetDiv = child;
                    break;
                }
            }
        }
        return targetDiv;
    }

    private boolean isTargetDivElementValid(Element targetDiv) {
        if (targetDiv != null) {
            int allChildrenInTarget = targetDiv.childrenSize();
            int headersInTarget = targetDiv.getElementsByTag(HEADER).size();
            int tablesInTarget = targetDiv.getElementsByTag(TABLE).size();
            if (allChildrenInTarget > 0 && headersInTarget > 0 && tablesInTarget > 0) {
                return headersInTarget == tablesInTarget && allChildrenInTarget == (headersInTarget + tablesInTarget);
            }
        }
        return false;
    }

}
