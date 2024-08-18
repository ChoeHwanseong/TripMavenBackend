package com.tripmaven.auth.userdetail;

public interface OAuthUserInfo {
	String getProvider();
	String getProviderId();
	String getEmail();
	String getName();
	
}
