package com.linkgem.domain.link;

import com.linkgem.domain.user.User;
import com.linkgem.infrastructure.config.TestQueryDslConfig;
import com.linkgem.infrastructure.link.LinkRepository;
import com.linkgem.infrastructure.user.UserRepository;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import com.linkgem.domain.link.opengraph.OpenGraph;

@Import(TestQueryDslConfig.class)
@DataJpaTest
@ActiveProfiles("test")
class LinkTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private LinkRepository linkRepository;

    @Autowired
    private TestEntityManager em;

    @Test
    public void 링크를_저장한다() {
        OpenGraph openGraph = OpenGraph.builder()
            .description("description")
            .imageUrl("imageUrl")
            .title("title")
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

    public User createUser(String nickname, String email) {
        User user = User.builder()
            .email(email)
            .nickName(nickname)
            .build();

        return userRepository.save(user);
    }
}
