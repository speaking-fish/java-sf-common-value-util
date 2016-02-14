package com.speakingfish.common.value.util;

import static com.speakingfish.common.closeable.Closeables.tryCatchClose;

public abstract class AbstractThreadLocalCloseableSingleton<T> implements SimpleCloseableSingleton<T> {

    protected final ThreadLocal<SimpleCloseableSingleton<T>> _singleton = new ThreadLocal<SimpleCloseableSingleton<T>>();

    public AbstractThreadLocalCloseableSingleton() {
        super();
    }

    protected abstract T create();

    public T get() {
        SimpleCloseableSingleton<T> singleton = _singleton.get();
        if(null == singleton) {
            singleton = new AbstractSimpleCloseableSingleton<T>() {
                @Override protected T create() {
                    return AbstractThreadLocalCloseableSingleton.this.create();
                }

                @Override protected void closeValue(T value) {
                    AbstractThreadLocalCloseableSingleton.this.closeValue(value);
                }
                
            };
            _singleton.set(singleton);
            return singleton.get();
        } else {
            return singleton.get();
        }
    }

    public void close() {
        SimpleCloseableSingleton<T> singleton = _singleton.get();
        if(null != singleton) {
            singleton.close();
        }
    }
    

    protected void closeValue(T value) {
        if(null != value) {
            tryCatchClose(value);
        }
    }
    
    /*
    public void reset(T value) {
        SimpleCloseableSingleton<T> singleton = _singleton.get();
        if(null != singleton) {
            singleton.close();
        }
    }
    */
    
    

}
