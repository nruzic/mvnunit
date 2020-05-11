/**
 * Copyright (c) 2020 Nikola Ruzic
 */
package com.github.nruzic.mvnunit.simple;

import java.io.File;

import com.github.nruzic.mvnunit.ModelFactory;
import com.github.nruzic.mvnunit.RepositoryFactory;
import com.github.nruzic.mvnunit.RepositorySystemFactory;

import org.apache.maven.model.Model;
import org.apache.maven.model.building.DefaultModelBuilderFactory;
import org.apache.maven.model.building.DefaultModelBuildingRequest;
import org.apache.maven.model.building.ModelBuilder;
import org.apache.maven.model.building.ModelBuildingException;
import org.apache.maven.model.building.ModelBuildingRequest;
import org.apache.maven.model.resolution.ModelResolver;

/**
 * @author nruzic
 *
 */
public class SimpleModelFactory implements ModelFactory
{
    private final RepositoryFactory repositoryFactory;

    private final RepositorySystemFactory repositorySystemFactory;

    public SimpleModelFactory()
    {
        this(new SimpleRepositoryFactory(), new SimpleRepositorySystemFactory());
    }

    public SimpleModelFactory(final RepositoryFactory repositoryFactory)
    {
        this(repositoryFactory, new SimpleRepositorySystemFactory());
    }

    public SimpleModelFactory(final RepositorySystemFactory repositorySystemFactory)
    {
        this(new SimpleRepositoryFactory(), repositorySystemFactory);
    }

    public SimpleModelFactory(
            final RepositoryFactory repositoryFactory,
            final RepositorySystemFactory repositorySystemFactory)
    {
        this.repositoryFactory = repositoryFactory;
        this.repositorySystemFactory = repositorySystemFactory;
    }

    @Override
    public Model getModel()
    {
        final SimpleModelResolverFactory modelResolverFactory = new SimpleModelResolverFactory(
               this.repositoryFactory, this.repositorySystemFactory);
        final ModelResolver modelResolver = modelResolverFactory.getModelResolver();

        final ModelBuildingRequest modelBuildingRequest = new DefaultModelBuildingRequest();
        modelBuildingRequest.setPomFile(new File("pom.xml"));
        modelBuildingRequest.setProcessPlugins(false);
        modelBuildingRequest.setValidationLevel(ModelBuildingRequest.VALIDATION_LEVEL_MINIMAL);
        modelBuildingRequest.setSystemProperties(System.getProperties());
        modelBuildingRequest.setModelResolver(modelResolver);

        final DefaultModelBuilderFactory modelBuilderFactory = new DefaultModelBuilderFactory();
        final ModelBuilder modelBuilder = modelBuilderFactory.newInstance();
        Model model = null;
        try
        {
            model = modelBuilder.build(modelBuildingRequest).getEffectiveModel();
        }
        catch (final ModelBuildingException e)
        {
            throw new RuntimeException(e);
        }
        return model;
    }
}
