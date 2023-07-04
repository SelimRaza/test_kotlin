package com.example.mbm.common

import timber.log.Timber

internal class CustomDebugTree : Timber.DebugTree() {
    override fun createStackElementTag(element: StackTraceElement): String {
        return "LOG: (${element.fileName}:${element.lineNumber})#${element.methodName}"
        }
}