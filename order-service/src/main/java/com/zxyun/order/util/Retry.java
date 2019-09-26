package com.zxyun.order.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * 重试机制
 *
 * @param <T>
 */
public class Retry<T> {
    private final int maxAttempts;
    private final long delay;
    private final AtomicInteger attempts;
    private final Predicate<Exception> test;
    private final List<Exception> errors;

    @SafeVarargs
    public Retry(
            int maxAttempts,
            long delay,
            Predicate<Exception>... ignoreTests
    ) {
        this.maxAttempts = maxAttempts;
        this.delay = delay;
        this.attempts = new AtomicInteger();
        this.test = Arrays.stream(ignoreTests).reduce(Predicate::or).orElse(e -> false);
        this.errors = new ArrayList<>();
    }


    public List<Exception> errors() {
        return Collections.unmodifiableList(this.errors);
    }


    public int attempts() {
        return this.attempts.intValue();
    }

    public <T,R> R perform(Function<T, R> function, T t) throws Exception {
        do {
            try {
                return function.apply(t);
            } catch (Exception e) {
                this.errors.add(e);

                if (this.attempts.incrementAndGet() >= this.maxAttempts || !this.test.test(e)) {
                    throw e;
                }

                try {
                    Thread.sleep(this.delay);
                } catch (InterruptedException f) {
                    //ignore
                }
            }
        }
        while (true);
    }
}
