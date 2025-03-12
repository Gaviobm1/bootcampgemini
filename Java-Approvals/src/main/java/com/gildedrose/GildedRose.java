package com.gildedrose;

class GildedRose {
    Item[] items;
    ComprobarItemImpl comprobador = new ComprobarItemImpl();

    public GildedRose(Item[] items) {
        this.items = items;
    }

    public void updateQuality() {
        for (int i = 0; i < items.length; i++) {
            if (comprobador.niBrieNiBackstage(items[i])) {
                if (comprobador.calidadMasQue(0, items[i])) {
                    if (!comprobador.esSulfuras(items[i])) {
                    	if (comprobador.calidadMasQueUnoYConjured(items[i])) {
                        	items[i].quality = items[i].quality - 1;
                        }
                        items[i].quality = items[i].quality - 1;     
                    }
                }
            } else {
                if (comprobador.calidadMenosQue(50, items[i])) {
                    items[i].quality = items[i].quality + 1;

                    if (comprobador.esBackstagePass(items[i])) {
                        if (items[i].sellIn < 11) {
                            if (items[i].quality < 50) {
                                items[i].quality = items[i].quality + 1;
                            }
                        }

                        if (comprobador.sellInMenosQue(6, items[i])) {
                            if (comprobador.calidadMenosQue(50, items[i])) {
                                items[i].quality = items[i].quality + 1;
                            }
                        }
                    }
                }
            }

            if (!comprobador.esSulfuras(items[i])) {
                items[i].sellIn = items[i].sellIn - 1;
            }

            if (comprobador.sellInMenosQue(0, items[i])) {
                if (!comprobador.esAgedBrie(items[i])) {
                    if (!comprobador.esBackstagePass(items[i])) {
                        if (comprobador.calidadMasQue(0, items[i])) {
                            if (!comprobador.esSulfuras(items[i])) {
                            	if (comprobador.calidadMasQueUnoYConjured(items[i])) {
                                	items[i].quality = items[i].quality - 1;
                                }
                                items[i].quality = items[i].quality - 1;               
                            }
                        }
                    } else {
                        items[i].quality = items[i].quality - items[i].quality;
                    }
                } else {
                    if (comprobador.calidadMenosQue(50, items[i])) {
                        items[i].quality = items[i].quality + 1;
                    }
                }
            }
        }
    }
}