import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;

class ClientHandler extends Thread {

  private DataInputStream fromClient = null;
  private PrintStream toClient = null;
  private Socket clientSocket = null;
  private ArrayList <User> tabUsers = null;
  private ArrayList <Email> tabEmails = null;
  private User actualUser = null;
  private User actualUserToSend = null;
  private Email actualEmail = null;
  private int userIndex = 0;


  public ClientHandler(Socket clientSocket, ArrayList <User> tabUsers) {
    this.clientSocket = clientSocket;
    this.tabUsers = tabUsers;
    
  }

  @SuppressWarnings("deprecation")
public void run() {

    try {
      /*
       * Create input and output streams for this client.
       */
      fromClient = new DataInputStream(clientSocket.getInputStream());
      toClient = new PrintStream(clientSocket.getOutputStream());
      
      String optionClient = null;
      do{
      toClient.println(" 1- Login; 2- Create Account ; 0 - Exit");
      optionClient = fromClient.readLine();

	      //Login
	      if(optionClient.equals("1")){   
	    	  tryLogin();
	      } 
	      //Create Account
	      else if(optionClient.equals("2")){
	    	  newUser();	   	  
	      }
	      else if(optionClient.equals("otherServer")){
	    	  sendEmailFromOtherServerToMyself();	   	  
	      }
	      
      }while(optionClient.equals("0") == false && actualUser == null);
      
      if(optionClient.equals("0") == false){
	      do{
	      toClient.println("Welcome " + actualUser.getRealName() + " you have " + actualUser.tabEmailsUser.size() + " emails!");
	      toClient.println(" 1- Send Email; 2- Read Email;3- List Email; 4-Delete Email; 0- Exit");
	      optionClient = fromClient.readLine();
	      
	      //Send Email
	      if(optionClient.equals("1")){   
	    	  sendEmail();
	      } 
	      //Read Email
	      else if(optionClient.equals("2")){
	    	  readEmail();
	      } 
	      //List Email
	      if(optionClient.equals("3")){   
	    	  listEmail();
	      } 
	      //Delete Email
	      else if(optionClient.equals("4")){
	    	  deleteEmail();
	      }
	      
	      }while(optionClient.equals("0") == false);
      }
 
      toClient.println("See you soon!");
      if(actualUser != null)
    	  System.out.println("User " +actualUser.getLogin() +" as disconnected" );
      System.out.println("Disconnection!");
      fromClient.close();
      toClient.close();
      clientSocket.close();
    } catch (IOException e) {
    }
  }

	@SuppressWarnings("deprecation")
	private void sendEmailFromOtherServerToMyself() {
		String n = null;
		String f = null;
		String t = null;
		String text = null;
		int userToEmail = 0;
		
	    try {
			 n = fromClient.readLine();
		     f = fromClient.readLine();
		     t = fromClient.readLine();
		     text = fromClient.readLine();
		    
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
	    int i = 0;
	    for(User u: tabUsers)
		{
			if(u.getRealName().equals(n)){
				userToEmail = i;
				actualUser = u;
				}
			i++;
		}
	    
	    if(actualUser != null){
		 tabEmails = actualUser.getEmailTab();
		 tabEmails.add(new Email(f,t,text));
		 actualUser.setEmailTab(tabEmails);
		 tabUsers.set(userToEmail, actualUser);
		 toClient.println("true");
	    }
	    else
	    	 toClient.println("false");
		 
	    
	
}

	@SuppressWarnings("deprecation")
	private void deleteEmail() {
		toClient.println("Insert Email number to delete!");
		String emailIndex = null;
		try {
			emailIndex = fromClient.readLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(emailIndex != null && Integer.parseInt(emailIndex) < tabEmails.size()){
			tabEmails.remove(tabEmails.get(Integer.parseInt(emailIndex)));
			actualUser.setEmailTab(tabEmails);
			tabUsers.set(userIndex, actualUser);
			toClient.println("Sucess Removing Element");
		}
		else
			toClient.println("Element NOT FOUND");
}

	private void listEmail() {
		toClient.println("Email List! Total :"+ tabEmails.size());
		for(int i = 0; i < tabEmails.size(); i++){
			toClient.println("Number:" + i + " From :" + tabEmails.get(i).getFrom() + " Title: " + tabEmails.get(i).getTitle());
		}
		toClient.println("DONE!");
}

	@SuppressWarnings("deprecation")
	private void readEmail() {
		toClient.println("Insert Email number to read!");
		String emailIndex = null;
		try {
			emailIndex = fromClient.readLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(emailIndex != null && Integer.parseInt(emailIndex) < tabEmails.size()){
			actualEmail = tabEmails.get(Integer.parseInt(emailIndex));
			
			toClient.println("From: " + actualEmail.getFrom());
			toClient.println("Title: " + actualEmail.getTitle());
			toClient.println("Text: " + actualEmail.getText());
			toClient.println("Sucess Reading Element");
		}
		else {
			toClient.println("From: NOT FOUND");
			toClient.println("Title: NOT FOUND");
			toClient.println("Text: NOT FOUND");
			toClient.println("ELEMENT NOT FOUND");
		}
	
}

	@SuppressWarnings("deprecation")
	private void sendEmail() {
		actualEmail = new Email(null, null, null);
		
		toClient.println("Insert Title");
		try {
			actualEmail.setTitle(fromClient.readLine());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		toClient.println("Insert Text");
		try {
			actualEmail.setText(fromClient.readLine());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		actualEmail.setFrom(actualUser.getRealName());
		
		toClient.println("Insert Number off receivers");
		int howMany = 0;
		try {
			howMany = Integer.parseInt(fromClient.readLine());
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(howMany != 0){
			String to = null;
			for(int i = 0; i < howMany; i++){
				try {
					to = fromClient.readLine();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if(to != null){
					 actualUserToSend = findByName(to);
					if( actualUserToSend!= null){
						tabEmails = actualUserToSend.getEmailTab();
						tabEmails.add(actualEmail);
						 actualUserToSend.setEmailTab(tabEmails);
						tabUsers.add(userIndex,  actualUserToSend);
						toClient.println("SUCESS SENDING TO " + to);
					}
					else{
						if(trySendAnotherServer(actualEmail,to) == true)
							toClient.println("SUCESS SENDING TO " + to + " HOSTED IN ANOTHER SERVER");
						else{
							toClient.println("ERROR SENDING TO " + to + " IN BOTH SERVERS");
						}
					}
				}
				else{
					toClient.println("ERROR SENDING TO " + to);
				}
				
					
			}
			toClient.println("DONE!");
		}
	// TODO Auto-generated method stub
	
}

	private boolean trySendAnotherServer(Email emailToNewServer, String realNameToNewServer) {

        try {
    		int serverPort = 9001;

            InetAddress host = InetAddress.getByName("localhost"); 
            System.out.println("Connecting to server on port " + serverPort); 
			Socket socket = new Socket(host,serverPort);
			
	        System.out.println("Just connected to " + socket.getRemoteSocketAddress()); 
	        
	        PrintWriter toServer = new PrintWriter(socket.getOutputStream(),true);
	        BufferedReader fromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
	        String admin = null;
			admin = fromServer.readLine();
			admin = "otherServer";
		    toServer.println(admin);
		    toServer.println(realNameToNewServer);
		    toServer.println(emailToNewServer.getFrom());
		    toServer.println(emailToNewServer.getTitle());
		    toServer.println(emailToNewServer.getText());
		    String valid = "false";
		    valid = fromServer.readLine();
		    
		    if(valid.equals("true"))
		    	return true;
		    
		    return false;
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		// TODO Auto-generated method stub
		return false;
	}

	@SuppressWarnings("deprecation")
	private void newUser() {
    	//name
        toClient.println("Real Name:");
        String realName = null;
		try {
			realName = fromClient.readLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
        //login
        toClient.println("Login:");
        String login = null;
		try {
			login = fromClient.readLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
        //password
        toClient.println("Password:");
        String password = null;
		try {
			password = fromClient.readLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
        //sucess? FALTA VALIDACAO SE JA EXISTIR LOGIN
        tabUsers.add(new User (login, password, realName, new ArrayList <Email>()));
        
        toClient.println("SUCESSO!");
        System.out.println("Novo user com o login name " + login);
        
	
}

	@SuppressWarnings("deprecation")
	private void tryLogin(){
	      toClient.println("Enter your login:");
	      String actualLogin = null;
	      String actualPassword = null;
		try {
			actualLogin = fromClient.readLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	      toClient.println("Enter your password:");
		try {
			actualPassword = fromClient.readLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	      
	      actualUser =confirmaLogin(actualLogin, actualPassword,tabUsers);
	      
	      if(actualUser != null){
	    	  tabEmails = actualUser.getEmailTab();
	    	  toClient.println("SUCESS LOGIN!");
	    	  System.out.println("User " + actualUser.getLogin() +" connected");
	      }
	      else{
	    	  toClient.println("INVALID LOGIN!");
	      }
}

	private User confirmaLogin(String actualLogin, String actualPassword, ArrayList<User> tabUsers2) {
			int i = 0;
			for(User u: tabUsers)
		{
			if(u.getLogin().equals(actualLogin) && u.getPassword().equals(actualPassword)){
				userIndex = i;
				return u;
				}
			i++;
		}
		
		return null;
	}
	
	private User findByName(String name){
		int i = 0;
		for(User u: tabUsers){
		if(u.getRealName().equals(name)){
			userIndex = i;
			return u;
			}
		i++;
	}
	return null;
		
	}
  
  
  
}
