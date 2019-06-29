package youke.common.utils;

/**
 * Created by Administrator on 2016/6/21 0021.
 */
import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.*;
import java.util.Properties;


public class MailUtil {

    private MimeMessage mimeMsg; //MIME邮件对象
    private Session session; //邮件会话对象
    private Properties props; //系统属性
    private boolean needAuth = false; //smtp是否需要认证
    //smtp认证用户名和密码
    private String username;
    private String password;
    private Multipart mp; //Multipart对象,邮件内容,标题,附件等内容均添加到其中后再生成MimeMessage对象

    /**
     * Constructor
     * @param smtp 邮件发送服务器
     */
    public MailUtil(String smtp){
        setSmtpHost(smtp);
        createMimeMessage();
    }

    /**
     * 设置邮件发送服务器
     * @param hostName String
     */
    public void setSmtpHost(String hostName) {
        System.out.println("设置系统属性：mail.smtp.host = "+hostName);
        if(props == null)
            props = System.getProperties(); //获得系统属性对象
        props.put("mail.smtp.host",hostName); //设置SMTP主机
    }


    /**
     * 创建MIME邮件对象
     * @return
     */
    public boolean createMimeMessage()
    {
        try {
            System.out.println("准备获取邮件会话对象！");
            session = Session.getDefaultInstance(props,null); //获得邮件会话对象
        }
        catch(Exception e){
            System.err.println("获取邮件会话对象时发生错误！"+e);
            return false;
        }

        System.out.println("准备创建MIME邮件对象！");
        try {
            mimeMsg = new MimeMessage(session); //创建MIME邮件对象
            mp = new MimeMultipart();

            return true;
        } catch(Exception e){
            System.err.println("创建MIME邮件对象失败！"+e);
            return false;
        }
    }

    /**
     * 设置SMTP是否需要验证
     * @param need
     */
    public void setNeedAuth(boolean need) {
        System.out.println("设置smtp身份认证：mail.smtp.auth = "+need);
        if(props == null) props = System.getProperties();
        if(need){
            props.put("mail.smtp.auth","true");
        }else{
            props.put("mail.smtp.auth","false");
        }
    }

    /**
     * 设置用户名和密码
     * @param name
     * @param pass
     */
    public void setNamePass(String name,String pass) {
        username = name;
        password = pass;
    }

    /**
     * 设置邮件主题
     * @param mailSubject
     * @return
     */
    public boolean setSubject(String mailSubject) {
        System.out.println("设置邮件主题！");
        try{
            mimeMsg.setSubject(mailSubject);
            return true;
        }
        catch(Exception e) {
            System.err.println("设置邮件主题发生错误！");
            return false;
        }
    }

    /**
     * 设置邮件正文
     * @param mailBody String
     */
    public boolean setBody(String mailBody) {
        try{
            BodyPart bp = new MimeBodyPart();
            bp.setContent(""+mailBody,"text/html;charset=GBK");
            mp.addBodyPart(bp);

            return true;
        } catch(Exception e){
            System.err.println("设置邮件正文时发生错误！"+e);
            return false;
        }
    }
    /**
     * 添加附件
     * @param filename String
     */
    public boolean addFileAffix(String filename) {
        System.out.println("增加邮件附件："+filename);
        try{
            BodyPart bp = new MimeBodyPart();
            FileDataSource fileds = new FileDataSource(filename);
            bp.setDataHandler(new DataHandler(fileds));
            bp.setFileName(MimeUtility.encodeText(fileds.getName()));
            mp.addBodyPart(bp);
            return true;
        } catch(Exception e){
            System.err.println("增加邮件附件："+filename+"发生错误！"+e);
            return false;
        }
    }

    /**
     * 设置发信人
     * @param from String
     */
    public boolean setFrom(String from) {
        System.out.println("设置发信人！");
        try{
            mimeMsg.setFrom(new InternetAddress(from)); //设置发信人
            return true;
        } catch(Exception e) {
            return false;
        }
    }
    /**
     * 设置收信人
     * @param to String
     */
    public boolean setTo(String to){
        if(to == null)return false;
        try{
            mimeMsg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            return true;
        } catch(Exception e) {
            return false;
        }
    }

    /**
     * 设置抄送人
     * @param copyto String
     */
    public boolean setCopyTo(String copyto)
    {
        if(copyto == null)return false;
        try{
            mimeMsg.setRecipients(Message.RecipientType.CC,(Address[]) InternetAddress.parse(copyto));
            return true;
        }
        catch(Exception e)
        { return false; }
    }

    /**
     * 发送邮件
     */
    public boolean sendOut()
    {
        try{
            mimeMsg.setContent(mp);
            mimeMsg.saveChanges();
            System.out.println("正在发送邮件....");

            Session mailSession = Session.getInstance(props,null);
            Transport transport = mailSession.getTransport("smtp");
            transport.connect((String)props.get("mail.smtp.host"),username,password);
            transport.sendMessage(mimeMsg,mimeMsg.getRecipients(Message.RecipientType.TO));
            //transport.sendMessage(mimeMsg,mimeMsg.getRecipients(Message.RecipientType.CC));
            //transport.send(mimeMsg);

            System.out.println("发送邮件成功！");
            transport.close();

            return true;
        } catch(Exception e) {
            e.printStackTrace();
            System.err.println("邮件发送失败！"+e);
            return false;
        }
    }

    /**
     * 调用sendOut方法完成邮件发送
     * @param smtp
     * @param from
     * @param to
     * @param subject
     * @param content
     * @param username
     * @param password
     * @return boolean
     */
    public static boolean send(String smtp,String from,String to,String subject,String content,String username,String password) {
        MailUtil theMailUtil = new MailUtil(smtp);
        theMailUtil.setNeedAuth(true); //需要验证

        if(!theMailUtil.setSubject(subject)) return false;
        if(!theMailUtil.setBody(content)) return false;
        if(!theMailUtil.setTo(to)) return false;
        if(!theMailUtil.setFrom(from)) return false;
        theMailUtil.setNamePass(username,password);

        if(!theMailUtil.sendOut()) return false;
        return true;
    }

    /**
     * 调用sendOut方法完成邮件发送,带抄送
     * @param smtp
     * @param from
     * @param to
     * @param copyto
     * @param subject
     * @param content
     * @param username
     * @param password
     * @return boolean
     */
    public static boolean sendAndCc(String smtp,String from,String to,String copyto,String subject,String content,String username,String password) {
        MailUtil theMailUtil = new MailUtil(smtp);
        theMailUtil.setNeedAuth(true); //需要验证

        if(!theMailUtil.setSubject(subject)) return false;
        if(!theMailUtil.setBody(content)) return false;
        if(!theMailUtil.setTo(to)) return false;
        if(!theMailUtil.setCopyTo(copyto)) return false;
        if(!theMailUtil.setFrom(from)) return false;
        theMailUtil.setNamePass(username,password);

        if(!theMailUtil.sendOut()) return false;
        return true;
    }

    /**
     * 调用sendOut方法完成邮件发送,带附件
     * @param smtp
     * @param from
     * @param to
     * @param subject
     * @param content
     * @param username
     * @param password
     * @param filename 附件路径
     * @return
     */
    public static boolean send(String smtp,String from,String to,String subject,String content,String username,String password,String filename) {
        MailUtil theMailUtil = new MailUtil(smtp);
        theMailUtil.setNeedAuth(true); //需要验证

        if(!theMailUtil.setSubject(subject)) return false;
        if(!theMailUtil.setBody(content)) return false;
        if(!theMailUtil.addFileAffix(filename)) return false;
        if(!theMailUtil.setTo(to)) return false;
        if(!theMailUtil.setFrom(from)) return false;
        theMailUtil.setNamePass(username,password);

        if(!theMailUtil.sendOut()) return false;
        return true;
    }

    /**
     * 调用sendOut方法完成邮件发送,带附件和抄送
     * @param smtp
     * @param from
     * @param to
     * @param copyto
     * @param subject
     * @param content
     * @param username
     * @param password
     * @param filename
     * @return
     */
    public static boolean sendAndCc(String smtp,String from,String to,String copyto,String subject,String content,String username,String password,String filename) {
        MailUtil theMailUtil = new MailUtil(smtp);
        theMailUtil.setNeedAuth(true); //需要验证

        if(!theMailUtil.setSubject(subject)) return false;
        if(!theMailUtil.setBody(content)) return false;
        if(!theMailUtil.addFileAffix(filename)) return false;
        if(!theMailUtil.setTo(to)) return false;
        if(!theMailUtil.setCopyTo(copyto)) return false;
        if(!theMailUtil.setFrom(from)) return false;
        theMailUtil.setNamePass(username,password);

        if(!theMailUtil.sendOut()) return false;
        return true;
    }


    /**
     * 发送邮件，含附件
     * @param to
     * @param subject
     * @param content
     * @param filename
     * @return
     */
    public static boolean send(String to,String subject,String content,String filename) {
//        MailUtil theMailUtil = new MailUtil(SkxxConfig.get("MAIL_SMTP"));
//        theMailUtil.setNeedAuth(true); //需要验证
//
//        if(!theMailUtil.setSubject(subject)) return false;
//        if(!theMailUtil.setBody(content)) return false;
//        if(filename!=null) {
//            if (!theMailUtil.addFileAffix(filename)) return false;
//        }
//        if(!theMailUtil.setTo(to)) return false;
//        if(!theMailUtil.setFrom(SkxxConfig.get("MAIL_FROM"))) return false;
//        theMailUtil.setNamePass(SkxxConfig.get("MAIL_USERNAME"),SkxxConfig.get("MAIL_PASSWORD"));
//
//        if(!theMailUtil.sendOut()) return false;
        return true;
    }

    public static void main(String[] args){
        String smtp = "smtp.wmq1688.com";
        String from = "service@wmq1688.com";
        String to = "liumingaccp@qq.com";
        String copyto = "";//抄送人
        String subject = "面试邀请";
        String content = "Dear 魏先生：\n" +
                "\n" +
                "   您的优秀专业素质给我们留下了深刻的印象，邀请您于5月9日，周一，上午10:30到我司参加技术经理的面试。\n" +
                "   【也欢迎您给我们推荐优秀的人选】\n" +
                "   收到邮件通知请回复。\n" +
                "\n" +
                "岗位职责（此工作地点为广州）：\n" +
                "1、参与系统模块的流程讨论及系统程序搭建，负责设计及开发文档的定稿；\n" +
                "2、负责项目核心模块开发工作，及时把关项目进度；\n" +
                "3、解决项目开发过程中遇到的技术和业务问题；\n" +
                "4、负责系统模块的规划设计，保证系统顺利实施上线；\n" +
                "5、负责开发团队的管理.\n" +
                "\n" +
                "任职要求：\n" +
                "1、熟练掌握和使用JAVA语言开发B/S架构软件，熟悉Struts、Spring、Hibernate等热门框架；\n" +
                "2、熟练运用XML, JavaScript，JQuery, Ajax，WebService等开发技术；\n" +
                "3、熟练掌握SVN、Ant、JUnit等相关辅助工具的使用；\n" +
                "4、熟练掌握MySql、Oracle11等常用关系数据库，熟练使用SQL语言；\n" +
                "5、熟悉TOMCAT应用服务器的部署和测试；\n" +
                "6、熟悉WINDOWS、Linux系统环境和常用操作指令，能独立完成相关系统环境搭建；\n" +
                "7、5年以上Java开发经验，2年项目管理经验；\n" +
                "8、认真仔细、主动、负责，能承受高强度压力，具有良好的团队合作精神。";
        String username="service@wmq1688.com";
        String password="Mike448532078";
        String filename = "d:\\about.html";
        MailUtil.send(smtp, from, to, subject, content, username, password,filename);
    }
}