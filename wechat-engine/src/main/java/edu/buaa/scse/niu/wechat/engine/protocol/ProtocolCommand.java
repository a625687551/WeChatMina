package edu.buaa.scse.niu.wechat.engine.protocol;

public interface ProtocolCommand {

	// public static final byte CMD_PUSH = 0x01;
	// public static final byte CMD_COMMIT = 0x02;
	/**
	 * 基本指令
	 */
	public static final byte CMD_CONNECT = 0x00;
	public static final byte CMD_REGISTER=0x01;
	public static final byte CMD_LOGIN = 0x02;
	public static final byte CMD_LOGOFF = 0X03;
	public static final byte CMD_HEARTBEAT = 0x04;
	public static final byte CMD_CLIENT_EXIT = 0x05;
	
	public static final byte CMD_CONNECT_RES = 0x08;
	public static final byte CMD_REGISTER_RES=0x09;
	public static final byte CMD_LOGIN_RES = 0x0A;
	public static final byte CMD_LOGOFF_RES = 0x0B;
	public static final byte CMD_HEARTBEAT_RES = 0x0C;
	public static final byte CMD_SERVER_EXIT = 0x0D;
	
	/**
	 * 消息相关指令
	 */
	public static final byte CMD_SENDMSG = 0x10;
	public static final byte CMD_SENDMSG_GROUP = 0x11;
	public static final byte CMD_GETMSG_OFFLINE = 0x12;
	
	public static final byte CMD_SENDMSG_RES = 0x18;
	public static final byte CMD_SENDMSG_GROUP_RES = 0x19;
	public static final byte CMD_GETMSG_OFFLINE_RES = 0x1A;
	
	/**
	 * 用户分组相关指令
	 */
	public static final byte CMD_GET_USER_GROUPS=0x20;//参数 accountId
	public static final byte CMD_ASYNC_USER_GROUP=0x21;//accountId 上次同步时间
	public static final byte CMD_ADD_USER_GROUP=0x22;//accountId groupName
	public static final byte CMD_DELETE_USER_GROUP=0x23;//accountId
	public static final byte CMD_RENAME_USER_GROUP=0x24;//accountId newName
	
	public static final byte CMD_GET_USER_GROUPS_RES=0x28;//返回用户分组的JSON数组
	public static final byte CMD_ASYNC_USER_GROUP_RES=0x29;//返回用户分组的JSON数组
	public static final byte CMD_ADD_USER_GROUP_RES=0x2A;//
	public static final byte CMD_DELETE_USER_GROUP_RES=0x2B;
	public static final byte CMD_RENAME_USER_GROUP_RES=0x2C;
	
	/**
	 * 好友相关指令
	 */
	public static final byte CMD_GET_FRIENDS=0x30;
	public static final byte CMD_ASYNC_FRIENDS=0x31;
	public static final byte CMD_ADD_FRIEND=0x32;
	public static final byte CMD_DELETE_FRIEND=0x33;
	public static final byte CMD_NOTE_FRIEND=0x34;
	public static final byte CMD_CHANGE_GROUP=0x35;
	
	public static final byte CMD_GET_FRIENDS_RES=0x38;
	public static final byte CMD_ASYNC_FRIENDS_RES=0x39;
	public static final byte CMD_ADD_FRIEND_RES=0x3A;
	public static final byte CMD_DELETE_FRIEND_RES=0x3B;
	public static final byte CMD_NOTE_FRIEND_RES=0x3C;
	public static final byte CMD_CHANGE_GROUP_RES=0x3D;
	
	/**
	 * 聊天组相关指令
	 */
	public static final byte CMD_GET_CHAT_GROUPS=0x40;
	public static final byte CMD_ASYNC_CHAT_GROUPS=0x41;
	public static final byte CMD_CREATE_CHAT_GROUP=0x42;
	public static final byte CMD_ADD_CHAT_GROUP_MEMBER=0x43;
	public static final byte CMD_EXIT_CHAR_GROUP=0x44;
	public static final byte CMD_NOTE_CHAT_GROUP=0x45;
	
	public static final byte CMD_GET_CHAT_GROUPS_RES=0x48;
	public static final byte CMD_ASYNC_CHAT_GROUPS_RES=0x49;
	public static final byte CMD_CREATE_CHAT_GROUP_RES=0x4A;
	public static final byte CMD_ADD_CHAT_GROUP_MEMBER_RES=0x4B;
	public static final byte CMD_EXIT_CHAR_GROUP_RES=0x4C;
	public static final byte CMD_NOTE_CHAT_GROUP_RES=0x4D;

//	public enum Cmd {
//		connect, login, logoff, heartbeat, clientExit, sendMsg, 
//		sendMsgGroup, getOfflineMsg, connect_res, login_res, logoff_res, 
//		heartbeat_res,serverExit, sendMsg_res, sendMsgGroup_res, getOfflineMsg_res
//	};
	
//	public static byte getCmd(Cmd cmd){
//		byte ret=0x00;
//		switch(cmd){
//		case clientExit:
//			ret=CMD_CLIENT_EXIT;
//			break;
//		case connect:
//			ret=CMD_CONNECT;
//			break;
//		case connect_res:
//			ret=CMD_CONNECT_RES;
//			break;
//		case getOfflineMsg:
//			ret=CMD_GETMSG_OFFLINE;
//			break;
//		case getOfflineMsg_res:
//			ret=CMD_GETMSG_OFFLINE_RES;
//			break;
//		case heartbeat:
//			ret=CMD_HEARTBEAT;
//			break;
//		case heartbeat_res:
//			ret=CMD_HEARTBEAT_RES;
//			break;
//		case login:
//			ret=CMD_LOGIN;
//			break;
//		case login_res:
//			ret=CMD_LOGIN_RES;
//			break;
//		case logoff:
//			ret=CMD_LOGOFF;
//			break;
//		case logoff_res:
//			ret=CMD_LOGOFF_RES;
//			break;
//		case sendMsg:
//			ret=CMD_SENDMSG;
//			break;
//		case sendMsgGroup:
//			ret=CMD_SENDMSG_GROUP;
//			break;
//		case sendMsgGroup_res:
//			ret=CMD_SENDMSG_GROUP_RES;
//			break;
//		case sendMsg_res:
//			ret=CMD_SENDMSG_RES;
//			break;
//		case serverExit:
//			ret=CMD_SERVER_EXIT;
//			break;
//		}
//		return ret;
//		
//	}
//	
//	public static Cmd getCmd(byte bt){
//		Cmd cmd=null;
//		switch(bt){
//		case CMD_CONNECT:
//			cmd=Cmd.connect;
//			break;
//		case CMD_LOGIN:
//			cmd=Cmd.login;
//			break;
//		case CMD_LOGOFF:
//			cmd=Cmd.logoff;
//			break;
//		case CMD_HEARTBEAT:
//			cmd=Cmd.heartbeat;
//			break;
//		case CMD_CLIENT_EXIT:
//			cmd=Cmd.clientExit;
//			break;
//		case CMD_SENDMSG:
//			cmd=Cmd.sendMsg;
//			break;
//		case CMD_SENDMSG_GROUP:
//			cmd=Cmd.sendMsgGroup;
//			break;
//		case CMD_GETMSG_OFFLINE:
//			cmd=Cmd.getOfflineMsg;
//			break;
//		case CMD_CONNECT_RES:
//			cmd=Cmd.connect_res;
//			break;
//		case CMD_LOGIN_RES:
//			cmd=Cmd.login_res;
//			break;
//		case CMD_LOGOFF_RES:
//			cmd=Cmd.logoff_res;
//			break;
//		case CMD_HEARTBEAT_RES:
//			cmd=Cmd.heartbeat_res;
//			break;
//		case CMD_SERVER_EXIT:
//			cmd=Cmd.serverExit;
//			break;
//		case CMD_SENDMSG_RES:
//			cmd=Cmd.sendMsg_res;
//			break;
//		case CMD_SENDMSG_GROUP_RES:
//			cmd=Cmd.sendMsgGroup_res;
//			break;
//		case CMD_GETMSG_OFFLINE_RES:
//			cmd=Cmd.getOfflineMsg_res;
//			break;
//		}
//		return cmd;
//	}
}
