package service;

import java.util.HashSet;
import java.util.Set;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

@ServerEndpoint("/mysocket")
public class MySocket {
	private static final Set<Session> connections = new HashSet<>();
	private Session session;
	
	@OnMessage
	public void onMessage(String message) {
		System.out.println(message);
	    broadcast(message);
	}
	
	@OnOpen
	public void start(Session session){
		System.out.println("session start");
		this.session = session;
		connections.add(session);
	}
	public void broadcast(String message) {
		for(Session client : connections){
			try{
				synchronized(client){
					System.out.println(message);
					client.getBasicRemote().sendText(message);
					//client.getBasicRemote().sendText(msg);
				}
			}catch(Exception e){
				connections.remove(client);
				try{
					client.close();
				} catch(Exception e1){
					
				}
				broadcast(message);
			}
		}
		
	}
	@OnClose
	public void end(){
		System.out.println("session 종료");
		connections.remove(session);
	}
	@OnError
	public void err(Throwable t){
		System.err.print(t.getMessage());
	}
}
