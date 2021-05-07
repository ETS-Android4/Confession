package com.example.confession.presenters;

import com.example.confession.binders.AddCommentBinder;
import com.example.confession.models.behaviors.GroupPost;
import com.example.confession.models.behaviors.PostComment;
import com.example.confession.models.behaviors.User;
import com.example.confession.models.data.BasicUserInfo;
import com.example.confession.models.data.GroupPostInfo;
import com.example.confession.models.data.PostCommentInfo;

public class AddCommentPresenter implements AddCommentBinder.Presenter {

	private final AddCommentBinder.View view;

	public AddCommentPresenter(AddCommentBinder.View view) {

		this.view = view;
	}

	@Override
	public void HandleAddComment(GroupPostInfo post_info, String content) {

		BasicUserInfo basic_user_info = User.GetInstance().GetBasicUserInfo();
		GroupPost post = new GroupPost(post_info, )
		PostCommentInfo comment_info = new PostCommentInfo(post_info, basic_user_info, content);
		PostComment comment = post.AddComment(comment_info, basic_user_info);

		if (comment != null)
			view.OnAddCommentSuccess();
		else
			view.OnAddCommentFailure("Failed to add comment");
	}
}
