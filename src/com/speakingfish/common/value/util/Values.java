package com.speakingfish.common.value.util;

import com.speakingfish.common.function.Creator;
import com.speakingfish.common.function.Getter;
import com.speakingfish.common.function.ThrowableGetter;

public class Values {

    public static <T> T getOrRethrow(ThrowableGetter<T> origin) {
        try {
            return origin.get();
        } catch(RuntimeException e) {
            throw e;
        } catch(Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    public static <T> Getter<T> rethrowableGetter(final ThrowableGetter<T> origin) {
        return new Getter<T>() {
            @Override public T get() {
                return getOrRethrow(origin);
            }
        };
    }
    
    public static <RESULT, PARAMS> Getter<RESULT> threadLocalCloseableSingleton(final Creator<RESULT, PARAMS> creator, final PARAMS params) {
        return new AbstractThreadLocalCloseableSingleton<RESULT>() {
            @Override protected RESULT create() {
                return creator.create(params);
            }
        };
    }
    
    public static <RESULT, SRC> Getter<RESULT> threadLocalCloseableSingleton(final Creator<RESULT, SRC> creator, final Getter<SRC> getter) {
        return new AbstractThreadLocalCloseableSingleton<RESULT>() {
            @Override protected RESULT create() {
                return creator.create(getter.get());
            }
        };
    }
    
    public static <RESULT, PARAMS> SimpleCloseableSingleton<RESULT> simpleCloseableSingleton(final Creator<RESULT, PARAMS> creator, final PARAMS params) {
        return new AbstractSimpleCloseableSingleton<RESULT>() {
            @Override protected RESULT create() {
                return creator.create(params);
            }
        };
    }
    
    public static <RESULT, PARAMS> SimpleCloseableSingleton<RESULT> lazySimpleCloseableSingleton(final Creator<RESULT, PARAMS> creator, final Getter<PARAMS> paramsGetter) {
        return new AbstractSimpleCloseableSingleton<RESULT>() {
            @Override protected RESULT create() {
                return creator.create(paramsGetter.get());
            }
        };
    }
    
    public static <RESULT, SRC> Getter<RESULT> wrappedSimpleCloseableSingleton(final Creator<RESULT, SRC> creator, final Getter<SRC> getter) {
        return new AbstractSimpleCloseableSingleton<RESULT>() {
            @Override protected RESULT create() {
                return creator.create(getter.get());
            }
        };
    }
    
    public static <RESULT> Getter<RESULT> threadLocalCloseableSingleton(final Creator<RESULT, Void> creator) {
        return Values.threadLocalCloseableSingleton(creator, (Void) null); // Void for java 1.5 compatibility
    }
    
    public static <RESULT> SimpleCloseableSingleton<RESULT> simpleCloseableSingleton(final Creator<RESULT, Void> creator) {
        return simpleCloseableSingleton(creator, null);
    }

    /*
    public static <DEST, SRC> Invoker<SRC> simpleCloseableSingleton(
        final Mapper<DEST, SRC> mapper,
        final Getter<Invoker<DEST>> getter
    ) {
        final SimpleCloseableSingleton<Invoker<SRC>> result = new AbstractSimpleCloseableSingleton<Invoker<SRC>>() {
            @Override protected Invoker<SRC> create() {
                return Invokers.invoker(mapper, getter.get());
            }
            
        };
        return new Invoker<SRC>() {
            @Override public void invoke(SRC value) {
                result.get().invoke(value);
            }};
    }
    */
/*    
    public static <RESULT, SRC> Getter<RESULT> wrappedSimpleCloseableSingleton(final Getter<SRC> getter, final Mapper<RESULT, SRC> mapper) {
        return new AbstractSimpleCloseableSingleton<RESULT>() {
            @Override protected RESULT create() {
                return Getters.mapper(getter.get());
            }
            
        };
    }
  */  
}
