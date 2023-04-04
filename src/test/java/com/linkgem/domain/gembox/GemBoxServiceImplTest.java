package com.linkgem.domain.gembox;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.linkgem.domain.link.LinkPersistence;
import com.linkgem.presentation.common.exception.BusinessException;
import com.linkgem.presentation.common.exception.ErrorCode;

@ExtendWith(MockitoExtension.class)
class GemBoxServiceImplTest {

    @InjectMocks
    private GemBoxServiceImpl gemBoxService;

    @Mock
    private GemBoxPersistence gemBoxPersistence;

    @Mock
    private LinkPersistence linkPersistence;

    @DisplayName("잼박스를 저장한다")
    @Test
    void 잼박스_저장() {

        final String name = "테스트";
        final Long userId = 1L;

        GemBox gemBox = GemBox.builder()
            .name(name)
            .userId(userId)
            .build();

        when(gemBoxService.isFull(any())).thenReturn(false);
        when(gemBoxService.isExisted(any())).thenReturn(false);
        when(linkPersistence.find(any(), any())).thenReturn(Optional.empty());
        when(gemBoxPersistence.create(any())).thenReturn(gemBox);

        GemBoxCommand.Create command = GemBoxCommand.Create.builder()
            .name(name)
            .linkIds(Arrays.asList(1L))
            .userId(userId)
            .build();

        GemBoxInfo.Create createInfo = gemBoxService.create(command);

        assertThat(createInfo.getName()).isEqualTo(name);
        assertThat(createInfo.getUserId()).isEqualTo(userId);
    }

    @DisplayName("잼박스를 초과 시 예외를 발생한다")
    @Test
    void 잼박스_초과_저장시_예외발생() {

        final String name = "테스트";
        final Long userId = 1L;

        when(gemBoxService.isFull(any())).thenReturn(true);

        GemBoxCommand.Create command = GemBoxCommand.Create.builder()
            .name(name)
            .linkIds(Arrays.asList(1L))
            .userId(userId)
            .build();

        assertThatThrownBy(() -> gemBoxService.create(command))
            .isInstanceOf(BusinessException.class)
            .hasMessageContaining(ErrorCode.GEMBOX_IS_FULL.getMessage());
    }

    @DisplayName("잼박스 중복 저장 시 예외를 발생시킨다")
    @Test
    void 잼박스_중복_저장시_예외발생() {

        final String name = "테스트";
        final Long userId = 1L;

        when(gemBoxService.isExisted(any())).thenReturn(true);

        GemBoxCommand.Create command = GemBoxCommand.Create.builder()
            .name(name)
            .linkIds(Arrays.asList(1L))
            .userId(userId)
            .build();

        assertThatThrownBy(() -> gemBoxService.create(command))
            .isInstanceOf(BusinessException.class)
            .hasMessageContaining(ErrorCode.GEMBOX_ALREADY_EXISTED.getMessage());
    }

    @DisplayName("잼박스를 수정한다")
    @Test
    void 잼박스_수정() {

        final String name = "테스트";
        final Long userId = 1L;
        final Long id = 1L;

        GemBox gemBox = GemBox.builder()
            .name(name)
            .userId(userId)
            .build();

        when(gemBoxPersistence.find(anyLong(), anyLong())).thenReturn(Optional.of(gemBox));
        when(gemBoxService.isExisted(any())).thenReturn(false);

        GemBoxCommand.Update command = GemBoxCommand.Update.builder()
            .id(id)
            .name(name)
            .userId(userId)
            .build();

        gemBoxService.update(command);
    }

    @DisplayName("잼박스 수정시 중복일 경우 예외가 발생한다")
    @Test
    void 잼박스_수정시_중복일경우_예외발생() {

        final String name = "테스트";
        final Long userId = 1L;
        final Long id = 1L;

        GemBox gemBox = GemBox.builder()
            .name(name)
            .userId(userId)
            .build();

        when(gemBoxService.isExisted(any())).thenReturn(true);

        GemBoxCommand.Update command = GemBoxCommand.Update.builder()
            .id(id)
            .name(name)
            .userId(userId)
            .build();

        assertThatThrownBy(() -> gemBoxService.update(command))
            .isInstanceOf(BusinessException.class)
            .hasMessageContaining(ErrorCode.GEMBOX_ALREADY_EXISTED.getMessage());
    }

    @DisplayName("잼박스 수정시 존재하지 않는 잼박스일 경우 예외가 발생한다")
    @Test
    void 잼박스_수정시_존재하지않는잼박스일경우_예외발생() {

        final String name = "테스트";
        final Long userId = 1L;
        final Long id = 1L;

        doThrow(new BusinessException(ErrorCode.GEMBOX_ALREADY_EXISTED)).when(gemBoxPersistence)
            .find(anyLong(), anyLong());

        GemBoxCommand.Update command = GemBoxCommand.Update.builder()
            .id(id)
            .name(name)
            .userId(userId)
            .build();

        assertThatThrownBy(() -> gemBoxService.update(command))
            .isInstanceOf(BusinessException.class)
            .hasMessageContaining(ErrorCode.GEMBOX_ALREADY_EXISTED.getMessage());
    }
}
