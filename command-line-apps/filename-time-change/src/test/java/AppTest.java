import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class AppTest {

    @Test
    void changeDateTimeTest() {
        assertThat(App.changeDateTime("20220416_235242", 40))
                .isEqualTo("20220416_235322");
        assertThat(App.changeDateTime("20220416_235132", -40))
                .isEqualTo("20220416_235052");
        assertThat(App.changeDateTime("20220416_235948", 40))
                .isEqualTo("20220417_000028");
        assertThat(App.changeDateTime("20220416_235948", 1200))
                .isEqualTo("20220417_001948");
    }
}
