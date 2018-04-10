package com.ljheee.commentdemo;

import java.util.ArrayList;

/**
 * Java bean
 * 一个用户动态 包含的所有数据
 */
public class Item {

    private int portraitId; // 头像
    private String nickName; // 昵称
    private String content; // 说说（动态）
    private String createdAt; // 发布时间
    private ArrayList<Comment> comments = new ArrayList<Comment>(); // 动态的所有评论


    public Item(int portraitId, String nickName, String content, String createdAt) {
        this.portraitId = portraitId;
        this.nickName = nickName;
        this.content = content;
        this.createdAt = createdAt;
    }

    public boolean hasComment() {
        return comments.size() > 0;
    }

    public int getPortraitId() {
        return portraitId;
    }

    public String getNickName() {
        return nickName;
    }

    public String getContent() {
        return content;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public ArrayList<Comment> getComments() {
        return comments;
    }
}
