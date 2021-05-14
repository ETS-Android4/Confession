package com.example.confession.presenters.user;

import com.example.confession.binders.user.SignInBinder;
import com.example.confession.models.behaviors.User;
import com.example.confession.utilities.Hashing;

public class SignInPresenter implements SignInBinder.Presenter {

	private final SignInBinder.View view;

	public SignInPresenter(SignInBinder.View view)
	{
		this.view = view;
	}

	@Override
	public void HandleLogin(String username, String password) {

		if (username.isEmpty() || password.isEmpty())
		{
			view.OnLoginFailure("Username and password can't be empty");
			return;
		}

		User user = User.Login(username, password);

		// Todo: Change to sha password
		user = User.Login(username, Hashing.SHA512(password));
		if (user != null)
			view.OnLoginSuccess(user);
		else
			view.OnLoginFailure("User not exists");
	}
	public void HandleLogin(String token)
	{
		User user = User.checkToken(token);

		if (user != null)
			view.OnLoginSuccess(user);
		else
			view.OnLoginFailure("User not exists");
	}

}
