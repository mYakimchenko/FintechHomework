package com.mihanjk.fintechhomework

import android.content.Context
import android.support.v4.app.Fragment
import android.view.inputmethod.InputMethodManager
import android.widget.Toast

fun Fragment.showNotEmptyMessage() {
    Toast.makeText(activity, R.string.must_be_not_empty, Toast.LENGTH_SHORT).show()
}

fun Fragment.showKeyboard(isShow: Boolean) {
    val inputMethodService = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodService.hideSoftInputFromWindow(activity.currentFocus.windowToken,
            if (isShow) InputMethodManager.SHOW_FORCED else InputMethodManager.HIDE_NOT_ALWAYS)
}

