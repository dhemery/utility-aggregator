package com.dhemery;

import java.util.function.Function;

public class
ExampleUtilityMethods {
    @UtilityMarker
    public static Object returnsObject() { return null; }

    @UtilityMarker
    public static <T> T returnsVariableType() { return null; }

    @UtilityMarker
    public static <T extends Number> T returnsVariableTypeWithUpperBound() { return null; }

    @UtilityMarker
    public static <T,R> R hasParametersWithGenericTypes(Function<? super T, ? extends R> function) { return null; }
}
