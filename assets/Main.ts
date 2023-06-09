import { _decorator, Component, director, native, Node, sys } from "cc";
import {
  googleLoginForAndroid,
  googleLoginForIOS,
  googleLoginForWindows,
} from "./googleLogin";
import {
  fbLoginForAndroid,
  fbLoginForIOS,
  fbLoginForWindows,
} from "./facebookLogin";
import {
  twitterLoginForAndroid,
  twitterLoginForIOS,
  twitterLoginForWindows,
} from "./twitterLogin";
const { ccclass, property } = _decorator;

@ccclass("Main")
export class Main extends Component {
  start() {}

  facebookLogin() {
    console.log("Facebook Login Native Call");
    switch (sys.os) {
      case sys.OS.ANDROID:
        fbLoginForAndroid();
        console.log("Facebook Login Call For Android");
        break;
      case sys.OS.WINDOWS:
        fbLoginForWindows();
        console.log("Facebook Login Call For Windows");
        break;
      case sys.OS.IOS:
        fbLoginForIOS();
        console.log("Facebook Login Call For IOS");
        break;
      default:
        console.log("Not a Compatible Device");
        break;
    }
  }

  googleLogin() {
    console.log("Google Login Call");
    switch (sys.os) {
      case sys.OS.ANDROID:
        googleLoginForAndroid();
        console.log("Google Login Call SuccessFull for Android");
        break;
      case sys.OS.IOS:
        googleLoginForIOS();
        console.log("Google Login Call SuccessFull for IOS");
        break;
      case sys.OS.WINDOWS:
        googleLoginForWindows();
        console.log("Google Login Call SuccessFull for Windows");
        break;
      default:
        console.log("Not a Compatible Device");
        break;
    }
  }

  twitterLogin() {
    console.log("Twitter Login Native Call");
    switch (sys.os) {
      case sys.OS.ANDROID:
        twitterLoginForAndroid();
        console.log("Twitter Login Call SuccessFul For Android");
        break;
      case sys.OS.IOS:
        twitterLoginForIOS();
        console.log("Twitter Login Call SuccessFul For IOS");
        break;
      case sys.OS.WINDOWS:
        twitterLoginForWindows();
        console.log("Twitter Login Call SuccessFul For Windows");
        break;
      default:
        console.log("Not a Compatible Device");
        break;
    }
  }

  changeScene() {
    director.loadScene("scene2");
  }

  errorWhileLogging() {
    console.log("Error! Login Failed");
  }
  update(deltaTime: number) {}
}
