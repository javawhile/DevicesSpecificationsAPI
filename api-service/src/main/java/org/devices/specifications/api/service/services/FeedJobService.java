package org.devices.specifications.api.service.services;

import org.devices.specifications.api.common.model.Brand;
import org.devices.specifications.api.common.model.Model;
import org.devices.specifications.api.common.model.Property;
import org.devices.specifications.api.common.model.Specifications;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.Set;

public abstract class FeedJobService {

    private final Logger logger = LoggerFactory.getLogger(FeedJobService.class);

    public abstract Set<Brand> getBrandsSet();
    public abstract Map<Brand, Set<Model>> getModelsByBrands(Set<Brand> brands);
    public abstract boolean isBrandModelExists(Brand brand, Model model);
    public abstract int getIterationsCountPerBlock();
    public abstract long getSleepTimePerBlock();
    public abstract Specifications getSpecifications(Brand brand, Model model);
    public abstract void saveSpecifications(Brand brand, Model model, Specifications specifications);
    public abstract Set<Property> getDetailSpecifications(Brand brand, Model model);
    public abstract void saveDetailSpecifications(Brand brand, Model model, Set<Property> properties);

    protected void log(String message, Object...arguments) {
        logger.info(message, arguments);
    }

    public void startFeedJob() {
        Runnable jobRunnable = this::startFeedJobBackground;
        String jobThreadName = String.format("feedJobThread_%s", System.currentTimeMillis());
        Thread thread = new Thread(jobRunnable, jobThreadName);
        thread.setPriority(Thread.MAX_PRIORITY);
        thread.setDaemon(true);
        thread.start();
        log("feedJob: threadName: {}, threadId: {}, threadPriority: {}", thread.getName(), thread.getId(), thread.getPriority());
    }

    private void startFeedJobBackground() {
        log("feedJob: started");

        if(getIterationsCountPerBlock() < 0) {
            log("feedJob: ended (getIterationsCountPerBlock < 0)");
            return;
        }

        Set<Brand> brands = getBrandsSet();
        log("feedJob: count(brands) count: {}", brands.size());

        Map<Brand, Set<Model>> modelsByBrands = getModelsByBrands(brands);
        log("feedJob: count(modelsByBrands): {}", modelsByBrands.size());

        int iterations = 0;
        for(Brand brand: modelsByBrands.keySet()) {
            log("feedJob: brand={} started", brand.getBrandName());
            for(Model model: modelsByBrands.get(brand)) {
                log("feedJob: model={} started", model.getModelName());
                if(!isBrandModelExists(brand, model)) {
                    log("feedJob: model={} not exists", model.getModelName());
                    saveSpecifications(brand, model, getSpecifications(brand, model));
                    saveDetailSpecifications(brand, model, getDetailSpecifications(brand, model));
                    log("feedJob: model={} saved", model.getModelName());
                    iterations++;
                    if(iterations == getIterationsCountPerBlock()) {
                        log("feedJob: iterations={} exhausted", iterations);
                        iterations = 0;
                        log("feedJob: entering sleep mode for {} ms", getSleepTimePerBlock());
                        sleepThread(getSleepTimePerBlock());
                        log("feedJob: sleep mode exit");
                    }
                } else {
                    log("feedJob: model={} already exists", model.getModelName());
                }
            }
        }

        log("feedJob: ended");
    }

    private void sleepThread(final long sleepTime) {
        try {
            Thread.sleep(sleepTime);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
