Orchextra Content	
===================
A library that gives you access to Orchextra Content platform from your Android device.


Getting started
-------------
Start by creating a project in [Orchextra Dashboard](https://dashboard.orchextra.io/start/login), if you haven't done it yet. Go to "Setting" > "SDK Configuration" to get the api key and api secret, you will need these values to start Orchextra SDK.

Installation
-------------
You can check how SDK works with the [:app module](https://github.com/Orchextra/orchextra-content-android-sdk/tree/master/app/src/main/java/com/gigigo/sample) of this repository.

Requirements
-------------
Orchextra can be integrated in Ice Cream Sandwich (v. 14) or later.

Adding the dependency
-------------
We have to add the gradle dependencies. In our rootproject **build.gradle** file, we add the following maven dependency. This is required in order to advice gradle that it has to look for Orchextra sdk inside **jitpack.io** maven repository. Gradle file is this one:

<img src="https://github.com/Orchextra/orchextra-android-sdk/blob/master/resources/rootGradleScreenshot.png" width="300">

```java
allprojects {
    repositories {
        jcenter()
        maven { url "https://jitpack.io" }
        maven { url "https://repo.leanplum.com/" }
    }
}
```

Then we have to add the following dependency into the build.gradle module:
```java
   compile 'com.github.Orchextra:orchextra-content-android-sdk:LAST_VERSION'
```

<img src="https://github.com/Orchextra/orchextra-android-sdk/blob/master/resources/appGradleScreenshot.png" width="300">

and we must sync gradle project.

**NOTE**: You can check the last version of this library in the 'Releases' tab and substitute the LAST_VERSION reference with the last library version. 


Integrating SDK
-------------
We have to created a class which **extends from Application** (if we didn't do yet) and add the Orchextra Content init method. 

```java
OcmBuilder ocmBuilder =
        new OcmBuilder(this)
        .setOrchextraCredentials(API_KEY, API_SECRET)
        .setOnDoRequiredLoginCallback(doRequiredLoginCallback)
        .setOnEventCallback(eventCallback)
        .setOnCustomSchemeReceiver(onCustomSchemeReceiver)
        .setContentLanguage(getContentLanguage())
        .setNotificationActivityClass(MainActivity.class);
        
Ocm.initialize(ocmBuilder);
```
**IMPORTANT** you must make this call in **public void onCreate()** of your Application class, if you do not call initialize in this method, the SDK will not initialize properly. You can check that using the logLevel.

**IMPORTANT** if you are using Android Studio 2.1 or higher, and have "Instant Run" enabled, the first time you install the APK is installed in new device, the initialize() spends too much time, maybe a minute on older devices. The second time the problem disappears. To avoid this problem in Android Studio, disables the " Instant Run" from settings-> Build , Execution , Deployment- > Instant Run

Then, in any part of our application we should start the library sdk.

```java
Ocm.start();
```

Change project/authCredentials SDK
-------------
If we doesn't set the new credentials when we initialize the sdk, or change them with new ones. We can set them in any moment during the application life.
```java
Ocm.startWithCredentials(NEW_API_KEY,NEW_API_SECRET, callback);
```
The callback returns the new access token credential. If the credentials have no change, the method do nothing.

Authorization
-------------
Some content of the app is blocked and the user, who use the app, needs to be logged. The setOnDoRequiredLoginCallback method is a listener that is executed when the library needs that the user is logged in the app. The integrative application must execute the login flow and communicate to the sdk that the user is logged in with the following method. If user is log out from the app, we have to execute this method as well.
```java
Ocm.setUserIsAuthorizated(true);
```

Analytics
-------------
The setOnEventCallback method receive some values only for analytics usage. This callback is executed when the user do some actions with the app.

 - *SHARE*: When a content is shared.
 - *CONTENT_START*: When a content is showed.

```java
 private OnEventCallback eventCallback = new OnEventCallback() {
    @Override public void doEvent(OcmEvent event, Object data) {
      switch (event) {
        case SHARE:
          break;
        case CONTENT_START:
          break;
        case CONTENT_END:
          break;
      }
    }
  };
  OcmBuilder ocmBuilder = new OcmBuilder(this).setOnEventCallback(eventCallback);
```


Custom Schemes
-------------
The setOnCustomSchemeReceiver setOnCustomSchemeReceiver execute the custom schemes that the library receives.

```java
 private CustomSchemeReceiver onCustomSchemeReceiver = new CustomSchemeReceiver() {
    @Override public void onReceive(String deepLink) {
    }
  };
  OcmBuilder ocmBuilder = new OcmBuilder(this).setOnCustomSchemeReceiver(onCustomSchemeReceiver);
```

Language
-------------
Set the content language of the sdk with setContentLanguage method.

```java
  OcmBuilder ocmBuilder = new OcmBuilder(this).setContentLanguage("EN");
```

Notification  Main Activity
-------------
You can set the main activity to execute the sdk notification.

```java
  OcmBuilder ocmBuilder = new OcmBuilder(this).setNotificationActivityClass(MainActivity.class);
```


Styling Sdk
-------------
You can set the style sdk with the following class.
```java
OcmStyleUiBuilder ocmStyleUiBuilder =
        new OcmStyleUiBuilder().setTitleFontAssetsPath("fonts/Gotham-Ultra.ttf")
            .setNormalFont("fonts/Gotham-Book.ttf")
            .setMediumFont("fonts/Gotham-Medium.ttf")
            .setLightFont("fonts/Gotham-Light.ttf");
    Ocm.setStyleUi(ocmStyleUiBuilder);
```
Setting Business Unit
-------------
```java
Ocm.setBusinessUnit("it");
```

###Menu
You can retrieve the menu of your content project with the following method. The UiMenu class has the info to show the app menu.
```java
Ocm.getMenus(new OnRetrieveUiMenuListener() {
      @Override public void onResult(final List<UiMenu> uiMenu) {
      }

      @Override public void onNoNetworkConnectionError() {
      }

      @Override public void onResponseDataError() {
      }
    });
```

###Content Grid View
When you retrieve the menu info, you can load the data from the each menu with the following method. The UiGridBaseContentData class is a fragment, which you can add to some activity.
```java
    UiGridBaseContentData uiGridBaseContentData =
        Ocm.generateGridView(uiMenu.get(position).getElementUrl(), filter);
        
    getSupportFragmentManager().beginTransaction()
        .replace(R.id.contentLayout, uiGridBaseContentData)
        .commit();
```

###Content Detail View
We can open some detail view with the element url provided for some custom scheme.

```java
UiDetailBaseContentData uiDetailBaseContentData = Ocm.generateDetailView(uiMenu.get(position).getElementUrl());
```

###Content Search View
We can open the search view to do search of the content.

```java
UiSearchBaseContentData uiSearchBaseContentData = Ocm.generateSearchView();
```

###Deep linking
When the app is opened with some deep link, we can provide this deep link to the sdk with the following method. The sdk will do some action, if the deep link is configured in the dashboard.

```java
Ocm.processDeepLinks("some/deep/link");
```

###Local Storage
We can provide a string pair list, which have the configuration for web and login views.

```java
Ocm.setLocalStorage(new HashMap<String, String>());
```

###Binding user
We can define a specific custom user.

```java
CrmUser crmUser = new CrmUser("crmId", new GregorianCalendar(1981, Calendar.MAY, 31), CrmUser.Gender.GenderMale);
Ocm.bindUser(crmUser);
```

###Clear cache
With this method we can clear all sdk cache. Next content data request will update all sdk data. 

```java
Ocm.clearCache();
```
