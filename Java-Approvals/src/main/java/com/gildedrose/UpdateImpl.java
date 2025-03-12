package com.gildedrose;

public class UpdateImpl implements Update {
	
	ComprobarItem comprobador = new ComprobarItemImpl();

	@Override
	public void updateItem(Item item) {
		this.updateItemCalidad(item);
		this.updateSellin(item);
        this.updateCalidadDespuesDeSellin(item);
	}
	
	@Override
	public void updateItemCalidad(Item item) {
		 if (comprobador.niBrieNiBackstage(item)) {
             if (comprobador.calidadMasQue(0, item)) {
                 if (!comprobador.esSulfuras(item)) {
                 	if (comprobador.calidadMasQueUnoYConjured(item)) {
                     	item.quality = item.quality - 1;
                     }
                     item.quality = item.quality - 1;     
                 }
             }
         } else {
             if (comprobador.calidadMenosQue(50, item)) {
                 item.quality = item.quality + 1;

                 if (comprobador.esBackstagePass(item)) {
                     if (comprobador.sellInMenosQue(11, item)) {
                         if (comprobador.calidadMenosQue(50, item)) {
                             item.quality = item.quality + 1;
                         }
                     }

                     if (comprobador.sellInMenosQue(6, item)) {
                         if (comprobador.calidadMenosQue(50, item)) {
                             item.quality = item.quality + 1;
                         }
                     }
                 }
             }
         }
	}

	
	@Override
	public void updateSellin(Item item) {
		if (!comprobador.esSulfuras(item)) {
            item.sellIn = item.sellIn - 1;
        }
	}

	@Override
	public void updateCalidadDespuesDeSellin(Item item) {
		if (comprobador.sellInMenosQue(0, item)) {
            if (!comprobador.esAgedBrie(item)) {
                if (!comprobador.esBackstagePass(item)) {
                    if (comprobador.calidadMasQue(0, item)) {
                        if (!comprobador.esSulfuras(item)) {
                        	if (comprobador.calidadMasQueUnoYConjured(item)) {
                            	item.quality = item.quality - 1;
                            }
                            item.quality = item.quality - 1;               
                        }
                    }
                } else {
                    item.quality = item.quality - item.quality;
                }
            } else {
                if (comprobador.calidadMenosQue(50, item)) {
                    item.quality = item.quality + 1;
                }
            }
        }
	}
}
