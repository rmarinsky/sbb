package com.github.rmarinsky;

import com.github.javafaker.Faker;
import lombok.Data;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static com.github.rmarinsky.SBB.sbb;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class AppendTests {

    private final Faker faker = new Faker();

    @Test
    @DisplayName("E2E test")
    void e2eTest() {
        String targetString = faker.beer().name();
        String actualText = sbb(targetString).t().append(targetString).w().sQuote(targetString).n().dq(targetString).w()
                .add(targetString).bld();

        String expectedString = new StringBuilder()
                .append(targetString).append("\t").append(targetString).append(" ").append("'").append(targetString)
                .append("'").append("\n")
                .append("\"").append(targetString).append("\"").append(" ").append(targetString)
                .toString();

        assertThat(actualText).isEqualTo(expectedString);
    }

    @DisplayName("Test for append methods")
    @ParameterizedTest(name = "{index} => expected={0}, methodName={2}")
    @MethodSource("appendMethods")
    void appendMethodsTests(String expected, Function<SBB, SBB> function, String methodName) {
        String actual = function.apply(sbb()).build();
        assertThat(actual).isEqualTo(expected);
    }

    @DisplayName("Test for wrapper methods")
    @ParameterizedTest(name = "{index} => expected={0}, methodName={2}")
    @MethodSource("wrappersMethods")
    void wrapperMethodsTests(String expectedValue, Function<SBB, SBB> targetFunction, String methodName) {
        String actual = targetFunction.apply(sbb()).build();
        assertThat(actual).isEqualTo(expectedValue);
    }

    @Test
    @DisplayName("Test for bld() method")
    void bldTest() {
        String expected = faker.chuckNorris().fact();
        String actual = sbb(expected).bld();
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("Test for build() method")
    void buildTest() {
        String expected = faker.chuckNorris().fact();
        SBB sbb = sbb(expected);
        String actual = sbb.build();

        assertThat(actual).isEqualTo(expected);

        assertThat(sbb.build().length()).isEqualTo(0);
    }

    @Test
    @DisplayName("Test for append null for append() method and wrapper")
    public void NullIsIgnoredTest() {
        String result = sbb(null).coma().sb(null).dot().bld();
        assertThat(result).isEqualTo(",[].");
    }

    @Test
    @DisplayName("Test for toString() method")
    void toStringTest() {
        String expected = faker.chuckNorris().fact();
        String actual = sbb().append(expected).toString();
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("Test for object appending in the build method")
    void testObjectAppenderTest() {
        Pet targetPet = getFakePet();

        String targetString = sbb().cb(sbb().n()
                .t().add(targetPet).n()
                .t().add(targetPet).n()
                .build()
        ).dot().build();

        String expectedString = new StringBuilder()
                .append("{").append("\n")
                .append("\t").append(targetPet).append("\n")
                .append("\t").append(targetPet).append("\n")
                .append("}")
                .append(".")
                .toString();
        assertThat(targetString).isEqualTo(expectedString);
    }

    @Test
    public void testBuild() {
        SBB sbb = SBB.sbb("test");
        String result = sbb.build();
        // Assert that build method returns the string
        assertEquals("test", result);
        // Assert that StringBuilder instance is empty after build
    }

    @Test
    @DisplayName("Thread safety test - concurrent usage")
    void concurrentUsageTest() throws InterruptedException {
        int threadCount = 100;
        int iterationsPerThread = 1000;
        ExecutorService executor = Executors.newFixedThreadPool(threadCount);
        CountDownLatch startLatch = new CountDownLatch(1);
        CountDownLatch doneLatch = new CountDownLatch(threadCount);
        AtomicInteger successCount = new AtomicInteger(0);
        AtomicInteger failCount = new AtomicInteger(0);

        for (int t = 0; t < threadCount; t++) {
            final int threadId = t;
            executor.submit(() -> {
                try {
                    startLatch.await(); // Wait for all threads to be ready
                    for (int i = 0; i < iterationsPerThread; i++) {
                        String expected = "Thread" + threadId + "-Item" + i;
                        String result = sbb("Thread").append(threadId).append("-Item").append(i).build();
                        if (expected.equals(result)) {
                            successCount.incrementAndGet();
                        } else {
                            failCount.incrementAndGet();
                        }
                    }
                } catch (Exception e) {
                    failCount.incrementAndGet();
                } finally {
                    doneLatch.countDown();
                }
            });
        }

        startLatch.countDown(); // Start all threads simultaneously
        doneLatch.await(30, TimeUnit.SECONDS);
        executor.shutdown();

        int totalOperations = threadCount * iterationsPerThread;
        assertThat(failCount.get())
                .as("No race conditions should occur - all %d operations should succeed", totalOperations)
                .isEqualTo(0);
        assertThat(successCount.get()).isEqualTo(totalOperations);
    }

    @Test
    @DisplayName("Thread safety test - parallel streams")
    void parallelStreamTest() {
        int itemCount = 10000;

        List<String> results = IntStream.range(0, itemCount)
                .parallel()
                .mapToObj(i -> sbb("Item-").append(i).sq("quoted").build())
                .collect(Collectors.toList());

        assertThat(results).hasSize(itemCount);

        // Verify each result has correct format (no interleaving)
        for (int i = 0; i < itemCount; i++) {
            String expected = "Item-" + i + "'quoted'";
            assertThat(results.get(i)).isEqualTo(expected);
        }
    }

    private static Stream<Arguments> appendMethods() {
        return Stream.of(
                Arguments.of("test", (Function<SBB, SBB>) sbb -> sbb.append("test"), "append()"),
                Arguments.of("test", (Function<SBB, SBB>) sbb -> sbb.add("test"), "add()"),
                Arguments.of("test", (Function<SBB, SBB>) sbb -> sbb.join("test"), "join()"),
                Arguments.of("\n", (Function<SBB, SBB>) SBB::n, "n"),
                Arguments.of("\t", (Function<SBB, SBB>) SBB::t, "t"),
                Arguments.of(" ", (Function<SBB, SBB>) SBB::w, "w")
        );
    }

    private static Stream<Arguments> wrappersMethods() {
        return Stream.of(
                Arguments.of("'test'", (Function<SBB, SBB>) sbb -> sbb.sQuote("test"), "sQuote()"),
                Arguments.of("'test'", (Function<SBB, SBB>) sbb -> sbb.sq("test"), "sq()"),
                Arguments.of("\"test\"", (Function<SBB, SBB>) sbb -> sbb.dQuote("test"), "dQuote()"),
                Arguments.of("\"test\"", (Function<SBB, SBB>) sbb -> sbb.dq("test"), "dq()"),
                Arguments.of("[test]", (Function<SBB, SBB>) sbb -> sbb.squareBrackets("test"), "squareBrackets()"),
                Arguments.of("[test]", (Function<SBB, SBB>) sbb -> sbb.sb("test"), "sb"),
                Arguments.of("{test}", (Function<SBB, SBB>) sbb -> sbb.curlyBrackets("test"), "curlyBrackets()"),
                Arguments.of("{test}", (Function<SBB, SBB>) sbb -> sbb.cb("test"), "cb"),
                Arguments.of("(test)", (Function<SBB, SBB>) sbb -> sbb.parentheses("test"), "parentheses()"),
                Arguments.of("(test)", (Function<SBB, SBB>) sbb -> sbb.p("test"), "p"),
                Arguments.of("<test>", (Function<SBB, SBB>) sbb -> sbb.angleBrackets("test"), "angleBrackets()"),
                Arguments.of("<test>", (Function<SBB, SBB>) sbb -> sbb.ab("test"), "ab()")
        );
    }

    private Pet getFakePet() {
        List<Pet.Categories> categories = new ArrayList<>();
        categories.add(new Pet.Categories(faker.number().randomNumber(), faker.funnyName().name()));
        categories.add(new Pet.Categories(faker.number().randomNumber(), faker.funnyName().name()));
        return new Pet(
                faker.number().randomNumber(), faker.animal().name(), faker.dog().breed(), categories, Pet.Color.RED);
    }


    @Data
    private static class Pet {

        private final Long id;
        private final String name;
        private final String type;
        private final List<Categories> categories;
        private final Color color;

        @Data
        private static class Categories {

            private final Long id;
            private final String name;

        }

        private enum Color {
            RED, GREEN, BLUE
        }

    }

}
