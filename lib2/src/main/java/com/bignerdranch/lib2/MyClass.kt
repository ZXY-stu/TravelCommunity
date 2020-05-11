package com.bignerdranch.lib2

 class MyClass(var i: Int) :Thread(){


     override fun run() {
         super.run()
         while(true){
             try {
                 Thread.sleep(1000)
             }catch (e:Exception){
                 e.printStackTrace()
             }
             i++
             println("11222221$i" )
         }
     }
}
fun main(){

    val map = HashMap<String,Any>()
    val dataList  = listOf("zxv","zxcasd","asdasd")
     map.put("array",dataList)
      map.put("name","zxy")
    map.put("userId",1)

    println(map["array"] as List<String>)
    print(map["name"] )
    print(map["userId"])

}