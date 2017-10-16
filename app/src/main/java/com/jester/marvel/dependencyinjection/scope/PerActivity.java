/*
Created by Helm  25/1/17.
*/


package com.jester.marvel.dependencyinjection.scope;

import javax.inject.Scope;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Scope
@Retention(RetentionPolicy.RUNTIME)
public @interface PerActivity {}

