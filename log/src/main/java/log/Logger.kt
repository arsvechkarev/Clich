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
  
  fun error(throwable: Throwable, lazyMessage: () -> String) {
    if (isActive) {
      MyTimber.d(throwable, lazyMessage.invoke())
    }
  }
}