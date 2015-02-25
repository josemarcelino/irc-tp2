import java.util.ArrayList;


@SuppressWarnings("serial")
public class User implements java.io.Serializable{
	
	String login;
	String password;
	String realName;
	ArrayList <Email> tabEmailsUser;
	
	User(String login, String pw, String name, ArrayList<Email> arrayList){
		this.login = login;
		this.password = pw;
		this.realName = name;
		this.tabEmailsUser = arrayList;
	}
	
	String getLogin(){	
		return(this.login);
	}
	
	String getPassword(){	
		return(this.password);
	}
	
	String getRealName(){	
		return(this.realName);
	}
	
	ArrayList <Email> getEmailTab(){
		return(this.tabEmailsUser);
	}
	
	void setLogin(String login){	
		this.login = login;
	}
	
	void setPassword(String password){	
		this.password = password;
	}
	
	void setName(String realName){	
		this.realName = realName;
	}
	
	void setEmailTab(ArrayList <Email> tabEmails){
		this.tabEmailsUser = tabEmails;
	}
	
}
