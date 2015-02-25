// Server Side
import java.net.*;
import java.util.ArrayList;
import java.io.*;

@SuppressWarnings("serial")
public class Server implements java.io.Serializable{ 
	
	public volatile  ArrayList <User> tabUsers = new ArrayList<User>();
	ArrayList <Email> adminEmails = new ArrayList<Email>();
	User admin = new User("admin", "1234", "Jose Marcelino", adminEmails);
	
	Socket server;
	
	public void run(int serverPort,String fileName) {

	  	try {
			load(fileName);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	  	
	  	if(tabUsers.isEmpty()){
	  		tabUsers.add(admin);
	  		
	  		try {
				save(fileName);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	  	}
	  	
	  try {
		 
	    ServerSocket serverSocket = new ServerSocket(serverPort);
	    serverSocket.setSoTimeout(1000000); 
	    while(true) {
	      System.out.println("Waiting for client on port " + serverSocket.getLocalPort() + "..."); 
	      server = serverSocket.accept();
	      if(server != null){
	    	  System.out.println("New Connection!");
	    	  new Thread(new ClientHandler(server,tabUsers)).start();
	      }
	      save(fileName);
	    }
	  }
	  catch(UnknownHostException ex) {
	    ex.printStackTrace();
	  }
	  catch(IOException e){
	    e.printStackTrace();
	  }
  }
  
  void save(String fileName) throws IOException {

			FileOutputStream FOS = new FileOutputStream(fileName);
			ObjectOutputStream OOS = new ObjectOutputStream(FOS);

			for(User u: tabUsers)
			{
					OOS.writeObject(u);
			}
			OOS.close();
	}
  
	void load(String fileName) throws IOException, ClassNotFoundException {

			FileInputStream FIS = new FileInputStream(fileName);
			ObjectInputStream OIS = new ObjectInputStream(FIS);

			while(FIS.available() > 0)
			{
				tabUsers.add((User) OIS.readObject());
			}	
			OIS.close();
		
		
	}
 

public static void main(String[] args) {
	
   	BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	int serverPort = 0;
    System.out.println("PORT?");
    try {
		serverPort = Integer.parseInt(br.readLine());
	} catch (NumberFormatException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
    String fileName = null;
    System.out.println("File Load?");
    try {
		fileName = br.readLine();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
    Server srv = new Server();
    srv.run(serverPort,fileName);
  }
}