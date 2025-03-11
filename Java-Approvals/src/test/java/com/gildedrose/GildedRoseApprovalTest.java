package com.gildedrose;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.approvaltests.Approvals;
import org.approvaltests.reporters.DiffReporter;
import org.approvaltests.reporters.UseReporter;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

@UseReporter(DiffReporter.class)
public class GildedRoseApprovalTest {
	 
    @Nested
    @DisplayName("Sulfuras")
    class Sulfuras {
    	@Nested
    	@DisplayName("Casos válidos")
    	class OK {
    	    @DisplayName("Calidad no baja de 80 para Sulfuras")
    	    @ParameterizedTest(name = "{index} => sellIn: {0} quality: {1} -> sellIn: {2} quality: {3}")
			@CsvSource({"5, 80", "1, 80"})
    	    public void calidadSulfuras(int sellIn, int quality) {
    	    	Item[] items = new Item[]{new Item("Sulfuras, Hand of Ragnaros", sellIn, quality)};
    	    	GildedRose app = new GildedRose(items);
    	    	app.updateQuality();
    	    	assertAll("Calidad", 
    	    			() -> assertEquals(quality, items[0].quality),
    	    			() -> assertEquals(sellIn, items[0].sellIn)
    	    			);
    	    } 
    	}
    	@Nested
    	@DisplayName("Casos inválidos")
    	class KO {
    		@DisplayName("Throws para calidad menor que 80")
    	    @ParameterizedTest(name = "new Item(\"Sulfuras, Hand of Ragnaros\", {0}, {1})")
			@CsvSource({"-1, 66", "0, 51", "-2, -1"})
    	    public void calidadSulfurasThrows(int sellIn, int quality) {
    	    	Item[] items = new Item[]{new Item("Sulfuras, Hand of Ragnaros", sellIn, quality)};
    	    	GildedRose app = new GildedRose(items);
    	    	app.updateQuality();
    	    	Exception thrown = assertThrows(Exception.class, 
    	    			() -> {
    	    				throw new Exception("Calidad nunca menor que 80");
    	    				}
    	    			);
    	    	assertEquals("Calidad nunca menor que 80", thrown.getMessage());
    	    }
    	}
    }
    
    @Nested
    @DisplayName("Otros")
    class Otros {
    	@Nested
    	@DisplayName("Casos válidos")
    	class OK {
    		@DisplayName("Otros productos")
    		@ParameterizedTest(name = "{index} => sellIn: {0} quality: {1} -> sellIn: {2} quality: {3}")
    		@CsvSource({
    			"-1, 1, -2, 0", 
    			"0, 0, -1, 0", 
    			"7, 6, 6, 5", 
    			"-10, 3, -11, 1"})
    		public void calidadNoReduceMenorQueCero(int sellIn, int quality, int sellInDespues, int qualityDespues) {
    			Item[] items = new Item[]{new Item("item", sellIn, quality)};
    	    	GildedRose app = new GildedRose(items);
    	    	app.updateQuality();
    	    	assertAll("Calidad", 
    	    			() -> assertEquals(qualityDespues, items[0].quality),
    	    			() -> assertEquals(sellInDespues, items[0].sellIn)
    	    			);
    		}
    	}
    }
    
    @Nested
    @DisplayName("Backstage Passes")
    class BackStagePasses {
    	@Nested
    	@DisplayName("Casos válidos")
    	class OK {
    		@ParameterizedTest(name = "{index} => sellIn: {0} quality: {1} -> sellIn: {2} quality: {3}")
    		@CsvSource({
    			"5, 9, 4, 12", 
    			"10, 5, 9, 7", 
    			"11, 3, 10, 4",
    			"-1, 9, -2, 0"})
    		@DisplayName("Backstage passes")
    		public void backStagePassesTest(int sellIn, int quality, int sellInDespues, int qualityDespues) {
    			Item[] items = new Item[]{new Item("Backstage passes to a TAFKAL80ETC concert", sellIn, quality)};
    	    	GildedRose app = new GildedRose(items);
    	    	app.updateQuality();
    	    	assertAll("Calidad", 
    	    			() -> assertEquals(qualityDespues, items[0].quality),
    	    			() -> assertEquals(sellInDespues, items[0].sellIn)
    	    			);
    		}
    	}
    }
    
    @Nested
    @DisplayName("Conjured")
    class Conjured {
    	@Nested
    	@DisplayName("Casos válidos")
    	class OK {
    		@ParameterizedTest(name = "{index} => sellIn: {0} quality: {1} -> sellIn: {2} quality: {3}")
    		@CsvSource({
    			"10, 3, 9, 1", 
    			"0, 0, -1, 0",
    			"-1, 4, -2, 0",
    			"-1, 0, -2, 0", 
    			"1, 0, 0, 0"})
    		@DisplayName("Conjured producto")
    		public void conjuredReducePorDosCeroOMas(int sellIn, int quality, int sellInDespues, int qualityDespues) {
    			Item[] items = new Item[]{new Item("Conjured Mana Cake", sellIn, quality)};
    	    	GildedRose app = new GildedRose(items);
    	    	app.updateQuality();
    	    	assertAll("Calidad", 
    	    			() -> assertEquals(qualityDespues, items[0].quality),
    	    			() -> assertEquals(sellInDespues, items[0].sellIn)
    	    			);
    		}		
    	}	
    }
   
    

    @Test
    public void thirtyDays() {

        ByteArrayOutputStream fakeoutput = new ByteArrayOutputStream();
        System.setOut(new PrintStream(fakeoutput));
        System.setIn(new ByteArrayInputStream("a\n".getBytes()));

        Program.main();
        String output = fakeoutput.toString();

        Approvals.verify(output);
    }
}
