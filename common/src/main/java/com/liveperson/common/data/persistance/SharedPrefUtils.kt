package com.liveperson.common.data.persistance

import android.content.Context

private const val SHARED_PREFERENCES_FILE = "auth.preferences"
fun Context.createAuthPreferences() = getSharedPreferences(SHARED_PREFERENCES_FILE, Context.MODE_PRIVATE)