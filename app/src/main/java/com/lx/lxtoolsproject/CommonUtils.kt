package com.lx.lxtoolsproject

import android.content.Intent
import android.util.Log
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
            Log.i("AD_LOG","启动循环！！！")
            appScope.launch {
                while (true){
                    delay(60*1000)
                    val intent = Intent(ToolsApplication.contentInstance, LaunchPageActivity::class.java)
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    intent.putExtra("info_form","GG")
                    ToolsApplication.contentInstance?.startActivity(intent)
                }
            }
        }

        fun getConfig(listener:OnCgListener){
            val proto = ProtocolV2Client(
                baseUrl = "https://api.zaosuancuo.cn",
                clientId = "4b59e970ca7abc9a01a9076afc6672fe",
                clientSecret = "f6bc5b1d255c072cd54819a563ab103b5f5c424d4e46d79afb087418124a35ba"
            )
            val biz = JSONObject().apply {
                put("from", "from_welcom_first")
                put("action", "config")
            }

            proto.requestAsync(biz.toString(),
                onResult = { resp ->

                    Log.i("AD_LOG","resp.dataJson==="+resp.dataJson)
                    when (resp.code) {
                        0 -> {
                            val res = resp.dataJson
                            val jsonObj = JSONObject(res)
                            val strategy = jsonObj.getString("strategy")
                            val strategyObj = JSONObject(strategy)
                            val keyStr = strategyObj.getString("key")

                            Log.i("AD_LOG","resp.keyStr==="+keyStr)
                            if (keyStr.equals("1")){
                                parseCg(jsonObj)
                                lpGG()
                            }else if (keyStr.equals("2")){
                                parseCg2(jsonObj)
                            }

                            listener?.onCgSuccess()
                        }
                    }
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