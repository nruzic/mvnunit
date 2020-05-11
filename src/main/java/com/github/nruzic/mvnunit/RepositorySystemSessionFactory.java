/**
 * Copyright (c) 2020 Nikola Ruzic
 */
package com.github.nruzic.mvnunit;

import org.eclipse.aether.RepositorySystemSession;

/**
 * A factory for creating RepositorySystemSession objects.
 */
public interface RepositorySystemSessionFactory
{

    /**
     * Gets the repository system session.
     *
     * @return the repository system session
     */
    RepositorySystemSession getRepositorySystemSession();
}
