package ua.marinsky;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static ua.marinsky.SBB.sbb;

public class AppendTests {

    @Test
    @DisplayName("Append text")
    void appendText() {
        String targetString = "appended";
        String actualText = sbb().append(targetString).bld();

        Assertions.assertThat(targetString).isEqualTo(actualText);
    }

}
