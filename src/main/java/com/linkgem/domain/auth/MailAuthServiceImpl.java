package com.linkgem.domain.auth;

import com.linkgem.domain.user.User;
import com.linkgem.domain.user.UserReader;
import com.linkgem.presentation.common.exception.BusinessException;
import com.linkgem.presentation.common.exception.ErrorCode;
import java.net.URI;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;
import javax.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
@RequiredArgsConstructor
@Slf4j
public class MailAuthServiceImpl implements MailAuthService {

    private final JavaMailSender javaMailSender;
    private final UserReader userReader;
    private final TemplateEngine templateEngine;
    private final AuthReader authReader;
    private final AuthStore authStore;


    @Override
    @Async
    @Transactional
    public void mailSend(String emailAddress, Long userId) {
        User user = userReader.find(userId).orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));
        String nickname = user.getNickname();
        String certificationCode = UUID.randomUUID().toString();
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        String replacedEmailAddress = emailAddress.replace("\"", "");
        log.info("이메일 주소: " + replacedEmailAddress);
        try {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
            messageHelper.setTo(replacedEmailAddress);
            messageHelper.setSubject("링크잼 이메일 인증메일 입니다.");
            Context context = new Context();
            context.setVariable("name", nickname);
            context.setVariable("redirectUrl",
                    "https://dev.linkgem.co.kr/api/v1/auth/mail/check?randomId=" + certificationCode + "&userId="
                            + userId.toString());
            String html = templateEngine.process("mailFormat", context);
            messageHelper.setText(html, true);
            javaMailSender.send(mimeMessage);
        } catch (Exception e) {
            log.error("mail sending errors : {} {}", e.toString(),e.getMessage());
            throw new BusinessException(ErrorCode.MAIL_SEND_ERROR);
        }
        LocalDateTime expiredDate = LocalDateTime.now().plusSeconds(180);
        Optional<Auth> foundAuth = authReader.findByUserIdAndAuthType(userId, AuthType.MAIL);
        if (foundAuth.isEmpty()) {
            Auth auth = Auth.builder()
                    .certificationCode(certificationCode)
                    .expiredDate(expiredDate)
                    .authType(AuthType.MAIL)
                    .user(user)
                    .build();
            authStore.create(auth);
        } else {
            Auth auth = foundAuth.get();
            auth.changeMailAuth(expiredDate, certificationCode);
        }
    }

    @Override
    @Transactional
    public URI mailCheck(Long userId, String randomId) {
        Optional<Auth> auth = authReader.findByUserIdAndAuthType(userId, AuthType.MAIL);
        if (auth.isEmpty() || !auth.get().getCertificationCode().equals(randomId) || auth.get().getExpiredDate().isBefore(LocalDateTime.now())) {
            return URI.create("https://dev-front.linkgem.co.kr/email/fail");
        } else {
            auth.get().authenticate();
            return URI.create("https://dev-front.linkgem.co.kr/email/success");
        }
    }

    @Override
    @Transactional
    public boolean mailConfirm(String emailAddress, Long userId) {
        User user = userReader.find(userId).orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));
        Optional<Auth> auth = authReader.findByUserIdAndAuthType(userId, AuthType.MAIL);
        if (auth.isEmpty()) {
            return false;
        } else if (auth.get().isAuth()) {
            user.saveMailEmail(emailAddress);
        }
        return auth.get().isAuth();
    }
}
