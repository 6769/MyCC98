/**
 * 
 */
package tk.djcrazy.libCC98.data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author DJ
 * 
 */
public class PostContentEntity implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * @return the postTopic
	 */
	public String getPostTopic() {
		return postTopic;
	}

	/**
	 * @param postTopic
	 *            the postTopic to set
	 */
	public void setPostTopic(String postTopic) {
		this.postTopic = postTopic;
	}

	/**
	 * @return the boardName
	 */
	public String getBoardName() {
		return boardName;
	}

	/**
	 * @param boardName
	 *            the boardName to set
	 */
	public void setBoardName(String boardName) {
		this.boardName = boardName;
	}

	/**
	 * @return the totalPage
	 */
	public int getTotalPage() {
		return totalPage;
	}

	/**
	 * @param totalPage
	 *            the totalPage to set
	 */
	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	/**
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * @param userName
	 *            the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * @return the postContent
	 */
	public String getPostContent() {
		return postContent;
	}

	/**
	 * @param postContent
	 *            the postContent to set
	 */
	public void setPostContent(String postContent) {
		this.postContent = postContent;
	}

	/**
	 * @return the userAvatarLink
	 */
	public String getUserAvatarLink() {
		return userAvatarLink;
	}

	/**
	 * @param userAvatarLink
	 *            the userAvatarLink to set
	 */
	public void setUserAvatarLink(String userAvatarLink) {
		this.userAvatarLink = userAvatarLink;
	}

	/**
	 * @return the postTitle
	 */
	public String getPostTitle() {
		return postTitle;
	}

	/**
	 * @param postTitle
	 *            the postTitle to set
	 */
	public void setPostTitle(String postTitle) {
		this.postTitle = postTitle;
	}

	/**
	 * @return the postFace
	 */
	public String getPostFace() {
		return postFace;
	}

	/**
	 * @param postFace
	 *            the postFace to set
	 */
	public void setPostFace(String postFace) {
		this.postFace = postFace;
	}

 	/**
	 * @return the gender
	 */
	public Gender getGender() {
		return gender;
	}

	/**
	 * @param gender
	 *            the gender to set
	 */
	public void setGender(Gender gender) {
		this.gender = gender;
	}

 	public int getRelpyId() {
		return relpyId;
	}

	public void setRelpyId(int relpyId) {
		this.relpyId = relpyId;
	}

	public Date getPostTime() {
		return postTime;
	}

	public void setPostTime(Date postTime) {
		this.postTime = postTime;
	}

    public void setSupportMarkDownSyntax(boolean v){
        supportMarkDownSyntax=v;
    }
    public boolean getSupportMarkDownSyntax(){
        return supportMarkDownSyntax;
    }

	private String postTopic = "";
	private String boardName = "";
	private int totalPage = 0;
	private String userName = "";
	private String postContent = "";
	private String userAvatarLink = "";
	private String postTitle = "";
	private String postFace = "face7.gif";
	private Date postTime = new Date();
	private Gender gender = Gender.MALE;
    private boolean supportMarkDownSyntax=false;
	private int relpyId=0;
}
