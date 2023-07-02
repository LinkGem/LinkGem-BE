package com.linkgem.presentation.gembox.dto;

import com.linkgem.domain.gembox.GemBox;
import com.linkgem.domain.gembox.GemBoxCommand;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class GemBoxRequest {

    @ApiModel(description = "잼박스 생성 요청")
    @Setter
    @AllArgsConstructor
    public static class CreateGemBoxRequest {

        @ApiModelProperty(value = "잼박스 이름", example = "잼박스 이름", required = true)
        @NotBlank(message = "잼박스 이름은 필수 값입니다.")
        @Pattern(regexp = GemBox.GEMBOX_NAME_REGEX, message = "잼박스 이름은 최대 8글자 숫자/한글/영문/공백 문자만 입력이 가능합니다.")
        private String name;

        @ApiModelProperty(value = "링크 아이디 목록", required = false)
        private List<Long> linkIds;

        public GemBoxCommand.Create to(Long userId) {
            return GemBoxCommand.Create.builder()
                .name(this.name)
                .linkIds(this.linkIds)
                .userId(userId)
                .build();
        }
    }

    @ApiModel(description = "잼박스 수정 요청")
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UpdateGemboxRequest {

        @ApiModelProperty(value = "잼박스 이름", example = "잼박스 이름", required = true)
        @NotBlank(message = "잼박스 이름은 필수 값입니다.")
        @Pattern(regexp = GemBox.GEMBOX_NAME_REGEX, message = "잼박스 이름은 최대 8글자 숫자/한글/영문/공백 문자만 입력이 가능합니다.")
        private String name;

        public GemBoxCommand.Update to(Long userId, Long id) {
            return GemBoxCommand.Update.builder()
                .id(id)
                .name(this.name)
                .userId(userId)
                .build();
        }
    }

    @ApiModel(description = "링크를 잼박스로 이동 요청")
    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    public static class PutLinksToGemboxRequest {

        @ApiModelProperty(value = "링크 아이디 목록", example = "링크 아이디 목록", required = true)
        @NotNull(message = "링크 ID 목록을 필수 값입니다")
        @Size(min = 1, message = "최소 한개 이상의 링크 ID가 있어야합니다.")
        private List<Long> links;
    }

    @ApiModel(description = "잼박스 생성 요청")
    @Setter
    @AllArgsConstructor
    public static class MergeMultiGemBoxRequest {

        @ApiModelProperty(value = "잼박스 이름", example = "잼박스 이름", required = true)
        @NotBlank(message = "잼박스 이름은 필수 값입니다.")
        @Pattern(regexp = GemBox.GEMBOX_NAME_REGEX, message = "잼박스 이름은 최대 8글자 숫자/한글/영문/공백 문자만 입력이 가능합니다.")
        private String name;

        @ApiModelProperty(value = "합쳐질 잼박스 아이디 목록", required = false)
        private List<Long> gemboxIds;

        public GemBoxCommand.MergeMulti to(Long userId) {
            return GemBoxCommand.MergeMulti.builder()
                .name(this.name)
                .gemboxIds(this.gemboxIds)
                .userId(userId)
                .build();
        }
    }

}
