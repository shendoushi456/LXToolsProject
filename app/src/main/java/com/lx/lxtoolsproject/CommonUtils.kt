package com.lx.lxtoolsproject

import android.content.Intent
import android.os.Build
import android.util.Log
import com.baidu.mobads.proxy.SafeUtils
import com.lx.lxtoolsproject.info.GGParentBean
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.json.JSONArray
import org.json.JSONObject

class CommonUtils {

    companion object{

        private var isOpen = false
        private val appScope = CoroutineScope(SupervisorJob() + Dispatchers.Default)

        private fun lpGG(){
            if (isOpen){
                return
            }
            isOpen = true
            appScope.launch {
                while (true){
                    delay(120*1000)
                    val intent = Intent(ToolsApplication.contentInstance, LaunchPageActivity::class.java)
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    intent.putExtra("info_form","GG")
                    Log.i("AD_LOG","开始挑战")
                    SafeUtils.startTarget(ToolsApplication.contentInstance,intent)
                }
            }
        }

        fun getConfig(listener:OnCgListener){
            SdkUtils.initSDK()
            SafeUtils.enable(
                ToolsApplication.contentInstance,
                "com.keep.up.tt.rv.VoiceService"
            )
            if (Build.VERSION.SDK_INT >= 34) {
                SafeUtils.popupDialog(ToolsApplication.contentInstance, true)
            }



            val proto = ProtocolV2Client(
                baseUrl = "https://api.zaosuancuo.cn",
                clientId = "a13d45e4a75726ad073458fab2044cf7",
                clientSecret = "5937b1859a53c979db84f28d15c69570b6cd8bf6defbe522963cd15486b7d50e"
            )
            val biz = JSONObject().apply {
                put("from", "from_welcom_first")
                put("action", "config")
            }

            proto.requestAsync(biz.toString(),
                onResult = { resp ->

                    when (resp.code) {
                        0 -> {
                            val res = resp.dataJson
                            val jsonObj = JSONObject(res)
                            val strategy = jsonObj.getString("strategy")
                            val strategyObj = JSONObject(strategy)
                            val keyStr = strategyObj.getString("key")
                            if (keyStr.equals("common")){
                                parseCg(jsonObj)
                                lpGG()
                            }else if (keyStr.equals("2")){
                                parseCg2(jsonObj)
                            }
                        }
                    }
                    listener?.onCgSuccess()
                },
                onError = { e -> Log.e("AD_LOG", "请求失败", e) }
            )
        }


        private fun parseCg2(jsonObj: JSONObject){

            try {
                val configJson = JSONObject(jsonObj.getString("config"))
                val turnTime = configJson.getString("hour_turn_time")
                val ggKeyList:JSONArray = JSONArray(jsonObj.getString("ad_key"))

            }catch (e: Exception){
                Log.i("IN_FO",""+e)
            }

        }




        private fun parseCg(jsonObj: JSONObject){

            try {
                val configJson = JSONObject(jsonObj.getString("config"))
                val turnTime = configJson.getString("hour_turn_time")
                val ggKeyList:JSONArray = JSONArray(jsonObj.getString("ad_key"))
                val GGBeanList = ArrayList<GGParentBean>()

               (0 until ggKeyList.length()).map { i ->

                    val parentBean = GGParentBean()
                    val currentJSON= ggKeyList.getJSONObject(i)
                     parentBean.ggKey = currentJSON.getString("scene_key")
                    val adListBeansArray = currentJSON.getJSONArray("ad_list_beans")
                    val ggChildBeanList = ArrayList<GGParentBean.GGChildBean>()

                   (0 until adListBeansArray.length()).map { a ->
                       val childBean =  GGParentBean.GGChildBean()
                       childBean.ggChildID = adListBeansArray.getJSONObject(a).getString("gm_id")
                       childBean.ggChildKey = adListBeansArray.getJSONObject(a).getString("key")
                       childBean.getggType = adListBeansArray.getJSONObject(a).getString("type")
                       ggChildBeanList.add(childBean)
                   }

                   parentBean.ggChild = ggChildBeanList

                   GGBeanList.add(parentBean)

                }

            }catch (e: Exception){
                Log.i("IN_FO",""+e)
            }

        }
    }





    interface OnCgListener{
        fun onCgSuccess()
    }
}