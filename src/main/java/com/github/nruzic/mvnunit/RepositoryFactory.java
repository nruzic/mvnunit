/**
 * Copyright (c) 2020 Nikola Ruzic
 */
package com.github.nruzic.mvnunit;

import java.util.List;

import org.eclipse.aether.repository.LocalRepository;
import org.eclipse.aether.repository.RemoteRepository;

/**
 * @author nruzic
 *
 */
public interface RepositoryFactory
{

    /**
     * Gets the local repository.
     *
     * @return the local repository
     */
    LocalRepository getLocalRepository();

    /**
     * Gets the remote repositories.
     *
     * @return the remote repositories
     */
    List<RemoteRepository> getRemoteRepositories();

    /**
     * Gets the proxy definition.
     *
     * @return the proxy definition
     */
    ProxyDefinition getProxyDefinition();
}
