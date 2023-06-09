import { native } from "cc";

export async function googleLoginForAndroid() {
  console.log("Native Call For Google Login (Android)");
  await native.reflection.callStaticMethod(
    "com/cocos/game/AppActivity",
    "googleLoginFromCocos",
    "()V"
  );
}

export async function googleLoginForWindows() {
  //To Be Implemented, Uncomment the below line and call it with valid googleUser
  // onSignUp(googleUserName)
}

export async function googleLoginForIOS() {}

function onSignUp(googleUser) {
  var profile = googleUser.getBasicProfile();
  console.log("ID: " + profile.getId()); // Do not send to your backend! Use an ID token instead.
  console.log("Name: " + profile.getName());
  console.log("Image URL: " + profile.getImageUrl());
  console.log("Email: " + profile.getEmail()); // This is null if the 'email' scope is not present.
}
