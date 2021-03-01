package com.arsvechkarev.views

import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.View.GONE
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.LinearLayout
import androidx.annotation.LayoutRes
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.arsvechkarev.core.R

operator fun View.contains(event: MotionEvent): Boolean {
  val x = event.x
  val y = event.y
  return x >= left + translationX
      && y >= top + translationY
      && x <= right + translationX
      && y <= bottom + translationY
}

fun View.visible() {
  visibility = VISIBLE
}

fun View.invisible() {
  visibility = INVISIBLE
}

fun View.gone() {
  visibility = GONE
}

fun View.layoutGravity(gravity: Int) {
  when (val params = layoutParams) {
    is FrameLayout.LayoutParams -> params.gravity = gravity
    is LinearLayout.LayoutParams -> params.gravity = gravity
    is CoordinatorLayout.LayoutParams -> params.gravity = gravity
    else -> throw IllegalStateException(
      "Unable to set gravity to " +
          "layout params ${params.javaClass.name}"
    )
  }
}

fun DrawerLayout.setupToggle(activity: AppCompatActivity, toolbar: Toolbar) {
  val toggle = ActionBarDrawerToggle(
    activity, this, toolbar,
    R.string.navigation_drawer_open, R.string.navigation_drawer_close
  )
  this.addDrawerListener(toggle)
  toggle.syncState()
}

fun DrawerLayout.close() {
  closeDrawer(GravityCompat.START)
}

fun DrawerLayout.isOpened(): Boolean {
  return isDrawerOpen(GravityCompat.START)
}

fun ViewGroup.inflate(@LayoutRes layoutId: Int): View {
  return LayoutInflater.from(this.context).inflate(layoutId, this, false)
}

fun EditText.string(): String = text.toString()

fun EditText.onTextChanged(block: (String) -> Unit) {
  this.addTextChangedListener(object : TextWatcher {
    override fun afterTextChanged(s: Editable?) {
      block(s.toString())
    }
    
    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
    
    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
  })
}