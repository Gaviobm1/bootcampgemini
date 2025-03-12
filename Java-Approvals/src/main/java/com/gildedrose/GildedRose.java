package com.gildedrose;

class GildedRose {
    Item[] items;
    UpdateImpl update = new UpdateImpl();

    public GildedRose(Item[] items) {
        this.items = items;
    }

    public void updateQuality() {
        for (int i = 0; i < items.length; i++) {
           update.updateItem(items[i]);
        }
    }
}