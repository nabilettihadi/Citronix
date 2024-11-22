package ma.nabil.Citronix;

import ma.nabil.Citronix.dtos.requests.HarvestDetailRequest;
import ma.nabil.Citronix.dtos.requests.HarvestRequest;
import ma.nabil.Citronix.dtos.responses.HarvestDetailResponse;
import ma.nabil.Citronix.dtos.responses.HarvestResponse;
import ma.nabil.Citronix.entities.*;
import ma.nabil.Citronix.enums.Season;
import ma.nabil.Citronix.exceptions.BusinessException;
import ma.nabil.Citronix.mappers.HarvestDetailMapper;
import ma.nabil.Citronix.mappers.HarvestMapper;
import ma.nabil.Citronix.repositories.*;
import ma.nabil.Citronix.services.impl.HarvestServiceImpl;
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
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class HarvestServiceTest {
    @Mock
    private HarvestRepository harvestRepository;
    @Mock
    private HarvestMapper harvestMapper;
    @Mock
    private HarvestDetailMapper harvestDetailMapper;
    @Mock
    private TreeRepository treeRepository;
    @Mock
    private FieldRepository fieldRepository;
    @Mock
    private HarvestDetailRepository harvestDetailRepository;
    
    @InjectMocks
    private HarvestServiceImpl harvestService;
    
    private HarvestRequest validRequest;
    private Harvest harvest;
    private HarvestResponse harvestResponse;
    private Field field;
    private Tree tree;
    private HarvestDetail harvestDetail;
    private HarvestDetailRequest detailRequest;
    private HarvestDetailResponse detailResponse;
    
    @BeforeEach
    void setUp() {
        LocalDate summerDate = LocalDate.of(2024, 7, 15);
        
        field = Field.builder()
                .id(1L)
                .name("Test Field")
                .area(5000.0)
                .build();
                
        tree = Tree.builder()
                .id(1L)
                .field(field)
                .plantingDate(LocalDate.now().minusYears(5))
                .build();
                
        validRequest = HarvestRequest.builder()
                .fieldId(1L)
                .harvestDate(summerDate)
                .season(Season.SUMMER)
                .build();
                
        harvest = Harvest.builder()
                .id(1L)
                .field(field)
                .harvestDate(summerDate)
                .season(Season.SUMMER)
                .totalQuantity(0.0)
                .harvestDetails(new ArrayList<>())
                .sales(new ArrayList<>())
                .build();
                
        harvestResponse = HarvestResponse.builder()
                .id(1L)
                .build();
                
        harvestDetail = HarvestDetail.builder()
                .id(1L)
                .harvest(harvest)
                .tree(tree)
                .quantity(100.0)
                .build();
                
        detailRequest = HarvestDetailRequest.builder()
                .treeId(1L)
                .quantity(100.0)
                .build();

        detailResponse = HarvestDetailResponse.builder()
                .id(1L)
                .quantity(100.0)
                .build();
    }

    @Test
    void create_ValidRequest_ShouldCreate() {
        when(fieldRepository.findById(anyLong())).thenReturn(Optional.of(field));
        when(harvestMapper.toEntity(any())).thenReturn(harvest);
        when(harvestRepository.save(any())).thenReturn(harvest);
        when(harvestMapper.toResponse(any())).thenReturn(harvestResponse);

        HarvestResponse result = harvestService.create(validRequest);

        assertNotNull(result);
        verify(harvestRepository).save(any());
        verify(harvestMapper).toEntity(any());
    }

    @Test
    void update_ValidRequest_ShouldUpdate() {
        when(harvestRepository.findById(anyLong())).thenReturn(Optional.of(harvest));
        when(harvestRepository.save(any())).thenReturn(harvest);
        when(harvestMapper.toResponse(any())).thenReturn(harvestResponse);

        HarvestResponse result = harvestService.update(1L, validRequest);

        assertNotNull(result);
        verify(harvestRepository).save(any());
    }

    @Test
    void delete_HarvestWithSales_ShouldThrowException() {
        harvest.setSales(List.of(new Sale()));
        when(harvestRepository.findById(anyLong())).thenReturn(Optional.of(harvest));

        assertThrows(BusinessException.class, () ->
                harvestService.delete(1L)
        );
        verify(harvestRepository, never()).delete(any());
    }

    @Test
    void delete_ValidHarvest_ShouldDelete() {
        when(harvestRepository.findById(anyLong())).thenReturn(Optional.of(harvest));

        harvestService.delete(1L);

        verify(harvestRepository).delete(harvest);
    }

    @Test
    void getTotalQuantity_ShouldReturnCorrectSum() {
        harvest.setTotalQuantity(150.0);
        when(harvestRepository.findById(anyLong())).thenReturn(Optional.of(harvest));

        Double result = harvestService.getTotalQuantity(1L);

        assertEquals(150.0, result);
    }

    @Test
    void getByFieldId_ShouldReturnList() {
        List<Harvest> harvests = List.of(harvest);
        when(harvestRepository.findByFieldId(anyLong())).thenReturn(harvests);
        when(harvestMapper.toResponse(any())).thenReturn(harvestResponse);

        List<HarvestResponse> results = harvestService.getByFieldId(1L);

        assertFalse(results.isEmpty());
        assertEquals(1, results.size());
    }

    @Test
    void getByFieldIdAndYear_ShouldReturnList() {
        List<Harvest> harvests = List.of(harvest);
        when(harvestRepository.findByFieldIdAndYear(anyLong(), anyInt())).thenReturn(harvests);
        when(harvestMapper.toResponse(any())).thenReturn(harvestResponse);

        List<HarvestResponse> results = harvestService.getByFieldIdAndYear(1L, 2024);

        assertFalse(results.isEmpty());
        assertEquals(1, results.size());
    }
}