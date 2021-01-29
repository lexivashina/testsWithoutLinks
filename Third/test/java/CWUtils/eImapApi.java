package CWUtils;

import org.testng.Assert;

import javax.mail.*;
import javax.mail.internet.MimeMultipart;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class EmailAuthenticator extends javax.mail.Authenticator
{
    private String login;
    private String password;
    public EmailAuthenticator (final String login, final String password)
    {
        this.login    = login;
        this.password = password;
    }
    public PasswordAuthentication getPasswordAuthentication()
    {
        return new PasswordAuthentication(login, password);
    }
}

public class eImapApi {
    private final String subject              = "HyperID Email Validation";
    private String IMAP_AUTH_EMAIL      = "";
    private String IMAP_AUTH_PWD        = "";
    private String IMAP_Server          = "";
    private String IMAP_Port            = "993";

    private boolean messageFound        = false;
    private final String verificationCodePattern = "([-a-zA-z_0-9]{10}\r\n)";
    private final String verificationCodeStartSplitter = "Email verification code: ";
    private final String verificationCodeEndSplitter = "\r\n";

    private Message targetMessage;
    private String verificationCode     = "";
    private String targetFolder;
    private String[] spamFolders = { "Junk", "\\Junk" };

    private Properties properties;
    private Session session;
    private Store store;
    private Folder folder;

    private boolean folderOpened = false;

    private HashMap<String, String> IMAPServers = new HashMap<String, String>() {
        {
            put("gmail.com", "imap.gmail.com");
            put("onecrypt.net", "mail.onecrypt.net");
            put("outlook.com", "imap-mail.outlook.com");
            put("office", "outlook.office365.com");
        }
    };

    public eImapApi(String email, String password) {
        this.IMAP_AUTH_EMAIL = email;
        this.IMAP_AUTH_PWD = password;
        this.targetFolder = "INBOX";

        this.setUpProperties();
        this.setUpIMAPServer();
        this.setUpSession();

        this.findTargetMessage(subject);
    }

    private void setUpProperties()
    {
        this.properties = new Properties();
        properties.put("mail.debug", "false");
        properties.put("mail.store.protocol", "imaps");
        properties.put("mail.imap.ssl.enable", "true");
        properties.put("mail.imap.port", IMAP_Port);
    }

    private void setUpSession()
    {
        Authenticator auth = new EmailAuthenticator(IMAP_AUTH_EMAIL,
                IMAP_AUTH_PWD);
        this.session = Session.getDefaultInstance(properties, auth);
        this.session.setDebug(false);
    }

    private void connectToRemoteTargetFolder() { connectToRemoteTargetFolder(targetFolder); }

    private void connectToRemoteTargetFolder(String folder)
    {
        try {
            this.store = session.getStore();
            this.store.connect(IMAP_Server, IMAP_AUTH_EMAIL, IMAP_AUTH_PWD);
            this.folder = store.getFolder(folder);
            this.folder.open(Folder.READ_ONLY);
            folderOpened = true;
        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        } catch (MessagingException e) {
            folderOpened = false;
            e.printStackTrace();
        }
    }

    private void closeConnection()
    {
        try {
            this.folder.close(true);
            this.store.close();
        } catch (MessagingException e) {
            e.printStackTrace();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }
    }

    private void findTargetMessage(String subject)
    {
        do {
            this.connectToRemoteTargetFolder();
            targetMessage = (folderOpened) ? getTargetMessage(subject) : null;

            if (messageFound)
                this.setVerificationCode();
            else
                tryToFindTargetMessageInSpam();

            this.closeConnection();
        } while (!messageFound);
    }

    private void tryToFindTargetMessageInSpam()
    {
        for (String spamFolder : spamFolders)
        {
            this.connectToRemoteTargetFolder(spamFolder);
            targetMessage = (folderOpened) ? getTargetMessage(subject) : null;
            if (messageFound) {
                this.setVerificationCode();
                break;
            }
        }
    }

    private Message getTargetMessage(String subject)
    {
        Message[] messages = new Message[0];
        try {
            messages = this.folder.getMessages();
        } catch (MessagingException e) {
            e.printStackTrace();
        }

        int latestMessagesCount = (int) Math.ceil(messages.length * 0.01);

        Message[] latestMessages = Arrays.copyOfRange(messages, messages.length - latestMessagesCount, messages.length);

        Collections.reverse(Arrays.asList(latestMessages));

        Message message = Arrays.stream(latestMessages).filter(m -> {
            try {
                return m.getSubject().equals(subject);
            } catch (MessagingException e) {
                e.printStackTrace();
            }
            return false;
        }).findFirst().orElse(null);

        messageFound = message != null;
        return message;
    }

    private void setUpIMAPServer() {
        String domain = this.IMAP_AUTH_EMAIL.split("@")[1];

        this.IMAP_Server = this.IMAPServers.get(domain);
    }

    private String parseVerificationCode(String content)
    {
        String contentSeparatedParts[] = content.split(verificationCodeStartSplitter);

        Pattern verificationCodeFilter = Pattern.compile(verificationCodePattern);

        String verificationCode = Arrays.stream(contentSeparatedParts)
                .filter(part -> verificationCodeFilter.matcher(part).find())
                .map(part -> part = part.split(verificationCodeEndSplitter)[0])
                .distinct()
                .findFirst()
                .orElse(null);

        Assert.assertTrue(verificationCode != null);
        return verificationCode;
    }

    private void setVerificationCode() {
        try {
            String content = "";

            if ( this.IMAP_AUTH_EMAIL.contains("@outlook.com") || this.IMAP_AUTH_EMAIL.contains("@gmail.com"))
            {
                MimeMultipart multipart = null;
                try {
                    multipart = ((MimeMultipart) ((MimeMultipart) targetMessage.getContent()).getBodyPart(0).getContent());
                    content =  (multipart.getCount() > 1) ? multipart.getBodyPart(1).getContent().toString() : multipart.getBodyPart(0).getContent().toString();
                } catch (ClassCastException e) {
                    content = (((MimeMultipart) targetMessage.getContent()).getBodyPart(0).getContent()).toString();
                }
            }
            else content = (((MimeMultipart) targetMessage.getContent()).getBodyPart(0).getContent()).toString();

            this.verificationCode = parseVerificationCode(content);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    public String getVerificationCode() { return this.verificationCode; }
}

