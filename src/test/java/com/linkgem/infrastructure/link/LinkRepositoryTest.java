package com.linkgem.infrastructure.link;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import com.linkgem.domain.link.Link;
import com.linkgem.domain.link.opengraph.OpenGraph;
import com.linkgem.domain.user.User;
import com.linkgem.infrastructure.config.TestQueryDslConfig;
import com.linkgem.infrastructure.user.UserRepository;

@Import(TestQueryDslConfig.class)
@DataJpaTest
@ActiveProfiles("test")
class LinkRepositoryTest {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private LinkRepository linkRepository;

    @Test
    void 링크를_저장한다() {
        OpenGraph openGraph = OpenGraph.builder()
            .description("description")
            .imageUrl("imageUrl")
            .title("title")
            .siteName("siteName")
            .build();

        Link link = Link.builder()
            .url("www.naver.com")
            .memo("GOOD")
            .user(createUser("tester", "www.naver.com"))
            .openGraph(openGraph)
            .build();

        linkRepository.save(link);

        Optional<Link> findLink = linkRepository.findById(link.getId());

        Assertions.assertTrue(findLink.isPresent());
    }

    @Test
    void 링크를_삭제한다() {
        Link link = Link.builder()
            .url("www.naver.com")
            .memo("GOOD")
            .user(createUser("tester", "www.naver.com"))
            .openGraph(null)
            .build();

        linkRepository.saveAndFlush(link);

        Optional<Link> findLink = linkRepository.findById(link.getId());

        Assertions.assertTrue(findLink.isPresent());

        linkRepository.delete(link);

        Optional<Link> findDeletedLink = linkRepository.findById(link.getId());

        Assertions.assertTrue(findDeletedLink.isEmpty());

    }

    public User createUser(String nickname, String email) {
        User user = User.builder()
            .loginEmail(email)
            .nickname(nickname)
            .oauthId("oauthId")
            .build();

        return userRepository.save(user);
    }

    @Test
    void 링크전부삭제(){
        User tester = createUser("tester", "www.naver.com");
        Link link = Link.builder()
            .url("www.naver.com")
            .memo("GOOD")
            .user(tester)
            .openGraph(null)
            .build();
        Link link2 = Link.builder()
            .url("www.naver.com")
            .memo("GOOD")
            .user(tester)
            .openGraph(null)
            .build();

        linkRepository.save(link);
        linkRepository.save(link2);
        System.out.println("link.getId() = " + link.getId());
        System.out.println("link2.getId() = " + link2.getId());
        Long id = tester.getId();
        linkRepository.deleteAllByUserId(id);
        Optional<Link> foundLink = linkRepository.findById(link.getId());
        Optional<Link> foundLink2 = linkRepository.findById(link2.getId());
        Assertions.assertTrue(foundLink.isEmpty());
        Assertions.assertTrue(foundLink2.isEmpty());

    }
}
