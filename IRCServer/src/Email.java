
@SuppressWarnings("serial")
public class Email implements java.io.Serializable{
	
	String from;
	String title;
	String text;
	
	Email(String from, String title, String text){
		this.from = from;
		this.title = title;
		this.text = text;
	}
	
	String getFrom(){	
		return(this.from);
	}
	
	String getTitle(){	
		return(this.title);
	}
	
	String getText(){	
		return(this.text);
	}
	
	void setFrom(String from){	
		this.from = from;
	}
	
	void setTitle(String title){	
		this.title = title;
	}
	
	void setText(String text){	
		this.text = text;
	}
}

