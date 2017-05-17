package model;

public class ErrorMsgFlag {
	private boolean id=false;
	private boolean alreadyId=false;
	private boolean password=false;
	private boolean rePassword=false;
	private boolean nickName=false;
	private boolean profileImageType=false;
	private boolean profileImageSize=false;


	public ErrorMsgFlag(){

	}


	public ErrorMsgFlag(int first){
		id=true;
		 alreadyId=true;
		password=true;
		rePassword=true;
		nickName=true;
		profileImageType=true;
		profileImageSize=true;
	}

	public boolean isId() {
		return id;
	}
	public void setId(boolean id) {
		this.id = id;
	}
	public boolean isPassword() {
		return password;
	}
	public void setPassword(boolean password) {
		this.password = password;
	}
	public boolean isRePassword() {
		return rePassword;
	}
	public void setRePassword(boolean rePassword) {
		this.rePassword = rePassword;
	}
	public boolean isNickName() {
		return nickName;
	}
	public void setNickName(boolean nickName) {
		this.nickName = nickName;
	}
	public boolean isProfileImageType() {
		return profileImageType;
	}
	public void setProfileImageType(boolean profileImageType) {
		this.profileImageType = profileImageType;
	}
	public boolean isProfileImageSize() {
		return profileImageSize;
	}
	public void setProfileImageSize(boolean profileImageSize) {
		this.profileImageSize = profileImageSize;
	}


	public boolean isAlreadyId() {
		return alreadyId;
	}


	public void setAlreadyId(boolean alreadyId) {
		this.alreadyId = alreadyId;
	}

}
