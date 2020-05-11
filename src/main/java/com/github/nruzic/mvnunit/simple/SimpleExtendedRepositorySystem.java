/**
 * Copyright (c) 2020 Nikola Ruzic
 */
package com.github.nruzic.mvnunit.simple;

import com.github.nruzic.mvnunit.ExtendedRepositorySystem;

import org.eclipse.aether.impl.RemoteRepositoryManager;
import org.eclipse.aether.internal.impl.DefaultRepositorySystem;

/**
 * @author nruzic
 *
 */
public class SimpleExtendedRepositorySystem extends DefaultRepositorySystem implements ExtendedRepositorySystem
{
    protected RemoteRepositoryManager cachedRemoteRepositoryManager;

    @Override
    public RemoteRepositoryManager getRemoteRepositoryManager()
    {
        return this.cachedRemoteRepositoryManager;
    }

    @Override
    public DefaultRepositorySystem setRemoteRepositoryManager(final RemoteRepositoryManager remoteRepositoryManager)
    {
        this.cachedRemoteRepositoryManager = remoteRepositoryManager;
        return super.setRemoteRepositoryManager(remoteRepositoryManager);
    }

}
