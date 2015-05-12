package com.aehtiopicus.cens.social;

import java.io.Serializable;

import org.springframework.social.connect.ConnectionData;
import org.springframework.social.connect.ConnectionKey;
import org.springframework.social.connect.UserProfile;

public interface SocialConnection<A> extends Serializable {

    ConnectionKey getKey();

    String getDisplayName();

    String getProfileUrl();

    String getImageUrl();

    void sync();

    boolean test();

    boolean hasExpired();

    void refresh();

    UserProfile fetchUserProfile();

    void updateStatus(String message);

    A getApi();

    ConnectionData createData();

}