package com.bankapplication.arch;

import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;

import static com.tngtech.archunit.library.Architectures.layeredArchitecture;

@AnalyzeClasses(packages = "com.bankapplication.bankservice")
public class ArchUnitTest {

    private static final String ROOT_PACKAGE = "com.bankapplication.bankservice.";


    @ArchTest
    public static final ArchRule hexArchRule =
            layeredArchitecture()
                    .consideringOnlyDependenciesInLayers()
                    .layer("Core")
                    .definedBy(ROOT_PACKAGE + "core..")
                    .layer("Driven Adapter")
                    .definedBy(ROOT_PACKAGE + "out..")
                    .layer("Driver Adapter")
                    .definedBy(ROOT_PACKAGE + "in..")
//                                        .layer("Common")
//                                        .definedBy(ROOT_PACKAGE + "common..")
                    .whereLayer("Core")
                    .mayNotAccessAnyLayer()
                    .whereLayer("Driven Adapter")
                    .mayOnlyAccessLayers("Core")
                    .whereLayer("Driver Adapter")
                    .mayOnlyAccessLayers("Core");
}
