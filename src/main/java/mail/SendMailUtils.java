package mail;

import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Properties;

/**邮件发送*/
public class SendMailUtils {
    //个人用户账号密码
    //邮箱服务器地址
    //收件人邮箱
    //收件内容
    private String myEmailAccount ;
    private String myEmailAuthPassword;
    private String receiveMailAccount;
    public  String myEmailSMTPHost = "smtp.163.com";
    //发送的信息 主题
    private String subject;
    //发送的信息 邮件正文
    private String content;
    private String contentType = "text/html;charset=UTF-8";

    public SendMailUtils(String myEmailAccount, String myEmailAuthPassword, String receiveMailAccount,String myEmailSMTPHost) {
        this.myEmailAccount = myEmailAccount;
        this.myEmailAuthPassword = myEmailAuthPassword;
        this.receiveMailAccount = receiveMailAccount;
        if (!("".equals(myEmailSMTPHost)||myEmailSMTPHost ==null)){
            this.myEmailSMTPHost = myEmailSMTPHost;
        }
    }

    public void sendMail(String subject,String content,String contentType) throws Exception {
        this.subject = subject;
        this.content = content;
        if (!("".equals(contentType)||contentType ==null)){
            this.contentType = contentType;
        }
        sendEmail();
    }

    private void sendEmail() throws Exception {
        // 1. 创建参数配置, 用于连接邮件服务器的参数配置
        Properties props = new Properties();                    // 参数配置
        props.setProperty("mail.transport.protocol", "smtp");   // 使用的协议（JavaMail规范要求）
        props.setProperty("mail.smtp.host", myEmailSMTPHost);   // 发件人的邮箱的 SMTP 服务器地址
        props.setProperty("mail.smtp.auth", "true");            // 需要请求认证

        // 2. 根据配置创建会话对象, 用于和邮件服务器交互
        Session session = Session.getInstance(props);
        // 设置为debug模式, 可以查看详细的发送 log
        session.setDebug(true);

        // 3. 创建一封邮件
        MimeMessage message = createMimeMessage(session,subject,content,contentType);

        // 4. 根据 Session 获取邮件传输对象
        Transport transport = session.getTransport();


        transport.connect(myEmailAccount, myEmailAuthPassword);

        // 6. 发送邮件, 发到所有的收件地址, message.getAllRecipients() 获取到的是在创建邮件对象时添加的所有收件人, 抄送人, 密送人
        transport.sendMessage(message, message.getAllRecipients());

        // 7. 关闭连接
        transport.close();
    }

    private  MimeMessage createMimeMessage(Session session,String subject,String content,String contentType) throws Exception {
        // 1. 创建一封邮件
        MimeMessage message = new MimeMessage(session);

        // 2. From: 发件人 显示的昵称(只用于显示, 没有特别的要求), 昵称的字符集编码
        message.setFrom(new InternetAddress(myEmailAccount, "LiXiang", StandardCharsets.UTF_8.name()));

        // 3. To: 收件人（可以增加多个收件人、抄送、密送）
        message.setRecipient(MimeMessage.RecipientType.TO, new InternetAddress(receiveMailAccount, receiveMailAccount, StandardCharsets.UTF_8.name()));

        // 4. Subject: 邮件主题
        message.setSubject(subject, StandardCharsets.UTF_8.name());

        // 5. Content: 邮件正文（可以使用html标签）
        message.setContent(content, contentType);
        // 6. 设置发件时间
        message.setSentDate(new Date());

        // 7. 保存设置
        message.saveChanges();

        return message;
    }

    public static void main(String[] args) {
         String myEmailAccount  = "17608065223@163.com";
         String myEmailAuthPassword = "GPYMPVVRPNFWNEBT";
         String receiveMailAccount = "982332072@qq.com";
//         String receiveMailAccount = "1782072968@qq.com";
          String myEmailSMTPHost = "smtp.163.com";
        //发送的信息 主题
         String subject = "?";
        //发送的信息 邮件正文
         String content = "你才猝死了 你再说我天天给你发邮件";
         String contentType = "text/html;charset=UTF-8";
        SendMailUtils utils = new SendMailUtils(myEmailAccount,myEmailAuthPassword,receiveMailAccount,myEmailSMTPHost);
        try {
            utils.sendMail(subject,content,contentType);
        } catch (Exception e) {
            System.out.println("发送邮件失败->"+e.getMessage());
        }
    }
}
