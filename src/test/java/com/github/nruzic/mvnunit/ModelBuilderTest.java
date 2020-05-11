/**
 * Copyright (c) 2020 Nikola Ruzic
 */
package com.github.nruzic.mvnunit;

import java.io.File;

import com.github.nruzic.mvnunit.simple.SimpleModelResolverFactory;
import com.github.nruzic.mvnunit.simple.SimpleRepositoryFactory;
import com.github.nruzic.mvnunit.simple.SimpleRepositorySystemFactory;

import org.apache.maven.model.Model;
import org.apache.maven.model.building.DefaultModelBuilderFactory;
import org.apache.maven.model.building.DefaultModelBuildingRequest;
import org.apache.maven.model.building.ModelBuilder;
import org.apache.maven.model.building.ModelBuildingRequest;
import org.apache.maven.model.resolution.ModelResolver;
import org.junit.Test;

/**
 * The Class ModelBuilderTest.
 */
public class ModelBuilderTest
{
    @Test
    public void testAlternative() throws Exception
    {
        ModelBuildingRequest modelBuildingRequest = new DefaultModelBuildingRequest();
        modelBuildingRequest.setPomFile(new File("pom-test.xml"));
        modelBuildingRequest.setProcessPlugins(false);
        modelBuildingRequest.setValidationLevel(ModelBuildingRequest.VALIDATION_LEVEL_MINIMAL);

        DefaultModelBuilderFactory modelBuilderFactory = new DefaultModelBuilderFactory();
        ModelBuilder modelBuilder = modelBuilderFactory.newInstance();
        Model model = modelBuilder.build(modelBuildingRequest).getEffectiveModel();
        System.out.println(model);
    }

    @Test
    public void testAlternative2() throws Exception
    {
        SimpleModelResolverFactory modelResolverFactory = new SimpleModelResolverFactory(
                new SimpleRepositoryFactory(), new SimpleRepositorySystemFactory());
        ModelResolver modelResolver = modelResolverFactory.getModelResolver();

        ModelBuildingRequest modelBuildingRequest = new DefaultModelBuildingRequest();
        modelBuildingRequest.setPomFile(new File("pom.xml"));
        modelBuildingRequest.setProcessPlugins(false);
        modelBuildingRequest.setValidationLevel(ModelBuildingRequest.VALIDATION_LEVEL_MINIMAL);
        modelBuildingRequest.setSystemProperties(System.getProperties());
        modelBuildingRequest.setModelResolver(modelResolver);

        DefaultModelBuilderFactory modelBuilderFactory = new DefaultModelBuilderFactory();
        ModelBuilder modelBuilder = modelBuilderFactory.newInstance();
        Model model = modelBuilder.build(modelBuildingRequest).getEffectiveModel();
        System.out.println(model);
    }
}
