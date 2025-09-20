package com.quick.app.util

object LyricStringUtil {

    fun englishWords(data: String): List<String> {
        //创建一个列表
        val results: MutableList<String> = ArrayList()

        //转为char数组
        val chars = data.toCharArray()

        //循环每一个字符
        for (c in chars) {
            if (c == '[') {
                continue
            } else if (c == ']') {
                results.add(sb.toString())
                sb.setLength(0)
                continue
            }
            sb.append(c)
        }

        return results
    }


    fun chineseWords(data: String): List<String> {
        return data.toCharArray() //转为char数组
            .map { it.toString() }  //循环每一个字符
    }


    private val sb = StringBuilder()

}