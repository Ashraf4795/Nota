package com.nota.utils



fun <T> MutableList<T>.addIfUnique(newList: List<T>) {
    newList.forEach {
        if (!this.contains(it)) {
            this.add(it)
        }
    }
}