package com.lottery.rmi.server;

public class Block {
	
	private long timeStamp;
	private String HashNumber;
	private String nonce;
	public String getNonce() {
		return nonce;
	}

	public void setNonce(String nonce) {
		this.nonce = nonce;
	}
	private String prevHash;
	private String username;
	
	public Block() {
		
	}

	public Block(long timeStamp, String hashNumber, String prevHash, String username, String nonce) {
		super();
		this.timeStamp = timeStamp;
		HashNumber = hashNumber;
		this.prevHash = prevHash;
		this.username = username;
		this.nonce = nonce;
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
	
	public void setTimeStamp(long ts) {
		this.timeStamp = ts;
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
