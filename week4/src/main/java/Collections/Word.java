package Collections;

import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import static Collections.Alphabetas.*;

public class Word {
    final List<Alphabetas> value;  // immutable after construction

    Word(Alphabetas[] value) {
        this.value = Arrays.asList(value);
    }

    public static Word fromString(CharSequence word) {
        Alphabetas[] value = new Alphabetas[word.length()];
        for (int i = 0; i < value.length; i++) {
          value[i] = AlphabetaUtils.fromChar(word.charAt(i));
        }
        return new Word(value);
    }
    @Override
    public String toString() {
        return  this.value
                .stream()
                .map(Enum::toString)
                .reduce(String::concat)
                .orElse("");
    }

}
