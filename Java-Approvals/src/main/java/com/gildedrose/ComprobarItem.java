package com.gildedrose;

public interface ComprobarItem {
	boolean esOtro(Item item);
	
	boolean esConjured(Item item);
	
	boolean esAgedBrie(Item item);
	
	boolean esBackstagePass(Item item);
	
	boolean esSulfuras(Item item);
	
	boolean masQueCero(Item item);
	
	boolean calidadMasQue(int num, Item item);
	
	boolean calidadMenosQue(int num, Item item);
	
	boolean sellInMasQue(int num, Item item);
	
	boolean sellInMenosQue(int num, Item item);
	
	boolean niBrieNiBackstage(Item item);
	
	boolean calidadMasQueUnoYConjured(Item item);
}
