package edu.buaa.scse.niu.wechat.engine.client;

public class ClientThread extends Thread {

	public ClientThread(Client client) {
		this.client = client;
	}

	private Client client;

	@Override
	public void run() {
		client.start();
		client.connect();
	}

}