package com.dhemery.aggregator.internal;

import com.dhemery.aggregator.helpers.SourceFile;
import com.dhemery.aggregator.helpers.TestTarget;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;

import javax.lang.model.element.ExecutableElement;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;

import static com.dhemery.aggregator.helpers.SourceFileBuilder.sourceFileForClass;
import static java.lang.String.format;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@RunWith(Parameterized.class)
public class MethodWriterVisitPrimitiveTypeTests extends MethodWriterTestBase {
    @Parameter
    public TestType type;

    @Parameters(name = "{0}")
    public static Collection<TestType> types() {
        return Arrays.asList(
                new TestType("void", ""),
                new TestType("byte", "return 0;"),
                new TestType("char", "return 0;"),
                new TestType("short", "return 0;"),
                new TestType("long", "return 0;"),
                new TestType("float", "return 0;"),
                new TestType("double", "return 0;")
        );
    }

    @Test
    public void primitiveType() throws IOException {
        SourceFile sourceFile = sourceFileForClass("PrimitiveReturnType")
                                        .withLine(format("@%s", TestTarget.class.getName()))
                                        .withLine(format("public static %s returnsPrimitiveType() { %s }", type.name, type.returnStatement))
                                        .build();

        StringBuilder declaration = new StringBuilder();

        process(sourceFile, by(withEachTypeIn(ExecutableElement::getReturnType, declaration::append)));

        assertThat(declaration.toString(), is(type.name));
    }

    private static class TestType {
        final String name;
        final String returnStatement;

        TestType(String name, String returnStatement) {
            this.name = name;
            this.returnStatement = returnStatement;
        }

        @Override
        public String toString() {
            return name;
        }
    }
}