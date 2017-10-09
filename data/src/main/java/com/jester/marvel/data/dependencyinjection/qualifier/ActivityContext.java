/*
Created by Helm  25/1/17.
*/


package com.jester.marvel.data.dependencyinjection.qualifier;

import javax.inject.Qualifier;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Qualifier
@Retention(RetentionPolicy.RUNTIME)
public @interface ActivityContext {}
