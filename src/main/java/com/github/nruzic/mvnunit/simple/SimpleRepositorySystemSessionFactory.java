/**
 * Copyright (c) 2020 Nikola Ruzic
 */
package com.github.nruzic.mvnunit.simple;

import java.util.Properties;

import com.github.nruzic.mvnunit.ProxyDefinition;
import com.github.nruzic.mvnunit.RepositorySystemSessionFactory;

import org.eclipse.aether.DefaultRepositorySystemSession;
import org.eclipse.aether.RepositorySystem;
import org.eclipse.aether.RepositorySystemSession;
import org.eclipse.aether.artifact.DefaultArtifactType;
import org.eclipse.aether.collection.DependencyGraphTransformer;
import org.eclipse.aether.collection.DependencyManager;
import org.eclipse.aether.collection.DependencySelector;
import org.eclipse.aether.collection.DependencyTraverser;
import org.eclipse.aether.repository.LocalRepository;
import org.eclipse.aether.repository.LocalRepositoryManager;
import org.eclipse.aether.util.artifact.DefaultArtifactTypeRegistry;
import org.eclipse.aether.util.graph.manager.ClassicDependencyManager;
import org.eclipse.aether.util.graph.transformer.ChainedDependencyGraphTransformer;
import org.eclipse.aether.util.graph.transformer.ConflictResolver;
import org.eclipse.aether.util.graph.transformer.JavaDependencyContextRefiner;
import org.eclipse.aether.util.graph.transformer.JavaScopeDeriver;
import org.eclipse.aether.util.graph.transformer.JavaScopeSelector;
import org.eclipse.aether.util.graph.transformer.NearestVersionSelector;
import org.eclipse.aether.util.graph.transformer.SimpleOptionalitySelector;
import org.eclipse.aether.util.graph.traverser.FatArtifactTraverser;
import org.eclipse.aether.util.repository.DefaultProxySelector;
import org.eclipse.aether.util.repository.SimpleArtifactDescriptorPolicy;

/**
 * @author nruzic
 *
 */
public class SimpleRepositorySystemSessionFactory implements RepositorySystemSessionFactory
{
    private final RepositorySystem repositorySystem;

    private final ProxyDefinition proxyDefinition;

    private final LocalRepository localRepository;

    private final DependencySelector dependencySelector;

    /**
     * Instantiates a new simple repository system session factory.
     *
     * @param repositorySystem
     *            the repository system
     * @param proxyDefinition
     *            the proxy definition
     * @param localRepository
     *            the local repository
     * @param dependencySelector
     *            the dependency selector
     */
    public SimpleRepositorySystemSessionFactory(final RepositorySystem repositorySystem,
            final ProxyDefinition proxyDefinition, final LocalRepository localRepository,
            final DependencySelector dependencySelector)
    {
        this.repositorySystem = repositorySystem;
        this.proxyDefinition = proxyDefinition;
        this.localRepository = localRepository;
        this.dependencySelector = dependencySelector;
    }

    @Override
    public RepositorySystemSession getRepositorySystemSession()
    {
        final DefaultRepositorySystemSession session = new DefaultRepositorySystemSession();

        final DependencyTraverser dependencyTraverser = new FatArtifactTraverser();
        session.setDependencyTraverser(dependencyTraverser);

        final DependencyManager dependencyManager = new ClassicDependencyManager();
        session.setDependencyManager(dependencyManager);
        session.setDependencySelector(this.dependencySelector);

        final DependencyGraphTransformer transformer = new ConflictResolver(new NearestVersionSelector(),
                new JavaScopeSelector(), new SimpleOptionalitySelector(), new JavaScopeDeriver());
        new ChainedDependencyGraphTransformer(transformer, new JavaDependencyContextRefiner());
        session.setDependencyGraphTransformer(transformer);

        final DefaultArtifactTypeRegistry stereotypes = new DefaultArtifactTypeRegistry();
        stereotypes.add(new DefaultArtifactType("pom"));
        stereotypes.add(new DefaultArtifactType("maven-plugin", "jar", "", "java"));
        stereotypes.add(new DefaultArtifactType("jar", "jar", "", "java"));
        stereotypes.add(new DefaultArtifactType("ejb", "jar", "", "java"));
        stereotypes.add(new DefaultArtifactType("ejb-client", "jar", "client", "java"));
        stereotypes.add(new DefaultArtifactType("test-jar", "jar", "tests", "java"));
        stereotypes.add(new DefaultArtifactType("javadoc", "jar", "javadoc", "java"));
        stereotypes.add(new DefaultArtifactType("java-source", "jar", "sources", "java", false, false));
        stereotypes.add(new DefaultArtifactType("war", "war", "", "java", false, true));
        stereotypes.add(new DefaultArtifactType("ear", "ear", "", "java", false, true));
        stereotypes.add(new DefaultArtifactType("rar", "rar", "", "java", false, true));
        stereotypes.add(new DefaultArtifactType("par", "par", "", "java", false, true));
        session.setArtifactTypeRegistry(stereotypes);

        session.setArtifactDescriptorPolicy(new SimpleArtifactDescriptorPolicy(true, true));

        // MNG-5670 guard against ConcurrentModificationException
        final Properties sysProps = new Properties();
        for (final String key : System.getProperties().stringPropertyNames())
        {
            sysProps.put(key, System.getProperty(key));
        }
        session.setSystemProperties(sysProps);
        session.setConfigProperties(sysProps);

        session.setTransferListener(new ConsoleTransferListener());
        session.setRepositoryListener(new ConsoleRepositoryListener());

        // Local repository manager
        final LocalRepositoryManager localRepositoryManager = this.repositorySystem.newLocalRepositoryManager(session,
                this.localRepository);
        session.setLocalRepositoryManager(localRepositoryManager);

        // uncomment to generate dirty trees
        // session.setDependencyGraphTransformer( null );

        // Proxy selector
        if (proxyDefinition != null)
        {
            final DefaultProxySelector proxySelector = new DefaultProxySelector();
            proxySelector.add(proxyDefinition.getProxy(), proxyDefinition.getNonProxyHosts());
            session.setProxySelector(proxySelector);
        }

        return session;
    }

}
