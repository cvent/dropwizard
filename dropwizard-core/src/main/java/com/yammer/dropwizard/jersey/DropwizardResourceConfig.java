package com.yammer.dropwizard.jersey;

import com.sun.jersey.api.core.ScanningResourceConfig;
import com.yammer.dropwizard.jersey.caching.CacheControlledResourceMethodDispatchAdapter;
import com.yammer.metrics.jersey.InstrumentedResourceMethodDispatchAdapter;

public class DropwizardResourceConfig extends ScanningResourceConfig {

    public DropwizardResourceConfig(boolean testOnly) {
        super();
        getFeatures().put(FEATURE_DISABLE_WADL, Boolean.TRUE);
        if (!testOnly) {

// Removed default mappers provided in 0.6 that force responses in HTML format instead of JSON            
//            getSingletons().add(new LoggingExceptionMapper<Throwable>() {});
//            getSingletons().add(new InvalidEntityExceptionMapper());
//            getSingletons().add(new JsonProcessingExceptionMapper());
            // create a subclass to pin it to Throwable
            getSingletons().add(new LoggingExceptionMapper<Throwable>() {
            });
            getSingletons().add(new InvalidEntityExceptionMapper());
            getSingletons().add(new ConstraintViolationExceptionMapper());
            getSingletons().add(new JsonProcessingExceptionMapper());
            getSingletons().add(new UnhandledExcpetionToJSONMapper());
        }
        getClasses().add(InstrumentedResourceMethodDispatchAdapter.class);
        getClasses().add(CacheControlledResourceMethodDispatchAdapter.class);
        getClasses().add(OptionalResourceMethodDispatchAdapter.class);
        getClasses().add(OptionalQueryParamInjectableProvider.class);
    }
}
