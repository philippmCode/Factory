package com.philippmeyer.factory.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.philippmeyer.factory.entity.Filter;
import com.philippmeyer.factory.entity.Merkmalskategorie;
import com.philippmeyer.factory.repository.FilterRepository;
import com.philippmeyer.factory.repository.MerkmalskategorieRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

class FilterServiceTest {

    @Mock
    private FilterRepository filterRepository;
    @Mock
    private MerkmalskategorieRepository merkmalskategorieRepository;

    @InjectMocks
    private FilterService filterService;

    @BeforeEach
    void setUp() {
        // Initializes the Mocks before each test run
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void save_should_persist_filter_with_loaded_categories() {
        // ARRANGE
        Long catId1 = 1L;
        Long catId2 = 2L;

        // 1. Input Filter (from the frontend) with the IDs
        Filter inputFilter = new Filter();
        inputFilter.setMerkmalskategorieIds(Arrays.asList(catId1, catId2));

        // 2. Expected loaded categories (from the database)
        Merkmalskategorie category1 = new Merkmalskategorie();
        Merkmalskategorie category2 = new Merkmalskategorie();
        List<Merkmalskategorie> loadedCategories = Arrays.asList(category1, category2);

        // 3. Stubbing the Repository: Simulates the database fetch
        when(merkmalskategorieRepository.findAllById(inputFilter.getMerkmalskategorieIds()))
                .thenReturn(loadedCategories);

        // 4. Stubbing the save operation: Returns the filter it received
        when(filterRepository.save(any(Filter.class))).thenReturn(inputFilter);

        // ACT
        Filter savedFilter = filterService.save(inputFilter);

        // ASSERT
        // 1. Check if the Set of categories was correctly assigned (size and content)
        assertEquals(2, savedFilter.getMerkmalskategorien().size());
        assertTrue(savedFilter.getMerkmalskategorien().containsAll(loadedCategories));

        // 2. Verify that the Repository methods were called
        verify(merkmalskategorieRepository, times(1)).findAllById(inputFilter.getMerkmalskategorieIds());
        verify(filterRepository, times(1)).save(inputFilter);
    }
}