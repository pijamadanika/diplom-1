package praktikum;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class BunTest {

    private final String name;
    private final float price;
    private Bun bun;

    public BunTest(String name, float price) {
        this.name = name;
        this.price = price;
    }

    @Parameterized.Parameters(name = "{index}: name={0}, price={1}")
    public static Object[][] bunData() {
        return new Object[][] {
                {"black bun", 100F},
                {"white bun", 150.75F},
                {"free bun", 0F}
        };
    }

    @Before
    public void setUp() {
        bun = new Bun(name, price);
    }

    @Test
    public void shouldReturnBunName() {
        assertEquals(name, bun.getName());
    }

    @Test
    public void shouldReturnBunPrice() {
        assertEquals(price, bun.getPrice(), 0.001F);
    }
}
