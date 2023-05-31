import { _decorator, Component, director, native, Node, sys } from "cc";
const { ccclass, property } = _decorator;

@ccclass("Main")
export class Main extends Component {
  start() {}

  fbLogin() {
    console.log("Native Call");
    if (sys.os === sys.OS.ANDROID) {
      native.reflection.callStaticMethod(
        "com/cocos/game/AppActivity",
        "fbLoginCallFromCocos",
        "()V"
      );
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
