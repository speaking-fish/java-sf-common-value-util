package com.speakingfish.common.value.util;

import static com.speakingfish.common.closeable.Closeables.*;

public abstract class AbstractSimpleCloseableSingleton<T> extends AbstractSimpleSingleton<T> implements SimpleCloseableSingleton<T> {
    
    protected void closeValue(T value) {
        if(null != value) {
            tryCatchClose(value);
        }
    }
    
    @Override public void close() {
        if(_retrieved) {
            _retrieved = false;
            T temp = _value;
            _value = null;
            closeValue(temp);
        }
    }
    
    @Override protected void finalize() throws Throwable {
        super.finalize();
        close();
    }

}
