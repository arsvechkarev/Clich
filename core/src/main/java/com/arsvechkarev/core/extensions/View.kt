package com.arsvechkarev.core.extensions

import android.text.Editable
import android.text.TextWatcher
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.ScrollView
import android.widget.TextView
import androidx.annotation.AttrRes
import androidx.annotation.LayoutRes
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.text.HtmlCompat
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.arsvechkarev.core.R

fun View.visible() {
  visibility = VISIBLE
}

fun View.invisible() {
  visibility = INVISIBLE
}

fun View.gone() {
  visibility = GONE
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

fun DrawerLayout.isOpen(): Boolean {
  return isDrawerOpen(GravityCompat.START)
}

fun ScrollView.scrollToTop() {
  this.smoothScrollTo(0, 0)
}

fun ViewGroup.inflate(@LayoutRes layoutId: Int): View {
  return LayoutInflater.from(this.context).inflate(layoutId, this, false)
}

fun EditText.onSearchClick(block: () -> Unit) {
  this.setOnEditorActionListener ed@{ _, actionId, _ ->
    if (actionId == EditorInfo.IME_ACTION_SEARCH) {
      block()
      return@ed true
    }
    return@ed false
  }
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

fun TextView.showHtml(text: String) {
  this.text = HtmlCompat.fromHtml(text, HtmlCompat.FROM_HTML_MODE_LEGACY)
}

fun TextView.setAttrsTextColor(@AttrRes attrRes: Int) {
  val theme = context.theme
  val typedValue = TypedValue()
  theme.resolveAttribute(attrRes, typedValue, true)
  setTextColor(typedValue.data)
}