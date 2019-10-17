package com.gildedrose;

class GildedRose {
    public static final String AGED_BRIE = "Aged Brie";
    public static final String BACKSTAGE_PASSES = "Backstage passes to a TAFKAL80ETC concert";
    public static final String SULFURAS = "Sulfuras, Hand of Ragnaros";

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
            item.sellIn = item.sellIn - 1;
        }

        if (!AGED_BRIE.equals(item.name) && !BACKSTAGE_PASSES.equals(item.name)) {
            if (!SULFURAS.equals(item.name) && item.quality > 0) {
                item.quality = item.quality - 1;
            }
        } else {
            if (item.quality < 50) {
                item.quality = item.quality + 1;

                this.processBackstagePasses(item);
            }
        }

        this.processNegativeDays(item);
    }

    private void processBackstagePasses(Item item) {
        if (BACKSTAGE_PASSES.equals(item.name)) {
            if (item.sellIn < 11 && item.quality < 50) {
                item.quality = item.quality + 1;
            }

            if (item.sellIn < 6 && item.quality < 50) {
                item.quality = item.quality + 1;
            }
        }
    }

    private void processNegativeDays(Item item) {
        if (item.sellIn < 0) {
            if (!AGED_BRIE.equals(item.name)) {
                this.processNoBackstagePassesNegative(item);
            } else {
                if (item.quality < 50) {
                    item.quality = item.quality + 1;
                }
            }
        }
    }

    private void processNoBackstagePassesNegative(Item item) {
        if (item.quality > 0 && !BACKSTAGE_PASSES.equals(item.name) && !SULFURAS.equals(item.name)) {
            item.quality = item.quality - 1;
        } else {
            item.quality = 0;
        }
    }
}
