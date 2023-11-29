package com.github.marinsky;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.github.marinsky.SBB.sbb;

public class AppendTests {

    @Test
    @DisplayName("E2E test")
    void e2eTest() {
        String targetString = "appended";
        String actualText = sbb(targetString).t().append(targetString).w().sQuote(targetString)
                .n().dq(targetString).w().add(targetString).bld();

        Assertions.assertThat(actualText).isEqualTo("appended\tappended 'appended'\n\"appended\" appended");
    }

}
