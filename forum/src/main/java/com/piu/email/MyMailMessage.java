package com.piu.email;


import java.util.Random;
import org.springframework.mail.SimpleMailMessage;


@SuppressWarnings("all")
public class MyMailMessage extends SimpleMailMessage{
	private static final char[] chars = {
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
            'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n',
            'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
            'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N',
            'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};
    // 字符数量
    private static final int SIZE = 5;
    //邮箱验证码
	private  String emailVerif;
   
    public MyMailMessage(){
    	super();
    	//发送者
    	super.setFrom("m13935385051@163.com");
    }
    
    public MyMailMessage(String to) {
    	this();
    	//接收者
    	super.setTo(to);
    	createCode();
    	//发送的标题
    	super.setSubject("欢迎加入我们的BBS");
    	//发送的内容
    	super.setText(emailVerif);
    }
    
    
    public void createCode(){
    	StringBuffer sb = new StringBuffer();
    	Random ran = new Random();
        for (int i = 0; i <SIZE; i++) {
            // 取随机字符索引
            int n = ran.nextInt(chars.length);
            sb.append(chars[n]);
        }
        this.emailVerif = sb.toString(); 
    }

	public String getEmailVerif() {
		return emailVerif;
	}

	public void setEmailVerif(String emailVerif) {
		this.emailVerif = emailVerif;
	}
}
