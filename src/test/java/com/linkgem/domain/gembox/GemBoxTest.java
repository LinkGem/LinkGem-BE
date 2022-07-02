package com.linkgem.domain.gembox;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import com.linkgem.domain.link.Link;
import com.linkgem.infrastructure.config.TestQueryDslConfig;
import com.linkgem.infrastructure.gembox.GemBoxRepository;
import com.linkgem.infrastructure.link.LinkRepository;

@Import(TestQueryDslConfig.class)
@DataJpaTest
@ActiveProfiles("test")
class GemBoxTest {

    @Autowired
    private GemBoxRepository gemBoxRepository;

    @Autowired
    private LinkRepository linkRepository;

    @Autowired
    private TestEntityManager em;

    @Test
    public void 잼박스를_저장한다() {
        GemBox newGemBox = GemBoxCommand.Create
            .builder()
            .userId(1L)
            .name("test")
            .build()
            .toEntity();

        GemBox savedGemBox = gemBoxRepository.save(newGemBox);

        Optional<GemBox> findGemBox = gemBoxRepository.findById(savedGemBox.getId());
        Assertions.assertTrue(findGemBox.isPresent());
    }

    @Transactional
    @Test
    public void 링크를_저장한다() {
        Link link = Link.builder()
            .url("www.naver.com")
            .memo("GOOD")
            .userId(1L)
            .build();

        Link savedLink = linkRepository.save(link);

        Assertions.assertTrue(linkRepository.findById(savedLink.getId()).isPresent());
    }

    @Test
    public void 잼박스에_링크를_저장한다() {
        Link link = Link.builder()
            .url("www.naver.com")
            .memo("GOOD")
            .userId(1L)
            .build();

        Link savedLink = linkRepository.save(link);

        GemBox newGemBox = GemBoxCommand.Create
            .builder()
            .userId(1L)
            .name("test")
            .build()
            .toEntity();

        newGemBox.addLink(savedLink);

        GemBox savedGemBox = gemBoxRepository.save(newGemBox);

        savedGemBox.getLinks().forEach(System.out::println);

        gemBoxRepository.delete(savedGemBox);
        em.flush();
    }

    @Test
    public void 잼박스_삭제시_저장된_링크들도_함께삭제된다() {
        Link link = Link.builder()
            .url("www.naver.com")
            .memo("GOOD")
            .userId(1L)
            .build();

        Link link2 = Link.builder()
            .url("www.google.com")
            .memo("GOOD")
            .userId(1L)
            .build();

        Link savedLink1 = linkRepository.save(link);
        Link savedLink2 = linkRepository.save(link2);

        GemBox newGemBox = GemBoxCommand.Create
            .builder()
            .userId(1L)
            .name("test")
            .build()
            .toEntity();

        newGemBox.addLink(savedLink1);
        newGemBox.addLink(savedLink2);

        GemBox savedGemBox = gemBoxRepository.save(newGemBox);

        Assertions.assertTrue(gemBoxRepository.findById(newGemBox.getId()).isPresent());
        Assertions.assertTrue(linkRepository.findById(newGemBox.getLinks().get(0).getId()).isPresent());
        Assertions.assertTrue(linkRepository.findById(newGemBox.getLinks().get(1).getId()).isPresent());

        gemBoxRepository.delete(savedGemBox);
        em.flush();

        Assertions.assertFalse(linkRepository.findById(savedLink1.getId()).isPresent());
        Assertions.assertFalse(linkRepository.findById(savedLink2.getId()).isPresent());
        Assertions.assertFalse(gemBoxRepository.findById(newGemBox.getId()).isPresent());

    }

}
