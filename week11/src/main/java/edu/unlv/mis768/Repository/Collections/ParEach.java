package edu.unlv.mis768.Repository.Collections;

import java.util.Spliterator;
import java.util.concurrent.CountedCompleter;
import java.util.function.Consumer;

class ParEach<T> extends CountedCompleter<Void> {
    final Spliterator<T> spliterator;
    final Consumer<T> action;
    final long targetBatchSize;

    ParEach(ParEach<T> parent, Spliterator<T> spliterator,
            Consumer<T> action, long targetBatchSize) {
        super(parent);
        this.spliterator = spliterator;
        this.action = action;
        this.targetBatchSize = targetBatchSize;
    }

    public void compute() {
        Spliterator<T> sub;
        while (spliterator.estimateSize() > targetBatchSize &&
                (sub = spliterator.trySplit()) != null) {
            addToPendingCount(1);
            new ParEach<>(this, sub, action, targetBatchSize).fork();
        }
        spliterator.forEachRemaining(action);
        propagateCompletion();
    }
}
