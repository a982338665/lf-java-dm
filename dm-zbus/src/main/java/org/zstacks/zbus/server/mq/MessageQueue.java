package org.zstacks.zbus.server.mq;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import java.util.concurrent.ExecutorService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zstacks.zbus.protocol.ConsumerInfo;
import org.zstacks.zbus.protocol.MqInfo;
import org.zstacks.zbus.server.mq.store.MessageStore;
import org.zstacks.znet.Message;
import org.zstacks.znet.nio.Session;

public abstract class MessageQueue implements Serializable{   
	private static final long serialVersionUID = 5719362844495027862L;

	private static final Logger log = LoggerFactory.getLogger(MessageQueue.class);
	
	protected final String broker; 
	protected final String name; 
	protected String creator;
	protected long createdTime = System.currentTimeMillis();
	protected String accessToken = "";
	protected final int mode;
	
	protected transient ExecutorService executor;
	protected transient MessageStore messageStore = null;
	
	public MessageQueue(String broker, String name, ExecutorService executor, int mode){
		this.broker = broker;
		this.name = name; 
		this.executor = executor; 
		this.mode = mode;
	}  
	
	public abstract void produce(Message msg, Session sess) throws IOException;
	public abstract void consume(Message msg, Session sess) throws IOException;
	
	abstract void doDispatch() throws IOException;
	public abstract void cleanSession();
	void dispatch(){
		executor.submit(new Runnable() {
			public void run() { 
				try {
					MessageQueue.this.doDispatch();
				} catch (IOException e) { 
					log.error(e.getMessage(), e);
				}
			}
		});
	}
	
	public long getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(long createdTime) {
		this.createdTime = createdTime;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public String getName() {
		return name;
	}

	public String getCreator() {
		return creator;
	} 
	
	
	public void setCreator(String creator) {
		this.creator = creator;
	}
	

	public int getMode() {
		return mode;
	} 

	public ExecutorService getExecutor() {
		return executor;
	}

	public void setExecutor(ExecutorService executor) {
		this.executor = executor;
	}

	public MqInfo getMqInfo(){
		MqInfo info = new MqInfo(); 
		info.setBroker(broker);
		info.setName(name);
		info.setCreator(creator);
		info.setCreatedTime(createdTime);
		info.setUnconsumedMsgCount(this.getMessageQueueSize()); 
		info.setConsumerInfoList(getConsumerInfoList());
		info.setMode(this.mode);
		return info; 
	}
	
	public abstract int getMessageQueueSize();
	public abstract List<ConsumerInfo> getConsumerInfoList(); 

	/**
	 * 为支持浏览器，特定处理消息状态 
	 * @param msg
	 */
	public void prepareMessageStatus(Message msg){
		String status = msg.getStatus();
		if(status == null){
			status = msg.getReplyCode(); 
			if(status == null){
				status = "200"; //default to OK
			}
			msg.setStatus(status);
		}
	}
	
	@Override
	public String toString() {
		return "MQ [name=" + name + ", creator=" + creator + ", createdTime="
				+ createdTime + "]";
	}

	public MessageStore getMessageStore() {
		return messageStore;
	}

	public void setMessageStore(MessageStore messageStore) {
		this.messageStore = messageStore;
	}
}
