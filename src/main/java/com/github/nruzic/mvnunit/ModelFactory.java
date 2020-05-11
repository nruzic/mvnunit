/**
 * Copyright (c) 2020 Nikola Ruzic
 */
package com.github.nruzic.mvnunit;

import org.apache.maven.model.Model;

/**
 * @author nruzic
 *
 */
public interface ModelFactory
{

    /**
     * Gets the model.
     *
     * @return the model
     */
    Model getModel();
}
