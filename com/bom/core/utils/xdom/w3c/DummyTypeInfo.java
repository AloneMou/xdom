package com.bom.core.utils.xdom.w3c;

import org.w3c.dom.TypeInfo;

public class DummyTypeInfo implements TypeInfo {

    private static final DummyTypeInfo INSTANCE = new DummyTypeInfo();

    public static DummyTypeInfo getInstance(){
        return INSTANCE;
    }

    @Override
    public String getTypeName() {
        return null;
    }

    @Override
    public String getTypeNamespace() {
        return null;
    }

    @Override
    public boolean isDerivedFrom(String typeNamespaceArg, String typeNameArg, int derivationMethod) {
        return false;
    }
}
