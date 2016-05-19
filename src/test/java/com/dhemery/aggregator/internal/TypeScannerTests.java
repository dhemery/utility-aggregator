package com.dhemery.aggregator.internal;

import com.dhemery.aggregator.helpers.*;
import org.junit.Test;

import javax.lang.model.element.Element;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import static com.dhemery.aggregator.helpers.SourceFileBuilder.sourceFileForClass;
import static java.lang.String.format;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;

public class TypeScannerTests extends ProcessorTestBase {
    private final TypeScanner scanner = new TypeScanner();

    @Test
    public void reportsDeclaredReturnReturnType() throws IOException {
        SourceFile sourceFile = sourceFileForClass("Simple")
                                        .withLine(format("@%s", TestTarget.class.getName()))
                                        .withLine("public static java.nio.file.Path makeAPath() { return null; }")
                                        .build();

        Set<String> scannedTypes = scanTypesUsedIn(sourceFile);

        assertThat(scannedTypes, containsInAnyOrder("java.nio.file.Path"));
    }

    private Set<String> scanTypesUsedIn(SourceFile sourceFile) throws IOException {
        Set<String> scannedTypes = new HashSet<>();

        process(sourceFile, by(scanningMethods(scanner, scannedTypes)));

        return scannedTypes;
    }

    private RoundProcessor scanningMethods(TypeScanner scanned, Set<String> scannedTypes) {
        return (annotations, roundEnvironment, processingEnv) -> {
            roundEnvironment.getElementsAnnotatedWith(TestTarget.class).stream()
                    .map(Element::asType)
                    .forEach(e -> e.accept(scanned, scannedTypes::add));
            return false;
        };
    }
}