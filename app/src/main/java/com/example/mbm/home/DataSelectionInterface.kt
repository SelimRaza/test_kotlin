package com.example.mbm.home

interface DataSelectionInterface {
    fun onSelect(obj: ResponseHome.ResponseItem)
    fun onRemove(obj: ResponseHome.ResponseItem)
}