package ma.nabil.Citronix;

import ma.nabil.Citronix.dtos.requests.FarmRequest;
import ma.nabil.Citronix.dtos.responses.FarmResponse;
import ma.nabil.Citronix.entities.Farm;
import ma.nabil.Citronix.exceptions.BusinessException;
import ma.nabil.Citronix.mappers.FarmMapper;
import ma.nabil.Citronix.repositories.FarmRepository;
import ma.nabil.Citronix.services.impl.FarmServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FarmServiceTest {

    @Mock
    private FarmRepository farmRepository;

    @Mock
    private FarmMapper farmMapper;

    @InjectMocks
    private FarmServiceImpl farmService;

    private FarmRequest validRequest;
    private Farm farm;
    private FarmResponse farmResponse;

    @BeforeEach
    void setUp() {
        validRequest = FarmRequest.builder()
                .name("Ferme Test")
                .location("Location Test")
                .area(10000.0)
                .creationDate(LocalDate.now())
                .fields(new ArrayList<>())
                .build();

        farm = Farm.builder()
                .id(1L)
                .name("Ferme Test")
                .location("Location Test")
                .area(10000.0)
                .creationDate(LocalDate.now())
                .fields(new ArrayList<>())
                .build();

        farmResponse = FarmResponse.builder()
                .id(1L)
                .name("Ferme Test")
                .location("Location Test")
                .area(10000.0)
                .creationDate(LocalDate.now())
                .fields(new ArrayList<>())
                .build();
    }

    @Test
    void create_ValidRequest_ShouldCreateFarm() {
        when(farmRepository.existsByNameIgnoreCase(anyString())).thenReturn(false);
        when(farmMapper.toEntity(any(FarmRequest.class))).thenReturn(farm);
        when(farmRepository.save(any(Farm.class))).thenReturn(farm);
        when(farmMapper.toResponse(any(Farm.class))).thenReturn(farmResponse);

        FarmResponse result = farmService.create(validRequest);

        assertNotNull(result);
        assertEquals(farmResponse.getName(), result.getName());
        assertEquals(farmResponse.getLocation(), result.getLocation());
        assertEquals(farmResponse.getArea(), result.getArea());
        verify(farmRepository).save(any(Farm.class));
    }

    @Test
    void create_DuplicateName_ShouldThrowException() {
        when(farmRepository.existsByNameIgnoreCase(anyString())).thenReturn(true);

        assertThrows(BusinessException.class, () -> farmService.create(validRequest));
        verify(farmRepository, never()).save(any(Farm.class));
    }

    @Test
    void create_InvalidArea_ShouldThrowException() {
        validRequest.setArea(500.0);

        assertThrows(BusinessException.class, () -> farmService.create(validRequest));
        verify(farmRepository, never()).save(any(Farm.class));
    }
}