package org.devices.specifications.api.common.fetcher;

import org.devices.specifications.api.common.model.ConnectionConfig;

public abstract class BaseFetcher<T> {
    public abstract T fetchForUrl(final String url, final ConnectionConfig connectionConfig);
}
