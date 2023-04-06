package com.linkgem.domain.gembox;

import static org.assertj.core.api.Assertions.*;

import com.linkgem.domain.gembox.domain.GemBox;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import com.linkgem.config.TestQueryDslConfig;
import com.linkgem.domain.gembox.persistence.GemBoxRepository;

@Import(TestQueryDslConfig.class)
@DataJpaTest
@ActiveProfiles("test")
class GemBoxRepositoryTest {

    @Autowired
    private GemBoxRepository gemBoxRepository;

    @DisplayName("잼박스를 생성하여 저장한다.")
    @Test
    void 잼박스_생성_저장() {
        GemBox gemBox = createGemBox();

        assertThat(gemBox.getId()).isNotNull();
    }

    @DisplayName("단일 잼박스를 조회한다")
    @Test
    void 단일_잼박스_조회() {

        GemBox newGemBox = createGemBox();
        GemBox findGemBox = gemBoxRepository.findById(newGemBox.getId()).get();

        assertThat(findGemBox.getId()).isEqualTo(newGemBox.getId());
        assertThat(findGemBox.getName()).isEqualTo(newGemBox.getName());
        assertThat(findGemBox.getUserId()).isEqualTo(newGemBox.getUserId());

    }

    private GemBox createGemBox() {
        final String name = "테스트";
        final Long userId = 1L;

        GemBox gemBox = GemBox.builder()
            .name(name)
            .userId(userId)
            .build();

        return gemBoxRepository.save(gemBox);

    }

}
