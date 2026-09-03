# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile

# 保留 BRVAH 的核心类
-keep class com.chad.library.adapter.** { *; }
-keep class com.chad.library.adapter.base.** { *; }
-keep class com.chad.library.adapter.base.module.** { *; }

# 保留 BaseViewHolder 及其子类
-keep public class * extends com.chad.library.adapter.base.BaseViewHolder

# 保留所有实现 BaseQuickAdapter 的类
-keep public class * extends com.chad.library.adapter.base.BaseQuickAdapter

# 保留注解（如 @LayoutRes、@NotNull 等）
-keepattributes *Annotation*

-optimizationpasses 5
-printmapping proguardMapping.txt
-optimizations !code/simplification/cast,!field/*,!class/merging/*
-keepattributes *Annotation*,InnerClasses
-keepattributes Signature
-keepattributes SourceFile,LineNumberTable

#-repackageclasses 'hal'
#----------------------------------------------------------------------------

#---------------------------------默认保留区---------------------------------
#继承activity,application,service,broadcastReceiver,contentprovider....不进行混淆
#-keep public class * extends android.app.Activity
#-keep public class * extends android.app.Application
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
-keep public class * extends android.app.backup.BackupAgentHelper
-keep public class * extends android.preference.Preference
-keep public class * extends android.view.View
-keep class android.support.** {*;}

-keep public class * extends android.view.View{
    *** get*();
    void set*(***);
    public <init>(android.content.Context);
    public <init>(android.content.Context, android.util.AttributeSet);
    public <init>(android.content.Context, android.util.AttributeSet, int);
}
-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet);
    public <init>(android.content.Context, android.util.AttributeSet, int);
}
#这个主要是在layout 中写的onclick方法android:onclick="onClick"，不进行混淆
-keepclassmembers class * extends android.app.Activity {
   public void *(android.view.View);
}

-keepclassmembers class * implements java.io.Serializable {
    static final long serialVersionUID;
    private static final java.io.ObjectStreamField[] serialPersistentFields;
    private void writeObject(java.io.ObjectOutputStream);
    private void readObject(java.io.ObjectInputStream);
    java.lang.Object writeReplace();
    java.lang.Object readResolve();
}
-keep class **.R$* {
 *;
}

-keepclassmembers class * {
    void *(*Event);
}

-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}
-keep class * implements android.os.Parcelable {
  public static final android.os.Parcelable$Creator *;
}
#// natvie 方法不混淆
-keepclasseswithmembernames class * {
    native <methods>;
}

#保持 Parcelable 不被混淆
-keep class * implements android.os.Parcelable {
  public static final android.os.Parcelable$Creator *;
}


#//聚合混淆
-keep class bykvm*.**
-keep class com.bytedance.msdk.adapter.**{ public *; }
-keep class com.bytedance.msdk.api.** {
 public *;
}
-keep class com.bytedance.msdk.base.TTBaseAd{*;}
-keep class com.bytedance.msdk.adapter.TTAbsAdLoaderAdapter{
    public *;
    protected <fields>;
}

# baidu sdk 不接入baidu sdk可以不引入
-ignorewarnings
-dontwarn com.baidu.mobads.sdk.api.**
-keepclassmembers class * extends android.app.Activity {
   public void *(android.view.View);
}

-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

-keep class com.baidu.mobads.** { *; }
-keep class com.style.widget.** {*;}
-keep class com.component.** {*;}
-keep class com.baidu.ad.magic.flute.** {*;}
-keep class com.baidu.mobstat.forbes.** {*;}

#ks  不接入ks sdk可以不引入
-keep class org.chromium.** {*;}
-keep class org.chromium.** { *; }
-keep class aegon.chrome.** { *; }
-keep class com.kwai.**{ *; }
-dontwarn com.kwai.**
-dontwarn com.kwad.**
-dontwarn com.ksad.**
-dontwarn aegon.chrome.**

# Admob 不接入admob sdk可以不引入
-keep class com.google.android.gms.ads.MobileAds {
 public *;
}

#sigmob  不接入sigmob sdk可以不引入
-dontwarn android.support.v4.**
-keep class android.support.v4.** { *; }
-keep interface android.support.v4.** { *; }
-keep public class * extends android.support.v4.**

-keep class sun.misc.Unsafe { *; }
-dontwarn com.sigmob.**
-keep class com.sigmob.**.**{*;}

#oaid 不同的版本混淆代码不太一致，你注意你接入的oaid版本 ，不接入oaid可以不添加
-dontwarn com.bun.**
-keep class com.bun.** {*;}
-keep class a.**{*;}
-keep class XI.CA.XI.**{*;}
-keep class XI.K0.XI.**{*;}
-keep class XI.XI.K0.**{*;}
-keep class XI.vs.K0.**{*;}
-keep class XI.xo.XI.XI.**{*;}
-keep class com.asus.msa.SupplementaryDID.**{*;}
-keep class com.asus.msa.sdid.**{*;}
-keep class com.huawei.hms.ads.identifier.**{*;}
-keep class com.samsung.android.deviceidservice.**{*;}
-keep class com.zui.opendeviceidlibrary.**{*;}
-keep class org.json.**{*;}
-keep public class com.netease.nis.sdkwrapper.Utils {public <methods>;}


#klevin 游可赢
-keep class com.tencent.tgpa.**{*;}
-keep class com.tencent.klevin.**{*;}


#Mintegral 不接入Mintegral sdk，可以不引入
-keepattributes Signature
-keepattributes *Annotation*
-keep class com.mbridge.** {*; }
-keep interface com.mbridge.** {*; }
-keep class android.support.v4.** { *; }
-dontwarn com.mbridge.**
-keep class **.R$* { public static final int mbridge*; }

#友盟混淆
-keep class com.umeng.** {*;}
-keepclassmembers class * {
   public <init> (org.json.JSONObject);
}
-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}
#json解析相关
-keepattributes Signature

# For using GSON @Expose annotation
-keepattributes *Annotation*

# Gson specific classes
-dontwarn sun.misc.**
#-keep class com.google.gson.stream.** { *; }

# Application classes that will be serialized/deserialized over Gson
# 注意这里只是谷歌的一个示例，具体需要替换成你自己的各种data、entity、bean等类
-keep class com.google.gson.examples.android.model.** { <fields>; }

# Prevent proguard from stripping interface information from TypeAdapterFactory,
# JsonSerializer, JsonDeserializer instances (so they can be used in @JsonAdapter)
-keep class * implements com.google.gson.TypeAdapterFactory
-keep class * implements com.google.gson.JsonSerializer
-keep class * implements com.google.gson.JsonDeserializer


################gson##################
-keep class com.google.gson.** {*;}
-keep class com.google.**{*;}
-keep class com.google.gson.stream.** { *; }
-keep class com.google.gson.examples.android.model.** { *; }
-keep class com.yao.tool.bean.SceneryBean.**{*;}

# Prevent R8 from leaving Data object members always null
-keepclassmembers,allowobfuscation class * {
  @com.google.gson.annotations.SerializedName <fields>;
}

#viewbinding
-keep class * implements androidx.viewbinding.ViewBinding {
    *;
}
# Octopus混淆
-dontwarn com.octopus.ad.**
-keep class com.octopus.ad.** {*;}

-keep class com.github.megatronking.stringfog.**{*;}
-keep class com.airbnb.lottie.**{*;}

#Bugly
-dontwarn com.tencent.bugly.**
-keep public class com.tencent.bugly.**{*;}
#///////////////////////////
-keep class com.tencent.mmkv.MMKV{*;}
-keep class android.util.Log{*;}
-keep class kotlin.jvm.internal.Intrinsics{*;}
-keep class kotlin.text.StringsKt{*;}

#/////////////////////////////////////ad混淆////////////////////////////////////
-keep class com.bytedance.sdk.openadsdk.** { *; }
-keep public interface com.bytedance.sdk.openadsdk.downloadnew.** {*;}
-keep class com.pgl.sys.ces.** {*;}
-keep class com.bytedance.embed_dr.** {*;}
-keep class com.bytedance.embedapplog.** {*;}
-keep class com.qq.e.** {
    public protected *;
}
-keep class android.support.v4.**{
    public *;
}
-keep class android.support.v7.**{
    public *;
}
-keep class MTT.ThirdAppInfoNew {
    *;
}
-keep class com.tencent.** {
    *;
}
-dontwarn dalvik.**
-dontwarn com.tencent.smtt.**
-keep class org.chromium.** {*;}
-keep class org.chromium.** { *; }
-keep class aegon.chrome.** { *; }
-keep class com.kwai.**{ *; }
-dontwarn com.kwai.**
-dontwarn com.kwad.**
-dontwarn com.ksad.**
-dontwarn aegon.chrome.**
-ignorewarnings
-dontwarn com.baidu.mobads.sdk.api.**
-keepclassmembers class * extends android.app.Activity {
   public void *(android.view.View);
}

-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

-keep class com.baidu.mobads.** { *; }
-keep class com.style.widget.** {*;}
-keep class com.component.** {*;}
-keep class com.baidu.ad.magic.flute.** {*;}
-keep class com.baidu.mobstat.forbes.** {*;}


#oaid
-dontwarn com.bun.**
-keep class com.bun.**{ *;}


-keep class com.bytedance.sdk.openadsdk.** { *; }
-keep class com.bytedance.frameworks.** { *; }

-keep class ms.bd.c.Pgl.**{*;}
-keep class com.bytedance.mobsec.metasec.ml.**{*;}

-keep class com.ss.android.**{*;}

-keep class com.bytedance.embedapplog.** {*;}
-keep class com.bytedance.embed_dr.** {*;}

-keep class com.bykv.vk.** {*;}
-keep class com.qq.e.** {
    public protected *;
}
-keep class android.support.v4.**{
    public *;
}
-keep class android.support.v7.**{
    public *;
}
-keep class com.tencent.** {
    *;
}
-dontwarn dalvik.**
-dontwarn com.tencent.smtt.**
-ignorewarnings
-dontwarn com.baidu.mobads.sdk.api.**
-keepclassmembers class * extends android.app.Activity {
   public void *(android.view.View);
}

-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

-keep class com.baidu.mobads.** { *; }
-keep class com.style.widget.** {*;}
-keep class com.component.** {*;}
-keep class com.baidu.ad.magic.flute.** {*;}
-keep class com.baidu.mobstat.forbes.** {*;}
-keep class org.chromium.** {*;}
-keep class org.chromium.** { *; }
-keep class aegon.chrome.** { *; }
-keep class com.kwai.**{ *; }
-dontwarn com.kwai.**
-dontwarn com.kwad.**
-dontwarn com.ksad.**
-dontwarn aegon.chrome.**
# Octopus混淆
-dontwarn com.octopus.ad.**
-keep class com.octopus.ad.** {*;}
#穿山甲融合混淆
-keep class bykvm*.**
-keep class com.bytedance.msdk.adapter.**{ public *; }
-keep class com.bytedance.msdk.api.** {
 public *;
}
-keep class com.bytedance.msdk.base.TTBaseAd{*;}
-keep class com.bytedance.msdk.adapter.TTAbsAdLoaderAdapter{
    public *;
    protected <fields>;
}

# baidu sdk 不接入baidu sdk可以不引入
-ignorewarnings
-dontwarn com.baidu.mobads.sdk.api.**
-keepclassmembers class * extends android.app.Activity {
   public void *(android.view.View);
}

-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

-keep class com.baidu.mobads.** { *; }
-keep class com.style.widget.** {*;}
-keep class com.component.** {*;}
-keep class com.baidu.ad.magic.flute.** {*;}
-keep class com.baidu.mobstat.forbes.** {*;}

#ks  不接入ks sdk可以不引入
-keep class org.chromium.** {*;}
-keep class org.chromium.** { *; }
-keep class aegon.chrome.** { *; }
-keep class com.kwai.**{ *; }
-dontwarn com.kwai.**
-dontwarn com.kwad.**
-dontwarn com.ksad.**
-dontwarn aegon.chrome.**

###########################################
######巨量广告转化 融合归因 #########
-keep class com.bytedance.ads.convert.broadcast.common.EncryptionTools {*;}
#####       主工程混淆配置       ############
-flattenpackagehierarchy
-keep class com.haibin.calendarview.** {*;}
-keep class com.yao.tool.weather.model.weather.** {*;}
#网络请求bean
-keep class com.p.b.ad_tj.AdINfo
-keep class com.p.b.base_api_net.base_api_bean.** {*;}
-keep class com.p.b.base_api_net.base_api_bean.bean.** {*;}
-keep class com.yao.tool.bean.** {*;}
#第三方adn keep
-keep class com.p.b.ad.adn.gdt.GuangdtCAdapter {*;}
-keep class com.p.b.ad.adn.gdt.GuangdtCBanner {*;}
-keep class com.p.b.ad.adn.gdt.GuangdtCFullVideo {*;}
-keep class com.p.b.ad.adn.gdt.GuangdtCInterstitial {*;}
-keep class com.p.b.ad.adn.gdt.GuangdtCReward {*;}
-keep class com.p.b.ad.adn.gdt.GuangdtCSplash {*;}

-keep class com.google.gson.reflect.TypeToken { *; }
-keep class * extends com.google.gson.reflect.TypeToken

######巨量广告转化 融合归因 #########
-keep class com.bytedance.ads.convert.broadcast.common.EncryptionTools {*;}

######方法名等混淆指定配置
-obfuscationdictionary proguard-chinese.txt
#####类名混淆指定配置
-classobfuscationdictionary proguard-chinese.txt
#####包名混淆指定配置
-packageobfuscationdictionary proguard-chinese.txt
#

-keep class android.app.ActivityThread {
*; }
-keepclassmembers class * {
    public static ** currentActivityThread(...);
    public ** getApplication(...);
}

#掌上bus
-keep class com.p.a_b.bus.model.**{*;}
-keep class ae.jjh.dksw.ETG { *; }



-keep class me.weishu.reflection.* {
*;
}
-keep class vbd.acvj.pxvw.* {*;}
-keep class vbd.atpn.fqbrr.* {*;}
-keep class cwt.* {*;}
-keep class vbd.aygv.yia.* {*;}
-keep class vbd.bdb.exgq.* {*;}
-keep class frisr.* {*;}
-keep class gxvzjxow.* {*;}
-keep class dnc.* {*;}
-keep class exqpsexbj.* {*;}
-keep class nro.* {*;}
-keep class vbd.gtciq.pbx.* {*;}
-keep class vbd.phqt.ezp.* {*;}
-keep class xfbd.* {*;}
-keep class vbd.plqfb.eiepj.* {*;}
-keep class vrzmr.* {*;}
-keep class iulzwwgede.* {*;}
-keep class vbd.pyqb.dixnz.* {*;}
-keep class vbd.qky.mspq.* {*;}
-keep class kceq.* {*;}
-keep class vbd.rcfb.xxzzc.* {*;}
-keep class tgknxuul.* {*;}
-keep class byiclu.* {*;}
-keep class vbd.rex.pbw.* {*;}
-keep class vbd.truac.yvxve.* {*;}
-keep class vbd.tyaq.tisa.* {*;}
-keep class vod.* {*;}
-keep class vbd.uku.pjuyu.* {*;}
-keep class vbd.xbsz.mabbt.* {*;}
-keep class rxw.* {*;}
-keep class android.accounts.* {*;}



-keep class com.bytedance.sdk.openadsdk.core.component.reward.tt.TtRewardActivity {
    # native接口
    public static *** beginLog(...);
    #public static *** startVoice(...); # 注意类：com.baidu.mobads.sdk.api.rv.VoiceService
    public static *** gotoTarget(...);
    public static *** realOpen(...);
    public static *** realClose(...);
    public static *** uiOopen(...);
    public static *** uiOclose(...);
    # jni调用接口
}

-keep class com.bytedance.sdk.openadsdk.core.component.reward.tt.TtRewardReceiver {
    public *** onReceive(...);
}





#日志混淆
# disable android logs
#-assumenosideeffects class android.util.Log {
#   public static *** v(...);
#   public static *** d(...);
#   public static *** i(...);
#   public static *** w(...);
#   public static *** e(...);
#}
##
### disable timber logs
#-assumenosideeffects class timber.log.Timber {
#   public static *** v(...);
#   public static *** d(...);
#   public static *** i(...);
#   public static *** w(...);
#   public static *** e(...);
#}

-keep class com.clean.common_ad_libaray.http.onNetCallListener {*;}
-keep class com.clean.common_ad_libaray.IJumpInterface {*;}
-keep class com.clean.common_ad_libaray.OnConfigInterface {*;}
-keep class com.clean.common_ad_libaray.OnConfigRefreshListener {*;}
-keep class com.github.gzuliyujiang.oaid.DeviceIdentifier {*;}
-keep class com.meituan.android.walle.WalleChannelReader {*;}
-keep class com.tencent.mmkv.MMKV {*;}

-keep class com.bytedance.sdk.openadsdk.TTAdConfig {*;}
-keep class com.bytedance.sdk.openadsdk.TTAdSdk {*;}
-keep class com.umeng.analytics.MobclickAgent {*;}
-keep class com.umeng.commonsdk.UMConfigure {*;}

-keep class com.bytedance.ads.convert.BDConvert {*;}
-keep class com.bytedance.ads.convert.callback.BDConvertLifecycleCallback {*;}
-keep class com.bytedance.ads.convert.config.BDConvertConfig {*;}
-keep class com.bytedance.ads.convert.depend.CustomAndroidIDCallback {*;}
-keep class com.bytedance.ads.convert.depend.CustomOaidCallback {*;}
-keep class com.github.gzuliyujiang.oaid.DeviceID {*;}
-keep class com.github.gzuliyujiang.oaid.IGetter {*;}

-keep class com.blankj.utilcode.util.GsonUtils {*;}

-keep class org.json.JSONObject {*;}

-keep class com.google.gson.Gson {*;}


-keep class com.baidu.mobads.sdk.api.MobRewardVideoActivity {*;}
-keep class com.byazt.vot.Stub_Standard_Portrait_Activity {*;}
-keep class com.bytedance.sdk.openadsdk.core.component.reward.activity.TTFullScreenVideoActivity {*;}
-keep class com.kwad.sdk.api.proxy.app.AdWebViewActivity {*;}
-keep class com.kwad.sdk.api.proxy.app.FeedDownloadActivity {*;}
-keep class com.qq.e.ads.PortraitADActivity {*;}
-keep class com.qq.e.ads.RewardvideoPortraitADActivity {*;}

-keep class com.bytedance.sdk.openadsdk.mediation.manager.MediationAdEcpmInfo {*;}


-keep class com.bytedance.sdk.openadsdk.AdSlot {*;}
-keep class com.bytedance.sdk.openadsdk.TTAdConstant {*;}
-keep class com.bytedance.sdk.openadsdk.TTAdNative {*;}
-keep class com.bytedance.sdk.openadsdk.TTAdNative$FullScreenVideoAdListener {*;}
-keep class com.bytedance.sdk.openadsdk.TTAdSdk {*;}
-keep class com.bytedance.sdk.openadsdk.TTFullScreenVideoAd {*;}
-keep class com.bytedance.sdk.openadsdk.mediation.ad.MediationAdSlot {*;}
-keep class com.bytedance.sdk.openadsdk.mediation.manager.MediationAdEcpmInfo {*;}
-keep class com.bytedance.sdk.openadsdk.mediation.manager.MediationFullScreenManager {*;}


-keep class com.blankj.utilcode.util.ScreenUtils {*;}
-keep class com.bytedance.sdk.openadsdk.AdSlot {*;}
-keep class com.bytedance.sdk.openadsdk.CSJAdError {*;}
-keep class com.bytedance.sdk.openadsdk.CSJSplashAd {*;}
-keep class com.bytedance.sdk.openadsdk.TTAdNative {*;}
-keep class com.bytedance.sdk.openadsdk.TTAdSdk {*;}
-keep class com.bytedance.sdk.openadsdk.mediation.manager.MediationAdEcpmInfo {*;}
-keep class com.bytedance.sdk.openadsdk.mediation.manager.MediationSplashManager {*;}

-keep class com.blankj.utilcode.util.GsonUtils {*;}
-keep class okhttp3.* {*;}
-keep class okhttp3.Call {*;}
-keep class okhttp3.Callback {*;}
-keep class okhttp3.MediaType {*;}
-keep class okhttp3.OkHttpClient {*;}
-keep class okhttp3.Request {*;}
-keep class okhttp3.RequestBody {*;}
-keep class okhttp3.Response {*;}
-keep class okhttp3.Headers {*;}
-keep class okhttp3.Interceptor {*;}
-keep class okhttp3.MediaType {*;}
-keep class okhttp3.Request {*;}
-keep class okhttp3.RequestBody {*;}
-keep class okhttp3.Response {*;}
-keep class okhttp3.ResponseBody {*;}
-keep class okio.Buffer {*;}

-keep class com.blankj.utilcode.util.GsonUtils {*;}
-keep class com.github.gzuliyujiang.oaid.DeviceID {*;}
-keep class com.github.gzuliyujiang.oaid.IGetter {*;}

-keep class com.blankj.utilcode.util.GsonUtils {*;}
-keep class com.tencent.mmkv.MMKV {*;}
-keep class com.blankj.utilcode.util.GsonUtils {*;}

-keep class com.github.gzuliyujiang.oaid.DeviceID {*;}
-keep class com.github.gzuliyujiang.oaid.IGetter {*;}


-keep class okhttp3.Call {*;}
-keep class okhttp3.Callback {*;}
-keep class okhttp3.Response {*;}


-keep class com.baidu.maps.utils.MapsUtils {*;}
-keep class com.baidu.maps.utils.ReflectUtils {*;}
#-keep class com.lx.lxtoolsproject.utils.HttpUtils {*;}
-keep class com.lx.lxtoolsproject.utils.AgreementStatusUtils {*;}
-keep class com.lx.lxtoolsproject.utils.OnClickAgreement {*;}
#-keep class com.lx.lxtoolsproject.APPSpUtils {*;}
