package com.gildedrose;

class GildedRose {
    public static final String AGED_BRIE = "Aged Brie";
    public static final String BACKSTAGE_PASSES = "Backstage passes to a TAFKAL80ETC concert";
    public static final String SULFURAS = "Sulfuras, Hand of Ragnaros";
    public static final String DEXTERITY = "+5 Dexterity Vest";

    Item[] items;

    public GildedRose(Item[] items) {
        this.items = items;
    }

    public void updateQuality() {
        for (int i = 0; i < items.length; i++) {
            this.process(items[i]);
        }
    }

    private void process(Item item) {
        if (!SULFURAS.equals(item.name)) {
            item.sellIn--;
        }

        if (DEXTERITY.equals(item.name) && item.quality > 0) {
            item.quality--;
        } else {
            if (item.quality < 50) {
                this.processBackstagePasses(item);
            }
        }

        this.processNegativeDays(item);
    }

    private void processBackstagePasses(Item item) {
        item.quality++;

        if (BACKSTAGE_PASSES.equals(item.name)) {
            if (item.sellIn < 11) {
                item.quality++;
            }

            if (item.sellIn < 6) {
                item.quality++;
            }
        }
    }

    private void processNegativeDays(Item item) {
        if (item.sellIn < 0) {
            if (!AGED_BRIE.equals(item.name)) {
                this.processNoBackstagePassesNegative(item);
            } else {
                item.quality++;
            }
        }
    }

    private void processNoBackstagePassesNegative(Item item) {
        if (item.quality > 0 && (DEXTERITY.equals(item.name) || AGED_BRIE.equals(item.name))) {
            item.quality--;
        } else {
            item.quality = 0;
        }
    }
}
