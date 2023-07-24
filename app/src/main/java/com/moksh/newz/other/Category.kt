package com.moksh.newz.other

import com.moksh.newz.R

interface Category {
    val category: String
    val Icon: Int
}

data class General(
    override val category: String = "general",
    override val Icon: Int = R.drawable.ic_general
) : Category

data class Business(
    override val category: String = "business",
    override val Icon: Int = R.drawable.ic_business
) : Category

data class Technology(
    override val category: String = "general",
    override val Icon: Int = R.drawable.ic_tech
) : Category

fun getTitleResource(activeCategory: Category): Int = when (activeCategory) {
    is General -> R.string.title_general
    is Business -> R.string.title_business
    is Technology -> R.string.title_technology
    else -> throw IllegalAccessException("Page number is invalid")
}