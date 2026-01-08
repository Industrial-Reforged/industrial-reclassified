package com.portingdeadmods.examplemod.api.capabilities;

import java.util.function.Consumer;

public interface StorageChangedListener {
    void setOnChangedFunction(Consumer<Integer> onChangedFunction);
}