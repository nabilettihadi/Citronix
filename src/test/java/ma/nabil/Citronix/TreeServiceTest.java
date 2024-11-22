package ma.nabil.Citronix;

import ma.nabil.Citronix.dtos.requests.TreeRequest;
import ma.nabil.Citronix.entities.Field;
import ma.nabil.Citronix.entities.Tree;
import ma.nabil.Citronix.exceptions.BusinessException;
import ma.nabil.Citronix.mappers.TreeMapper;
import ma.nabil.Citronix.repositories.FieldRepository;
import ma.nabil.Citronix.repositories.TreeRepository;
import ma.nabil.Citronix.services.impl.TreeServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TreeServiceTest {

    @Mock
    private TreeRepository treeRepository;

    @Mock
    private FieldRepository fieldRepository;

    @Mock
    private TreeMapper treeMapper;

    @InjectMocks
    private TreeServiceImpl treeService;

    private TreeRequest validRequest;
    private Field field;
    private Tree tree;

    @BeforeEach
    void setUp() {
        field = Field.builder()
                .id(1L)
                .area(10000.0)
                .build();

        validRequest = TreeRequest.builder()
                .plantingDate(LocalDate.of(2024, 3, 15))
                .build();

        tree = Tree.builder()
                .id(1L)
                .plantingDate(LocalDate.of(2024, 3, 15))
                .field(field)
                .build();
    }

    @Test
    void create_ValidRequest_ShouldCreateTree() {
        when(fieldRepository.findById(anyLong())).thenReturn(Optional.of(field));
        when(treeRepository.countByFieldId(anyLong())).thenReturn(0L);
        when(treeMapper.toEntity(any(TreeRequest.class))).thenReturn(tree);
        when(treeRepository.save(any(Tree.class))).thenReturn(tree);

        assertDoesNotThrow(() -> treeService.create(1L, validRequest));
        verify(treeRepository).save(any(Tree.class));
    }

    @Test
    void create_InvalidPlantingDate_ShouldThrowException() {
        validRequest.setPlantingDate(LocalDate.of(2024, 1, 15));

        assertThrows(BusinessException.class, () -> treeService.create(1L, validRequest));
        verify(treeRepository, never()).save(any(Tree.class));
    }

    @Test
    void create_MaxDensityReached_ShouldThrowException() {
        when(fieldRepository.findById(anyLong())).thenReturn(Optional.of(field));
        when(treeRepository.countByFieldId(anyLong())).thenReturn(1000L);

        assertThrows(BusinessException.class, () -> treeService.create(1L, validRequest));
        verify(treeRepository, never()).save(any(Tree.class));
    }

    @Test
    void create_FieldNotFound_ShouldThrowException() {
        when(fieldRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(BusinessException.class, () -> treeService.create(1L, validRequest));
        verify(treeRepository, never()).save(any(Tree.class));
    }
}