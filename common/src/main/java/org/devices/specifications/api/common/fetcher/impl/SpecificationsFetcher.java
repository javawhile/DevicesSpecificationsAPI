package org.devices.specifications.api.common.fetcher.impl;

import org.devices.specifications.api.common.fetcher.BaseFetcher;
import org.devices.specifications.api.common.fetcher.constants.Constants;
import org.devices.specifications.api.common.model.Specifications;
import org.devices.specifications.api.common.model.ConnectionConfig;

import org.devices.specifications.api.common.utils.Utils;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SpecificationsFetcher extends BaseFetcher<Specifications> implements Constants {

	@Autowired
	private Utils utils;

	private static final Logger logger = LoggerFactory.getLogger(SpecificationsFetcher.class);

	@Override
	public Specifications fetchForUrl(final String url, final ConnectionConfig connectionConfig) {
		logger.debug("fetchForUrl: started for url={}", url);

		DocumentFetcher documentFetcher = new DocumentFetcher();
		Specifications specifications = new Specifications();
		Document document = documentFetcher.fetchForUrl(url, connectionConfig);

		if(document == null) {
			String errorMessage = String.format("Unknown Error: fetched document cannot be null for url=%s", url);
			logger.error(errorMessage);
			throw new RuntimeException(errorMessage);
		}

        Element briefSpecificationsBlock = document.getElementById(BRIEF_SPECIFICATIONS_BLOCK);

		if(briefSpecificationsBlock == null) {
			String errorMessage = String.format("Unknown Error: briefSpecificationsBlock cannot be null for url=%s", url);
			logger.error(errorMessage);
			return specifications;
		}

		String briefSpecificationsBlockHtml = briefSpecificationsBlock.html();

		if(briefSpecificationsBlockHtml.isEmpty()) {
			String errorMessage = String.format("Unknown Error: rawHtml cannot be null or empty for url=%s", url);
			logger.error(errorMessage);
			return specifications;
		}

		List<String> specificationPerString = utils.preprocessStringArray(briefSpecificationsBlockHtml.split(TAG_BR));
		if(!specificationPerString.isEmpty()) {
			specificationPerString.remove(specificationPerString.size() - 1);
		}

		String deviceName = utils.removeTags(document.title(),"- Specifications");
		populateToObject("devicename", deviceName, specifications);

		for (String specification: specificationPerString) {
			if(specification == null || specification.trim().isEmpty()) {
				continue;
			}
			int splitIndex = specification.indexOf(TAG_B_END);
			if(splitIndex < 0) {
				continue;
			}

			String specificationLabel = specification.substring(0, splitIndex);
			String specificationValue = specification.substring(splitIndex);

			if(specificationLabel.trim().isEmpty()) {
				continue;
			}

			//PREPROCESS KEY TO MATCH BETTER
			specificationLabel = utils.removeTags(specificationLabel.trim().toLowerCase(),
					TAG_B_START, TAG_B_END, SPACE_LINES, COMMA, HYPHEN, SPACE);
			specificationValue = utils.removeTags(specificationValue,
					TAG_B_START, TAG_B_END, SPACE_LINES);

			populateToObject(specificationLabel, specificationValue, specifications);
		}

		logger.debug("fetchForUrl: ended for url={}", url);

		return specifications;
	}

	private void populateToObject(String specificationLabel, String specificationValue, Specifications specifications) {
		switch (specificationLabel) {
			case "devicename":
				specifications.setDeviceName(specificationValue);
				return;
			case "dimensions":
				specifications.setDimensions(specificationValue);
				return;
			case "weight":
				specifications.setWeight(specificationValue);
				return;
			case "soc":
				specifications.setSoc(specificationValue);
				return;
			case "cpu":
				specifications.setCpu(specificationValue);
				return;
			case "camera":
				specifications.setCamera(specificationValue);
				return;
			case "gpu":
				specifications.setGpu(specificationValue);
				return;
			case "ram":
				specifications.setRam(specificationValue);
				return;
			case "storage":
				specifications.setStorage(specificationValue);
				return;
			case "memorycards":
				specifications.setMemoryCards(specificationValue);
				return;
			case "display":
				specifications.setDisplay(specificationValue);
				return;
			case "battery":
				specifications.setBattery(specificationValue);
				return;
			case "os":
				specifications.setOs(specificationValue);
				return;
			case "wifi":
				specifications.setWifi(specificationValue);
				return;
			case "usb":
				specifications.setUsb(specificationValue);
				return;
			case "bluetooth":
				specifications.setBluetooth(specificationValue);
				return;
			case "simcard":
				specifications.setSimCards(specificationValue);
				return;
			case "positioning":
				specifications.setPositioning(specificationValue);
				return;
			default:
				logger.warn("Invalid specificationLabel={} and specificationValue={}", specificationLabel, specificationValue);
		}
	}

}
