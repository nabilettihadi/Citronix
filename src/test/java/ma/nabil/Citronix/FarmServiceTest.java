package ma.nabil.Citronix;

import ma.nabil.Citronix.dtos.requests.FarmRequest;
import ma.nabil.Citronix.dtos.requests.FieldRequest;
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
import java.util.List;
import java.util.Optional;

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

    // Tests de création
    @Test
    void create_ValidRequest_ShouldCreateFarm() {
        when(farmRepository.existsByNameIgnoreCase(anyString())).thenReturn(false);
        when(farmMapper.toEntity(any(FarmRequest.class))).thenReturn(farm);
        when(farmRepository.save(any(Farm.class))).thenReturn(farm);
        when(farmMapper.toResponse(any(Farm.class))).thenReturn(farmResponse);

        FarmResponse result = farmService.create(validRequest);

        assertNotNull(result);
        assertEquals(farmResponse.getName(), result.getName());
        verify(farmRepository).save(any(Farm.class));
    }

    @Test
    void create_DuplicateName_ShouldThrowException() {
        when(farmRepository.existsByNameIgnoreCase(anyString())).thenReturn(true);
        
        assertThrows(BusinessException.class, () -> 
            farmService.create(validRequest)
        );
        verify(farmRepository, never()).save(any(Farm.class));
    }

    @Test
    void create_AreaTooSmall_ShouldThrowException() {
        validRequest.setArea(500.0);
        when(farmRepository.existsByNameIgnoreCase(anyString())).thenReturn(false);
        
        assertThrows(BusinessException.class, () -> 
            farmService.create(validRequest)
        );
    }

    @Test
    void create_TooManyFields_ShouldThrowException() {
        List<FieldRequest> fields = new ArrayList<>();
        for (int i = 0; i < 11; i++) {
            fields.add(new FieldRequest());
        }
        validRequest.setFields(fields);
        
        assertThrows(BusinessException.class, () -> 
            farmService.create(validRequest)
        );
    }

    // Tests de mise à jour
    @Test
    void update_ValidRequest_ShouldUpdateFarm() {
        when(farmRepository.findById(anyLong())).thenReturn(Optional.of(farm));
        when(farmRepository.save(any(Farm.class))).thenReturn(farm);
        when(farmMapper.toResponse(any(Farm.class))).thenReturn(farmResponse);
        
        FarmResponse result = farmService.update(1L, validRequest);
        
        assertNotNull(result);
        verify(farmRepository).save(any(Farm.class));
    }

    @Test
    void update_NonExistingFarm_ShouldThrowException() {
        when(farmRepository.findById(anyLong())).thenReturn(Optional.empty());
        
        assertThrows(BusinessException.class, () -> 
            farmService.update(1L, validRequest)
        );
    }

    // Tests de suppression
    @Test
    void delete_ExistingFarm_ShouldDelete() {
        when(farmRepository.findById(anyLong())).thenReturn(Optional.of(farm));
        
        farmService.delete(1L);
        
        verify(farmRepository).delete(farm);
    }

    @Test
    void delete_NonExistingFarm_ShouldThrowException() {
        when(farmRepository.findById(anyLong())).thenReturn(Optional.empty());
        
        assertThrows(BusinessException.class, () -> 
            farmService.delete(1L)
        );
    }
}