package com.daking.lottery.api;

import org.reactivestreams.Publisher;

import io.reactivex.Flowable;
import io.reactivex.functions.Function;

public class JavaTest {

    public void testavaMethod(){
        Flowable.just(0)
                .retryWhen(new Function<Flowable<Throwable>, Publisher<?>>() {
                    @Override
                    public Publisher<?> apply(Flowable<Throwable> throwableFlowable) throws Exception {
                        return throwableFlowable;
                    }
                }).subscribe();
    }
}
