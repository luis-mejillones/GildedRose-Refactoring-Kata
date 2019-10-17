package com.gildedrose;

import org.junit.Test;

import static com.gildedrose.GildedRose.AGED_BRIE;
import static com.gildedrose.GildedRose.BACKSTAGE_PASSES;
import static com.gildedrose.GildedRose.DEXTERITY;
import static com.gildedrose.GildedRose.SULFURAS;
import static org.junit.Assert.assertEquals;

public class GildedRoseTest {

    @Test
    public void foo() {
        Item[] items = new Item[] {new Item("foo", 0, 0)};
        GildedRose app = new GildedRose(items);
        app.updateQuality();

        assertEquals("foo", app.items[0].name);
    }

    @Test
    public void testDecreaseQualityTwiceIfSellByDateIsPassed() {
        Item[] items = new Item[] {new Item(DEXTERITY, 0, 20)};
        GildedRose app = new GildedRose(items);

        app.updateQuality();
        Item item = app.items[0];

        assertEquals(-1, item.sellIn);
        assertEquals(18, item.quality);
    }

    @Test
    public void testQualityOfItemShouldNotBeNegative() {
        Item[] items = new Item[] {new Item(DEXTERITY, 0, 0)};
        GildedRose app = new GildedRose(items);

        app.updateQuality();
        Item item = app.items[0];

        assertEquals(-1, item.sellIn);
        assertEquals(0, item.quality);
    }

    @Test
    public void testIncreaseQualityOfAgedBrieOlderItGets() {
        Item[] items = new Item[] {new Item(AGED_BRIE, 2, 0)};
        GildedRose app = new GildedRose(items);

        app.updateQuality();
        Item item = app.items[0];

        assertEquals(1, item.sellIn);
        assertEquals(1, item.quality);

    }

    @Test
    public void testAllowQualityMoreThan50ForLegendaryItemsSulfuras() {
        Item[] items = new Item[] {
                new Item(AGED_BRIE, 2, 50),
                new Item(SULFURAS, 0, 80)
        };

        GildedRose app = new GildedRose(items);

        app.updateQuality();
        Item item = app.items[0];

        assertEquals(1, item.sellIn);
        assertEquals(50, item.quality);

        item = app.items[1];
        assertEquals(0, item.sellIn);
        assertEquals(80, item.quality);
    }

    @Test
    public void testNotIncreaseOrDecreaseLegendaryItemQualityAndItIsNotToBeSoldSulfuras() {
        Item[] items = new Item[] {new Item(SULFURAS, 0, 80)};
        GildedRose app = new GildedRose(items);

        app.updateQuality();
        Item item = app.items[0];

        assertEquals(0, item.sellIn);
        assertEquals(80, item.quality);
    }

    @Test
    public void testIncreaseInQualityByOneIfMoreThanTenDaysBackstagePasses() {
        Item[] items = new Item[] {new Item(BACKSTAGE_PASSES, 15, 20)};
        GildedRose app = new GildedRose(items);

        app.updateQuality();
        Item item = app.items[0];

        assertEquals(14, item.sellIn);
        assertEquals(21, item.quality);
    }

    @Test
    public void testIncreaseInQualityBytwoIfLessThanOrEqualToTenDaysBackstagePasses() {
        Item[] items = new Item[] {new Item(BACKSTAGE_PASSES, 10, 20)};
        GildedRose app = new GildedRose(items);

        app.updateQuality();
        Item item = app.items[0];

        assertEquals(9, item.sellIn);
        assertEquals(22, item.quality);
    }

    @Test
    public void testIncreaseInQualityByThreeIfLessThanOrEqualToFiveDaysBackstagePasses() {
        Item[] items = new Item[] {new Item(BACKSTAGE_PASSES, 5, 20)};
        GildedRose app = new GildedRose(items);

        app.updateQuality();
        Item item = app.items[0];

        assertEquals(4, item.sellIn);
        assertEquals(23, item.quality);
    }

    @Test
    public void testZeroTheQualityAfterSellInDateBackstagePasses() {
        Item[] items = new Item[] {new Item(BACKSTAGE_PASSES, 0, 20)};
        GildedRose app = new GildedRose(items);

        app.updateQuality();
        Item item = app.items[0];

        assertEquals(-1, item.sellIn);
        assertEquals(0, item.quality);
    }
}
