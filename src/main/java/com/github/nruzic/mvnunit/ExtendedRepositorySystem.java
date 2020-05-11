/**
 * Copyright (c) 2020 Nikola Ruzic
 */
package com.github.nruzic.mvnunit;

import org.eclipse.aether.RepositorySystem;
import org.eclipse.aether.impl.RemoteRepositoryManager;

/**
 * @author nruzic
 *
 */
public interface ExtendedRepositorySystem extends RepositorySystem
{

    /**
     * Gets the remote repository manager.
     *
     * @return the remote repository manager
     */
    RemoteRepositoryManager getRemoteRepositoryManager();
}
