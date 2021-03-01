package com.arsvechkarev.views

import android.content.Context
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.graphics.drawable.AnimatedVectorDrawable
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat
import com.arsvechkarev.core.extensions.startIfNotRunning
import com.arsvechkarev.core.extensions.stopIfRunning

class MaterialProgressBar @JvmOverloads constructor(
  context: Context,
  attrs: AttributeSet? = null,
  defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {
  
  private val drawable get() = background as AnimatedVectorDrawable
  
  init {
    val attributes = context.obtainStyledAttributes(attrs, R.styleable.MaterialProgressBar, 0, 0)
    val color = attributes.getColor(R.styleable.MaterialProgressBar_color, Color.BLACK)
    background = ContextCompat.getDrawable(context, R.drawable.progress_anim_normal)!!.apply {
      colorFilter = PorterDuffColorFilter(color, PorterDuff.Mode.SRC_ATOP)
    }
    attributes.recycle()
  }
  
  override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
    drawable.setBounds(0, 0, w, h)
  }
  
  override fun onVisibilityChanged(changedView: View, visibility: Int) {
    if (visibility == VISIBLE) {
      drawable.startIfNotRunning()
    } else {
      drawable.stopIfRunning()
    }
  }
  
  override fun onAttachedToWindow() {
    super.onAttachedToWindow()
    drawable.startIfNotRunning()
  }
  
  override fun onDetachedFromWindow() {
    super.onDetachedFromWindow()
    drawable.stopIfRunning()
  }
}