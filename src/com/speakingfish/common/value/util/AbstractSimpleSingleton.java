package com.speakingfish.common.value.util;

import com.speakingfish.common.function.Getter;

public abstract class AbstractSimpleSingleton<T> implements Getter<T> {

    protected boolean _retrieved = false;
    protected T       _value     = null ;

    protected abstract T create();

    public T get() {
        if(!_retrieved) {
            _value = create();
            _retrieved = true;
        }
        return _value;
    }
    
    public boolean retrieved() { return _retrieved; }
    public T       value    () { return _value    ; }

}
