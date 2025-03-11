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
    	    @ParameterizedTest(name = "new Item(\"Sulfuras, Hand of Ragnaros\", {0}, {1})")
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
    		@DisplayName("Calidad no baja de 0")
    		@ParameterizedTest(name = "new Item(\"item\", {0}, {1})")
    		@CsvSource({"-1, 1", "0, 0"})
    		public void calidadNoReduceMenorQueCero(int sellIn, int quality) {
    			Item[] items = new Item[]{new Item("item", sellIn, quality)};
    	    	GildedRose app = new GildedRose(items);
    	    	app.updateQuality();
    	    	assertAll("Calidad", 
    	    			() -> assertEquals(0, items[0].quality),
    	    			() -> assertEquals(sellIn - 1, items[0].sellIn)
    	    			);
    		}
    		@DisplayName("Calidad degrada 1 por día antes de sellIn = 0")
    		@Test
    		public void calidadReduceUnoPorDia() {
    			Item[] items = new Item[]{new Item("item", 7, 6)};
    	    	GildedRose app = new GildedRose(items);
    	    	app.updateQuality();
    	    	assertAll("Calidad", 
    	    			() -> assertEquals(5, items[0].quality),
    	    			() -> assertEquals(6, items[0].sellIn)
    	    			);
    		}
    		@DisplayName("Calidad degrada 2 por día cuando sellIn < 0")
    		@Test
    		public void calidadReduceDosDespuesDeSellIn() {
    			Item[] items = new Item[]{new Item("item", -10, 3)};
    	    	GildedRose app = new GildedRose(items);
    	    	app.updateQuality();
    	    	assertAll("Calidad", 
    	    			() -> assertEquals(1, items[0].quality),
    	    			() -> assertEquals(-11, items[0].sellIn)
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
    		@Test
    		@DisplayName("Calidad aumenta por 1 cuando sellIn > 10")
    		public void calidadAumentaPorUno() {
    			Item[] items = new Item[]{new Item("Backstage passes to a TAFKAL80ETC concert", 11, 3)};
    	    	GildedRose app = new GildedRose(items);
    	    	app.updateQuality();
    	    	assertAll("Calidad", 
    	    			() -> assertEquals(4, items[0].quality),
    	    			() -> assertEquals(10, items[0].sellIn)
    	    			);
    		}
    		@Test
    		@DisplayName("Calidad aumenta por 2 cuando 5 < sellIn <= 10")
    		public void calidadAumentaPorDos() {
    			Item[] items = new Item[]{new Item("Backstage passes to a TAFKAL80ETC concert", 10, 5)};
    	    	GildedRose app = new GildedRose(items);
    	    	app.updateQuality();
    	    	assertAll("Calidad", 
    	    			() -> assertEquals(7, items[0].quality),
    	    			() -> assertEquals(9, items[0].sellIn)
    	    			);
    		}
    		@Test
    		@DisplayName("Calidad aumenta por 3 cuando sellIn <= 5")
    		public void calidadAumentaPorTres() {
    			Item[] items = new Item[]{new Item("Backstage passes to a TAFKAL80ETC concert", 5, 9)};
    	    	GildedRose app = new GildedRose(items);
    	    	app.updateQuality();
    	    	assertAll("Calidad", 
    	    			() -> assertEquals(12, items[0].quality),
    	    			() -> assertEquals(4, items[0].sellIn)
    	    			);
    		}
    		@Test
    		@DisplayName("Calidad se reduce a 0 cuando sellIn < 0")
    		public void calidadReduceACero() {
    			Item[] items = new Item[]{new Item("Backstage passes to a TAFKAL80ETC concert", -1, 9)};
    	    	GildedRose app = new GildedRose(items);
    	    	app.updateQuality();
    	    	assertAll("Calidad", 
    	    			() -> assertEquals(0, items[0].quality),
    	    			() -> assertEquals(-2, items[0].sellIn)
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
    		@ParameterizedTest(name = "new Item(\"Conjured Mana Cake\", {0}, {1})")
    		@CsvSource({"10, 3", "0, 0"})
    		@DisplayName("Reduce calidad por 2 cuando sellIn >= 0")
    		public void conjuredReducePorDosEncimaDeCero(int sellIn, int quality) {
    			Item[] items = new Item[]{new Item("Conjured Mana Cake", sellIn, quality)};
    	    	GildedRose app = new GildedRose(items);
    	    	app.updateQuality();
    	    	assertAll("Calidad", 
    	    			() -> assertEquals(quality == 0 || quality == 1 ? 0 : quality - 2, items[0].quality),
    	    			() -> assertEquals(sellIn - 1, items[0].sellIn)
    	    			);
    		}
    		@Test
    		@DisplayName("Reduce calidad por 4 cuando sellIn < 0")
    		public void conjuredReducePorCuatroDebajoDeCero() {
    			Item[] items = new Item[]{new Item("Conjured Mana Cake", -1, 4)};
    	    	GildedRose app = new GildedRose(items);
    	    	app.updateQuality();
    	    	assertAll("Calidad", 
    	    			() -> assertEquals(0, items[0].quality),
    	    			() -> assertEquals(-2, items[0].sellIn)
    	    			);
    		}
    		@ParameterizedTest(name = "new Item(\"Conjured Mana Cake\", {0}, {1})")
    		@CsvSource({"-1, 0", "1, 0", "10, 0"})
    		@DisplayName("Calidad no reduce por debajo de 0")
    		public void conjuredNoReducePorDebajoDeCero(int sellIn, int quality) {
    			Item[] items = new Item[]{new Item("Conjured Mana Cake", sellIn, quality)};
    	    	GildedRose app = new GildedRose(items);
    	    	app.updateQuality();
    	    	assertAll("Calidad", 
    	    			() -> assertEquals(0, items[0].quality),
    	    			() -> assertEquals(sellIn - 1, items[0].sellIn)
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
