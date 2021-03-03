package com.arsvechkarev.core.extensions

import android.app.Activity
import android.content.Context
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment

fun showKeyboard(activity: Activity) {
  val inputMethodManager =
    activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
  inputMethodManager!!.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0)
}

fun Fragment.showKeyboard() {
  showKeyboard(activity!!)
}

fun Fragment.hideKeyboard() {
  val inputMethodManager =
    activity!!.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
  inputMethodManager!!.hideSoftInputFromWindow(activity!!.window.peekDecorView().windowToken, 0)
}

fun Fragment.showToast(@StringRes resId: Int) {
  Toast.makeText(context, getString(resId), Toast.LENGTH_LONG).show()
}

fun Fragment.showToast(message: String) {
  Toast.makeText(context, message, Toast.LENGTH_LONG).show()
}

fun Fragment.popBackStack() {
  requireActivity().supportFragmentManager.popBackStack()
}