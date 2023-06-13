# compose-progressive-profiling

Completed project for the _Add Progressive Profiling to Auth0 Authentication in Android Apps Built with Jetpack Compose_ article in the Auth0 Developer Blog.


## Project Description

This is a companion repository for the Auth0 Developer Blog article _Add Progressive Profiling to Auth0 Authentication in Android Apps Built with Jetpack Compose_. The article walks the reader through the process of building a simple Auth0 login/logout Android app built using Jetpack Compose and adding progressive profiling to it.

This repository has two versions of the application featured in the article:

1. The starter project, located in the `starter` directory, which the reader uses as a starting point for the exercise in the article.
2. The completed project, located in the `complete` directory.


## How to Run the App

### Prerequisities

You’ll need the following to build the app:

#### 1. An Auth0 account

The app uses Auth0 to authenticate users, meaning you need an Auth0 account. You can <a href="https://auth0.com/signup" data-amp-replace="CLIENT_ID" data-amp-addparams="anonId=CLIENT_ID(cid-scope-cookie-fallback-name)">sign up for a free account</a>, which lets you add login/logout to 10 applications, with support for 7,000 users and unlimited logins — plenty for your prototyping, development, and testing needs.

#### 2. An Android development setup

To develop applications for Android, make sure you have the following, in the order given below:

* Any computer running Linux, macOS, or Windows from 2013 or later with at least 8 GB RAM. When it comes to RAM, more is generally better.
* [**Java SE Developer Kit (JDK), version 11 or later.**](https://www.oracle.com/java/technologies/javase-jdk16-downloads.html) You can find out which version is on your computer by opening a command-line interface and entering `java --version`.
* [**Android Studio,**](https://developer.android.com/studio) version 2021.2.1 Patch 2 (also known as “Chipmunk”) or later. Jetpack Compose is a recent development, so you should use the most recent stable version of Android Studio.
* **At least one Android SDK (Software Development Kit) platform.** You can confirm that you have one (and install one if you don’t) in Android Studio. Open _Tools_ → _SDK Manager_. You’ll see a list of Android SDK platforms. Select the current SDK (**Android 13.0 (Tiramisu)** at the time of writing), click the _Apply_ button, and click the _OK_ button in the confirmation dialog that appears. Wait for the SDK platform to install and click the _Finish_ button when installation is complete.
* **An Android device, real or virtual:**
	* **Using a real device:** Connect the device to your computer with a USB cable. Make sure that your device has Developer Options and USB debugging enabled.
	* **Using a virtual device:** Using Android Studio, you can build a virtual device (emulator) that runs on your computer. Here’s my recipe for a virtual device that simulates a current-model inexpensive Android phone:
		1. Open _Tools_ → _AVD Manager_ (AVD is short for “Android Virtual Device”). The _Device Manager_ panel will appear. Make sure that the _Virtual_ device tab is selected and click the _Create device_ button.
		2. The _Select Hardware_ window will appear. Ensure the _Phone_ category is selected, then select _Pixel 5_ from the list of phones and click the _Next_ button. The [Google Pixel 5](https://www.gsmarena.com/google_pixel_5-10386.php) was released in 2020, three years prior to the time of writing, and is a reasonable “representative” phone.
		3. The _System Image_ window will appear, and you’ll see a list of Android versions. Select the latest version of Android, _Tiramisu_ (API 33, also known as Android 13.0). If the _Next_ button is disabled and you see a _Download_ link beside _Tiramisu_, click that download link to download the OS. Once  _Tiramisu_ no longer has a download icon beside it and is available for selection, select it and click the _Next_ button.
		4. The _Android Virtual Device (AVD)_ window will appear. The _AVD Name_ field should contain _Pixel 5 API 33_, the two rows below it should have the titles _Pixel 5_ and _Tiramisu_. Select _Portrait_ in the _Startup orientation_ section, then click the _Finish_ button.
		5. You will be back at the _Your Virtual Devices_ window. The list will now contain _Pixel 5 API 33_, and that device will be available to you when you run the app.


### Installing and running the app


Clone the repo. It contains two versions of the project, each in its own directory:

- `starter`: A “starter” version of the project, which the reader should use when following the steps in the article.
- `complete`: A complete version of the project, which reflects what the reader should have after following the steps in the article.

You’ll need to register the app in the Auth0 dashboard. Do this by following these steps:

1. Log into the Auth0 dashboard, select _Applications_ → _Applications_ from the menu on the left side of the page, and create a new **Native** application.
1. Once you have created the app, copy these values from the app’s Settings page in the Auth0 dashboard:
	- Domain
	- Client ID
1. Switch to Android Studio. In the project (either the starter or complete one), open the auth0.xml resource file and...
	- Replace `Enter your Auth0 tenant’s domain here.` with the domain you copied from the Auth0 dashboard.
	- Replace `Enter your app’s client ID here.` with the domain you copied from the Auth0 dashboard.
1. Go back to the Auth0 dashboard, and in the application’s page, scroll down to the _Application URIs_ section and paste the following into both the _Allowed Callback URLs_ and _Allowed Logout URLs_: `app://{Your tenant domain}/android/com.auth0.composeprogressiveprofiling/callback`, making sure to replace `{Your tenant domain}` with your tenant’s domain. Then click the _Save_ button at the bottom of the page to save your changes.

You’ll need to log into the app, so make sure your tenant has at least one user. You can create a new user in the Auth0 dashboard by selecting _User Management_ → _Users_ from the menu on the left side of the page and clicking the _Create User_ button.

If you are running the completed app, you’ll want to set the user’s metadata so that the progressive profiling feature is enabled. Select the user whose account you’ll use to log into the app and set the _user_metadata_ field to the following...

```
{}
```

...and set the _app_metadata_ field to this:

```
{
  "progressive_profiling": {
    "question": "What's your preferred programming language?",
    "answer_field": "preferred_programming_language"
  }
}
```

		
## License

The code in this repository is licensed under the Apache 2.0 License.