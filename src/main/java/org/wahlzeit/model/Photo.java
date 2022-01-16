/*
 * SPDX-FileCopyrightText: 2006-2009 Dirk Riehle <dirk@riehle.org> https://dirkriehle.com
 * SPDX-License-Identifier: AGPL-3.0-or-later
 */

package org.wahlzeit.model;

import java.sql.*;
import java.net.*;

import org.wahlzeit.annotations.PatternInstance;
import org.wahlzeit.services.*;
import org.wahlzeit.utils.*;

/**
 * A photo represents a user-provided (uploaded) photo.
 */
@PatternInstance(
		patternName = "Mediator",
		participants = { "Managed Object" }
)

@PatternInstance(
		patternName = "Abstract Factory",
		participants = {
				"Abstract Product", "Concrete Product"
		}
)
public class Photo extends DataObject {

	/**
	 * 
	 */
	public static final String IMAGE = "image";
	public static final String THUMB = "thumb";
	public static final String LINK = "link";
	public static final String PRAISE = "praise";
	public static final String NO_VOTES = "noVotes";
	public static final String CAPTION = "caption";
	public static final String DESCRIPTION = "description";
	public static final String KEYWORDS = "keywords";

	public static final String TAGS = "tags";

	public static final String STATUS = "status";
	public static final String IS_INVISIBLE = "isInvisible";
	public static final String UPLOADED_ON = "uploadedOn";
	
	/**
	 * 
	 */
	public static final int MAX_PHOTO_WIDTH = 420;
	public static final int MAX_PHOTO_HEIGHT = 600;
	public static final int MAX_THUMB_PHOTO_WIDTH = 105;
	public static final int MAX_THUMB_PHOTO_HEIGHT = 150;
	
	/**
	 * 
	 */
	protected PhotoId id = null;
	
	/**
	 * 
	 */
	protected int ownerId = 0;
	protected String ownerName;

	/**
	 * 
	 */
	protected boolean ownerNotifyAboutPraise = false;
	protected EmailAddress ownerEmailAddress = EmailAddress.EMPTY;
	protected Language ownerLanguage = Language.ENGLISH;
	protected URL ownerHomePage;
	
	/**
	 * 
	 */
	protected int width;
	protected int height;
	protected PhotoSize maxPhotoSize = PhotoSize.MEDIUM; // derived
	
	/**
	 * 
	 */
	protected Tags tags = Tags.EMPTY_TAGS;

	/**
	 * 
	 */
	protected PhotoStatus status = PhotoStatus.VISIBLE;
	
	/**
	 * 
	 */
	protected int praiseSum = 10;
	protected int noVotes = 1;
	
	/**
	 * 
	 */
	protected long creationTime = System.currentTimeMillis();

	/**
	 *
	 */
	protected Location location;


	/**
	 * 
	 */
	public Photo() {
		id = PhotoId.getNextId();
		incWriteCount();
	}
	
	/**
	 * 
	 * @methodtype constructor
	 */
	public Photo(PhotoId myId) {
		if (myId == null) throw new NullPointerException("myId is null");
		id = myId;
		
		incWriteCount();
	}
	
	/**
	 * 
	 * @methodtype constructor
	 */
	public Photo(ResultSet rset) throws SQLException {
		if (rset == null) throw new NullPointerException("rset is null");
		readFrom(rset);
	}

	/**
	 * 
	 * @methodtype get
	 */
	public String getIdAsString() {
		return String.valueOf(id.asInt());
	}
	
	/**
	 * 
	 */
	public void readFrom(ResultSet rset) throws SQLException {
		if (rset == null) throw new NullPointerException("rset is null");
		id = PhotoId.getIdFromInt(rset.getInt("id"));

		ownerId = rset.getInt("owner_id");
		ownerName = rset.getString("owner_name");
		
		ownerNotifyAboutPraise = rset.getBoolean("owner_notify_about_praise");
		ownerEmailAddress = EmailAddress.getFromString(rset.getString("owner_email_address"));
		ownerLanguage = Language.getFromInt(rset.getInt("owner_language"));
		ownerHomePage = StringUtil.asUrl(rset.getString("owner_home_page"));

		width = rset.getInt("width");
		height = rset.getInt("height");

		tags = new Tags(rset.getString("tags"));

		status = PhotoStatus.getFromInt(rset.getInt("status"));
		praiseSum = rset.getInt("praise_sum");
		noVotes = rset.getInt("no_votes");

		creationTime = rset.getLong("creation_time");
		if (location != null) {
			this.location.readFrom(rset);
		}

		maxPhotoSize = PhotoSize.getFromWidthHeight(width, height);
	}
	
	/**
	 * 
	 */
	public void writeOn(ResultSet rset) throws SQLException {
		if (rset == null) throw new NullPointerException("rset is null");
		rset.updateInt("id", id.asInt());
		rset.updateInt("owner_id", ownerId);
		rset.updateString("owner_name", ownerName);
		rset.updateBoolean("owner_notify_about_praise", ownerNotifyAboutPraise);
		rset.updateString("owner_email_address", ownerEmailAddress.asString());
		rset.updateInt("owner_language", ownerLanguage.asInt());
		rset.updateString("owner_home_page", ownerHomePage.toString());
		rset.updateInt("width", width);
		rset.updateInt("height", height);
		rset.updateString("tags", tags.asString());
		rset.updateInt("status", status.asInt());
		rset.updateInt("praise_sum", praiseSum);
		rset.updateInt("no_votes", noVotes);
		rset.updateLong("creation_time", creationTime);
		if(this.location != null) {
			this.location.writeOn(rset);
		}
	}

	@Override
	public boolean isDirty() {
		boolean dirty = this.writeCount != 0;
		boolean ldirty = this.location != null && this.location.isDirty();

		return dirty || ldirty;
	}

	/**
	 * 
	 */
	public void writeId(PreparedStatement stmt, int pos) throws SQLException {
		if (stmt == null) throw new NullPointerException("stmt is null");
		stmt.setInt(pos, id.asInt());
	}
	
	/**
	 * 
	 * @methodtype get
	 */
	public PhotoId getId() {
		return id;
	}
	
	/**
	 * 
	 * @methodtype get
	 */
	public int getOwnerId() {
		return ownerId;
	}
	
	/**
	 * 
	 * @methodtype set
	 */
	public void setOwnerId(int newId) {
		ownerId = newId;
		incWriteCount();
	}
	
	/**
	 * 
	 * @methodtype get
	 */
	public String getOwnerName() {
		return ownerName;
	}
	
	/**
	 * 
	 * @methodtype set
	 */
	public void setOwnerName(String newName) {
		ownerName = newName;
		incWriteCount();
	}
	
	/**
	 * 
	 * @methodtype get
	 */
	public String getSummary(ModelConfig cfg) {
		if (cfg == null) throw new NullPointerException("cfg is null");
		return cfg.asPhotoSummary(ownerName);
	}

	/**
	 * 
	 * @methodtype get
	 */
	public String getCaption(ModelConfig cfg) {
		return cfg.asPhotoCaption(ownerName, ownerHomePage);
	}

	/**
	 * 
	 * @methodtype get
	 */
	public boolean getOwnerNotifyAboutPraise() {
		return ownerNotifyAboutPraise;
	}
	
	/**
	 * 
	 * @methodtype set
	 */
	public void setOwnerNotifyAboutPraise(boolean newNotifyAboutPraise) {
		ownerNotifyAboutPraise = newNotifyAboutPraise;
		incWriteCount();
	}

	/**
	 * 
	 * @methodtype get
	 */
	public EmailAddress getOwnerEmailAddress() {
		return ownerEmailAddress;
	}
	
	/**
	 * 
	 * @methodtype set
	 */
	public void setOwnerEmailAddress(EmailAddress newEmailAddress) {
		if (newEmailAddress == null) throw new NullPointerException("newEmailAddress is null");
		ownerEmailAddress = newEmailAddress;
		incWriteCount();
	}

	/**
	 * 
	 */
	public Language getOwnerLanguage() {
		return ownerLanguage;
	}
	
	/**
	 * 
	 */
	public void setOwnerLanguage(Language newLanguage) {
		if (newLanguage == null) throw new NullPointerException("newLanguage is null");
		ownerLanguage = newLanguage;
		incWriteCount();
	}

	public String format_location(Location l){
		String a = "";
		try {
			a = String.format("X=%f Y=%f Z=%f", l.getCoordinate().asCartesianCoordinate().getX(), l.getCoordinate().asCartesianCoordinate().getY(), l.getCoordinate().asCartesianCoordinate().getZ());
		}catch (CoordinateException ex){
			ex.printStackTrace();
		}
		return a;
	}

	/**
	 * 
	 * @methodtype get
	 */
	public URL getOwnerHomePage() {
		return ownerHomePage;
	}
	
	/**
	 * 
	 * @methodtype set
	 */
	public void setOwnerHomePage(URL newHomePage) {
		if (newHomePage == null) throw new NullPointerException("newHomePage is null");
		ownerHomePage = newHomePage;
		incWriteCount();
	}
	
	/**
	 * 
	 * @methodtype boolean-query
	 */
	public boolean hasSameOwner(Photo photo) {
		if (photo == null) throw new NullPointerException("photo is null");
		return photo.getOwnerEmailAddress().equals(ownerEmailAddress);
	}

	/**
	 * 
	 * @methodtype boolean-query
	 */
	public boolean isWiderThanHigher() {
		return (height * MAX_PHOTO_WIDTH) < (width * MAX_PHOTO_HEIGHT);
	}
	
	/**
	 * 
	 * @methodtype get
	 */
	public int getWidth() {
		return width;
	}
	
	/**
	 * 
	 * @methodtype get
	 */
	public int getHeight() {
		return height;
	}
	
	/**
	 * 
	 * @methodtype get
	 */
	public int getThumbWidth() {
		return isWiderThanHigher() ? MAX_THUMB_PHOTO_WIDTH : (width * MAX_THUMB_PHOTO_HEIGHT / height);
	}
	
	/**
	 * 
	 * @methodtype get
	 */
	public int getThumbHeight() {
		return isWiderThanHigher() ? (height * MAX_THUMB_PHOTO_WIDTH / width) : MAX_THUMB_PHOTO_HEIGHT;
	}
	
	/**
	 * 
	 * @methodtype set
	 */
	public void setWidthAndHeight(int newWidth, int newHeight) {
		width = newWidth;
		height = newHeight;

		maxPhotoSize = PhotoSize.getFromWidthHeight(width, height);

		incWriteCount();
	}
	
	/**
	 * Can this photo satisfy provided photo size?
	 * 
	 * @methodtype boolean-query
	 */
	public boolean hasPhotoSize(PhotoSize size) {
		return maxPhotoSize.asInt() >= size.asInt();
	}
	
	/**
	 * 
	 * @methodtype get
	 */
	public PhotoSize getMaxPhotoSize() {
		return maxPhotoSize;
	}
	
	/**
	 * 
	 * @methodtype get
	 */
	public double getPraise() {
		return (double) praiseSum / noVotes;
	}
	
	/**
	 * 
	 * @methodtype get
	 */
	public String getPraiseAsString(ModelConfig cfg) {
		if (cfg == null) throw new NullPointerException("cfg is null");
		return cfg.asPraiseString(getPraise());
	}
	
	/**
	 * 
	 */
	public void addToPraise(int value) {
		praiseSum += value;
		noVotes += 1;
		incWriteCount();
	}
	
	/**
	 * 
	 * @methodtype boolean-query
	 */
	public boolean isVisible() {
		return status.isDisplayable();
	}
	
	/**
	 * 
	 * @methodtype get
	 */
	public PhotoStatus getStatus() {
		return status;
	}
	
	/**
	 * 
	 * @methodtype set
	 */
	public void setStatus(PhotoStatus newStatus) {
		if (newStatus == null) throw new NullPointerException("newStatus is null");
		status = newStatus;
		incWriteCount();
	}
	
	/**
	 * 
	 * @methodtype boolean-query
	 */
	public boolean hasTag(String tag) {
		return tags.hasTag(tag);
	}
	
	/**
	 * 
	 * @methodtype get
	 */
	public Tags getTags() {
		return tags;
	}

	/**
	 * 
	 * @methodtype set
	 */
	public void setTags(Tags newTags) {
		if (newTags == null) throw new NullPointerException("newTags is null");
		tags = newTags;
		incWriteCount();
	}
	
	/**
	 * 
	 * @methodtype get
	 */
	public long getCreationTime() {
		return creationTime;
	}
	
}
