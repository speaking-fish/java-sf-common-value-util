package com.speakingfish.common.value.util;

import com.speakingfish.common.closeable.Closeable;
import com.speakingfish.common.function.Getter;

public interface SimpleCloseableSingleton<T> extends Getter<T>, Closeable {
    @Override void close();
}
