package Collections;

import java.util.Spliterator;
import java.util.concurrent.CountedCompleter;
import java.util.concurrent.ForkJoinPool;
import java.util.function.Consumer;

class TaggedArray<T> {
   private final Object[] elements; // immutable after construction
   TaggedArray(T[] data, Object[] tags) {
     int size = data.length;
     if (tags.length != size) throw new IllegalArgumentException();
     this.elements = new Object[2 * size];
     for (int i = 0, j = 0; i < size; ++i) {
       elements[j++] = data[i];
       elements[j++] = tags[i];
     }
   }

   public Spliterator<T> spliterator() {
     return new TaggedArraySpliterator<>(elements, 0, elements.length);
   }

   static class TaggedArraySpliterator<T> implements Spliterator<T> {
     private final Object[] array;
     private int origin; // current index, advanced on split or traversal
     private final int fence; // one past the greatest index

     TaggedArraySpliterator(Object[] array, int origin, int fence) {
       this.array = array; this.origin = origin; this.fence = fence;
     }

     public void forEachRemaining(Consumer<? super T> action) {
       for (; origin < fence; origin += 2)
         action.accept((T) array[origin]);
     }

     public boolean tryAdvance(Consumer<? super T> action) {
       if (origin < fence) {
         action.accept((T) array[origin]);
         origin += 2;
         return true;
       }
       else // cannot advance
         return false;
     }

     public Spliterator<T> trySplit() {
       int lo = origin; // divide range in half
       int mid = ((lo + fence) >>> 1) & ~1; // force midpoint to be even
       if (lo < mid) { // split out left half
         origin = mid; // reset this Spliterator's origin
         return new TaggedArraySpliterator<>(array, lo, mid);
       }
       else       // too small to split
         return null;
     }

     public long estimateSize() {
       return (long)((fence - origin) / 2);
     }

     public int characteristics() {
       return ORDERED | SIZED | IMMUTABLE | SUBSIZED;
     }
   }
    static <T> void parEach(TaggedArray<T> a, Consumer<T> action) {
        Spliterator<T> s = a.spliterator();
        long targetBatchSize = s.estimateSize() / (ForkJoinPool.getCommonPoolParallelism() * 8);
        new ParEach(null, s, action, targetBatchSize).invoke();
    }

    static class ParEach<T> extends CountedCompleter<Void> {
        final Spliterator<T> spliterator;
        final Consumer<T> action;
        final long targetBatchSize;

        ParEach(ParEach<T> parent, Spliterator<T> spliterator,
                Consumer<T> action, long targetBatchSize) {
            super(parent);
            this.spliterator = spliterator; this.action = action;
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
 }