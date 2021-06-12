package ru.job4j.exam5;

import org.junit.Test;

import java.util.*;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.*;

public class LogicTest {

    @Test
    public void whenEmailEqualThenMerge() {
        Map<String, Set<String>> input = new HashMap<>();
        input.put("user1", new LinkedHashSet<>(Arrays.asList("foo@gmail.com", "boo@gmail.com")));
        input.put("user2", new LinkedHashSet<>(Arrays.asList("foo@gmail.com", "boo@gmail.com", "zoo@gmail.com")));
        Map<String, Set<String>> out = new HashMap<>();
        out.put("user1", new LinkedHashSet<>(Arrays.asList("foo@gmail.com", "boo@gmail.com", "zoo@gmail.com")));
        assertThat(Logic.merge(input), is(out));
    }

    @Test
    public void whenAllSame() {
        Map<String, Set<String>> input = new HashMap<>();
        input.put("user1", new LinkedHashSet<>(Arrays.asList("foo@gmail.com", "hoo@gmail.com", "zoo@gmail.com")));
        input.put("user2", new LinkedHashSet<>(Arrays.asList("foo@gmail.com", "hoo@gmail.com", "zoo@gmail.com")));
        Map<String, Set<String>> out = Logic.merge(input);
        assertThat(out.get("user2"), nullValue());
    }

}