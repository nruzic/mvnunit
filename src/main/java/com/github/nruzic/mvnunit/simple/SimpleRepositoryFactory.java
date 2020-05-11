/**
 * Copyright (c) 2020 Nikola Ruzic
 */
package com.github.nruzic.mvnunit.simple;

import java.util.Arrays;
import java.util.List;

import com.github.nruzic.mvnunit.ProxyDefinition;
import com.github.nruzic.mvnunit.RepositoryFactory;

import org.eclipse.aether.repository.LocalRepository;
import org.eclipse.aether.repository.RemoteRepository;

/**
 * A factory for creating SimpleRepository objects.
 */
public class SimpleRepositoryFactory implements RepositoryFactory
{
    private final String localRepoPath;

    private final String remoteRepoId;

    private final String remoteRepoType;

    private final String remoteRepoUrl;

    private final String proxyScheme;

    private final String proxyHost;

    private final int proxyPort;

    private final String nonProxyHosts;

    private final boolean proxyDisable;

    /**
     * Instantiates a new simple repository factory.
     */
    public SimpleRepositoryFactory()
    {
        this(null, "central", "default", "https://repo1.maven.org/maven2/", "http", "localhost", 3128,
                "localhost|127.0.0.1", true);
    }

    /**
     * Instantiates a new simple repository factory.
     *
     * @param localRepoPath
     *            the local repo path
     * @param remoteRepoId
     *            the remote repo id
     * @param remoteRepoType
     *            the remote repo type
     * @param remoteRepoUrl
     *            the remote repo url
     * @param proxyScheme
     *            the proxy scheme
     * @param proxyHost
     *            the proxy host
     * @param proxyPort
     *            the proxy port
     * @param nonProxyHosts
     *            the non proxy hosts
     * @param proxyDisable
     *            the proxy disable
     */
    public SimpleRepositoryFactory(final String localRepoPath, final String remoteRepoId, final String remoteRepoType,
            final String remoteRepoUrl, final String proxyScheme, final String proxyHost, final int proxyPort,
            final String nonProxyHosts, final boolean proxyDisable)
    {
        this.localRepoPath = localRepoPath;
        this.remoteRepoId = remoteRepoId;
        this.remoteRepoType = remoteRepoType;
        this.remoteRepoUrl = remoteRepoUrl;
        this.proxyScheme = proxyScheme;
        this.proxyHost = proxyHost;
        this.proxyPort = proxyPort;
        this.nonProxyHosts = nonProxyHosts;
        this.proxyDisable = proxyDisable;
    }

    @Override
    public LocalRepository getLocalRepository()
    {
        return new LocalRepository(this.getLocalRepoPathWithFallback(this.localRepoPath));
    }

    @Override
    public List<RemoteRepository> getRemoteRepositories()
    {
        // Set repo location
        final RemoteRepository.Builder repositoryBuilder = new RemoteRepository.Builder(this.remoteRepoId,
                this.remoteRepoType, this.remoteRepoUrl);

        // Set repo proxy
        if (this.getProxyDefinition() != null)
        {
            repositoryBuilder.setProxy(this.getProxyDefinition().getProxy());
        }

        return Arrays.asList(repositoryBuilder.build());
    }

    @Override
    public ProxyDefinition getProxyDefinition()
    {
        return this.proxyDisable ? null
                : new ProxyDefinition(this.proxyScheme, this.proxyHost, this.proxyPort, this.nonProxyHosts);
    }

    private String getLocalRepoPathWithFallback(final String localRepoPath)
    {
        return localRepoPath != null ? localRepoPath
                : System.getProperty("java.io.tmpdir") + "/repository-" + System.currentTimeMillis();
    }

}
