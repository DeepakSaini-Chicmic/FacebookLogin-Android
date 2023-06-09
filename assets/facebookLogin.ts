import { native } from "cc";

export async function fbLoginForAndroid() {
  console.log("Native Call For Facebook Login (Android)");
  await native.reflection.callStaticMethod(
    "com/cocos/game/AppActivity",
    "fbLoginCallFromCocos",
    "()V"
  );
}
export function fbLoginForWindows() {}
export function fbLoginForIOS() {}
