import { native } from "cc";

export async function twitterLoginForAndroid() {
  console.log("Native Call For Twitter Login (Android)");
  await native.reflection.callStaticMethod(
    "com/cocos/game/AppActivity",
    "twitterLoginFromCocos",
    "()V"
  );
}
export function twitterLoginForIOS() {}
export function twitterLoginForWindows() {}
