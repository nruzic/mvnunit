/**
 * Copyright (c) 2020 Nikola Ruzic
 */
package com.github.nruzic.mvnunit;

import org.apache.maven.model.resolution.ModelResolver;

/**
 * A factory for creating ModelResolver objects.
 */
public interface ModelResolverFactory
{

    /**
     * Gets the model resolver.
     *
     * @return the model resolver
     */
    ModelResolver getModelResolver();

}
