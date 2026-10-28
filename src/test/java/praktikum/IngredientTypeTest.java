package praktikum;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.junit.Assert.assertSame;

@RunWith(Parameterized.class)
public class IngredientTypeTest {

    private final String name;
    private final IngredientType expectedType;

    public IngredientTypeTest(String name, IngredientType expectedType) {
        this.name = name;
        this.expectedType = expectedType;
    }

    @Parameterized.Parameters(name = "{index}: type={0}")
    public static Object[][] ingredientTypeData() {
        return new Object[][] {
                {"SAUCE", IngredientType.SAUCE},
                {"FILLING", IngredientType.FILLING}
        };
    }

    @Test
    public void shouldReturnIngredientTypeByName() {
        assertSame(expectedType, IngredientType.valueOf(name));
    }
}
