/**
 * Copyright (c) 2020 Nikola Ruzic
 */
package com.github.nruzic.mvnunit;

/**
 * A factory for creating ProxyDefinition objects.
 */
public interface ProxyDefinitionFactory
{

    /**
     * Gets the proxy definition.
     *
     * @return the proxy definition
     */
    ProxyDefinition getProxyDefinition();
}
