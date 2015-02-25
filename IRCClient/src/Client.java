import java.io.*;
import java.net.*;

public class Client {
  public void run() {
    try {
    	
    	//Engage Connection
    	BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        
        int serverPort = 0;
        System.out.println("PORT?");
        serverPort = Integer.parseInt(br.readLine());
        String hostName = null;
        System.out.println("HOST?");
        hostName = br.readLine();
        InetAddress host = InetAddress.getByName(hostName); 
        System.out.println("Connecting to server on port " + serverPort); 

        Socket socket = new Socket(host,serverPort); 
        
        System.out.println("Just connected to " + socket.getRemoteSocketAddress()); 
        
        PrintWriter toServer = new PrintWriter(socket.getOutputStream(),true);
        BufferedReader fromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        
        
        //optionClient 1- Login; 2- New Acount 3-Exit 4-ServerAutoConnect
        String optionClient = null;
        String sucessLogin = null;
        do{
			System.out.println(fromServer.readLine());
			optionClient = br.readLine();
		    toServer.println(optionClient);
		    
		    //if login
		    if(optionClient.equals("1")){
		        //login
		        System.out.println(fromServer.readLine());
		        toServer.println(br.readLine());
		        //password
		        System.out.println(fromServer.readLine());
		        toServer.println(br.readLine());
		        //sucess?
		        sucessLogin = fromServer.readLine();
		        System.out.println(sucessLogin);
		        if(sucessLogin.equals("INVALID LOGIN!") == true)
		        	sucessLogin = null;
		        
		    }
		    
		    else if(optionClient.equals("2")){
		    	//name
		        System.out.println(fromServer.readLine());
		        toServer.println(br.readLine());
		        //login
		        System.out.println(fromServer.readLine());
		        toServer.println(br.readLine());
		        //password
		        System.out.println(fromServer.readLine());
		        toServer.println(br.readLine());
		        //sucess?
		        System.out.println(fromServer.readLine());
		    }
        }while(optionClient.equals("0") == false && sucessLogin == null);
        
        if(optionClient.equals("0") == false){
        	do{
        		//option? 1- Mandar Email; 2- Ler Email;3- Listar Email; 4-Apagar Email; 0- Sair
        		System.out.println(fromServer.readLine());
        		System.out.println(fromServer.readLine());
        		
        		optionClient = br.readLine();
        		toServer.println(optionClient);
        		
      	      //Send Email
      	      if(optionClient.equals("1")){   
      	    	System.out.println(fromServer.readLine());
      	    	toServer.println(br.readLine());
      	    	
      	    	System.out.println(fromServer.readLine());
      	    	toServer.println(br.readLine());
      	    	
      	    	System.out.println(fromServer.readLine());
      	    	String howManyString = br.readLine();
      	    	
      	    	int howMany = Integer.parseInt(howManyString);
      	    	toServer.println(howMany);
      	    	
      	    	for(int i = 0; i< howMany; i++){
      	    		System.out.println("User " + i);
      	    		toServer.println(br.readLine());
      	    		System.out.println(fromServer.readLine());
      	    		
      	    	}
      	    	
      	    	System.out.println(fromServer.readLine());
      	    	
      	    	/*boolean valid = true;
      	    	String context = null;
      	    	while(valid){
      	    		context = fromServer.readLine();
      	    		System.out.println(context);
      	    		if(context.equals("DONE!"))
      	    			valid = false;
      	    		
      	    	}*/
      	    	  
      	      } 
      	      //Read Email
      	      else if(optionClient.equals("2")){
      	    	System.out.println(fromServer.readLine());
      	    	toServer.println(br.readLine());
      	    	
      	    	System.out.println(fromServer.readLine());
      	    	System.out.println(fromServer.readLine());
      	    	System.out.println(fromServer.readLine());
      	    	
      	    	System.out.println(fromServer.readLine());
      	      } 
      	      //List Email
      	      if(optionClient.equals("3")){  
      	    	System.out.println(fromServer.readLine());
      	    	boolean valid = true;
      	    	String data = null;
      	    	while(valid){
      	    		data = fromServer.readLine();
      	    		System.out.println(data);
      	    		if(data.equals("DONE!"))
      	    			valid = false;
      	    	}
      	    	  
      	    	  
      	      } 
      	      //Delete Email
      	      else if(optionClient.equals("4")){
    	    	System.out.println(fromServer.readLine());
      	    	toServer.println(br.readLine());
      	    	
      	    	System.out.println(fromServer.readLine());
      	      }
        		
        		
        	}while(optionClient.equals("0") == false);
        	
        }
        System.out.println(fromServer.readLine());
        toServer.close();
        fromServer.close();
        socket.close();
    }
    catch(UnknownHostException ex) {
        ex.printStackTrace();
    }
    catch(IOException e){
        e.printStackTrace();
    }
  }
    
  public static void main(String[] args) {
        Client client = new Client();
        client.run();
  }
}
