package com.lottery.rmi.client;

public class Block {
	
	private long timeStamp;
	private String HashNumber;
	private String prevHash;
	private String username;	
	public Block() {
		
	}

	public Block(long timeStamp, String hashNumber, String prevHash, String username) {
		super();
		this.timeStamp = timeStamp;
		HashNumber = hashNumber;
		this.prevHash = prevHash;
		this.username = username;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public long getTimeStamp() {
		return timeStamp;
	}
	
	public void setTimeStamp(int timeStamp) {
		this.timeStamp = timeStamp;
	}
	public String getHashNumber() {
		return HashNumber;
	}
	public void setHashNumber(String hashNumber) {
		HashNumber = hashNumber;
	}

	public String getPrevHash() {
		return prevHash;
	}
	public void setPrevHash(String prevHash) {
		this.prevHash = prevHash;
	}
	
	

}
