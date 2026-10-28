package praktikum;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

@RunWith(Parameterized.class)
public class IngredientTest {

    private final IngredientType type;
    private final String name;
    private final float price;
    private Ingredient ingredient;

    public IngredientTest(IngredientType type, String name, float price) {
        this.type = type;
        this.name = name;
        this.price = price;
    }

    @Parameterized.Parameters(name = "{index}: type={0}, name={1}, price={2}")
    public static Object[][] ingredientData() {
        return new Object[][] {
                {IngredientType.SAUCE, "hot sauce", 25.5F},
                {IngredientType.FILLING, "cutlet", 200F},
                {IngredientType.SAUCE, "free sauce", 0F}
        };
    }

    @Before
    public void setUp() {
        ingredient = new Ingredient(type, name, price);
    }

    @Test
    public void shouldReturnIngredientPrice() {
        assertEquals(price, ingredient.getPrice(), 0.001F);
    }

    @Test
    public void shouldReturnIngredientName() {
        assertEquals(name, ingredient.getName());
    }

    @Test
    public void shouldReturnIngredientType() {
        assertSame(type, ingredient.getType());
    }
}
