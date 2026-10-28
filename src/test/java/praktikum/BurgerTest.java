package praktikum;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.Collections;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.StrictStubs.class)
public class BurgerTest {

    @Mock
    private Bun bun;

    @Mock
    private Ingredient sauce;

    @Mock
    private Ingredient filling;

    @Mock
    private Ingredient cheese;

    private Burger burger;

    @Before
    public void setUp() {
        burger = new Burger();
    }

    @Test
    public void shouldSetBuns() {
        burger.setBuns(bun);

        assertSame(bun, burger.bun);
    }

    @Test
    public void shouldReplaceBuns() {
        Bun replacementBun = mock(Bun.class);
        burger.setBuns(bun);

        burger.setBuns(replacementBun);

        assertSame(replacementBun, burger.bun);
    }

    @Test
    public void shouldAddIngredientsInOrder() {
        burger.addIngredient(sauce);
        burger.addIngredient(filling);

        assertEquals(Arrays.asList(sauce, filling), burger.ingredients);
    }

    @Test
    public void shouldRemoveIngredientAtIndex() {
        burger.addIngredient(sauce);
        burger.addIngredient(filling);
        burger.addIngredient(cheese);

        burger.removeIngredient(1);

        assertEquals(Arrays.asList(sauce, cheese), burger.ingredients);
    }

    @Test
    public void shouldLeaveNoIngredientsWhenOnlyIngredientIsRemoved() {
        burger.addIngredient(sauce);

        burger.removeIngredient(0);

        assertTrue(burger.ingredients.isEmpty());
    }

    @Test
    public void shouldMoveIngredientToHigherIndex() {
        burger.addIngredient(sauce);
        burger.addIngredient(filling);
        burger.addIngredient(cheese);

        burger.moveIngredient(0, 2);

        assertEquals(Arrays.asList(filling, cheese, sauce), burger.ingredients);
    }

    @Test
    public void shouldMoveIngredientToLowerIndex() {
        burger.addIngredient(sauce);
        burger.addIngredient(filling);
        burger.addIngredient(cheese);

        burger.moveIngredient(2, 0);

        assertEquals(Arrays.asList(cheese, sauce, filling), burger.ingredients);
    }

    @Test
    public void shouldKeepIngredientAtSameIndex() {
        burger.addIngredient(sauce);

        burger.moveIngredient(0, 0);

        assertEquals(Collections.singletonList(sauce), burger.ingredients);
    }

    @Test
    public void shouldCalculatePriceForBothBunsWithoutIngredients() {
        when(bun.getPrice()).thenReturn(100.25F);
        burger.setBuns(bun);

        float actualPrice = burger.getPrice();

        assertEquals(200.5F, actualPrice, 0.001F);
        verify(bun).getPrice();
    }

    @Test
    public void shouldCalculatePriceForBothBunsAndAllIngredients() {
        when(bun.getPrice()).thenReturn(100.25F);
        when(sauce.getPrice()).thenReturn(25.5F);
        when(filling.getPrice()).thenReturn(200.75F);
        burger.setBuns(bun);
        burger.addIngredient(sauce);
        burger.addIngredient(filling);

        float actualPrice = burger.getPrice();

        assertEquals(426.75F, actualPrice, 0.001F);
        verify(bun).getPrice();
        verify(sauce).getPrice();
        verify(filling).getPrice();
    }

    @Test
    public void shouldIncludeEachPortionOfSameIngredientInPrice() {
        when(bun.getPrice()).thenReturn(100F);
        when(sauce.getPrice()).thenReturn(25.5F);
        burger.setBuns(bun);
        burger.addIngredient(sauce);
        burger.addIngredient(sauce);

        float actualPrice = burger.getPrice();

        assertEquals(251F, actualPrice, 0.001F);
        verify(sauce, times(2)).getPrice();
    }

    @Test
    public void shouldReturnReceiptWithIngredientsInOrderAndTotalPrice() {
        when(bun.getName()).thenReturn("black bun");
        when(bun.getPrice()).thenReturn(100.25F);
        when(sauce.getName()).thenReturn("hot sauce");
        when(sauce.getType()).thenReturn(IngredientType.SAUCE);
        when(sauce.getPrice()).thenReturn(25.5F);
        when(filling.getName()).thenReturn("cutlet");
        when(filling.getType()).thenReturn(IngredientType.FILLING);
        when(filling.getPrice()).thenReturn(200.75F);
        burger.setBuns(bun);
        burger.addIngredient(sauce);
        burger.addIngredient(filling);
        String expectedReceipt = String.format(
                "(==== black bun ====)%n"
                        + "= sauce hot sauce =%n"
                        + "= filling cutlet =%n"
                        + "(==== black bun ====)%n"
                        + "%nPrice: %f%n", 426.75F);

        String actualReceipt = burger.getReceipt();

        assertEquals(expectedReceipt, actualReceipt);
    }

    @Test
    public void shouldReturnReceiptWithoutIngredients() {
        when(bun.getName()).thenReturn("white bun");
        when(bun.getPrice()).thenReturn(150.75F);
        burger.setBuns(bun);
        String expectedReceipt = String.format(
                "(==== white bun ====)%n"
                        + "(==== white bun ====)%n"
                        + "%nPrice: %f%n", 301.5F);

        String actualReceipt = burger.getReceipt();

        assertEquals(expectedReceipt, actualReceipt);
    }
}
