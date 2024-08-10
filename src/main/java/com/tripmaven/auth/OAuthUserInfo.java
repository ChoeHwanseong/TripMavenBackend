package com.tripmaven.auth;

public interface OAuthUserInfo {
	String getProvider();
	String getProviderId();
	String getEmail();
	String getName();
	
}
