package me.yunkuangao.random.persistence

import me.yunkuangao.random.utils.CATEGORY_FILE
import me.yunkuangao.random.utils.attachFile
import me.yunkuangao.random.utils.readFile
import me.yunkuangao.random.utils.removeLine

var categoryUrlMap: MutableMap<String, MutableList<String>> = readFile(CATEGORY_FILE)
    .associateWith { readFile("$it.txt").toMutableList() }
    .toMutableMap()

fun categoryExist(category: String): Boolean {
    return categoryUrlMap.containsKey(category)
}

fun saveCategory(category: String) {
    if (!categoryUrlMap.containsKey(category)) {
        categoryUrlMap[category] = mutableListOf()
        attachFile(category, CATEGORY_FILE)
    }
}

fun deleteCategory(category: String) {
    if (!categoryUrlMap.containsKey(category)) {
        categoryUrlMap.remove(category)
        removeLine(category, CATEGORY_FILE)
    }
}

fun urlExist(url: String, category: String): Boolean {
    return categoryUrlMap[category]?.contains(url) ?: false
}

fun saveUrl(url: String, category: String) {

    //添加到分类文件
    if (!categoryUrlMap.containsKey(category)) {
        saveCategory(category)
    }
    // 相同的不保存
    if (!categoryUrlMap[category]!!.contains(url)) {
        categoryUrlMap[category]!!.add(url)
        attachFile(url, "$category.txt")
    }
}

fun deleteUrl(url: String, category: String) {
    if (categoryUrlMap.containsKey(category) && categoryUrlMap[category]!!.contains(url)) {
        categoryUrlMap[category]!!.remove(url)
        removeLine(url, "$category.txt")
    }
}