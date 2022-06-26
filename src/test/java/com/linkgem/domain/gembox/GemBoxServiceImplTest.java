package com.linkgem.domain.gembox;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.linkgem.presentation.common.exception.BusinessException;
import com.linkgem.presentation.common.exception.ErrorCode;

@ExtendWith(MockitoExtension.class)
class GemBoxServiceImplTest {

    @InjectMocks
    private GemBoxServiceImpl gemBoxService;

    @Mock
    private GemBoxStore gemBoxStore;

    @Mock
    private GemBoxDomainService gemBoxDomainService;

    @DisplayName("잼박스를 저장한다")
    @Test
    public void 잼박스_저장() {

        final String name = "테스트";
        final Long userId = 1L;

        GemBox gemBox = new GemBox(name, userId);

        when(gemBoxDomainService.isFull(any())).thenReturn(false);
        when(gemBoxDomainService.isExisted(any())).thenReturn(false);
        when(gemBoxStore.create(any())).thenReturn(gemBox);

        GemBoxCommand.Create command = GemBoxCommand.Create.builder()
            .name(name)
            .linkIds(Arrays.asList(1L))
            .userId(userId)
            .build();

        GemBoxInfo.Create createInfo = gemBoxService.create(command);

        assertThat(createInfo.getName()).isEqualTo(name);
        assertThat(createInfo.getUserId()).isEqualTo(userId);
    }

    @DisplayName("잼박스를 초과 저장한다")
    @Test
    public void 잼박스_초과_저장() {

        final String name = "테스트";
        final Long userId = 1L;

        when(gemBoxDomainService.isFull(any())).thenReturn(true);

        GemBoxCommand.Create command = GemBoxCommand.Create.builder()
            .name(name)
            .linkIds(Arrays.asList(1L))
            .userId(userId)
            .build();

        assertThatThrownBy(() -> gemBoxService.create(command))
            .isInstanceOf(BusinessException.class)
            .hasMessageContaining(ErrorCode.GEMBOX_IS_FULL.getMessage());
    }

    @DisplayName("잼박스를 중복 저장한다")
    @Test
    public void 잼박스_중복_저장() {

        final String name = "테스트";
        final Long userId = 1L;

        when(gemBoxDomainService.isExisted(any())).thenReturn(true);

        GemBoxCommand.Create command = GemBoxCommand.Create.builder()
            .name(name)
            .linkIds(Arrays.asList(1L))
            .userId(userId)
            .build();

        assertThatThrownBy(() -> gemBoxService.create(command))
            .isInstanceOf(BusinessException.class)
            .hasMessageContaining(ErrorCode.GEMBOX_IS_ALREADY_EXISTED.getMessage());
    }

}
