package com.gildedrose;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class GildedRoseTest {

    @Test
    void foo() {
        Item[] items = new Item[] { new Item("foo", 0, 0) };
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertEquals("foo", app.items[0].name);
    }
    
    @Test
    @DisplayName("Test quality increase for brie")
    void agedBrieQualityTest() {
    	Item[] items = new Item[] { new Item("Aged Brie", 0, 0)};
    	GildedRose app = new GildedRose(items);
    	app.updateQuality();
    	assertEquals(2, app.items[0].quality);
    }

}
