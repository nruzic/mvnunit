/**
 * Copyright (c) 2020 Nikola Ruzic
 */
package com.github.nruzic.mvnunit.simple;

import java.util.List;

import com.github.nruzic.mvnunit.ExtendedRepositorySystem;
import com.github.nruzic.mvnunit.ModelResolverFactory;
import com.github.nruzic.mvnunit.ProxyDefinition;
import com.github.nruzic.mvnunit.RepositoryFactory;
import com.github.nruzic.mvnunit.RepositorySystemFactory;

import org.apache.maven.model.resolution.ModelResolver;
import org.apache.maven.project.ProjectBuildingRequest.RepositoryMerging;
import org.apache.maven.project.ProjectModelResolver;
import org.eclipse.aether.RepositorySystemSession;
import org.eclipse.aether.RequestTrace;
import org.eclipse.aether.repository.LocalRepository;
import org.eclipse.aether.repository.RemoteRepository;
import org.eclipse.aether.util.graph.selector.AndDependencySelector;
import org.eclipse.aether.util.graph.selector.ExclusionDependencySelector;
import org.eclipse.aether.util.graph.selector.OptionalDependencySelector;
import org.eclipse.aether.util.graph.selector.ScopeDependencySelector;

/**
 * @author nruzic
 *
 */
public class SimpleModelResolverFactory implements ModelResolverFactory
{
    private final List<RemoteRepository> remoteRepositories;

    private final LocalRepository localRepository;

    private final ProxyDefinition proxyDefinition;

    private final RepositoryMerging repositoryMerging;

    private final RequestTrace requestTrace;

    private final ExtendedRepositorySystem repositorySystem;

    private final RepositorySystemSession repositorySystemSession;

    /**
     * Instantiates a new simple model resolver factory.
     *
     * @param repositoryFactory
     *            the repository factory
     * @param repositorySystemFactory
     *            the repository system factory
     */
    public SimpleModelResolverFactory(final RepositoryFactory repositoryFactory,
            final RepositorySystemFactory repositorySystemFactory)
    {
        // Repositories
        this.remoteRepositories = repositoryFactory.getRemoteRepositories();
        this.localRepository = repositoryFactory.getLocalRepository();
        this.proxyDefinition = repositoryFactory.getProxyDefinition();

        // Repository system
        this.repositorySystem = repositorySystemFactory.getRepositorySystem();

        // Repository system session
        this.repositorySystemSession = new SimpleRepositorySystemSessionFactory(this.repositorySystem,
                this.proxyDefinition, this.localRepository,
                new AndDependencySelector(new ScopeDependencySelector("test", "provided"),
                        new OptionalDependencySelector(), new ExclusionDependencySelector()))
                                .getRepositorySystemSession();

        // Repository preference
        this.repositoryMerging = RepositoryMerging.REQUEST_DOMINANT;

        // Tracing system
        this.requestTrace = new RequestTrace(null);
    }

    @Override
    public ModelResolver getModelResolver()
    {
        final ProjectModelResolver modelResolver = new ProjectModelResolver(this.repositorySystemSession,
                this.requestTrace, this.repositorySystem, this.repositorySystem.getRemoteRepositoryManager(),
                this.remoteRepositories, this.repositoryMerging, null);
        return modelResolver;
    }
}
