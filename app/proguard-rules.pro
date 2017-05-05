# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in H:\ProgramFiles\android\AndroidSDK/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}
-ignorewarnings                     # 忽略警告，避免打包时某些警告出现
-optimizationpasses 5               # 指定代码的压缩级别
-dontusemixedcaseclassnames         # 是否使用大小写混合
-dontskipnonpubliclibraryclasses    # 是否混淆第三方jar
-dontpreverify                      # 混淆时是否做预校验
-verbose                            # 混淆时是否记录日志
-optimizations !code/simplification/arithmetic,!field/*,!class/merging/*        # 混淆时所采用的算法

-dontwarn org.apache.**
-dontwarn android.support.**     #缺省proguard 会检查每一个引用是否正确，但是第三方库里面往往有些不会用到的类，没有正确引用。如果不配置的话，系统就会报错。
-dontwarn android.os.**
-dontwarn javax.xml.stream.events.**
-dontwarn roboguice.test.**
-dontwarn roboguice.activity.RoboMapActivity
-dontwarn com.github.**
-dontwarn com.twitter.**
-dontwarn com.google.common.**

-keep class tk.djcrazy.MyCC98.module.*
-keep class * implements java.io.Serializable
-keepattributes Signature

-keep class roboguice.**
-keep class com.google.**
-keep class com.github.**
-keep class com.twitter.**
-keep class android.support.**
-keep class com.jfeinstein.**
-keep class ch.boye.**
-keep class android.support.** { *; }        # 保持哪些类不被混淆
-keep class com.baidu.** { *; }
-keep class com.github.** { *; }
-keep class android.os.**{*;}

-keep interface android.support.** { *; }
-keep public class * extends android.support.**
-keep public class * extends android.app.Fragment

-keep public class * extends android.app.Activity
-keep public class * extends android.app.Application
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
-keep public class * extends android.support.v4.widget
-keep public class com.google.vending.licensing.ILicensingService
-keep public class com.android.vending.licensing.ILicensingService

-keepclasseswithmembernames class * {       # 保持 native 方法不被混淆
    native <methods>;
}

-keepclasseswithmembers class * {            # 保持自定义控件类不被混淆
    public <init>(android.content.Context, android.util.AttributeSet);
}

-keepclasseswithmembers class * {            # 保持自定义控件类不被混淆
    public <init>(android.content.Context, android.util.AttributeSet, int);
}

-keepclassmembers class * extends android.app.Activity {
   public void *(android.view.View);
}

-keepclassmembers enum * {                  # 保持枚举 enum 类不被混淆
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

-keep class * implements android.os.Parcelable {    # 保持 Parcelable 不被混淆
  public static final android.os.Parcelable$Creator *;
}

-keepclassmembers class tk.djcrazy.MyCC98.PostContentsJSActivity {
   public *;
}

-keepclassmembers class * { @com.google.inject.Provides *; @android.test.suitebuilder.annotation.* *; void test*(...); }

-keepclassmembers class * { @com.google.inject.Inject <init>(...); }
-keepclassmembers class * implements android.os.Parcelable { static android.os.Parcelable$Creator *; }
-keepclassmembers class * {
	@com.google.inject.Inject <init>(...);
	@com.google.inject.Inject <fields>;
	@roboguice.inject.InjectView <init>(...);
	@com.google.inject.InjectView <fields>;
	@javax.annotation.Nullable <fields>;
}

-keepclassmembers class * extends com.actionbarsherlock.ActionBarSherlock { public <init>(...); }
-renamesourcefileattribute SourceFile
-keepattributes SourceFile,LineNumberTable,*Annotation*,Signature
-keep class com.google.inject.** { *; }
-keep class javax.inject.** { *; }
-keep class javax.annotation.** { *; }
-keep class roboguice.** { *; }
-keep class tk.djcrazy.MyCC98.application.**{ *; }
-keep class tk.djcrazy.libCC98.** { *; }
