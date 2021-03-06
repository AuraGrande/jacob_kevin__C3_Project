import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RestaurantTest {

    Restaurant restaurant;
    //REFACTOR ALL THE REPEATED LINES OF CODE
    @BeforeEach
    public void create_Restaurant_Obj(){
        LocalTime openingTime = LocalTime.parse("10:30:00");
        LocalTime closingTime = LocalTime.parse("22:00:00");
        restaurant = new Restaurant("Amelie's cafe","Chennai",openingTime,closingTime);
        restaurant.addToMenu("Sweet corn soup",119);
        restaurant.addToMenu("Vegetable lasagne", 269);
    }

    //>>>>>>>>>>>>>>>>>>>>>>>>>OPEN/CLOSED<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    //-------FOR THE 2 TESTS BELOW, YOU MAY USE THE CONCEPT OF MOCKING, IF YOU RUN INTO ANY TROUBLE

    @Test
    public void is_restaurant_open_should_return_true_if_time_is_between_opening_and_closing_time(){
        //WRITE UNIT TEST CASE HERE
        Restaurant restoSpy = Mockito.spy(restaurant);
        Mockito.when(restoSpy.getCurrentTime()).thenReturn(LocalTime.parse("10:30:01"),LocalTime.parse("21:59:59"),LocalTime.parse("13:31:00"));
        //A second after opening
        assertTrue(restoSpy.isRestaurantOpen());
        //A second before closing
        assertTrue(restoSpy.isRestaurantOpen());
        //In between the timings
        assertTrue(restoSpy.isRestaurantOpen());
    }

    @Test
    public void is_restaurant_open_should_return_false_if_time_is_outside_opening_and_closing_time(){
        //WRITE UNIT TEST CASE HERE
        Restaurant restoSpy = Mockito.spy(restaurant);
        Mockito.when(restoSpy.getCurrentTime()).thenReturn(LocalTime.parse("10:29:59"),LocalTime.parse("22:00:01"),LocalTime.parse("23:00:00"),LocalTime.parse("08:00:00"));
        //A second Before opening
        assertFalse(restoSpy.isRestaurantOpen());
        //A second after closing
        assertFalse(restoSpy.isRestaurantOpen());
        //After Closing
        assertFalse(restoSpy.isRestaurantOpen());
        //Before Opening
        assertFalse(restoSpy.isRestaurantOpen());
    }

    //<<<<<<<<<<<<<<<<<<<<<<<<<OPEN/CLOSED>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


    //>>>>>>>>>>>>>>>>>>>>>>>>>>>MENU<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    @Test
    public void adding_item_to_menu_should_increase_menu_size_by_1(){
        int initialMenuSize = restaurant.getMenu().size();
        restaurant.addToMenu("Sizzling brownie",319);
        assertEquals(initialMenuSize+1,restaurant.getMenu().size());
    }

    @Test
    public void removing_item_from_menu_should_decrease_menu_size_by_1() throws itemNotFoundException {
        int initialMenuSize = restaurant.getMenu().size();
        restaurant.removeFromMenu("Vegetable lasagne");
        assertEquals(initialMenuSize-1,restaurant.getMenu().size());
    }

    @Test
    public void removing_item_that_does_not_exist_should_throw_exception() {
        assertThrows(itemNotFoundException.class,
                ()->restaurant.removeFromMenu("French fries"));
    }
    //<<<<<<<<<<<<<<<<<<<<<<<MENU>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

    //<<<<<<<<<<<<<<<<<<<<<<<COMPUTE COST>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
    @Test
    public void get_the_total_cost_of_items_selected_by_the_user() {
        //Create a string list and add the name of the items
        ArrayList<String> itemNames = new ArrayList<>();
        //Make sure no items returns zero
        assertEquals(0, restaurant.calculate_Total(itemNames));
        //Check if the total cost of a single item is it's price
        itemNames.add("Sweet corn soup");
        assertEquals(119, restaurant.calculate_Total(itemNames));
        //Check if total is calculated properly
        itemNames.add("Vegetable lasagne");
        assertEquals((119+269), restaurant.calculate_Total(itemNames));
    }
    //<<<<<<<<<<<<<<<<<<<<<<<COMPUTE COST>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
}