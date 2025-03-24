package com.gildedrose;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.approvaltests.Approvals;
import org.approvaltests.reporters.DiffReporter;
import org.approvaltests.reporters.UseReporter;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
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
    	    @ParameterizedTest(name = "{index} => sellIn: {0} quality: {1} -> sellIn: {0} quality: {1}")
			@CsvSource({
				"5, 80", 
				"1, 80"})
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
   
    
   
//	@ParameterizedTest(name = "{index} => sellIn: {0} quality: {1} -> sellIn: {2} quality: {3}")
//	@CsvFileSource(resources = "casos-de-prueba.csv", numLinesToSkip = 1)
//	@DisplayName("Conjured producto")
//	public void dataSourceTest(String producto, int sellIn, int quality, int sellInDespues, int qualityDespues) {
//		String name = producto.replace("'\'", "");
//		Item product =new Item(name, sellIn, quality);
//	    GildedRose app = new GildedRose(new Item[] {product});
//	    app.updateQuality();
//	    assertAll("Calidad", 
//	    		() -> assertEquals(qualityDespues, product.quality, "sellIn"),
//	    		() -> assertEquals(sellInDespues, product.sellIn, "quality")
//	    		);
//	}
    
    void instantanea() {
		Item[] items = new Item[] { 
				new Item("+5 Dexterity Vest", 10, 20), 
				new Item("Aged Brie", 2, 0),
				new Item("Elixir of the Mongoose", 5, 7), 
				new Item("Sulfuras, Hand of Ragnaros", 0, 80),
				new Item("Sulfuras, Hand of Ragnaros", -1, 80),
				new Item("Backstage passes to a TAFKAL80ETC concert", 15, 20),
				new Item("Backstage passes to a TAFKAL80ETC concert", 10, 49),
				new Item("Backstage passes to a TAFKAL80ETC concert", 5, 49),
				// this conjured item does not work properly yet
				new Item("Conjured Mana Cake", 3, 6) 
				};

		GildedRose app = new GildedRose(items);
		StringBuilder output = new StringBuilder();
		output.append("day,name, sellIn, quality\n");
		List.of(items).forEach(item -> output.append("0," + item + "\n"));
		for (int i = 1; i <= 31; i++) {
			app.updateQuality();
			for(Item item: items)
				output.append(i + "," + item + "\n");
		}
		Approvals.verify(output);
	}

//    @Test
//    public void thirtyDays() {
//
//        ByteArrayOutputStream fakeoutput = new ByteArrayOutputStream();
//        System.setOut(new PrintStream(fakeoutput));
//        System.setIn(new ByteArrayInputStream("a\n".getBytes()));
//
//        Program.main();
//        String output = fakeoutput.toString();
//
//        Approvals.verify(output);
//    }
}
