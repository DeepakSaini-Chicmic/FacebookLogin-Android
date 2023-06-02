import { _decorator, Component, director, native, Node, sys } from "cc";
const { ccclass, property } = _decorator;

@ccclass("Main")
export class Main extends Component {
  start() {}

  facebookLogin() {
    console.log("Native Call");
    if (sys.os === sys.OS.ANDROID) {
      native.reflection.callStaticMethod(
        "com/cocos/game/AppActivity",
        "fbLoginCallFromCocos",
        "()V"
      );
      console.log("Facebook Login Call Successful");
    } else {
      console.log("Run App in Android");
    }
  }

  googleLogin() {
    console.log("Google Login Native Call");
    if (sys.os === sys.OS.ANDROID) {
      native.reflection.callStaticMethod(
        "com/cocos/game/AppActivity",
        "googleLoginFromCocos",
        "()V"
      );
      console.log("Google Login Call SuccessFul");
    } else if (sys.os === sys.OS.IOS) {
      // native.reflection.callStaticMethod("AppDelegate","")
    } else {
      console.log("Run App in Android");
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
