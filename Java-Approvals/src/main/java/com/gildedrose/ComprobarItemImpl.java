package com.gildedrose;

public class ComprobarItemImpl implements ComprobarItem {

	@Override
	public boolean esOtro(Item item) {
		if (!item.name.equals("Aged Brie")
             && !item.name.equals("Backstage passes to a TAFKAL80ETC concert")
             && !item.name.equals("Sulfuras, Hand of Ragnaros")) {
			return true;
		}
		return false;
	}
	
	@Override
	public boolean esConjured(Item item) {
		if (item.name.contains("Conjured")) {
			return true;
		}
		return false;
	}


	@Override
	public boolean esAgedBrie(Item item) {
		if (item.name.equals("Aged Brie")) {
			return true;
		}
		return false;
	}

	@Override
	public boolean esBackstagePass(Item item) {
		if (item.name.equals("Backstage passes to a TAFKAL80ETC concert")) {
			return true;
		}
		return false;
	}

	@Override
	public boolean esSulfuras(Item item) {
		if (item.name.equals("Sulfuras, Hand of Ragnaros")) {
			return true;
		}
		return false;
	}
	
	@Override
	public boolean calidadMasQue(int num, Item item) {
		return item.quality > num;
	}
	
	public boolean calidadMenosQue(int num, Item item) {
		return item.quality < num;
	}

	@Override
	public boolean masQueCero(Item item) {
		return false;
	}

	@Override
	public boolean sellInMasQue(int num, Item item) {
		return item.sellIn > num;
	}

	@Override
	public boolean sellInMenosQue(int num, Item item) {
		return item.sellIn < num;
	}

	@Override
	public boolean niBrieNiBackstage(Item item) {
		return !this.esAgedBrie(item) && !this.esBackstagePass(item);
	}
	
	public boolean calidadMasQueUnoYConjured(Item item) {
		return this.calidadMasQue(1, item) && this.esConjured(item);
	}

}
