package ru.clevertec.cheque.cache.impl;

import ru.clevertec.cheque.model.entity.Product;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

class LFUCacheTest {
    private LFUCache cache = new LFUCache(3);

    @BeforeEach
    void setUp() {
        cache = new LFUCache(3);
        cache.set(1L, new Product(1L, "Apple", 3.22f, false, "255GKP11LL"));
        cache.set(2L, new Product(2L, "Pineapple", 5.22f, true, "415GJO63KP"));
    }

    @Test
    void checkGetShouldReturnEntityWithId1() {
        //given
        Product expected = new Product(1L, "Apple", 3.22f, false, "255GKP11LL");

        //when
        Object actual = cache.get(1L);

        //then
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void checkGetAllShouldReturnEntitiesWithIds1And2() {
        //given
        List<Product> expected = List.of(
                new Product(1L, "Apple", 3.22f, false, "255GKP11LL"),
                new Product(2L, "Pineapple", 5.22f, true, "415GJO63KP")
        );

        //when
        List<Object> actual = cache.getAll();

        //then
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void checkSetShouldAddThirdEntityToCache() {
        //given
        List<Product> expected = List.of(
                new Product(1L, "Apple", 3.22f, false, "255GKP11LL"),
                new Product(2L, "Pineapple", 5.22f, true, "415GJO63KP"),
                new Product(3L, "Watermelon", 7.22f, false, "633MOK87ZN")
        );

        //when
        cache.set(3L, new Product(3L, "Watermelon", 7.22f, false, "633MOK87ZN"));
        List<Object> actual = cache.getAll();

        //then
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void checkSetShouldRemoveEntityWithId2ToGetCapacityForNewOneAndAddNewEntity() {
        //given
        List<Product> expected = List.of(
                new Product(1L, "Apple", 3.22f, false, "255GKP11LL"),
                new Product(3L, "Watermelon", 7.22f, false, "633MOK87ZN"),
                new Product(4L, "Melon", 9.22f, true, "496ACE56JK")
        );
        cache.set(3L, new Product(3L, "Watermelon", 7.22f, false, "633MOK87ZN"));
        cache.get(1L);
        cache.get(3L);

        //when
        cache.set(4L, new Product(4L, "Melon", 9.22f, true, "496ACE56JK"));
        List<Object> actual = cache.getAll();

        //then
        Assertions.assertEquals(expected, actual);

    }

    @Test
    void checkDeleteShouldDeleteEntityFromCache() {
        //given
        int sizeBeforeDelete = cache.getAll().size();

        //when
        cache.delete(1L);
        int sizeAfterDelete = cache.getAll().size();

        //then
        Assertions.assertNotEquals(sizeBeforeDelete, sizeAfterDelete);
    }
}