/**
 * Copyright (c) 2020 Nikola Ruzic
 */
package com.github.nruzic.mvnunit;

import org.eclipse.aether.repository.Proxy;

/**
 * The Class ProxyDefinition.
 * @author nruzic
 */
public class ProxyDefinition
{
    private Proxy proxy;

    private String nonProxyHosts;

    /**
     * Instantiates a new proxy definition.
     *
     * @param type
     *            the type
     * @param host
     *            the host
     * @param port
     *            the port
     * @param nonProxyHosts
     *            the non proxy hosts
     */
    public ProxyDefinition(final String type, final String host, final int port, final String nonProxyHosts)
    {
        this.proxy = new Proxy(type, host, port);
        this.nonProxyHosts = nonProxyHosts;
    }

    /**
     * @return the proxy
     */
    public Proxy getProxy()
    {
        return proxy;
    }

    /**
     * @param proxy
     *            the proxy to set
     */
    public void setProxy(final Proxy proxy)
    {
        this.proxy = proxy;
    }

    /**
     * @return the nonProxyHosts
     */
    public String getNonProxyHosts()
    {
        return nonProxyHosts;
    }

    /**
     * @param nonProxyHosts
     *            the nonProxyHosts to set
     */
    public void setNonProxyHosts(final String nonProxyHosts)
    {
        this.nonProxyHosts = nonProxyHosts;
    }

}
