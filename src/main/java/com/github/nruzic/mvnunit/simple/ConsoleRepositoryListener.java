/**
 * Copyright (c) 2020 Nikola Ruzic
 */
package com.github.nruzic.mvnunit.simple;

import java.io.PrintStream;

import org.eclipse.aether.AbstractRepositoryListener;
import org.eclipse.aether.RepositoryEvent;

/**
 * A simplistic repository listener that logs events to the console.
 */
public class ConsoleRepositoryListener extends AbstractRepositoryListener
{

    private final PrintStream out;

    /**
     * Instantiates a new console repository listener.
     */
    public ConsoleRepositoryListener()
    {
        this(null);
    }

    /**
     * Instantiates a new console repository listener.
     *
     * @param out
     *            the out
     */
    public ConsoleRepositoryListener(final PrintStream out)
    {
        this.out = (out != null) ? out : System.out;
    }

    @Override
    public void artifactDeployed(final RepositoryEvent event)
    {
        out.println("Deployed " + event.getArtifact() + " to " + event.getRepository());
    }

    @Override
    public void artifactDeploying(final RepositoryEvent event)
    {
        out.println("Deploying " + event.getArtifact() + " to " + event.getRepository());
    }

    @Override
    public void artifactDescriptorInvalid(final RepositoryEvent event)
    {
        out.println(
                "Invalid artifact descriptor for " + event.getArtifact() + ": " + event.getException().getMessage());
    }

    @Override
    public void artifactDescriptorMissing(final RepositoryEvent event)
    {
        out.println("Missing artifact descriptor for " + event.getArtifact());
    }

    @Override
    public void artifactInstalled(final RepositoryEvent event)
    {
        out.println("Installed " + event.getArtifact() + " to " + event.getFile());
    }

    @Override
    public void artifactInstalling(final RepositoryEvent event)
    {
        out.println("Installing " + event.getArtifact() + " to " + event.getFile());
    }

    @Override
    public void artifactResolved(final RepositoryEvent event)
    {
        out.println("Resolved artifact " + event.getArtifact() + " from " + event.getRepository());
    }

    @Override
    public void artifactDownloading(final RepositoryEvent event)
    {
        out.println("Downloading artifact " + event.getArtifact() + " from " + event.getRepository());
    }

    @Override
    public void artifactDownloaded(final RepositoryEvent event)
    {
        out.println("Downloaded artifact " + event.getArtifact() + " from " + event.getRepository());
    }

    @Override
    public void artifactResolving(final RepositoryEvent event)
    {
        out.println("Resolving artifact " + event.getArtifact());
    }

    @Override
    public void metadataDeployed(final RepositoryEvent event)
    {
        out.println("Deployed " + event.getMetadata() + " to " + event.getRepository());
    }

    @Override
    public void metadataDeploying(final RepositoryEvent event)
    {
        out.println("Deploying " + event.getMetadata() + " to " + event.getRepository());
    }

    @Override
    public void metadataInstalled(final RepositoryEvent event)
    {
        out.println("Installed " + event.getMetadata() + " to " + event.getFile());
    }

    @Override
    public void metadataInstalling(final RepositoryEvent event)
    {
        out.println("Installing " + event.getMetadata() + " to " + event.getFile());
    }

    @Override
    public void metadataInvalid(final RepositoryEvent event)
    {
        out.println("Invalid metadata " + event.getMetadata());
    }

    @Override
    public void metadataResolved(final RepositoryEvent event)
    {
        out.println("Resolved metadata " + event.getMetadata() + " from " + event.getRepository());
    }

    @Override
    public void metadataResolving(final RepositoryEvent event)
    {
        out.println("Resolving metadata " + event.getMetadata() + " from " + event.getRepository());
    }

}
