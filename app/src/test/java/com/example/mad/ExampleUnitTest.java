package com.example.mad;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {

    private Cafe_MenuList cafe_menuList;
    private Item_Details item_details;
    private createOne arraylis;

    @Before
    public void setup(){
        cafe_menuList = new Cafe_MenuList();
    }

    @Test
    public void amount_isCorrect(){
        double amount =cafe_menuList.calculateAmount(100.0,5.0);
        assertEquals(95.0,amount,0.001);
    }

    //testing the item count
    @Before
    public void setUP(){
        item_details = new Item_Details();
    }

    @Test
    public void testCount(){
        boolean success = true;
        success = item_details.checkItemCount(4);
        assertEquals(true, success);
    }

    @Before
    public void setUp(){
        arraylis = new createOne();
    }

    @Test
    public  void getLength(){
        int length = 0;
        length = createOne.arrayList.size();
        assertEquals(2,length);
    }
}