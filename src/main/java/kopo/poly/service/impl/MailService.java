package kopo.poly.service.impl;

import kopo.poly.dto.MailDTO;
import kopo.poly.service.IMailService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.springframework.stereotype.Service;

@Slf4j
@Service("MailService")
public class MailService implements IMailService {

    static final String user = "rjsdndtptkd@naver.com"; // 본인 네이버 아이디
    static final String password = ""; // 본인 네이버 아이디

    @Override
    public int doSendMail(MailDTO pDTO) throws Exception {
        String charset = "UTF-8";
        log.info("메일 전송 시작");

        String email = pDTO.getToMail();
        log.info("email : " + email);
        String title = pDTO.getTitle();
        String body = pDTO.getContents();
        log.info("내용:" + body);


        HtmlEmail email1 = new HtmlEmail();
        email1.setHostName("smtp.naver.com");
        email1.setSmtpPort(465);
        email1.setAuthentication(user, password);

        email1.setSSLOnConnect(true);
        email1.setStartTLSEnabled(true);



        int res = 0;

        try{
            email1.setFrom("rjsdndtptkd@naver.com", "COQUAT", "utf-8");
            email1.addTo(email, "이름", "utf-8");
            email1.setSubject(title);


            StringBuffer msg = new StringBuffer();

            msg.append("<p>" +body + "</p>");


            email1.setHtmlMsg(msg.toString());
            email1.send();
            res = 1;
        } catch (EmailException e) {
            e.printStackTrace();
        }

        log.info("메일 전송 완료");
        return  res;
    }
}
