/**
 * Copyright (c) 2020 Nikola Ruzic
 */
package com.github.nruzic.mvnunit;

/**
 * A factory for creating RepositorySystem objects.
 */
public interface RepositorySystemFactory
{

    /**
     * Gets the repository system.
     *
     * @return the repository system
     */
    ExtendedRepositorySystem getRepositorySystem();
}
