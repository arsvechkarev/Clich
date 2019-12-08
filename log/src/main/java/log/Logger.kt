package log

object Logger {
  
  private var isActive = false
  
  fun activate() {
    isActive = true
    MyTimber.plant(MyTimber.DebugTree())
  }
  
  fun debug(lazyMessage: () -> String) {
    if (isActive) {
      MyTimber.d(lazyMessage.invoke())
    }
  }
  
  fun error(lazyMessage: () -> String, throwable: Throwable) {
    if (isActive) {
      MyTimber.d(throwable, lazyMessage.invoke())
    }
  }
  
}