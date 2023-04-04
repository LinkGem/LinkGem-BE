package com.linkgem.domain;

import com.linkgem.domain.auth.Auth;
import com.linkgem.domain.auth.AuthPersistence;
import com.linkgem.domain.auth.AuthType;
import com.linkgem.domain.auth.MailAuthService;
import com.linkgem.domain.user.User;
import com.linkgem.domain.user.UserPersistence;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
public class test {

    @Autowired
    AuthPersistence authPersistence;

    @Autowired
    UserPersistence userPersistence;

    @Autowired
    MailAuthService mailAuthService;


    @Test
    @Rollback(value = false)
    @Transactional
    public void test1() throws InterruptedException {
        int threadCount = 10;
        ExecutorService executorService = Executors.newFixedThreadPool(32);
        CountDownLatch countDownLatch = new CountDownLatch(threadCount);
        for (int i = 0; i < threadCount; i++) {
            executorService.submit(()->{
                try {
                    mailAuthService.mailSend("hose0728@naver.com",6L);
                }finally {
                    countDownLatch.countDown();
                }
            });
        }
        countDownLatch.await();

    }

    @Test
    @Rollback(value = false)
    @Transactional
    public void test2(){
        User user = userPersistence.find(6L).orElseThrow();
        System.out.println("user.getNickname() = " + user.getNickname());
        LocalDateTime expiredDate = LocalDateTime.now().plusSeconds(180);
        Optional<Auth> foundAuth = authPersistence.findByUserIdAndAuthType(6L, AuthType.MAIL);
        System.out.println("foundAuth.isEmpty() = " + foundAuth.isEmpty());
        if (foundAuth.isEmpty()) {
            Auth auth = Auth.builder()
                    .certificationCode("test")
                    .expiredDate(expiredDate)
                    .authType(AuthType.MAIL)
                    .user(user)
                    .build();
            authPersistence.create(auth);
        } else {
            Auth auth = foundAuth.get();
            System.out.println("auth.getId() = " + auth.getId());
            auth.changeMailAuth(expiredDate, "test2");
        }
    }
}
