package com.dhemery.aggregator.helpers;

import javax.lang.model.element.Element;
import javax.lang.model.type.*;
import java.util.Collections;
import java.util.List;


public class FakeDeclaredType implements FakeAnnotatedConstruct, DeclaredType {
    private final String name;

    public FakeDeclaredType(String name) {
        this.name = name;
    }

    @Override
    public <R, P> R accept(TypeVisitor<R, P> v, P p) {
        return v.visitDeclared(this, p);
    }

    @Override
    public Element asElement() {
        throw new UnsupportedOperationException();
    }

    @Override
    public TypeMirror getEnclosingType() {
        throw new UnsupportedOperationException();
    }

    @Override
    public TypeKind getKind() {
        return TypeKind.DECLARED;
    }

    @Override
    public List<? extends TypeMirror> getTypeArguments() {
        return Collections.emptyList();
    }
}